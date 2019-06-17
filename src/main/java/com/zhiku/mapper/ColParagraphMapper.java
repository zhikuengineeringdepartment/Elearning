package com.zhiku.mapper;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.ColParagraphKey;
import com.zhiku.view.ColParagraphView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ColParagraphMapper {
    int deleteByPrimaryKey(ColParagraphKey key);

    int insert(ColParagraph record);

    int insertSelective(ColParagraph record);

    ColParagraph selectByPrimaryKey(ColParagraphKey key);

    int updateByPrimaryKeySelective(ColParagraph record);

    int updateByPrimaryKey(ColParagraph record);

    List<ColParagraph> selectBySid(@Param("uid") int uid, @Param("sid") int sid);

    List<ColParagraphView> selectParagraphView(int uid);
}