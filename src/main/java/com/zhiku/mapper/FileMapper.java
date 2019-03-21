package com.zhiku.mapper;

import com.zhiku.entity.File;

public interface FileMapper {
    int deleteByPrimaryKey(Integer fid);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer fid);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKeyWithBLOBs(File record);

    int updateByPrimaryKey(File record);

    File selectBySha(String sha);
}