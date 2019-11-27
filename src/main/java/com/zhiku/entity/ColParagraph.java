package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Document(collection = "content")
public class ColParagraph extends ColParagraphKey {
    @Id
    private ObjectId id;
    @Field("paragraph_seq")
    private Integer paraSeq;
    private Date colpDate;

    public ObjectId getId() {
        return id;
    }

    public Integer getParaSeq() {
        return paraSeq;
    }

    public Date getColpDate() {
        return colpDate;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setParaSeq(Integer paraSeq) {
        this.paraSeq = paraSeq;
    }

    public void setColpDate(Date colpDate) {
        this.colpDate = colpDate;
    }
}