package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Document(collection = "collect")
public class ColParagraph{
    @Id
    private ObjectId id;
    @Field("paragraph_seq")
    private ObjectId paraSeq;
    @Field("colp_date")
    private Date colpDate;
    @Field("colp_user")
    private int colpUser;

    public ObjectId getId() {
        return id;
    }

    public ObjectId getParaSeq() {
        return paraSeq;
    }

    public Date getColpDate() {
        return colpDate;
    }

    public int getColpUser() {
        return colpUser;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setParaSeq(ObjectId paraSeq) {
        this.paraSeq = paraSeq;
    }

    public void setColpDate(Date colpDate) {
        this.colpDate = colpDate;
    }

    public void setColpUser(int colpUser) {
        this.colpUser = colpUser;
    }
}