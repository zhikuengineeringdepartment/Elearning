package com.zhiku.view;

import com.zhiku.entity.Knowledge;
import com.zhiku.entity.Section;

import java.util.List;

/**
 * 节视图
 * 主要用于展示一小节的所有内容
 * 节-知识点-段落
 */
public class SectionView extends Section {
    private List<KnowledgeView> knowledgeViews;

    public List<KnowledgeView> getKnowledgeViews(){
        return this.knowledgeViews;
    }

    public void setKnowledgeViews(List<KnowledgeView> knowledgeViews){
        this.knowledgeViews = knowledgeViews;
    }
}
