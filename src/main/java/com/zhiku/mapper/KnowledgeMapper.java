package com.zhiku.mapper;

import com.zhiku.entity.mysql.Knowledge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface KnowledgeMapper {
    int deleteByPrimaryKey(Integer kid);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer kid);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);

    //自定义
    int insertAllGetIds(List<Knowledge> record);

    int deleteBySeqCourse(Integer cid);
}