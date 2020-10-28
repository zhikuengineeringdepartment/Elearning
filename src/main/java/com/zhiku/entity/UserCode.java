package com.zhiku.entity;

public class UserCode {
    private User user;

    private String userCode;

    private String deadline;
    //验证码用途，后期扩展，目前只用1
    private Integer function;

    public UserCode() {
    }

    ;

    public UserCode(User user, String userCode, String deadline, Integer function) {
        this.user = user;
        this.userCode = userCode;
        this.deadline = deadline;
        this.function = function;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getFunction() {
        return function;
    }

    public void setFunction(Integer function) {
        this.function = function;
    }
}