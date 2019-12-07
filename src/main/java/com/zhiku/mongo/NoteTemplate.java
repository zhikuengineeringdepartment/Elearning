package com.zhiku.mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhiku.entity.mongodb.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class NoteTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateByPrimaryKey(Note note){
        Query query = new Query();
        Update update = new Update();
        query.addCriteria( Criteria.where("_id").is(note.get_id()));
        DBObject dbDoc = new BasicDBObject();
        mongoTemplate.getConverter().write(note, (Bson) dbDoc );
        update=ContentTemplate.fromDBObjectExcludeNullFields(dbDoc,update);
        mongoTemplate.upsert(query, update, Note.class);
    }

    public Note selectNote(int uid, ObjectId paragraphSeq){
        Query query = new Query();
        query.addCriteria(new Criteria("note_user").is(uid).and("note_para").is(paragraphSeq));
        Note n = mongoTemplate.findOne(query,Note.class);
        return n;
    }



    public boolean addNote(int uid, Note note,ObjectId paragrapaSeq){
        note.setNoteDate(new Date());
        note.setNoteUser(uid);
        note.setNotePara(paragrapaSeq);
        mongoTemplate.insert(note);
        return true;
    }

    public boolean removeNote(int uid,ObjectId paragraphSeq){
        Query query = new Query();
        query.addCriteria(new Criteria("note_user").is(uid).and("note_para").is(paragraphSeq));
        mongoTemplate.remove(query, Note.class);
        return true;
    }
    public boolean modifyNote(Note note){
        Query query = new Query();
        query.addCriteria(new Criteria("note_user").is(note.getNoteUser()).and("note_para").is(note.getNotePara()));
        Update update = new Update().set("note_date",new Date()).set("note_content",note.getNoteContent());
        mongoTemplate.updateFirst(query,update,Note.class);
        return true;

    }
}
