package com.zhiku.view;

import com.zhiku.entity.mysql.AccessRecord;

public class AccessRecordView extends AccessRecord {
    private String pageName;//页面名称
    private Integer stayTimeAvg;//平均停留时间，单位秒
    private Integer accessIpNumber;//访问人数，以ip记

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Integer getStayTimeAvg() {
        return stayTimeAvg;
    }

    public void setStayTimeAvg(Integer stayTimeAvg) {
        this.stayTimeAvg = stayTimeAvg;
    }

    public Integer getAccessIpNumber() {
        return accessIpNumber;
    }

    public void setAccessIpNumber(Integer accessIpNumber) {
        this.accessIpNumber = accessIpNumber;
    }
}
