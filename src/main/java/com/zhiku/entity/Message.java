package com.zhiku.entity;

import java.util.Date;

public class Message {
    private Integer mid;

    private Integer messageFrom;

    private Integer messageTo;

    private Date messageDate;

    private String messageRead;

    private String messageContent;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(Integer messageFrom) {
        this.messageFrom = messageFrom;
    }

    public Integer getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(Integer messageTo) {
        this.messageTo = messageTo;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageRead() {
        return messageRead;
    }

    public void setMessageRead(String messageRead) {
        this.messageRead = messageRead == null ? null : messageRead.trim();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }
}