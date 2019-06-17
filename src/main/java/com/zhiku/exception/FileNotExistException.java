package com.zhiku.exception;

public class FileNotExistException extends RuntimeException {
    private String msg;

    public FileNotExistException(){}
    public FileNotExistException(String msg){super(msg);}

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
