package com.zhiku.mapper;

import com.zhiku.entity.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);
}