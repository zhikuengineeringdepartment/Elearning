package com.zhiku.entity;

import java.util.Date;

public class Schedule extends ScheduleKey {
    private Date scheduleDate;

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}