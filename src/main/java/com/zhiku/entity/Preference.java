package com.zhiku.entity;

public class Preference {
    private Integer prfid;

    private String prfName;

    public Integer getPrfid() {
        return prfid;
    }

    public void setPrfid(Integer prfid) {
        this.prfid = prfid;
    }

    public String getPrfName() {
        return prfName;
    }

    public void setPrfName(String prfName) {
        this.prfName = prfName == null ? null : prfName.trim();
    }
}