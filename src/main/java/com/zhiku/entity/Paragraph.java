package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("content")
public class Paragraph {
    @Id
    private ObjectId id;
    private Integer pid;
    @Field("paragraph_seq")
    private Integer paragraphSeq;
    @Field("paragraph_knowledge")
    private Integer paragraphKnowledge;
    @Field("paragraph_type")
    private String paragraphType;
    @Field("paragraph_newline")
    private String paragraphNewline;
    @Field("paragraph_content")
    private String paragraphContent;

    public ObjectId getId() {
        return id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getParagraphSeq() {
        return paragraphSeq;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getParagraphNewline() {
        return paragraphNewline;
    }

    public void setParagraphNewline(String paragraphNewline) {
        this.paragraphNewline = paragraphNewline == null ? null : paragraphNewline.trim();
    }

    public String getParagraphContent() {
        return paragraphContent;
    }

    public void setParagraphContent(String paragraphContent) {
        this.paragraphContent = paragraphContent == null ? null : paragraphContent.trim();
    }
}