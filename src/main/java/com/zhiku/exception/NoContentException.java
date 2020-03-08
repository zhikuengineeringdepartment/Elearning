package com.zhiku.exception;

/**
 * 内容为空异常（可用于查询不到帖子内容或回复内容时）
 */
public class NoContentException extends RuntimeException {
    private String msg;

    public NoContentException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
