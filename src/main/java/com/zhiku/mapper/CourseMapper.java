package com.zhiku.mapper;

import com.zhiku.entity.Course;
import com.zhiku.view.CourseView;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    //自定义方法
    List<Course> getAllCourse();

    CourseView getCourseView(Integer cid);
}