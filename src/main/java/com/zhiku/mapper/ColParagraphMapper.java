package com.zhiku.mapper;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.ColParagraphKey;

public interface ColParagraphMapper {
    int deleteByPrimaryKey(ColParagraphKey key);

    int insert(ColParagraph record);

    int insertSelective(ColParagraph record);

    ColParagraph selectByPrimaryKey(ColParagraphKey key);

    int updateByPrimaryKeySelective(ColParagraph record);

    int updateByPrimaryKey(ColParagraph record);
}