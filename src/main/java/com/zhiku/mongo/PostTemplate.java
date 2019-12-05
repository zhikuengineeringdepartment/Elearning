package com.zhiku.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zhiku.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String TABNAME="post";

    public void add(Post post) {
        mongoTemplate.insert(post);
    }

    public Post get(String postId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(postId));
        Post post = mongoTemplate.findOne(query, Post.class, TABNAME);
        return post;

    }

    public long deleteOne(String postId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(postId));
        Update update = Update.update("isDelete",true).set("updateTime",new Date());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Post.class);
        return updateResult.getModifiedCount();
    }
}
