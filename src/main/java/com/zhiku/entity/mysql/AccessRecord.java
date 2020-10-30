package com.zhiku.entity.mysql;

import java.util.Date;

public class AccessRecord {
    private Integer aid;

    private String ip;

    private String uri;

    private Date date;

    private Integer number;

    private Integer stayTime;

    private Date latestTime;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public AccessRecord(){ }

    public AccessRecord(AccessRecord accessRecord){
        this.aid=accessRecord.aid;
        this.ip=accessRecord.ip;
        this.uri=accessRecord.uri;
        this.date=accessRecord.date;
        this.number=accessRecord.number;
        this.stayTime=accessRecord.stayTime;
        this.latestTime=accessRecord.latestTime;
    }
}
