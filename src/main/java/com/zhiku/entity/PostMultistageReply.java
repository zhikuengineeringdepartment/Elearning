package com.zhiku.entity;

import java.util.List;

public class PostMultistageReply {

    private String rId;

    private String replyContent; //回复内容(待定)

    private Integer replyAuthor;  //回复者Id

    private Integer replyedAuthor; //被回复者Id

    private Integer agreeCount;   //点赞数

    private List<Integer> agreeUsers; //点赞用户列表

    private Integer disagreeCount; //反对数

    private List<Integer> disagreeUsers; //反对用户列表

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Integer getReplyAuthor() {
        return replyAuthor;
    }

    public void setReplyAuthor(Integer replyAuthor) {
        this.replyAuthor = replyAuthor;
    }

    public Integer getReplyedAuthor() {
        return replyedAuthor;
    }

    public void setReplyedAuthor(Integer replyedAuthor) {
        this.replyedAuthor = replyedAuthor;
    }

    public Integer getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(Integer agreeCount) {
        this.agreeCount = agreeCount;
    }

    public List<Integer> getAgreeUsers() {
        return agreeUsers;
    }

    public void setAgreeUsers(List<Integer> agreeUsers) {
        this.agreeUsers = agreeUsers;
    }

    public Integer getDisagreeCount() {
        return disagreeCount;
    }

    public void setDisagreeCount(Integer disagreeCount) {
        this.disagreeCount = disagreeCount;
    }

    public List<Integer> getDisagreeUsers() {
        return disagreeUsers;
    }

    public void setDisagreeUsers(List<Integer> disagreeUsers) {
        this.disagreeUsers = disagreeUsers;
    }
}
