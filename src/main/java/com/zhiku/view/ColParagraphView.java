package com.zhiku.view;

import com.zhiku.entity.mysql.ColParagraphMysql;

/**
 * 个人中心收藏段落视图
 */
public class ColParagraphView extends ColParagraphMysql {
    private Integer paragraphSeq;

    private Integer paragraphKnowledge;

    private String paragraphType;

    private String paragraphNewline;

    private String paragraphContent;

    private Integer cid;

    private String courseName;

    private String courseDesc;

    private String courseIcon;

    public void setParagraphSeq(Integer paragraphSeq) {
        this.paragraphSeq = paragraphSeq;
    }

    public void setParagraphKnowledge(Integer paragraphKnowledge) {
        this.paragraphKnowledge = paragraphKnowledge;
    }

    public void setParagraphType(String paragraphType) {
        this.paragraphType = paragraphType;
    }

    public void setParagraphNewline(String paragraphNewline) {
        this.paragraphNewline = paragraphNewline;
    }

    public void setParagraphContent(String paragraphContent) {
        this.paragraphContent = paragraphContent;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public void setCourseIcon(String courseIcon) {
        this.courseIcon = courseIcon;
    }

    public Integer getParagraphSeq() {
        return paragraphSeq;
    }

    public Integer getParagraphKnowledge() {
        return paragraphKnowledge;
    }

    public String getParagraphType() {
        return paragraphType;
    }

    public String getParagraphNewline() {
        return paragraphNewline;
    }

    public String getParagraphContent() {
        return paragraphContent;
    }

    public Integer getCid() {
        return cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public String getCourseIcon() {
        return courseIcon;
    }
}
