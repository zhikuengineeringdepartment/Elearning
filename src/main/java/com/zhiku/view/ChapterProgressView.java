package com.zhiku.view;

import java.util.List;

/**
 * 课程内容章进度，记录那一章有哪些节
 */
public class ChapterProgressView {
    //章序号
    private Integer chapter;
    //节序号
    private List<Integer> sections;

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public List<Integer> getSections() {
        return sections;
    }

    public void setSections(List<Integer> sections) {
        this.sections = sections;
    }
}
