package com.zhiku.mapper;

import com.zhiku.entity.PreferKey;
import com.zhiku.view.PreferView;
import org.springframework.stereotype.Component;

@Component
public interface PreferMapper {
    int deleteByPrimaryKey(PreferKey key);

    int insert(PreferKey record);

    int insertSelective(PreferKey record);

    //自定义SQL
    PreferView selectPreferViewByUid(int uid);
}