package com.zhiku.entity.mysql;

import java.util.Date;

public class ColCourse extends ColCourseKey {
    private Date colcDate;

    public Date getColcDate() {
        return colcDate;
    }

    public void setColcDate(Date colcDate) {
        this.colcDate = colcDate;
    }
}