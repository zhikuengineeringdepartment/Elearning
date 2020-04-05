package com.zhiku.service;


import com.zhiku.entity.mysql.SpecialColumn;
import com.zhiku.entity.mysql.SpecialColumnArticle;
import com.zhiku.mapper.SpecialColumnArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecialColumnArticleService {

    @Autowired
    private SpecialColumnArticleMapper specialColumnArticleMapper;

    public List<SpecialColumnArticle> list(SpecialColumnArticle specialColumnArticle, Integer start, Integer size){
        Map<String,Object> hashMap=new HashMap();
        if(specialColumnArticle.getAid()!=null){
            hashMap.put("aid",specialColumnArticle.getAid());
        }
        if(specialColumnArticle.getArticlePicUrl()!=null){
            hashMap.put("articlePicUrl",specialColumnArticle.getArticlePicUrl());
        }
        if(specialColumnArticle.getArticleTitle()!=null){
            hashMap.put("articleTitle",specialColumnArticle.getArticleTitle());
        }
        if(specialColumnArticle.getArticleUrl()!=null){
            hashMap.put("articleUrl",specialColumnArticle.getArticleUrl());
        }
        if(specialColumnArticle.getCreateTime()!=null){
            hashMap.put("createTime",specialColumnArticle.getCreateTime());
        }
        if(specialColumnArticle.getIsDelete()!=null){
            hashMap.put("isDelete",specialColumnArticle.getIsDelete());
        }
        if (specialColumnArticle.getUpdateTime()!=null){
            hashMap.put("updateTime",specialColumnArticle.getUpdateTime());
        }
        if(specialColumnArticle.getSpecialColumnId()!=null){
            hashMap.put("specialColumnId",specialColumnArticle.getSpecialColumnId());
        }
        hashMap.put("start",start);
        hashMap.put("size",size);
        return specialColumnArticleMapper.list(hashMap);
    }
    public int getTotal(SpecialColumnArticle specialColumnArticle){
        return specialColumnArticleMapper.getTotal(specialColumnArticle);
    }
    public int deleteById(Integer aid){
       return specialColumnArticleMapper.deleteByPrimaryKey(aid);
    }

    public int add(SpecialColumnArticle specialColumnArticle) {
        return specialColumnArticleMapper.insert(specialColumnArticle);
    }

    public int update(SpecialColumnArticle specialColumnArticle) {
        return specialColumnArticleMapper.updateByPrimaryKey(specialColumnArticle);
    }

    public int delete(Integer id) {
        return specialColumnArticleMapper.deleteByPrimaryKey(id);
    }
}
