package com.zhiku.mapper;

import com.zhiku.entity.User;
import com.zhiku.view.UserBaseInfoView;
import javafx.util.Pair;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    List<Map<String,Object> > countByDateInterval(@Param( "begin" ) Date begin, @Param( "end" ) Date end);

}