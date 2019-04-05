package com.zhiku.exception;

public class TokenVerifyErrorException extends RuntimeException{
    private String msg;

    public TokenVerifyErrorException(){}
    public TokenVerifyErrorException(String msg){
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
