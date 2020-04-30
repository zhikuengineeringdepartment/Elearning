package com.zhiku.mapper;

import com.zhiku.entity.UserCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface UserCodeMapper {
    @Transactional
    UserCode selectByPrimaryKey(Integer uid);

    int deleteByPrimaryKey(Integer uid);

    int insert(UserCode record);

    int updateByPrimaryKey(UserCode userCode);

}
