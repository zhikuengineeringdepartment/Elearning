package com.zhiku.mapper;

import com.zhiku.entity.ColCourse;
import com.zhiku.entity.ColCourseKey;
import com.zhiku.view.ColCourseView;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ColCourseMapper {
    int deleteByPrimaryKey(ColCourseKey key);

    int insert(ColCourse record);

    int insertSelective(ColCourse record);

    ColCourse selectByPrimaryKey(ColCourseKey key);

    int updateByPrimaryKeySelective(ColCourse record);

    int updateByPrimaryKey(ColCourse record);

    List<ColCourseView> selectColCourseView(Integer uid);
}