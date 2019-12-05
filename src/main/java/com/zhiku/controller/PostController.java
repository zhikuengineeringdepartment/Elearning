package com.zhiku.controller;

import com.zhiku.entity.Post;
import com.zhiku.entity.User;
import com.zhiku.service.PostService;
import com.zhiku.util.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;


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
        Date currentDate=new Date();
        Post post=new Post();
        post.setContent(postContent);
        post.setTitle(postTitle);
        post.setAuthor(user.getUid());
        post.setDelete(false);
        post.setReplyCount(0);
        post.setAgreeCount(0);
        post.setDisagreeUsers(new ArrayList<>());
        post.setUpdateTime(currentDate);
        post.setCreateTime(currentDate);
        post.setAgreeUsers(new ArrayList<>());
        if(courseId==null){
            post.setCourseId(0);
        }else{
            post.setCourseId(courseId);
        }
        try {
            postService.add(post);
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
        if(post.getAuthor()!=user.getUid()){
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
     * 获取帖子列表
     * @param page 分页-页码，从1开始
     * @param pageSize 分页-页大小
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseData list(Integer page,Integer pageSize,Integer order){
        if(page<1||pageSize<1){
            return ResponseData.badRequest();
        }
        ResponseData responseData= ResponseData.ok();
        responseData.putDataValue( "postView",postService.list( page,pageSize,order ) );
        return responseData;
    }




}
