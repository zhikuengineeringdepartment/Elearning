package com.zhiku.service;

import com.zhiku.entity.Child;
import com.zhiku.entity.Index;
import com.zhiku.mongo.IndexTemplate;
import com.zhiku.view.CourseView;
import com.zhiku.view.IndexView;
import com.zhiku.view.SectionContentView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    private IndexTemplate indexTemplate;

    public CourseView getLeftIndex(int cid){
        CourseView courseView = indexTemplate.getLeftIndex(cid);
        return courseView;
    }

    public SectionContentView getSecondLevelIndex(int sid){
        SectionContentView sectionContentView = indexTemplate.getSectionContent(sid);
        return sectionContentView;
    }
}
