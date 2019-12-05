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
}
