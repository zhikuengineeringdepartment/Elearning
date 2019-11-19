package com.zhiku.entity;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Document(collection = "collect")
public class Note extends NoteKey {
    @Id
    private ObjectId _id;
    @Field("paragraph_seq")
    private Integer paraSeq;

    private Date noteDate;

    @Field("note_content")
    private String noteContent;

    public ObjectId get_id() {
        return _id;
    }

    public Integer getParaSeq() {
        return paraSeq;
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

    public void setParaSeq(Integer paraSeq) {
        this.paraSeq = paraSeq;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}