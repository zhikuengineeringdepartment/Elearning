package com.zhiku;

import com.zhiku.entity.Note;
import com.zhiku.mongo.CollectTemplate;
import com.zhiku.mongo.CourseTemplate;
import com.zhiku.mongo.IndexTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CourseTest extends  ApplicationTest{
    @Autowired
    CourseTemplate courseTemplate;
    @Autowired
    IndexTemplate indexTemplate;


    @Test
    public void TestGetAllCourses(){
        courseTemplate.getAllCourse();
    }

    @Test
    public void TestGetIndexOfCourse(){
        indexTemplate.getIndexOfCourse(112);
    }

    @Test
    public void TestGetSecondLevelIndex(){
        indexTemplate.getSecondLevelIndex(11203);
    }
}
