package com.zhiku.entity.mysql;

import java.util.Date;

public class Fileop {
    private Integer fopid;

    private Integer fopUser;

    private Integer fopFile;

    private Date fopDate;

    private String fopIp;

    private String fopType;

    private String fopDesc;

    public Integer getFopid() {
        return fopid;
    }

    public void setFopid(Integer fopid) {
        this.fopid = fopid;
    }

    public Integer getFopUser() {
        return fopUser;
    }

    public void setFopUser(Integer fopUser) {
        this.fopUser = fopUser;
    }

    public Integer getFopFile() {
        return fopFile;
    }

    public void setFopFile(Integer fopFile) {
        this.fopFile = fopFile;
    }

    public Date getFopDate() {
        return fopDate;
    }

    public void setFopDate(Date fopDate) {
        this.fopDate = fopDate;
    }

    public String getFopIp() {
        return fopIp;
    }

    public void setFopIp(String fopIp) {
        this.fopIp = fopIp == null ? null : fopIp.trim();
    }

    public String getFopType() {
        return fopType;
    }

    public void setFopType(String fopType) {
        this.fopType = fopType == null ? null : fopType.trim();
    }

    public String getFopDesc() {
        return fopDesc;
    }

    public void setFopDesc(String fopDesc) {
        this.fopDesc = fopDesc == null ? null : fopDesc.trim();
    }
}