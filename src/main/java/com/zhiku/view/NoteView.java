package com.zhiku.view;

import com.zhiku.entity.Note;
import com.zhiku.entity.Paragraph;

public class NoteView extends Note {
    private int paragraphSeq;

    public void setParagraphSeq(int paragraphSeq) {
        this.paragraphSeq = paragraphSeq;
    }

    public int getParagraphSeq() {
        return paragraphSeq;
    }
}
