package com.zhiku.mapper;
import com.zhiku.entity.mysql.Role;
import org.springframework.stereotype.Component;

@Component
public interface RoleMapper {
    int deleteByPrimaryKey(Role key);

    int insert(Role record);

    int insertSelective(Role record);
}