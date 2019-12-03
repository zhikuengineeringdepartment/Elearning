package com.zhiku.entity;

import org.bson.types.ObjectId;

public class NoteKey {
    private Integer noteUser;

    private ObjectId notePara;

    public Integer getNoteUser() {
        return noteUser;
    }

    public void setNoteUser(Integer noteUser) {
        this.noteUser = noteUser;
    }

    public ObjectId getNotePara() {
        return notePara;
    }

    public void setNotePara(ObjectId notePara) {
        this.notePara = notePara;
    }
}