package com.zhiku.view;

import com.zhiku.entity.Note;
import com.zhiku.entity.Paragraph;

public class NoteView extends Note {
    private ColParagraphView colParagraphView;

    public void setColParagraphView(ColParagraphView colParagraphView) {
        this.colParagraphView = colParagraphView;
    }

    public ColParagraphView getColParagraphView() {
        return colParagraphView;
    }
}
