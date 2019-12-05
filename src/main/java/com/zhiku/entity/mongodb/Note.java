package com.zhiku.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "note")
public class Note{
    @Id
    private ObjectId _id;
    @Field("note_date")
    private Date noteDate;
    @Field("note_content")
    private String noteContent;
    @Field("note_user")
    private Integer noteUser;
    @Field("note_para")
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

    public ObjectId get_id() {
        return _id;
    }

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

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}