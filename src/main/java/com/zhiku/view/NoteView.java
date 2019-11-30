package com.zhiku.view;

import com.zhiku.entity.mysql.NoteMysql;

public class NoteView extends NoteMysql {
    private ColParagraphView colParagraphView;

    public void setColParagraphView(ColParagraphView colParagraphView) {
        this.colParagraphView = colParagraphView;
    }

    public ColParagraphView getColParagraphView() {
        return colParagraphView;
    }
}
