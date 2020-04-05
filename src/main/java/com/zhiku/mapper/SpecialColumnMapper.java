package com.zhiku.mapper;


import com.zhiku.entity.mysql.SpecialColumn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SpecialColumnMapper {

    int getTotal(SpecialColumn specialColumn);

    List<SpecialColumn> list(Map<String,Object> hashMap);

    int deleteByPrimaryKey(Integer sid);

    int insert(SpecialColumn specialColumn);

    int insertSelective(SpecialColumn specialColumn);

    SpecialColumn  selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(SpecialColumn specialColumn);

    int updateByPrimaryKey(SpecialColumn specialColumn);

}
