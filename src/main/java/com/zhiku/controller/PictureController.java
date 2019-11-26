package com.zhiku.controller;

import com.zhiku.entity.Picture;
import com.zhiku.entity.User;
import com.zhiku.service.PictureService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.PictureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/picture")
public class PictureController {
    //管理员uid，只有登录这些账号只能访问页面和使用功能
    private int[] admUids={101};

    @Autowired
    PictureService pictureService;
    //todo:暂时url不拼接ip
//    @Autowired
//    protected HttpServletRequest request;
    /**
     * 上传图片
     * @param user 自动获取
     * @param file jpg|png|Gif
     *  cid 课程id
     *  sections 章节索引数组字符串["第一章","第一节"...]
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(User user, @RequestParam("file") MultipartFile file, Picture picture) {
        if(!isAdm(user)) {
            return ResponseData.powerError();
        }
        //储存图片
        picture.setUid( user.getUid() );
        String re=pictureService.addPicture( file,picture );
        if(re!=null){
            new ResponseData( 400,re+"!" );
        }
        ResponseData responseData=new ResponseData(  );
        //拼接url前地址
//        String url=request.getScheme()+"://"+ request.getServerName()+":"+request.getLocalPort()+picture.getUrl();
        String url=picture.getUrl();
        responseData.putDataValue( "url",url);
        return responseData;
    }

    /**
     * 获取上传图片记录
     * @param user 自动获取
     * @param page 分页-页码
     * @param pageSize 分页-页每页大小
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseData list(User user,@RequestParam(required = false) Integer page
            ,@RequestParam(required = false) Integer pageSize) {
        if(user==null||user.getUid()==null) {
            return ResponseData.loginError();
        }
        List<PictureView> pictures=pictureService.getList( user.getUid(),page,pageSize );
        //拼接url前地址
//        for (PictureView pictureView:pictures) {
//            pictureView.setUrl(request.getScheme()+"://"+ request.getServerName()+":"+request.getLocalPort()+pictureView.getUrl(  ) );
//        }

        ResponseData responseData=new ResponseData(  );
        responseData.putDataValue( "pictures",pictures );
        return responseData;
    }

    /**
     * 删除一条记录
     * @param user 自动获取
     * @param picId 图片记录id
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData delete(User user,Integer picId) {
        if(!isAdm(user)) {
            return ResponseData.powerError();
        }
        pictureService.delete( picId );

        return ResponseData.ok();
    }

    //判断是否是管理员
    private boolean isAdm(User user){
        int uid=user.getUid();
        for (int admUid : admUids) {
            if (uid == admUid) {
                return true;
            }
        }
        return false;
    }

}
