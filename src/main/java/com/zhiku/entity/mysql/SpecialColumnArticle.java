package com.zhiku.entity.mysql;

import java.util.Date;

public class SpecialColumnArticle {

    private Integer aid;

    private String articleTitle;

    private String articleUrl;

    private String articlePicUrl;

    private Integer specialColumnId;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;  //是否删除 0未删除  1删除

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public Integer getSpecialColumnId() {
        return specialColumnId;
    }

    public void setSpecialColumnId(Integer specialColumnId) {
        this.specialColumnId = specialColumnId;
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

    public String getArticlePicUrl() {
        return articlePicUrl;
    }

    public void setArticlePicUrl(String articlePicUrl) {
        this.articlePicUrl = articlePicUrl;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public SpecialColumnArticle() {
    }

    public SpecialColumnArticle(String articleTitle, String articleUrl, String articlePicUrl, Integer specialColumnId, Date createTime, Date updateTime, Integer isDelete) {
        this.articleTitle = articleTitle;
        this.articleUrl = articleUrl;
        this.articlePicUrl = articlePicUrl;
        this.specialColumnId = specialColumnId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public SpecialColumnArticle(Integer aid, String articleTitle, String articleUrl, String articlePicUrl, Integer specialColumnId, Date updateTime) {
        this.aid = aid;
        this.articleTitle = articleTitle;
        this.articleUrl = articleUrl;
        this.articlePicUrl = articlePicUrl;
        this.specialColumnId = specialColumnId;
        this.updateTime = updateTime;
    }
}
