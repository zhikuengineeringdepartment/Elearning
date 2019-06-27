package com.zhiku.view;

import com.zhiku.entity.ColParagraph;

/**
 * 用户对应小节的收藏段落
 */
public class ColParagraphSectionView extends ColParagraph {
    private Integer paragraphSeq;

    public Integer getParagraphSeq(){
        return this.paragraphSeq;
    }

    public void setParagraphSeq(Integer paragraphSeq){
        this.paragraphSeq = paragraphSeq;
    }
}
