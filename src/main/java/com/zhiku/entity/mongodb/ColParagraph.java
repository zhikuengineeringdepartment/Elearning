package com.zhiku.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "collect")
public class ColParagraph {
    @Id
    @Field("_id")
    private ObjectId id;
    @Field("colp_para")
    private ObjectId colpPara;
    @Field("colp_date")
    private Date colpDate;
    @Field("colp_user")
    private Integer colpUser;

    public Integer getColpUser() {
        return colpUser;
    }

    public void setColpUser(Integer colpUser) {
        this.colpUser = colpUser;
    }

    public ObjectId getId() {
        return id;
    }

    public ObjectId getColpPara() {
        return colpPara;
    }

    public Date getColpDate() {
        return colpDate;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setColpPara(ObjectId colpPara) {
        this.colpPara = colpPara;
    }

    public void setColpDate(Date colpDate) {
        this.colpDate = colpDate;
    }
}