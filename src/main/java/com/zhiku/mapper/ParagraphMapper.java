package com.zhiku.mapper;

import com.zhiku.entity.mysql.ParagraphMysql;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ParagraphMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(ParagraphMysql record);

    int insertSelective(ParagraphMysql record);

    ParagraphMysql selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(ParagraphMysql record);

    int updateByPrimaryKeyWithBLOBs(ParagraphMysql record);

    int updateByPrimaryKey(ParagraphMysql record);

//    自定义方法
    int insertAll(List<Paragraph> record);

    ParagraphMysql selectByParagraphSeq(Integer paragraphSeq);

    int deleteBySeqCourse(Integer cid);
}