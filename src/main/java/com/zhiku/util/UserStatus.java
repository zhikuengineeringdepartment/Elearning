package com.zhiku.util;

/**
 * 用户状态枚举类
 */
public enum UserStatus {
    NORMAL("n","正常"),FORBID("f","该账户已经被封禁"),UNCHECKED("u","该账户邮箱尚未激活"),EMAIL_TIMEOUT("t","邮箱激活时间到期");

    private final String code;  //用户状态
    private final String message;   //状态说明

    private UserStatus(String code,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
