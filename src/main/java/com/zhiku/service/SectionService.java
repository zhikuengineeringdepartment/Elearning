package com.zhiku.service;

import com.zhiku.entity.mysql.Section;
import com.zhiku.mapper.SectionMapper;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    @Autowired
    private SectionMapper sectionMapper;

    //获得节
    public Section getSection(int sid){
        return sectionMapper.selectByPrimaryKey(sid);
    }

    //获得节视图（节-知识点-段落）
    public SectionView getSectionView(int sid){
        SectionView sectionView = sectionMapper.getSectionViewBySid(sid);
        return  sectionView;
    }

    //TODO 删除该方法，原先是用来获得课程第一个小节的，后来被遗弃
    @Deprecated
    public SectionView getIntroduce(int cid){

        return getSectionView(cid*100 + 1);
    }
}
