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
    //管理员uid，只有登录这些账号只能访问页面和使用功能
    private int[] admUids={101};

    //临时大事年记表示：用改uid上传的图片为大事年记图
    private int chronologyUid=-100;

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
    public ResponseData save(User user, @RequestParam("file") MultipartFile file,
                             Integer cid, String[] sections) {
        if(!isAdm(user)) {
            return ResponseData.powerError();
        }
        //储存图片
        Picture picture=new Picture();
        picture.setCid( cid );
        picture.setSections(StringUtils.join(sections,','));
        picture.setUid( user.getUid() );
        String re=pictureService.addPicture( file,picture );
        if(re!=null){
            new ResponseData( 400,re+"!" );
        }
        ResponseData responseData=new ResponseData(  );
        //拼接url前地址
//        String url=request.getScheme()+"://"+ request.getServerName()+":"+request.getLocalPort()+picture.getUrl();
        String url=".."+picture.getUrl();
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
        for (PictureView pictureView:pictures) {
//            pictureView.setUrl(request.getScheme()+"://"+ request.getServerName()+":"+request.getLocalPort()+pictureView.getUrl(  ) );
            pictureView.setUrl(".."+pictureView.getUrl(  ) );
        }

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

    /**
     * 大事年记图片上传
     * @param user 自动获取
     * @param file jpg|png|Gif
     *  cid 课程id
     *  sections 章节索引数组字符串["第一章","第一节"...]
     */
    @RequestMapping(value = "/chronology/update",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData chronologyUpdate(User user, @RequestParam("file") MultipartFile file,
                                         @RequestParam(required = false) Date date) {
        if(!isAdm(user)) {
            return ResponseData.powerError();
        }
        Picture picture=new Picture();
        //储存图片
        picture.setUid( chronologyUid );
        picture.setCreateTime( date );

        String re=pictureService.addPicture( file,picture );
        if(re!=null){
            new ResponseData( 400,re+"!" );
        }
        ResponseData responseData=new ResponseData(  );
        //拼接url前地址
        String url=".."+picture.getUrl();
        responseData.putDataValue( "url",url);
        return responseData;
    }

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
            map.put( "date",pictureView.getCreateTime() );
            re[++i]=map;
        }

        ResponseData responseData=new ResponseData(  );
        responseData.putDataValue( "pictures",re );
        return responseData;
    }

    //判断是否是管理员
    private boolean isAdm(User user0){
        User user = userService.getUserById(user0.getUid());
        //检查该用户是否是admin用户，即它的权限值为a（admin），普通的用户权限值为u（user）
        if(!"a".equals(user.getUserAuth())){
            return false;
        }
        return true;
    }

}
