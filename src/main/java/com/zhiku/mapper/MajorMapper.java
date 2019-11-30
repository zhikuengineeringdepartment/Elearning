package com.zhiku.mapper;

import com.zhiku.entity.mysql.Major;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface MajorMapper {
    int deleteByPrimaryKey(Integer majorId);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer majorId);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);

//    自定义方法
    List<Major> selectAllMajors();
}