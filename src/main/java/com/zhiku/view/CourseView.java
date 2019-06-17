package com.zhiku.view;

import com.zhiku.entity.Course;
import com.zhiku.entity.Section;

import java.util.List;

/**
 * 课程视图
 * 用来返回对应课程及其对应小节的目录
 */
public class CourseView extends Course {
    private List<Section> sections ;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
