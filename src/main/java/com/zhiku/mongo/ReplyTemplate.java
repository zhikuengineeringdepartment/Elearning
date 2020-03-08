package com.zhiku.mongo;

import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zhiku.entity.PostFirstReply;
import com.zhiku.entity.PostReply;
import com.zhiku.exception.NoContentException;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.mongo.results.FirstReplyResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 分页查询帖子中的评论
     * @param page 页号
     * @param pageSize 页大小
     * @param pid 帖子号
     * @return List<PostFirstReply>，所有的楼中楼一并返回
     */
//    db.getCollection('postReply').aggregate([
//    {"$match":{"pid":1}},
//    {"$unwind":"$postFirstReplyList"},
//    {"$skip":1},
//    {"$limit":1},
//    {"$project":{"postFirstReplyList":1}}
//    ])
    public List<FirstReplyResult> getPagedFirstReply(int page, int pageSize, int pid){
        TypedAggregation<PostReply> aggregation = Aggregation.newAggregation(
                PostReply.class,
                Aggregation.match(Criteria.where("pid").is(pid)),
                Aggregation.unwind("postFirstReplyList"),
                Aggregation.skip((long)(page-1)*pageSize),
                Aggregation.limit((long)pageSize),
                //Aggregation.sort(Sort.by(Sort.Order.asc("rid"))),
                Aggregation.project("postFirstReplyList")
        );

        AggregationResults<FirstReplyResult> aggregationResults =
                mongoTemplate.aggregate(aggregation, FirstReplyResult.class);
        List<FirstReplyResult> firstReplyResults = aggregationResults.getMappedResults();
        if(firstReplyResults == null || firstReplyResults.size() == 0){
            //抛出内容为空异常
            throw new NoContentException("查询不到该帖子");
        }
        return firstReplyResults;
    }

    /**
     * 设置一级评论的点赞数
     * @param rid 评论号
     * @param newAgreeCount 新的点赞数
     * @return 是否设置成功
     */
    public boolean setFirstReplyAgreeCount(int rid, int newAgreeCount){
        Query query = new Query();
        query.addCriteria(Criteria.where("postFirstReplyList.rid").is(rid));
        Update update = new Update();
        update.set("postFirstReplyList.$.agreeCount", newAgreeCount);
        try{
            mongoTemplate.updateFirst(query, update, PostReply.class);
            return true;
        }catch(Exception e) {
            return false;
        }
    }
//db.getCollection('postReply').update(
//    {},
//    { $set:{"postFirstReplyList.$[outter].postMultistepReplyList.$[inner].agreeCount" : 3} },
//    { arrayFilters:[{"outter.postMultistepReplyList":{$ne:null}}, {"inner.rid":{$eq: 201}} ]}
//)
}
