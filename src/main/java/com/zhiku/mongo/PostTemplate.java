package com.zhiku.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zhiku.entity.Post;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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

    /**
     * 分页查询全部帖子列表
     * @param page 页码
     * @param pageSize 页大小
     * @param orderByTime 按更新时间排序，1==正序，-1==倒序，0==不按
     * @param orderByAgree 按热度（点赞数）排序 同上
     * @return
     */
    public List<Post> pageList(int page,int pageSize,Integer orderByTime,Integer orderByAgree){
        Query query = new Query();
        //设置起始数
        query.skip((page - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        if(orderByAgree==1){
            query.with( Sort.by( Sort.Order.asc("agreeCount")));
        }else if(orderByAgree==-1){
            query.with( Sort.by( Sort.Order.desc("agreeCount")));
        }else if(orderByTime==1){
            query.with( Sort.by( Sort.Order.asc("updateTime")));
        }else if(orderByTime==-1){
            query.with( Sort.by( Sort.Order.desc("updateTime")));
        }

        return mongoTemplate.find(query,Post.class);
    }
}
