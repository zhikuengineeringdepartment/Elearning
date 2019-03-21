package com.zhiku.entity;

public class Knowledge {
    private Integer kid;

    private Integer knowledgeSeq;

    private String knowledgeName;

    private Integer knowledgeSection;

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public Integer getKnowledgeSeq() {
        return knowledgeSeq;
    }

    public void setKnowledgeSeq(Integer knowledgeSeq) {
        this.knowledgeSeq = knowledgeSeq;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName == null ? null : knowledgeName.trim();
    }

    public Integer getKnowledgeSection() {
        return knowledgeSection;
    }

    public void setKnowledgeSection(Integer knowledgeSection) {
        this.knowledgeSection = knowledgeSection;
    }
}