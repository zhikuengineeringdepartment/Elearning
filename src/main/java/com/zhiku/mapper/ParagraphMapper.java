package com.zhiku.mapper;

import com.zhiku.entity.Paragraph;

public interface ParagraphMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Paragraph record);

    int insertSelective(Paragraph record);

    Paragraph selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Paragraph record);

    int updateByPrimaryKeyWithBLOBs(Paragraph record);

    int updateByPrimaryKey(Paragraph record);
}