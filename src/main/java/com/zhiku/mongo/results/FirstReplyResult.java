package com.zhiku.mongo.results;

import com.zhiku.entity.PostFirstReply;

public class FirstReplyResult {
    private String _id;
    private PostFirstReply postFirstReplyList;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public PostFirstReply getPostFirstReplyList() {
        return postFirstReplyList;
    }

    public void setPostFirstReplyList(PostFirstReply postFirstReplyList) {
        this.postFirstReplyList = postFirstReplyList;
    }
}
