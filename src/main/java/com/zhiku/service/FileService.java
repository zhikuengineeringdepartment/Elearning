package com.zhiku.service;

import com.zhiku.entity.File;
import com.zhiku.entity.Fileop;
import com.zhiku.entity.User;
import com.zhiku.exception.FileNotExistException;
import com.zhiku.mapper.FileMapper;
import com.zhiku.mapper.FileopMapper;
import com.zhiku.util.FileStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService{

    @Value("${basePath_upload}")
    private String uploadPath;
    @Value("${max_size}")
    private int max_size;
    @Value("${type_reg}")
    private String type_reg;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileopMapper fileopMapper;

    /**
     * 检查上传的文件是否符合要求
     * @param multipartFile
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
     * 保存文件
     * @param multipartFile
     * @param file
     * @return
     */
    public boolean storeFile(MultipartFile multipartFile, File file){
        boolean done ;
        //产生新的文件名和路径
        String path = makePath(multipartFile.getOriginalFilename(),uploadPath);
        String newFileName = makeFileName(multipartFile.getOriginalFilename());
        String realPath = path + java.io.File.separator + newFileName;
        if(storeFileToSys(multipartFile,realPath)){
            file.setFilePath(realPath);
            storeFileToDB(multipartFile,file);
            done = true;
        }else{
            done = false;
        }

        return done;
    }

    /**
     * 将文件存入文件系统
     * @param multipartFile
     * @param realPath
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
     * @param multipartFile
     * @param file
     * @return
     */
    private boolean storeFileToDB(MultipartFile multipartFile, File file){
        boolean finish ;
        try{
            file.setFileName(multipartFile.getOriginalFilename());
            file.setFileStatus("n");
            file.setFileSha(DigestUtils.sha256Hex(multipartFile.getInputStream()));
            file.setFileUploadTime(new Date());
            file.setFileType(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1));
            file.setFileScore(3.0F);
            file.setFileDownloadCount(0);
            System.out.println(file);
            fileMapper.insertSelective(file);
            finish = true;
        }catch (Exception e){
            finish = false;
        }
        return finish;
    }

    /**
     * 产生新的文件名
     * @param filename
     * @return
     */
    private String makeFileName(String filename){
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 产生保存路径
     * @param filename
     * @param savePath
     * @return
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

    public ResponseEntity<byte[]> fileDownload(User user, File file) {
        java.io.File realFile = new java.io.File(file.getFilePath());
        storeFileOp(user.getUid(),file.getFid(),"d");
        ResponseEntity<byte[]> entity = null;
        try{
            byte[] body ;
            InputStream is = new FileInputStream(realFile);
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + file.getFileName());
            HttpStatus statusCode = HttpStatus.OK;
            entity = new ResponseEntity<>(body, headers, statusCode);
            return entity;
        }catch (FileNotFoundException fnfe){
            //日志记录
        }catch (IOException ioe){
            //日志记录
        }
        return entity;
    }

    public File getFileByFid(int fid) throws FileNotExistException{
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
     * 将文件的操作记录进数据库
     * @param uid
     * @param fid
     * @param type
     * @return
     */
    public boolean storeFileOp(int uid,int fid,String type){
        Fileop fileop = new Fileop();
        fileop.setFopFile(fid);
        fileop.setFopUser(uid);
        fileop.setFopDate(new Date());
        fileop.setFopIp("127.0.0.1");
        fileop.setFopType(type);
        fileopMapper.insert(fileop);
        return true;
    }
}
