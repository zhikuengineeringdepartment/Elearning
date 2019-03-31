package com.zhiku.view;

import com.zhiku.entity.Note;
import com.zhiku.entity.Paragraph;

public class NoteView extends Note {
    private Paragraph paragraph;

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
    }
}
