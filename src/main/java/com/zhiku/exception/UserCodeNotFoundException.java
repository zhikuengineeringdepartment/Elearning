package com.zhiku.exception;

public class UserCodeNotFoundException extends RuntimeException{
    private String msg;

    public UserCodeNotFoundException(){}
    public UserCodeNotFoundException(String msg){
        super(msg);
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}
