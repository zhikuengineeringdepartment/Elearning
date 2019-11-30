package com.zhiku.entity.mysql;

import java.util.Date;
public class NoteMysql extends NoteKey {

    private Date noteDate;
    private String noteContent;

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent == null ? null : noteContent.trim();
    }
}