package com.zhiku.view;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.Paragraph;

public class ColParagraphView extends ColParagraph {
    private Paragraph paragraph;

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
    }
}
