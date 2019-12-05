package com.zhiku.entity;

import java.util.Date;

public class Picture {
    private Integer picId;

    private Integer uid;

    private String orgName;

    private Integer cid;

    private String sections;

    private String url;

    private String bupUrl;

    private Date createTime;

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections == null ? null : sections.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getBupUrl() {
        return bupUrl;
    }

    public void setBupUrl(String bupUrl) {
        this.bupUrl = bupUrl == null ? null : bupUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}