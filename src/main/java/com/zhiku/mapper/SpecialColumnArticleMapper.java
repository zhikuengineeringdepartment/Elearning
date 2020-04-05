package com.zhiku.mapper;


import com.zhiku.entity.mysql.SpecialColumnArticle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SpecialColumnArticleMapper {

    int getTotal(SpecialColumnArticle specialColumnArticle);

    List<SpecialColumnArticle> list( Map<String,Object> hashMap);

    int deleteByPrimaryKey(Integer aid);

    int insert(SpecialColumnArticle specialColumnArticle);

    int insertSelective(SpecialColumnArticle specialColumnArticle);

    SpecialColumnArticle  selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(SpecialColumnArticle specialColumnArticle);

    int updateByPrimaryKey(SpecialColumnArticle specialColumnArticle);

}
