package com.zhiku.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zhiku.entity.PostReply;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class ReplyTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入一个新的文章评论
     * @param postReply
     * */
    public boolean insert(PostReply postReply){
        mongoTemplate.insert(postReply);
        return true;
    }
    /**
     * 查找一个文章的所有评论
     * @param pId
     * */
    public PostReply get(ObjectId pId){
        Query query = new Query(Criteria.where("pId").is(pId));
        PostReply reply = mongoTemplate.findOne(query,PostReply.class);
        return reply;
    }

    /**
     * 更新文章的评论
     * @param pId
     * @param postReply
     * */
    public boolean update(ObjectId pId,PostReply postReply){
        Query query = new Query(Criteria.where("pId").is(pId));
        Update update = new Update();
        update.set("postFirstReplyList",postReply.getPostFirstReplyList());
        UpdateResult updateResult = mongoTemplate.upsert(query,update,PostReply.class);
        Long count = updateResult.getModifiedCount();
        if (count<=0){
            return false;
        }
        return true;
    }

    /**
     * 删除文章所有评论
     * @param pId
     * */
    public boolean deleteAll(ObjectId pId){
        Query query = new Query(Criteria.where("pId").is(pId));
        DeleteResult deleteResult = mongoTemplate.remove(query,PostReply.class);
        Long count = deleteResult.getDeletedCount();
        if (count>0){
            return true;
        }
        return false;
    }
}
