package com.zhiku.entity.mysql;

import java.util.Date;

public class SpecialColumn {

    private Integer sid;

    private String specialcName;

    private String speaiclcRemark;

    private Date createTime;

    private Date updateTime;

    private boolean isDelete;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSpecialcName() {
        return specialcName;
    }

    public void setSpecialcName(String specialcName) {
        this.specialcName = specialcName;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getSpeaiclcRemark() {
        return speaiclcRemark;
    }

    public void setSpeaiclcRemark(String speaiclcRemark) {
        this.speaiclcRemark = speaiclcRemark;
    }
}
