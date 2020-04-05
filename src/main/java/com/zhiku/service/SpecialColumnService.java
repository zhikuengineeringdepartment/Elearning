package com.zhiku.service;


import com.zhiku.entity.mysql.SpecialColumn;
import com.zhiku.entity.mysql.SpecialColumnArticle;
import com.zhiku.mapper.SpecialColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecialColumnService {

    @Autowired
    private SpecialColumnMapper specialColumnMapper;

    @Autowired
    private SpecialColumnArticleService specialColumnArticleService;

    public List<SpecialColumn> list(SpecialColumn specialColumn,Integer start,Integer size){
        Map<String,Object> hashMap=new HashMap<>();
        if(specialColumn.getSid()!=null){
            hashMap.put("sid",specialColumn.getSid());
        }
        if(specialColumn.getCreateTime()!=null){
            hashMap.put("createTime",specialColumn.getCreateTime());
        }
        if(specialColumn.getUpdateTime()!=null){
            hashMap.put("updateTime",specialColumn.getUpdateTime());
        }
        if(specialColumn.getIsDelete()!=null){
            hashMap.put("isDelete",specialColumn.getIsDelete());
        }
        if(specialColumn.getSpecialcRemark()!=null){
            hashMap.put("specialcRemark",specialColumn.getSpecialcRemark());
        }
        if(specialColumn.getSpecialcName()!=null){
            hashMap.put("specialcName",specialColumn.getSpecialcName());
        }
        hashMap.put("start",start);
        hashMap.put("size",size);
        return specialColumnMapper.list(hashMap);
    }
    public int getTotal(SpecialColumn specialColumn){
        return specialColumnMapper.getTotal(specialColumn);
    }

    public int delete(Integer sid) throws Exception{
        SpecialColumnArticle specialColumnArticle=new SpecialColumnArticle();
        specialColumnArticle.setSpecialColumnId(sid);
        int total;
        try {
            total= specialColumnArticleService.getTotal(specialColumnArticle);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        if(total>0){
            return -1;
        }
        return specialColumnMapper.deleteByPrimaryKey(sid);
    }

    public int add(SpecialColumn specialColumn){
        return specialColumnMapper.insertSelective(specialColumn);
    }

    public int update(SpecialColumn specialColumn) {
        return specialColumnMapper.updateByPrimaryKey(specialColumn);
    }
}
