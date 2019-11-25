package com.zhiku.entity;


import java.util.List;

public class PostMultistepReply extends PostFirstReply{

    private Integer replyedAuthor;  //被回复者Id

    public Integer getReplyedAuthor() {
        return replyedAuthor;
    }

    public void setReplyedAuthor(Integer replyedAuthor) {
        this.replyedAuthor = replyedAuthor;
    }




}
