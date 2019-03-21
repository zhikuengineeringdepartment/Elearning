package com.zhiku.mapper;

import com.zhiku.entity.Action;

public interface ActionMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Action record);

    int insertSelective(Action record);

    Action selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKeyWithBLOBs(Action record);

    int updateByPrimaryKey(Action record);
}