package com.zhiku.view;

import com.zhiku.entity.Picture;

import java.util.List;

public class PictureView extends Picture {
    private String course;
    private List<String> sectionList;

    public List<String> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<String> sectionList) {
        this.sectionList = sectionList;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
