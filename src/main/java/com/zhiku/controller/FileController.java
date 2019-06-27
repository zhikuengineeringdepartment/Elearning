/*
文件相关的请求
 */
package com.zhiku.controller;

import com.zhiku.entity.File;
import com.zhiku.entity.FileKeys;
import com.zhiku.entity.User;
import com.zhiku.exception.FileNotExistException;
import com.zhiku.service.FileService;
import com.zhiku.service.UserService;
import com.zhiku.util.FileStatus;
import com.zhiku.util.ResponseData;
import com.zhiku.view.FileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;
import java.util.List;

/**
 * 文件控制层
 */
@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    /**
     *用户上传文件
     * @param user 用户
     * @param multipartFile 上传的文件对象
     * @param file 将要保存的文件实体（数据库实体），包含了fileCourse属性
     * @param fileKeys 上传时用户提供的文件关键字
     * @return 是否操作成功
     */
    @ResponseBody
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ResponseData fileUpload(
            User user,
            @RequestParam(value = "multipartFile") MultipartFile multipartFile,
            File file,
            FileKeys fileKeys){
        ResponseData responseData = null;
        //检查文件是否规范
        FileStatus fileStatus = fileService.checkFile(multipartFile);
        if(fileStatus == FileStatus.NORMAL){
            // 这里调用了一个事务的方法，如果这个方法报错，整个操作会回滚
            //将文件存到数据库和内存中
            if(fileService.storeFileToFileSystemAndinInsertIntoDB(multipartFile,file,user,fileKeys)){
                responseData = ResponseData.ok();
            }else{
                responseData = ResponseData.serverInternalError();
                responseData.setMessage("please retry");
            }
        }else{
            responseData = ResponseData.customerError();
            responseData.setMessage(fileStatus.getName());
        }
        return responseData;
    }

    /**
     * 文件下载请求
     * @param user 用户
     * @param request 请求
     * @param response 响应，用于文件流的写出
     * @param fid 下载的文件id
     * @throws FileNotExistException 文件找不到异常
     */
    @RequestMapping(value = "download",method = RequestMethod.GET)
    public void fileDownload(
            User user,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "fid") int fid) throws FileNotExistException {
        user = userService.getUserById(user.getUid());
        File file = fileService.getFileByFid(fid);
        fileService.fileDownload(request,response,user,file);
    }

    //TODO 待处理抛出的ConnectException
    /**
     * 非管理员文件预览请求，仅可预览10页
     * @param response 响应，用于pdf流的写出
     * @param fid 预览的文件id
     * @throws ConnectException openoffice服务找不到
     */
    @RequestMapping(value = "preview",method = RequestMethod.GET)
    public void filePreview(HttpServletResponse response,int fid) throws ConnectException {
        File file = fileService.getFileByFid(fid);
        if(file != null){
            //以非管理员预览文件
            String  rmsg = fileService.filePreview(response,file,false);
            System.out.println(rmsg);
        }
    }

    /**
     * 获得文件列表
     * @param keyWord   关键字，可以是文件名，文件作者，标签
     * @param file  文件对象，主要用来接收文件所属课程的信息，即fileCourse属性
     * @param page  获得第几页的列表
     * @param order 排序的依据，（时间顺序，时间倒序）
     * @return  文件列表
     */
    @ResponseBody
    @RequestMapping(value = "getFileList",method = RequestMethod.GET)
    public ResponseData getFileList(String keyWord,File file,int page,boolean order){
        ResponseData responseData = null;
        //查询正常的文件列表
        List<FileView> files = fileService.getFileList(keyWord,file,page,order,FileStatus.NORMAL.getCode());
        responseData = ResponseData.ok();
        responseData.putDataValue("files",files);
        return  responseData;
    }
}
