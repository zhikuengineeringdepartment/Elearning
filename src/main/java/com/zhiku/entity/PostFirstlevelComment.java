package com.zhiku.entity;


import java.util.List;

public class PostFirstlevelComment {

    private String cid;    //id 不重复

    private Integer author;  //评论用户Id

    private String comment_content; //回答内容(待定)

    private Integer agreeCount;   //点赞数

    private List<Integer> agreeUsers; //点赞用户列表

    private Integer disagreeCount; //反对数

    private List<Integer> disagreeUsers; //反对用户列表

    private List<PostMultistageReply> postMultistageReplyList;


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
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

    public List<PostMultistageReply> getPostMultistageReplyList() {
        return postMultistageReplyList;
    }

    public void setPostMultistageReplyList(List<PostMultistageReply> postMultistageReplyList) {
        this.postMultistageReplyList = postMultistageReplyList;
    }
}
