package com.zhiku.service;

import com.sun.star.uno.RuntimeException;
import com.zhiku.entity.Post;
import com.zhiku.mongo.PostTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Service
public class PostService {

    @Autowired
    private PostTemplate postTemplate;


    public Post get(String postId) {
        return  postTemplate.get(postId);
    }


    public void deleteOne(String postId) {
        long num = postTemplate.deleteOne(postId);
        if(num!=1){
            throw new RuntimeException("删除异常");
        }
    }

    public void add(Integer uid, String postTitle, String postContent,Integer courseId) {

        Date currentDate=new Date();
        Post post=new Post();
        post.setContent(postContent);
        post.setTitle(postTitle);
        post.setAuthor(uid);
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
        postTemplate.add(post);
    }
}
