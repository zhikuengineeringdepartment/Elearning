package com.zhiku.view;

import java.util.List;

//获取指定小节的全部内容
public class SectionContentView extends SubView{
    public List<KnowledgeView> getKnowledgeViews() {
        return knowledgeViews;
    }

    public void setKnowledgeViews(List<KnowledgeView> knowledgeViews) {
        this.knowledgeViews = knowledgeViews;
    }

    private List<KnowledgeView> knowledgeViews;

}
