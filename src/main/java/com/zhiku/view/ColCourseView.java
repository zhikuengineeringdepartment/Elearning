package com.zhiku.view;

import com.zhiku.entity.mysql.ColCourse;
import com.zhiku.entity.Course;

/**
 * 收藏课程视图
 * 用于展示用户收藏的课程及相关进度
 */
public class ColCourseView extends ColCourse {
    private Course course;
    //已经完成的小节数
    private int completedSection;
    //课程总的小节数
    private int totalSection;

    public Course getCourse() {
        return course;
    }

    public int getCompletedSection() {
        return completedSection;
    }

    public int getTotalSection() {
        return totalSection;
    }
}
