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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
            HttpServletResponse response,
            @RequestParam(value = "fid") int fid) throws UserNotFoundException,FileNotExistException {
        user = userService.getUserById(user.getUid());
        File file = fileService.getFileByFid(fid);
        fileService.fileDownload(response,user,file);
    }

    @RequestMapping(value = "preview",method = RequestMethod.GET)
    public void filePreview(HttpServletResponse response,int fid) throws ConnectException {
        File file = fileService.getFileByFid(fid);
        if(file != null){
            String  rmsg = fileService.filePreview(response,file);
            System.out.println(rmsg);
        }
    }

    @ResponseBody
    @RequestMapping(value = "getFileListByCourse",method = RequestMethod.GET)
    public ResponseData getFileList(int cid){
        ResponseData responseData = null;
        List<File> files = fileService.getFileListByCid(cid);
        responseData = ResponseData.ok();
        responseData.putDataValue("files",files);
        return  responseData;
    }
}
