package com.zhiku.exception;

public class UserNotFoundException extends Exception {
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
