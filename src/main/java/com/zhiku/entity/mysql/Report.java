package com.zhiku.entity.mysql;

import java.util.Date;

public class Report {
    public static String TYPE_POST="post",TYPE_FIRST_REPLY="first_reply",TYPE_SECOND_REPLY="second_reply";
    public static String STATE_NULL="u",STATE_SUCCESS="n",STATE_FAIL="f";
    private Integer rid;

    private String pid;

    private String reid;

    private Integer uid;

    private String type;

    private Date date;

    private String state;

    private String reason;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid == null ? null : reid.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}