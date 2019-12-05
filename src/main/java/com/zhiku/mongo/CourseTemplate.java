package com.zhiku.mongo;

import com.zhiku.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

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
//        ListIterator<Course> iter = courses.listIterator();
//        while(iter.hasNext()){
//            Course c = iter.next();
//            System.out.println(c.getCid());
//            System.out.println(c.getCourseDesc());
//            System.out.println(c.getCourseName());
//        }
        return courses;
    }
}
