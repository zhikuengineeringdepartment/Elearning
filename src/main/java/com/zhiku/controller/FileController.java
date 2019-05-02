package com.zhiku.controller;

import com.zhiku.entity.File;
import com.zhiku.entity.FileKeys;
import com.zhiku.entity.User;
import com.zhiku.exception.FileNotExistException;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.service.FileService;
import com.zhiku.service.UserService;
import com.zhiku.util.FileStatus;
import com.zhiku.util.ResponseData;
import com.zhiku.view.FileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;
import java.util.List;


@Controller
@RequestMapping(value = "file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ResponseData fileUpload(
            User user,
            @RequestParam(value = "multipartFile") MultipartFile multipartFile,
            File file,
            FileKeys fileKeys){
        ResponseData responseData = null;
        //检查文件是否规范
        System.out.println(user.getUid());
        FileStatus fileStatus = fileService.checkFile(multipartFile);
        if(fileStatus == FileStatus.NORMAL){
            if(fileService.storeFile(multipartFile,file,user,fileKeys)){
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

    @RequestMapping(value = "download",method = RequestMethod.GET)
    public void fileDownload(
            User user,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "fid") int fid) throws UserNotFoundException,FileNotExistException {
        user = userService.getUserById(user.getUid());
        File file = fileService.getFileByFid(fid);
        fileService.fileDownload(request,response,user,file);
    }

    @RequestMapping(value = "preview",method = RequestMethod.GET)
    public void filePreview(HttpServletResponse response,int fid) throws ConnectException {
        File file = fileService.getFileByFid(fid);
        if(file != null){
            String  rmsg = fileService.filePreview(response,file);
            System.out.println(rmsg);
        }
    }

    /**
     * 获得文件列表
     * @param keyWord   关键字，可以是文件名，文件作者，标签
     * @param file  文件对象，主要用来接收文件所属课程的信息
     * @param page  获得第几页的列表
     * @param order 排序的依据，（时间顺序，时间倒序）
     * @return  文件列表
     */
    @ResponseBody
    @RequestMapping(value = "getFileList",method = RequestMethod.GET)
    public ResponseData getFileList(String keyWord,File file,int page,boolean order){
        System.out.println(keyWord);
        System.out.println(file.getFileCourse());
        System.out.println(page);
        System.out.println(order);
        ResponseData responseData = null;
        List<FileView> files = fileService.getFileList(keyWord,file,page,order);
        responseData = ResponseData.ok();
        responseData.putDataValue("files",files);
        return  responseData;
    }
}
