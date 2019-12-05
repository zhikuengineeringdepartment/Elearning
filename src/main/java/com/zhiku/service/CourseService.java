package com.zhiku.service;

import com.zhiku.entity.mysql.ColCourse;
import com.zhiku.entity.mysql.ColCourseKey;
import com.zhiku.entity.Course;
import com.zhiku.mapper.ColCourseMapper;
import com.zhiku.mapper.CourseMapper;
import com.zhiku.mapper.ScheduleMapper;
import com.zhiku.mongo.CourseTemplate;
import com.zhiku.mongo.IndexTemplate;
import com.zhiku.view.ColCourseView;
import com.zhiku.view.CourseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ColCourseMapper colCourseMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private CourseTemplate courseTemplate;
    @Autowired
    private IndexTemplate indexTemplate;

//    public List<Course> getAllCourse(){
//        return courseMapper.getAllCourse();
//    }
    public List<Course> getAllCourse(){
        return courseTemplate.getAllCourse();
    }

    public CourseView getCourseDetails(int cid){
        return courseMapper.getCourseView(cid);
    }

    /**
     * 查询收藏的课程及相关进度
     * @param uid
     * @return
     */
    public List<ColCourseView> getColCourses(int uid){
        return colCourseMapper.selectColCourseView(uid);
    }

    /**
     * 添加收藏课程
     * @param uid
     * @param cid
     * @return
     */
    public boolean colCourse(int uid,int cid){
        boolean finish ;
        ColCourse colCourse = new ColCourse();
        colCourse.setColcCourse(cid);
        colCourse.setColcCourse(uid);
        colCourse.setColcDate(new Date());
        int code = colCourseMapper.insert(colCourse);
        if(code > 0 ){
            finish = true;
        }else{
            finish = false;
        }
        return finish;
    }

    public void removeColCourse(int uid,int cid){
        //删除所收藏的课
        ColCourseKey colCourseKey = new ColCourseKey();
        colCourseKey.setColcCourse(cid);
        colCourseKey.setColcUser(uid);
        colCourseMapper.deleteByPrimaryKey(colCourseKey);
        //删除所收藏课对应的学习进度

    }

}
