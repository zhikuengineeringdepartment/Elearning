package com.zhiku.service;

import com.zhiku.mapper.SectionMapper;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    @Autowired
    private SectionMapper sectionMapper;

    public SectionView getSection(int sid){
        SectionView sectionView = sectionMapper.getSectionViewBySid(sid);
        return  sectionView;
    }

    public SectionView getIntroduce(int cid){

        return getSection(cid*100 + 1);
    }
}
