package com.zhiku.view;

import com.zhiku.entity.ColParagraph;

public class ColParagraphSectionView extends ColParagraph {
    private Integer paragraphSeq;

    public Integer getParagraphSeq(){
        return this.paragraphSeq;
    }

    public void setParagraphSeq(Integer paragraphSeq){
        this.paragraphSeq = paragraphSeq;
    }
}
