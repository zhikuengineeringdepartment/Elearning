package com.zhiku.view;

import com.zhiku.entity.mongodb.Paragraph;
import com.zhiku.entity.mysql.Knowledge;

import java.util.List;

/**
 * 知识点视图
 * 配合节视图，展示知识点下的所有内容
 */
public class KnowledgeView extends Knowledge {
    private List<Paragraph> paragraphs;

    public List<Paragraph> getParagraphs(){
        return this.paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs){
        this.paragraphs = paragraphs;
    }
}
