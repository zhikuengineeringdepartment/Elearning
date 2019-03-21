package com.zhiku.mapper;

import com.zhiku.entity.Schedule;
import com.zhiku.entity.ScheduleKey;

public interface ScheduleMapper {
    int deleteByPrimaryKey(ScheduleKey key);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(ScheduleKey key);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);
}