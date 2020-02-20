package com.zhiku.service;


import com.zhiku.entity.mysql.File;
import com.zhiku.entity.mysql.FileKeys;
import com.zhiku.entity.mysql.Fileop;
import com.zhiku.entity.User;
import com.zhiku.exception.FileNotExistException;
import com.zhiku.mapper.FileKeysMapper;
import com.zhiku.mapper.FileMapper;
import com.zhiku.mapper.FileopMapper;
import com.zhiku.mapper.UserMapper;
import com.zhiku.util.FileStatus;
import com.zhiku.util.Office2PDF;
import com.zhiku.view.FileDownloadRecordView;
import com.zhiku.view.FileView;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileService{

    //文件的上传路径/var/zhiku/upload
    @Value("${file_path.upload_path}")
    private String uploadPath;
    //    private String uploadPath = "/var"+ java.io.File.separator + "zhiku" + java.io.File.separator + "upload";
//    private String uploadPath = "E:\\14_zhiku\\upload";
    @Value("${basePath_preview}")
    private String previewPath;
    @Value("${max_size}")
    private int max_size;
    @Value("${type_reg}")
    private String type_reg;
    //默认的用户预览页数
    public static int PAGE_SIZE = 10;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileopMapper fileopMapper;
    @Autowired
    private FileKeysMapper fileKeysMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 检查上传的文件是否符合要求
     * @param multipartFile 文件流对象
     * @return FileStatus
     */
    public FileStatus checkFile(MultipartFile multipartFile){
        //检查文件大小
        if(multipartFile.getSize()>max_size){
            return FileStatus.TOO_LARGE;
        }
        //检查文件格式
        String filename = "";
        filename = multipartFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.')+1);
        if(!suffix.matches(type_reg)){
            return FileStatus.TYPE_ERROR;
        }
        //检查文件是否重复
        try{
            File file = fileMapper.selectBySha(DigestUtils.sha256Hex(multipartFile.getInputStream()));
            if(file != null){
                return FileStatus.DUPLICATE;
            }
        }catch (IOException ioe){
            return FileStatus.FILE_ERROR;
        }
        return FileStatus.NORMAL;
    }

    /**
     * 事务保存文件+插入数据到数据库
     * @param multipartFile 文件流对象
     * @param file 数据库文件实例
     * @param user 用户
     * @param fileKeys 关键字
     * @exception IOException io异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void storeFile(MultipartFile multipartFile, File file, User user, FileKeys fileKeys) throws IOException {
        //产生新的文件名和路径
        String path = makePath(multipartFile.getOriginalFilename(),uploadPath);
        String newFileName = makeFileName(multipartFile.getOriginalFilename());
        String realPath = path + java.io.File.separator + newFileName;
        System.out.println(realPath);
        file.setFilePath(realPath);

        // 文件记录->数据库
        storeFileToDB(multipartFile,file,user);

        // 重新赋值file,保存文件的关键字
        if(fileKeys.getKey1()!=null){//数据库中要求key1不为null，用户不设置标签也可以
            file = fileMapper.selectBySha(DigestUtils.sha256Hex(multipartFile.getInputStream()));
            fileKeys.setFid(file.getFid());
            storeFileKeys(fileKeys);
        }

        //更新用户的上传次数
        user = userMapper.selectByPrimaryKey(user.getUid());
        user.setUserUploadCount(user.getUserUploadCount()+1);
        userMapper.updateByPrimaryKeySelective(user);
        storeFileToSys(multipartFile,realPath);
    }

    /**
     * 事务保存文件+插入数据到数据库
     * @param multipartFile 文件流
     * @param file 文件实例
     * @param user 用户
     * @param fileKeys 关键字
     */
    public boolean storeFileToFileSystemAndinInsertIntoDB(MultipartFile multipartFile, File file, User user, FileKeys fileKeys){
        // 调用之前的代码，里面抛出异常，异常的话这个storeFile这个老方法会回滚，同时这里捕捉异常又能打印异常信息
        boolean flag = false;
        try {
            storeFile(multipartFile, file, user, fileKeys);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 执行到这里成功了
        return flag;
    }

    /**
     * 保存文件关键字
     * @param fileKeys 关键字对象
     */
    private void storeFileKeys(FileKeys fileKeys) {
        fileKeysMapper.insertSelective(fileKeys);
    }

    /**
     * 将文件存入文件系统
     * @param multipartFile 文件流
     * @param realPath 保存的具体路径
     * @return
     */
    private boolean storeFileToSys(MultipartFile multipartFile,String realPath){
        boolean done;
        try{
            java.io.File file = new java.io.File(realPath);
            multipartFile.transferTo(file);
            done = true;
        }catch (Exception e){
            //日志记录发生故障的原因
            done = false;
        }
        return done;
    }

    /**
     * 将文件信息存入数据库
     * @param multipartFile 文件流
     * @param file 文件实例
     * @param user 用户
     * @return 是否保存成功
     */
    private boolean storeFileToDB(MultipartFile multipartFile, File file,User user) throws IOException {
        //boolean finish ;
        //try{
            file.setFileUpper(user.getUid());
            file.setFileName(multipartFile.getOriginalFilename());
            file.setFileStatus(FileStatus.UNCHECK.getCode());
            file.setFileSha(DigestUtils.sha256Hex(multipartFile.getInputStream()));
            file.setFileUploadTime(new Date());
            file.setFileType(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1));
            file.setFileScore(3.0F);
            file.setFileDownloadCount(0);
            file.setFileDesc("");
            fileMapper.insertSelective(file);
            //finish = true;
        //}catch (Exception e){
            //finish = false;
        //}
        //return finish;
        // 执行到这里都没有报错，就是完成成储存到数据库的过程
        return true;
    }

    /**
     * 产生新的文件名
     * @param filename 原文件名
     * @return
     */
    private String makeFileName(String filename){
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 产生保存路径，在savePath下生成两级目录
     * @param filename 文件名
     * @param savePath 保存目录
     * @return 新路径
     */
    private String makePath(String filename,String savePath){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf; //0--15
        int dir2 = (hashcode&0xf0)>>4; //0-15
        //构造新的保存目录
        String dir = savePath + java.io.File.separator + dir1 + java.io.File.separator + dir2; //upload\2\3 upload\3\5
        //File既可以代表文件也可以代表目录
        java.io.File file = new java.io.File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }

    /**
     * 文件下载
     * @param request 请求
     * @param response 响应
     * @param user 用户
     * @param file 文件实例
     */
    public void fileDownload(HttpServletRequest request, HttpServletResponse response, User user, File file) {
        java.io.File realFile = new java.io.File(file.getFilePath());
        //保存用户的文件操作记录
        storeFileOp(request,user.getUid(),file.getFid(),"d");
        //更新文件的下载量
        file.setFileDownloadCount(file.getFileDownloadCount()+1);
        fileMapper.updateByPrimaryKeySelective(file);
        //更新用户的文件下载量
        user.setUserDownloadCount(user.getUserDownloadCount()+1);
        userMapper.updateByPrimaryKeySelective(user);
        //输出下载文件
        try{
            response.setContentType("application/x-download");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
            //读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(file.getFilePath());
            //创建输出流，注意这里使用的是response创建的输出流
            OutputStream out = response.getOutputStream();
            //创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区当中
            while((len=in.read(buffer))>0){
                //输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //注释原先的ResponseEntity方式
//        ResponseEntity<byte[]> entity = null;
//        try{
//            byte[] body ;
//            InputStream is = new FileInputStream(realFile);
//            body = new byte[is.available()];
//            is.read(body);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType("application/x-download"));
//            headers.add("Content-Disposition", "attchement;filename=" + file.getFileName());
//            HttpStatus statusCode = HttpStatus.OK;
//            entity = new ResponseEntity<>(body, headers, statusCode);
//            return entity;
//        }catch (FileNotFoundException fnfe){
//            //日志记录
//        }catch (IOException ioe){
//            //日志记录
//        }
//        return entity;
    }

    /**
     * 依据fid找到文件
     * @param fid 文件id
     * @return 文件实例
     * @throws FileNotExistException 文件找不到异常
     */
    public File getFileByFid(int fid) throws FileNotExistException {
        File file = null;
        try{
            file = fileMapper.selectByPrimaryKey(fid);
        }catch (Exception e){
            FileNotExistException fileNotExistException = new FileNotExistException();
            throw fileNotExistException;
        }
        return file;
    }

    /**
     * 更新文件状态
     * @param file 文件
     */
    public void updateFileStatus(File file){
        fileMapper.updateByPrimaryKeySelective(file);
    }

    /**
     * 将文件的操作记录存进数据库
     *
     * @param request 请求
     * @param uid 用户id
     * @param fid 文件id
     * @param type 操作的类型
     * @return 是否存入成功
     */
    public boolean storeFileOp(HttpServletRequest request, int uid, int fid, String type){
        Fileop fileop = new Fileop();
        fileop.setFopFile(fid);
        fileop.setFopUser(uid);
        fileop.setFopDate(new Date());
        fileop.setFopIp(request.getRemoteAddr());
        fileop.setFopType(type);
        fileopMapper.insert(fileop);
        return true;
    }

    /**
     * 获得文件列表
     * @param keyWord 关键字
     * @param file 文件，主要是fileCourse属性，即所属课程
     * @param page 第几页
     * @param order 是否按照时间降序
     * @param status 获取什么状态的文件（待审核，正常等）
     * @return
     */
    public List<FileView> getFileList(String keyWord, File file, int page, boolean order,String status) {
        int startLine = (page-1)*PAGE_SIZE;
        return fileMapper.selectFiles(keyWord,file,startLine,PAGE_SIZE,order,status,FileStatus.DELETE.getCode());
    }

    /**
     * 处理文件的预览操作
     * 管理员可预览全部文件，用户只能浏览默认的10页
     * @param response 响应
     * @param file 文件
     * @param isAdmin 是否管理员
     * @return 操作结果
     * @throws ConnectException openoffice的链接异常
     */
    public String filePreview(HttpServletResponse response, File file,boolean isAdmin) throws ConnectException {
        String rmsg = "";
        //检查文件是否是openoffice支持的可转化类型
        if(Office2PDF.isConvert(file.getFilePath())){
            String outputFilePath = file.getFilePath();
            //如果可以转化并且不是pdf，则进行转化
            if(!"pdf".equals(Office2PDF.getPostfix(file.getFilePath()))){
                //获得转化后文件的全路径
                outputFilePath = Office2PDF.getOutputFilePath(file.getFilePath());
                if(!Office2PDF.openOfficeToPDF(file.getFilePath(), outputFilePath)){
                    rmsg = "发生了一个未知的错误code:1，稍后重试";
                }
            }
            //将文件输出到前端
            try{
                response.setContentType("application/pdf;charset=utf-8");
                response.setHeader("pragme", "no-cache");
                OutputStream outer = response.getOutputStream();
                //预览文件输出
                Office2PDF.writeOut(new java.io.File(outputFilePath), outer,isAdmin);
                outer.close();
                return null;
            }catch(Exception e){
                e.printStackTrace();
                rmsg = "发生了一个未知错误code:2，请重试";
            }
        }else{
            rmsg = "暂不支持本格式的预览！";
        }
        return  rmsg;
    }

    /**
     * 获得下载记录
     * @param user 用户
     * @param page 第几页
     * @return 文件列表
     */
    public List<FileDownloadRecordView> getFileDownloadRecords(User user, int page){
        int startLine = (page -1)*PAGE_SIZE;
        return fileopMapper.selectFileDownloadRecords(user,startLine,PAGE_SIZE);
    }

    /**
     * 获得上传记录
     * @param user 用户
     * @param page 第几页
     * @return 文件列表
     */
    public List<FileView> getFileUploadRecords(User user, int page){
        int startLine = (page -1)*PAGE_SIZE;
        return fileMapper.selectFileUploadRecords(user,startLine,PAGE_SIZE);
    }

}
