package com.zhiku.mapper;

import com.zhiku.entity.mysql.Schedule;
import com.zhiku.entity.mysql.ScheduleKey;
import org.springframework.stereotype.Component;

@Component
public interface ScheduleMapper {
    int deleteByPrimaryKey(ScheduleKey key);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(ScheduleKey key);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);
}