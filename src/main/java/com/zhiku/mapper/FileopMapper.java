package com.zhiku.mapper;

import com.zhiku.entity.Fileop;

public interface FileopMapper {
    int deleteByPrimaryKey(Integer fopid);

    int insert(Fileop record);

    int insertSelective(Fileop record);

    Fileop selectByPrimaryKey(Integer fopid);

    int updateByPrimaryKeySelective(Fileop record);

    int updateByPrimaryKey(Fileop record);
}