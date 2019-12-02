package com.zhiku.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.zhiku.entity.Course;
import com.zhiku.entity.mongodb.Index;
import com.zhiku.entity.mongodb.MongoSystem;
import com.zhiku.entity.mongodb.Paragraph;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CourseTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    //自定义方法
    /*
    得到所有课程列表
     */
    public List<Course> getAllCourse(){
        Query query = new Query();
        List<Course> courses = mongoTemplate.find(query, Course.class);
        return courses;
    }

    /**
     * 查询一个课程
     * @param cid 课程id
     */
    public Course findByPrimaryKey(Integer cid){
        Query query=new Query(  ).addCriteria( Criteria.where( "cid" ).is(cid) );
        return mongoTemplate.findOne( query,Course.class );
    }

    /**
     * 插入新课程，不知道cid
     * @param course
     */
    public void insert(Course course){
        Update update= new Update().inc("max_cid", 1);
        Query query=new Query(  );
        MongoSystem mongoSystem=mongoTemplate.findAndModify(query,update, MongoSystem.class );
        course.setCid( mongoSystem.getMaxCid() );
        List<Course> courses=new ArrayList<>(  );
        courses.add( course );
        mongoTemplate.insert(courses, Course.class );
    }

    /**
     * 更新
     * 注：只更新非空的属性
     * @param course cid必有
     */
    public void updateByPrimaryKey(Course course){
        Query query = new Query();
        Update update = new Update();
        query.addCriteria( Criteria.where("cid").is(course.getCid()));
        DBObject dbDoc = new BasicDBObject();
        mongoTemplate.getConverter().write(course, (Bson) dbDoc );
        update=ContentTemplate.fromDBObjectExcludeNullFields(dbDoc,update);
        mongoTemplate.upsert(query, update, Course.class);
    }

    /**
     * 更新或插入
     * @param course
     */
    public void upset(Course course){
        Query query=new Query(  );
        query.addCriteria( Criteria.where( "cid" ).is(course.getCid()) );
        Update update = new Update();
        DBObject dbDoc = new BasicDBObject();
        mongoTemplate.getConverter().write(course, (Bson) dbDoc );
        update=ContentTemplate.fromDBObjectExcludeNullFields(dbDoc,update);
        mongoTemplate.upsert( query,update, Course.class );
    }

    public void deleteByCid(Integer cid){
        Query query=new Query(  );
        query.addCriteria( Criteria.where( "cid" ).is(cid) );
        mongoTemplate.remove( query ,Course.class);
    }

    //获得课程的全部版本
    public List<String> getVids(Integer cid){
        Query query = new Query();
        query.addCriteria( Criteria.where( "cid" ).is( cid ) );
        Course course=mongoTemplate.findOne( query,Course.class );
        if (course==null)
            return null;
        Set<String> vids=new HashSet<>(  );
        vids.add( course.getVid() );
        query = new Query();
        query.addCriteria( Criteria.where( "cid" ).is( cid ) );
        query.fields().include("vid"); //包含该字段
//        query.fields().include("cid"); //包含该字段
//       query.fields().exclude("catalog");//不包含该字段,注：include和exclude不能同时使用！
        List<Index> indices=mongoTemplate.find( query,Index.class );
        if(indices!=null){
            for(Index index:indices ){
                vids.add( index.getVid() );
            }
        }
        return  new ArrayList<>(vids);
    }
}
