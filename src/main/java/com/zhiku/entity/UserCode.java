package com.zhiku.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserCode {
    private User user;

    private String userCode;

    private String deadline;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public UserCode(){};

    public UserCode(User user, String userCode){
        this.user = user;
        this.userCode = userCode;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        this.deadline = dateFormat.format(calendar);
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getUserCode(){
        return userCode;
    }

    public void setUserCode(String userCode){
        this.userCode = userCode;
    }

    public String getDeadline(){
        return deadline;
    }

    public void setDeadline(Calendar calendar) {
        this.deadline = dateFormat.format(calendar);
    }
}
