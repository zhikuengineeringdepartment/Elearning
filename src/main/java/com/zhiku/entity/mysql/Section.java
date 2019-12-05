package com.zhiku.entity.mysql;

public class Section {
    private Integer sid;

    private String sectionName;

    private String sectionSeq;

    private String sectionRecommendPath;

    private Integer sectionCourse;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName == null ? null : sectionName.trim();
    }

    public String getSectionSeq() {
        return sectionSeq;
    }

    public void setSectionSeq(String sectionSeq) {
        this.sectionSeq = sectionSeq == null ? null : sectionSeq.trim();
    }

    public String getSectionRecommendPath() {
        return sectionRecommendPath;
    }

    public void setSectionRecommendPath(String sectionRecommendPath) {
        this.sectionRecommendPath = sectionRecommendPath == null ? null : sectionRecommendPath.trim();
    }

    public Integer getSectionCourse() {
        return sectionCourse;
    }

    public void setSectionCourse(Integer sectionCourse) {
        this.sectionCourse = sectionCourse;
    }
}