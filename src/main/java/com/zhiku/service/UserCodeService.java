package com.zhiku.service;

import com.zhiku.entity.UserCode;
import com.zhiku.mapper.UserCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCodeService {
    @Autowired
    private UserCodeMapper userCodeMapper;

    public void saveUserCode(UserCode userCode){
        userCodeMapper.insert(userCode);
    }

    public UserCode getUserCodeById(int uid){
        UserCode record = userCodeMapper.selectByPrimaryKey(uid);
        return record;
    }

    public void deleteByPrimaryKey(int uid){
        userCodeMapper.deleteByPrimaryKey(uid);
    }
}
