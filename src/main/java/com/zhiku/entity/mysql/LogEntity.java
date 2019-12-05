package com.zhiku.entity.mysql;

import com.google.gson.Gson;
import com.zhiku.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录的模板实体类
 */
public class LogEntity {
    private User user;
    private String ip;
    private String requestName;
    private Map<String,Object> args = new HashMap<>();      //记录参数名和参数值

    public void addItemToArgs(String key,Object value){
        args.put(key,value);
    }

    public User getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }

    public String getRequestName() {
        return requestName;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "{" +
                "user=" + user +
                ", ip='" + ip + '\'' +
                ", requestName='" + requestName + '\'' +
                ", args=" + toStringArgs() +
                '}';
    }

    private String toStringArgs(){
        Gson gson = new Gson();
        return gson.toJson(args);
    }
}
