package com.zhiku.entity;

import java.util.Date;
import java.util.Objects;

public class User {
    private Integer uid;

    private String userUsername;

    private String userNick;

    private String userPassword;

    private String userAvatar;

    private String userSign;

    private String userGender;

    private Integer userCoin;

    private String userEmail;

    private String userPhone;

    private String userQq;

    private Integer userCollege;

    private Integer userMajor;

    private String userAuth;

    private String userStatus;

    private Date userRegtime;

    private String userRegip;

    private Date userLasttime;

    private String userLastip;

    private Date userMailtime;

    private Integer userUploadCount;

    private Integer userDownloadCount;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername == null ? null : userUsername.trim();
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick == null ? null : userNick.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar == null ? null : userAvatar.trim();
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign == null ? null : userSign.trim();
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender == null ? null : userGender.trim();
    }

    public Integer getUserCoin() {
        return userCoin;
    }

    public void setUserCoin(Integer userCoin) {
        this.userCoin = userCoin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq == null ? null : userQq.trim();
    }

    public Integer getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(Integer userCollege) {
        this.userCollege = userCollege;
    }

    public Integer getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(Integer userMajor) {
        this.userMajor = userMajor;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth == null ? null : userAuth.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public Date getUserRegtime() {
        return userRegtime;
    }

    public void setUserRegtime(Date userRegtime) {
        this.userRegtime = userRegtime;
    }

    public String getUserRegip() {
        return userRegip;
    }

    public void setUserRegip(String userRegip) {
        this.userRegip = userRegip == null ? null : userRegip.trim();
    }

    public Date getUserLasttime() {
        return userLasttime;
    }

    public void setUserLasttime(Date userLasttime) {
        this.userLasttime = userLasttime;
    }

    public String getUserLastip() {
        return userLastip;
    }

    public void setUserLastip(String userLastip) {
        this.userLastip = userLastip == null ? null : userLastip.trim();
    }

    public Date getUserMailtime() {
        return userMailtime;
    }

    public void setUserMailtime(Date userMailtime) {
        this.userMailtime = userMailtime;
    }

    public Integer getUserUploadCount() {
        return userUploadCount;
    }

    public void setUserUploadCount(Integer userUploadCount) {
        this.userUploadCount = userUploadCount;
    }

    public Integer getUserDownloadCount() {
        return userDownloadCount;
    }

    public void setUserDownloadCount(Integer userDownloadCount) {
        this.userDownloadCount = userDownloadCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid.equals(user.uid) &&
                userUsername.equals(user.userUsername) &&
                userPassword.equals(user.userPassword) &&
                userEmail.equals(user.userEmail) &&
                userMailtime.equals(user.userMailtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, userUsername, userPassword, userEmail, userMailtime);
    }
}