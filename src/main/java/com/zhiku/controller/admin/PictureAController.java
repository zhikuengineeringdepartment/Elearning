package com.zhiku.controller.admin;

import com.zhiku.entity.Picture;
import com.zhiku.entity.User;
import com.zhiku.service.PictureService;
import com.zhiku.service.UserService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.PictureView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/picture")
public class PictureAController {
    @Autowired
    UserService userService;

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

        System.out.println("<<<>>>UIDS="+user.getUid());////////////////

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
        String url="http://sharingideas.cn"+picture.getUrl();
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
        System.out.println("<<<>>>UID="+user.getUid());////////////////

        List<PictureView> pictures=pictureService.getList( user.getUid(),page,pageSize );
        //拼接url前地址
        for (PictureView pictureView:pictures) {
//            pictureView.setUrl(request.getScheme()+"://"+ request.getServerName()+":"+request.getLocalPort()+pictureView.getUrl(  ) );
            pictureView.setUrl("http://sharingideas.cn"+pictureView.getUrl(  ) );
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
        Picture picture=pictureService.getByPicId( picId );
        if(picture!=null&& picture.getUid().equals( user.getUid() )){
            pictureService.delete( picId );
            return ResponseData.ok();
        }else{
            return ResponseData.customerError();
        }
    }

    /**
     * 大事年记图片上传
     * @param user 自动获取
     * @param file jpg|png|Gif
     *  cid 课程id
     *  sections 章节索引数组字符串["第一章","第一节"...]
     */
    @RequestMapping(value = "/chronology/upload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData chronologyUpdate(User user, @RequestParam("file") MultipartFile file,
                                         @RequestParam(required = false) Date date) {
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
     * 大事年记-删除一条记录
     * @param picId 图片记录id
     */
    @RequestMapping(value = "/chronology/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData chronologyDelete(Integer picId) {
        Picture picture=pictureService.getByPicId( picId );
        if(picture!=null&& picture.getUid().equals( chronologyUid )){
            pictureService.delete( picId );
            return ResponseData.ok();
        }else{
            return ResponseData.customerError();
        }
    }
}
