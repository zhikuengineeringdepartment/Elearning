package com.zhiku.entity;

import java.util.Date;

public class Action {
    private Integer aid;

    private Integer actionUser;

    private Date actionDate;

    private String actionType;

    private String actionItem;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getActionUser() {
        return actionUser;
    }

    public void setActionUser(Integer actionUser) {
        this.actionUser = actionUser;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public String getActionItem() {
        return actionItem;
    }

    public void setActionItem(String actionItem) {
        this.actionItem = actionItem == null ? null : actionItem.trim();
    }
}