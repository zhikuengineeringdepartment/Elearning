package com.zhiku.mapper;

import com.zhiku.entity.mysql.WebPage;

public interface WebPageMapper {
    int deleteByPrimaryKey(String uri);

    int insert(WebPage record);

    int insertSelective(WebPage record);

    WebPage selectByPrimaryKey(String uri);

    int updateByPrimaryKeySelective(WebPage record);

    int updateByPrimaryKey(WebPage record);
}