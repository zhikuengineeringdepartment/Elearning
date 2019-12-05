package com.zhiku.exception;

/**
 * Token验证时验证不正确抛出该异常，表示用户登录信息过期或错误
 */
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
