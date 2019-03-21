package com.zhiku.controller;

import com.zhiku.entity.File;
import com.zhiku.entity.User;
import com.zhiku.exception.FileNotExistException;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.service.FileService;
import com.zhiku.service.UserService;
import com.zhiku.util.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ModelAndView fileUpload(
            @RequestParam(value = "multipartFile") MultipartFile multipartFile,
            File file){
        ModelAndView modelAndView = new ModelAndView();
        //检查文件是否规范
        FileStatus fileStatus = fileService.checkFile(multipartFile);
        if(fileStatus == FileStatus.NORMAL){
            if(fileService.storeFile(multipartFile,file)){
                modelAndView.setStatus(HttpStatus.OK);
            }else{
                modelAndView.setStatus(HttpStatus.NOT_ACCEPTABLE);
                modelAndView.addObject("message","please retry");
            }
        }else{
            modelAndView.setStatus(HttpStatus.ACCEPTED);
            modelAndView.addObject("message",fileStatus.getName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> fileDownload(
            @RequestParam(value = "uid")int uid,
            @RequestParam(value = "fid") int fid) throws UserNotFoundException,FileNotExistException {
        User user = userService.getUserById(uid);
        File file = fileService.getFileByFid(fid);
        return fileService.fileDownload(user,file);
    }
}
