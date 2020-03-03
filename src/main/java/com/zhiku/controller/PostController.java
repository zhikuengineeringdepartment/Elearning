package com.zhiku.controller;

import com.zhiku.entity.Post;
import com.zhiku.entity.mysql.Report;
import com.zhiku.entity.User;
import com.zhiku.service.PostSearchService;
import com.zhiku.service.PostService;
import com.zhiku.service.ReportService;
import com.zhiku.util.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private PostSearchService postSearchService;


    @ResponseBody
    @RequestMapping("/add")
    public ResponseData add(User user, @RequestParam(value="postTitle",required=false)String postTitle,
                            @RequestParam(value="postContent",required=false)String postContent,
                            @RequestParam(value="courseId",required=false)Integer courseId){
        ResponseData responseData;
        if(StringUtils.isBlank(postTitle)){
            responseData=ResponseData.customerError();
            responseData.setMessage("帖子标题为空");
            return responseData;
        }
        if(StringUtils.isBlank(postContent)){
            responseData=ResponseData.customerError();
            responseData.setMessage("帖子内容为空");
            return responseData;
        }
        try {
            //postService.add(post);
            postService.add(user.getUid(),postTitle,postContent,courseId);
            responseData=ResponseData.ok();
        }catch (Exception e){
            e.printStackTrace();
            responseData=ResponseData.serverInternalError();
        }
        return responseData;
    }


    @ResponseBody
    @RequestMapping("/delete")
    public ResponseData delete(User user,@RequestParam(value="postId",required=false)String postId ){
        ResponseData responseData;
        if(StringUtils.isBlank(postId)){
            responseData=ResponseData.customerError();
            responseData.setMessage("参数为空");
            return responseData;
        }
        Post post=postService.get(postId);
        if(post==null){
            responseData=ResponseData.customerError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        if(!post.getAuthor().equals(user.getUid())){
            responseData=ResponseData.unauthorized();
            return responseData;
        }
        try {
            postService.deleteOne(postId);
            responseData=ResponseData.ok();
        }catch (Exception e){
            e.printStackTrace();
            responseData=ResponseData.serverInternalError();
        }
        return responseData;
    }

    /**
     * 搜索帖子//未完
     * @param page 分页-页码，从1开始
     * @param pageSize 分页-页大小
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData search(Integer page,Integer pageSize,Integer order,String keyWords){
        if(page<1||pageSize<1){
            return ResponseData.badRequest();
        }
        ResponseData responseData= ResponseData.ok();

        responseData.putDataValue( "postView",postSearchService.search( keyWords.trim(),page,pageSize,order ) );
        return responseData;
    }

    /**
     * 举报帖子
     * pid 帖子id
     * uid 举报人id
     * reason 举报理由
     */
    @RequestMapping(value = "/report",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData report(User user,Report report){
        report.setUid( user.getUid() );
        report.setType( Report.TYPE_POST );
        report.setState( Report.STATE_NULL );
        report.setDate( new Date(  ) );
        report.setRid( null );
        reportService.add( report );
        return ResponseData.ok();
    }

}
