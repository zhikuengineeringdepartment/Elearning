package com.zhiku.entity;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "postReply")
public class PostReply {

    private ObjectId prId;

    private ObjectId pid; //对应帖子Id (正确性待考察)

    private List<PostFirstlevelComment> postFirstlevelCommentList;

    public ObjectId getPrId() {
        return prId;
    }

    public void setPrId(ObjectId prId) {
        this.prId = prId;
    }

    public ObjectId getPid() {
        return pid;
    }

    public void setPid(ObjectId pid) {
        this.pid = pid;
    }

    public List<PostFirstlevelComment> getPostFirstlevelCommentList() {
        return postFirstlevelCommentList;
    }

    public void setPostFirstlevelCommentList(List<PostFirstlevelComment> postFirstlevelCommentList) {
        this.postFirstlevelCommentList = postFirstlevelCommentList;
    }
}
