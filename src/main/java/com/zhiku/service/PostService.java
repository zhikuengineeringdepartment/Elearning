package com.zhiku.service;

import com.sun.star.uno.RuntimeException;
import com.zhiku.entity.Post;
import com.zhiku.mongo.PostTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private PostTemplate postTemplate;

    public void add(Post post) {
        postTemplate.add(post);
    }

    public Post get(String postId) {
        return  postTemplate.get(postId);
    }


    public void deleteOne(String postId) {
        long num = postTemplate.deleteOne(postId);
        if(num!=1){
            throw new RuntimeException("删除异常");
        }
    }
}
