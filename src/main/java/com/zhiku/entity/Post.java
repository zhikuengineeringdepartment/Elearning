package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(value = "post")
public class Post {

    private ObjectId pid;    //帖子Id

    private String title;    //文章标题

    private String content;  //文章内容(格式待确定)

    private Integer author;  //关联用户Id

    private Date createTime; //创建时间

    private Date updateTime; //更新时间

    private Integer agreeCount;  //点赞数

    private List<Integer> agreeUsers;  //点赞用户列表(格式待定)

    private Integer disagreeCount;   //反对数目

    private List<Integer> disagreeUsers;  //反对的用户列表(格式待定)

    private Integer replyCount ;    //回复数

    private boolean isDelete;       //是否删除(格式待定)

    private Integer courseId;      //关联的课程Id,没有则为0


    public ObjectId getPid() {
        return pid;
    }

    public void setPid(ObjectId pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }


}
