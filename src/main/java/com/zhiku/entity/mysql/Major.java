package com.zhiku.entity.mysql;

public class Major {
    private Integer majorId;

    private String majorName;

    private Integer majorCollege;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public Integer getMajorCollege() {
        return majorCollege;
    }

    public void setMajorCollege(Integer majorCollege) {
        this.majorCollege = majorCollege;
    }
}