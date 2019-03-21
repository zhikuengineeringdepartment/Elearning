package com.zhiku.entity;

import java.util.Date;

public class Boardcast {
    private Integer bid;

    private Date boardcastDate;

    private String boardcastImg;

    private String boardcastLink;

    private String boardcastContent;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Date getBoardcastDate() {
        return boardcastDate;
    }

    public void setBoardcastDate(Date boardcastDate) {
        this.boardcastDate = boardcastDate;
    }

    public String getBoardcastImg() {
        return boardcastImg;
    }

    public void setBoardcastImg(String boardcastImg) {
        this.boardcastImg = boardcastImg == null ? null : boardcastImg.trim();
    }

    public String getBoardcastLink() {
        return boardcastLink;
    }

    public void setBoardcastLink(String boardcastLink) {
        this.boardcastLink = boardcastLink == null ? null : boardcastLink.trim();
    }

    public String getBoardcastContent() {
        return boardcastContent;
    }

    public void setBoardcastContent(String boardcastContent) {
        this.boardcastContent = boardcastContent == null ? null : boardcastContent.trim();
    }
}