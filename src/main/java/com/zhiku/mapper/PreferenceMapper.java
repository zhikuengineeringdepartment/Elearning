package com.zhiku.mapper;

import com.zhiku.entity.Preference;

import java.util.List;

public interface PreferenceMapper {
    int deleteByPrimaryKey(Integer prfid);

    int insert(Preference record);

    int insertSelective(Preference record);

    Preference selectByPrimaryKey(Integer prfid);

    int updateByPrimaryKeySelective(Preference record);

    int updateByPrimaryKey(Preference record);

    //自定义方法
    List<Preference> selectAllPreferences();

}