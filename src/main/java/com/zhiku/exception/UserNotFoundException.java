package com.zhiku.exception;

/**
 * 用户找不到异常，在根据用户名或邮箱找用户时可能触发该异常
 */
public class UserNotFoundException extends RuntimeException {
    private String usrMsg;

    public UserNotFoundException(){}
    public UserNotFoundException(String usrMsg){
        super(usrMsg);
    }

    public String getUsrMsg() {
        return usrMsg;
    }

    public void setUsrMsg(String usrMsg) {
        this.usrMsg = usrMsg;
    }
}
