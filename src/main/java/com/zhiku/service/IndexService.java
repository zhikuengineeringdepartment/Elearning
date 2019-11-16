package com.zhiku.service;

import com.zhiku.entity.Child;
import com.zhiku.entity.Index;
import com.zhiku.mongo.IndexTemplate;
import com.zhiku.view.CourseView;
import com.zhiku.view.IndexView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    private IndexTemplate indexTemplate;
    public IndexView getIndexOfCourse(int cid){
        List<Index> indexlist = indexTemplate.getIndexOfCourse(cid);
        IndexView indexView = new IndexView();
        indexView.setIndexList(indexlist);
        return indexView;
    }

    public CourseView getFirstLevelIndex(int cid){
        CourseView courseView = indexTemplate.getFirstLevelIndex(cid);
        return courseView;
    }

    public SectionView getSecondLevelIndex(int sid){
        SectionView sectionView = indexTemplate.getSecondLevelIndex(sid);
        return sectionView;
    }
}
