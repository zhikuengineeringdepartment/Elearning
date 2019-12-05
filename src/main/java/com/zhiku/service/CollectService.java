package com.zhiku.service;

import com.zhiku.entity.mongodb.ColParagraph;
import com.zhiku.entity.mongodb.Note;
import com.zhiku.mongo.CollectTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectService {
    @Autowired
    private CollectTemplate template;

    public List<ColParagraph> getAllColPara(){
        return this.template.getAllColPara();
    }
    public List<Note> getAllNote(){
        return this.template.getAllNote();
    }
}
