package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "course")
public class Course {
    @Id
    private ObjectId objectId;
    @Indexed
    private Integer cid;
    @Field("course_name")
    private String courseName;
    @Field("course_desc")
    private String courseDesc;
    @Field("course_icon")
    private String courseIcon;
    @Field("vid")
    private String vid;
//    @PersistenceConstructor
//    public Course(Integer cid, String course_name, String course_desc, String course_icon){
//        this.cid = cid;
//        this.courseName = course_name;
//        this.courseDesc = course_desc;
//        this.courseIcon = course_icon;
//    }
    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc == null ? null : courseDesc.trim();
    }

    public String getCourseIcon() {
        return courseIcon;
    }

    public void setCourseIcon(String courseIcon) {
        this.courseIcon = courseIcon == null ? null : courseIcon.trim();
    }


}