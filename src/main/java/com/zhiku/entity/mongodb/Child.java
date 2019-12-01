package com.zhiku.entity.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

public class Child {
    private int sid;
    private Integer section_seq;
    private String section_name;
    private int level;
    private List<Document> contents;
    private List<Child> sub;

    public List<Document> getContents() {
        return contents;
    }

    public void setContents(List<Document> contents) {
        this.contents = contents;
    }

    public List<Child> getSub() {
        return sub;
    }

    public void setSub(List<Child> sub) {
        this.sub = sub;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getSection_seq() {
        return section_seq;
    }

    public void setSection_seq(Integer section_seq) {
        this.section_seq = section_seq;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
