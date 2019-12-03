package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Document(collection = "note")
public class Note{
    @Id
    private ObjectId _id;
    @Field("note_para")
    private ObjectId paraSeq;
    @Field("note_date")
    private Date noteDate;

    @Field("note_content")
    private String noteContent;
    @Field("note_user")
    private int noteUser;

    public ObjectId get_id() {
        return _id;
    }

    public ObjectId getParaSeq() {
        return paraSeq;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public int getNoteUser() {
        return noteUser;
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

    public void setParaSeq(ObjectId paraSeq) {
        this.paraSeq = paraSeq;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public void setNoteUser(int noteUser) {
        this.noteUser = noteUser;
    }
}