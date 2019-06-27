package com.zhiku.controller;

import com.zhiku.entity.File;
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

/**
 * 管理员控制层，所有管理员的请求都在这个控制层，配合TokenAop进行管理员身份认证。
 */
@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    FileService fileService;

    /**
     * 本来设计用来做管理员的首页的，后台工期紧，搁置了，暂时用不到
     * @return
     */
    @RequestMapping(value = "index")
    public String toAdmin(){
        return "adminIndex";
    }

    //TODO 工期原因，目前只能查询待审核的文件，只能将待审核的文件改为正常，没法封禁文件
    /**
     * 获得待审核的文件列表
     * @param keyWord 关键字
     * @param file  包含fileCourse属性，选择哪一门课程下的文件
     * @param page  页数
     * @param order 是否按时间降序排序
     * @return  待审核文件列表
     */
    @ResponseBody
    @RequestMapping(value = "getUncheckFiles",method = RequestMethod.GET)
    public ResponseData getUncheckFileList(String keyWord, File file, int page, boolean order){
        ResponseData responseData = null;
        //查询待审核的文件
        List<FileView> files = fileService.getFileList(keyWord,file,page,order, FileStatus.UNCHECK.getCode());
        responseData = ResponseData.ok();
        responseData.putDataValue("files",files);
        return  responseData;
    }

    /**
     * 修改文件状态
     * @param file  包含fid，确定哪一个文件
     * @param status    修改的状态
     * @return  是否修改成功
     */
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

    //TODO 待处理抛出的ConnectException
    /**
     * 管理员的预览请求，管理员可以预览整个文件内容
     * @param response 负责pdf流的写出
     * @param fid   文件id
     * @throws ConnectException openoffice链接异常
     */
    @RequestMapping(value = "preview",method = RequestMethod.GET)
    public void filePreview(HttpServletResponse response, int fid) throws ConnectException {
        File file = fileService.getFileByFid(fid);
        if(file != null){
            //以管理员身份进行预览
            String  rmsg = fileService.filePreview(response,file,true);
            System.out.println(rmsg);
        }
    }
}
