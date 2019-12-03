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




}
