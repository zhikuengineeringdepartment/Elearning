package com.zhiku.mapper;

import com.zhiku.entity.Message;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

//    自定义方法
    List<Message> selectMessagesByUser(Message message);
}