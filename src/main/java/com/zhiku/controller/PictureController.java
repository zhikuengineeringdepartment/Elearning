package com.zhiku.controller;

import com.zhiku.entity.Picture;
import com.zhiku.entity.User;
import com.zhiku.exception.TokenVerifyErrorException;
import com.zhiku.service.PictureService;
import com.zhiku.service.UserService;
import com.zhiku.util.JWTUtil;
import com.zhiku.util.ResponseData;
import com.zhiku.view.ChapterProgressView;
import com.zhiku.view.PictureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.StringUtils;

import java.util.*;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/picture")
public class PictureController {
    @Autowired
    UserService userService;

    //临时大事年记表示：用改uid上传的图片为大事年记图
    private int chronologyUid=-100;

    @Autowired
    PictureService pictureService;
    /**
     * 大事年记列表获取
     * @param page 分页-页码
     * @param pageSize 分页-页每页大小
     */
    @RequestMapping(value = "/chronology/list")
    @ResponseBody
    public ResponseData chronologyList(@RequestParam(required = false) Integer page
            ,@RequestParam(required = false) Integer pageSize) {
        List<PictureView> pictures=pictureService.getList( chronologyUid,page,pageSize );

        Map<String,Object>[] re=new Map[pictures.size()];
        int i=-1;
        //拼接url前地址
        for (PictureView pictureView:pictures) {
            Map<String,Object> map=new HashMap<>(  );
            map.put( "url",".."+pictureView.getUrl(  ) );
            map.put( "date",pictureView.getCreateTime( ) );
            re[++i]=map;
        }

        ResponseData responseData=new ResponseData(  );
        responseData.putDataValue( "pictures",re );
        return responseData;
    }

    //判断是否是管理员
    private boolean isAdm(User user0,String uri){
        User user = userService.getUserById(user0.getUid());
        return userService.checkAuthority( user,uri );
    }

}
