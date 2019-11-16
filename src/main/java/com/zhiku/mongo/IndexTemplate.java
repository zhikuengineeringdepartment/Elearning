package com.zhiku.mongo;

import com.zhiku.entity.Child;
import com.zhiku.entity.Course;
import com.zhiku.entity.Index;
import com.zhiku.view.CourseView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;

import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class IndexTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
        获取指定课程的全部索引
     */
    public List<Index> getIndexOfCourse(int cid){
        Criteria criteria = Criteria.where("cid").is(cid);
        List<Index> indexlist = mongoTemplate.find(query(criteria), Index.class);
//        ListIterator<Index> iter = indexlist.listIterator();
//        while(iter.hasNext()){
//            Index i = iter.next();
//            List<Child> children = i.getChild();
//            ListIterator<Child> iter1 = children.listIterator();
//            while(iter1.hasNext()){
//                Child c = iter1.next();
//                System.out.println(c.getSid());
//                System.out.println(c.getSection_name());
//                System.out.println(c.getSection_seq());
//            }
//        }
        return indexlist;
    }
    /*
        获取指定课程的一级索引
     */
    public CourseView getFirstLevelIndex(int cid){
        //查询出课程号
        Criteria classinfo = Criteria.where("cid").is(cid);
        List<Course> courses = mongoTemplate.find(query(classinfo), Course.class);
        Course course = courses.get(0);
        //查询出对应课程号的一级索引
        Criteria index = Criteria.where("cid").is(cid);
        List<Index> indexlist = mongoTemplate.find(query(index), Index.class);
        ListIterator<Index> iter = indexlist.listIterator();
        Index i;
        while(iter.hasNext()){
            i = iter.next();
            i.setObjectId(null);
            i.setChild(null);
        }
        CourseView courseView = new CourseView();
        courseView.setSections(indexlist);
        courseView.setCid(course.getCid());
        courseView.setCourseName(course.getCourseName());
        courseView.setCourseDesc(course.getCourseDesc());
        courseView.setCourseIcon(course.getCourseIcon());
        return courseView;
    }
    /*
        获取指定课程的二级索引
     */
    public SectionView getSecondLevelIndex(int sid){
        //int cid = Integer.parseInt(String.valueOf(sid).substring(0, 3));
        //System.out.println(cid);
        Criteria criteria = Criteria.where("sid").is(sid);
        Index index = mongoTemplate.findOne(query(criteria), Index.class);
        SectionView sectionView = new SectionView();
        sectionView.setKnowledgeViews(index.getChild());
        sectionView.setSid(index.getSid());
        sectionView.setSectionName(index.getSection_name());
        sectionView.setSectionSeq(String.valueOf(index.getSection_seq()));
        sectionView.setSectionCourse(index.getCid());
        return sectionView;
    }
}

