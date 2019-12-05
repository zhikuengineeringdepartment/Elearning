package com.zhiku.mapper;

import com.zhiku.entity.Course;
import com.zhiku.view.CourseView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CourseMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    //自定义方法
    int insertGetId(Course record);

    List<Course> getAllCourse();

    CourseView getCourseView(Integer cid);

    Course selectByTitleDec(@Param( "title" ) String title, @Param( "describe" ) String describe);

}