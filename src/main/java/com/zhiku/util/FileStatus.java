package com.zhiku.util;

public enum FileStatus {
    NORMAL("正常"),DUPLICATE("文件已存在"),TOO_LARGE("文件过大"),TYPE_ERROR("文件类型错误"),FILE_ERROR("上传文件受损");
    private final String name;

    private FileStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
