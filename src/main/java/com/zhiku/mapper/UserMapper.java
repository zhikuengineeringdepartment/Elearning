package com.zhiku.mapper;

import com.zhiku.entity.User;
import com.zhiku.view.UserBaseInfoView;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface UserMapper {
    @Transactional
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //自定义方法
    User selectByUsername(String username);
    User selectByEmail(String email);

    UserBaseInfoView selectBaseInfo(Integer uid);

    List<User> selectByUids(List<Integer> uids);
}