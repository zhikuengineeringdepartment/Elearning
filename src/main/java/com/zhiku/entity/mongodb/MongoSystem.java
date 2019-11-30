package com.zhiku.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "system")
public class MongoSystem {
    @Id
    private ObjectId  _id;
    @Field("max_cid")
    private Integer maxCid;
    @Field("max_sid")
    private Integer maxSid;


    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Integer getMaxSid() {
        return maxSid;
    }

    public void setMaxSid(Integer maxSid) {
        this.maxSid = maxSid;
    }

    public Integer getMaxCid() {
        return maxCid;
    }

    public void setMaxCid(Integer maxCid) {
        this.maxCid = maxCid;
    }
}
