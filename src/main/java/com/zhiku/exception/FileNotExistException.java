package com.zhiku.exception;

/*
 * 下载预览时如果遇到文件不存在时抛出该异常
 */
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
