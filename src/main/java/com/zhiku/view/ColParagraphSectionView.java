package com.zhiku.view;

import com.zhiku.entity.mysql.ColParagraphMysql;

public class ColParagraphSectionView extends ColParagraphMysql {
    private Integer paragraphSeq;

    public Integer getParagraphSeq(){
        return this.paragraphSeq;
    }

    public void setParagraphSeq(Integer paragraphSeq){
        this.paragraphSeq = paragraphSeq;
    }
}
