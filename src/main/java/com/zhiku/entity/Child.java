package com.zhiku.entity;

public class Child {
    private String sid;
    private String section_seq;
    private String section_name;
    private String level;

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSection_seq() {
        return section_seq;
    }

    public void setSection_seq(String section_seq) {
        this.section_seq = section_seq;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
