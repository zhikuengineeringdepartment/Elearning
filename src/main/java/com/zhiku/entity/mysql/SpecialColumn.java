package com.zhiku.entity.mysql;

import java.util.Date;

public class SpecialColumn {

    private Integer sid;

    private String specialcName;

    private String specialcRemark;

    private Date createTime;

    private Date updateTime;

    private  Integer isDelete;  //是否删除 0未删除  1删除

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

    public String getSpecialcRemark() {
        return specialcRemark;
    }

    public void setSpecialcRemark(String specialcRemark) {
        this.specialcRemark = specialcRemark;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public SpecialColumn() {
    }

    public SpecialColumn(String specialcName, String specialcRemark, Date createTime, Date updateTime, Integer isDelete) {
        this.specialcName = specialcName;
        this.specialcRemark = specialcRemark;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public SpecialColumn(Integer sid, String specialcName, String specialcRemark,Date updateTime) {
        this.sid = sid;
        this.specialcName = specialcName;
        this.specialcRemark = specialcRemark;
        this.updateTime = updateTime;
    }
}
