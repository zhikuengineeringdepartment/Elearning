package com.zhiku.controller;

import com.zhiku.entity.mysql.File;
import com.zhiku.service.FileService;
import com.zhiku.util.FileStatus;
import com.zhiku.util.ResponseData;
import com.zhiku.view.FileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;
import java.util.List;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    FileService fileService;

    @RequestMapping(value = "index")
    public String toAdmin(){
        return "adminIndex";
    }

    @ResponseBody
    @RequestMapping(value = "getUncheckFiles",method = RequestMethod.GET)
    public ResponseData getUncheckFileList(String keyWord, File file, int page, boolean order){
        ResponseData responseData = null;
        List<FileView> files = fileService.getFileList(keyWord,file,page,order, FileStatus.UNCHECK.getCode());
        responseData = ResponseData.ok();
        responseData.putDataValue("files",files);
        return  responseData;
    }

    @ResponseBody
    @RequestMapping(value = "modifyFileStatus",method = RequestMethod.POST)
    public ResponseData modifyFileStatus(File file,String status){
        ResponseData responseData = null;
        File f = fileService.getFileByFid(file.getFid());
        f.setFileStatus(status);
        fileService.updateFileStatus(f);
        responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "preview",method = RequestMethod.GET)
    public void filePreview(HttpServletResponse response, int fid) throws ConnectException {
        File file = fileService.getFileByFid(fid);
        if(file != null){
            String  rmsg = fileService.filePreview(response,file,true);
            System.out.println(rmsg);
        }
    }
}
