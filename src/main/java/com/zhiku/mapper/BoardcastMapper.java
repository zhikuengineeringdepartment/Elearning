package com.zhiku.mapper;

import com.zhiku.entity.Boardcast;

public interface BoardcastMapper {
    int deleteByPrimaryKey(Integer bid);

    int insert(Boardcast record);

    int insertSelective(Boardcast record);

    Boardcast selectByPrimaryKey(Integer bid);

    int updateByPrimaryKeySelective(Boardcast record);

    int updateByPrimaryKeyWithBLOBs(Boardcast record);

    int updateByPrimaryKey(Boardcast record);
}