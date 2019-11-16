package com.zhiku.view;

import com.zhiku.entity.Child;
import com.zhiku.entity.Index;
import com.zhiku.entity.Section;

import java.util.List;

/**
 * 节视图
 * 主要用于展示一小节的所有内容
 * 节-知识点-段落
 */
public class SectionView extends Section {
    private List<Child> knowledgeViews;

    public List<Child> getKnowledgeViews(){
        return this.knowledgeViews;
    }

    public void setKnowledgeViews(List<Child> knowledgeViews){
        this.knowledgeViews = knowledgeViews;
    }
}
