package com.zhiku.entity;

public class Paragraph {
    private Integer pid;

    private Integer paragraphSeq;

    private Integer paragraphKnowledge;

    private String paragraphType;

    private String paragraphContent;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getParagraphSeq() {
        return paragraphSeq;
    }

    public void setParagraphSeq(Integer paragraphSeq) {
        this.paragraphSeq = paragraphSeq;
    }

    public Integer getParagraphKnowledge() {
        return paragraphKnowledge;
    }

    public void setParagraphKnowledge(Integer paragraphKnowledge) {
        this.paragraphKnowledge = paragraphKnowledge;
    }

    public String getParagraphType() {
        return paragraphType;
    }

    public void setParagraphType(String paragraphType) {
        this.paragraphType = paragraphType == null ? null : paragraphType.trim();
    }

    public String getParagraphContent() {
        return paragraphContent;
    }

    public void setParagraphContent(String paragraphContent) {
        this.paragraphContent = paragraphContent == null ? null : paragraphContent.trim();
    }
}