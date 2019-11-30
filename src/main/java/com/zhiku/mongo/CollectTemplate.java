package com.zhiku.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhiku.entity.mongodb.ColParagraph;
import com.zhiku.entity.mongodb.Note;
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
public class CollectTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ColParagraph> getAllColPara(){
        Query query = new Query();
        List<ColParagraph> paragraphs = mongoTemplate.find(query, ColParagraph.class);
//        ListIterator<ColParagraphMysql> iter = courses.listIterator();
//        while(iter.hasNext()){
//            ColParagraphMysql c = iter.next();
//            System.out.println(c.getColpPara());
//            System.out.println(c.getColpUser());
//            System.out.println(c.getClass());
//        }
        return paragraphs;
    }

    public List<Note> getAllNote(){
        Query query = new Query();
        List<Note> notes = mongoTemplate.find(query, Note.class);
//        ListIterator<NoteMysql> iter = courses.listIterator();
//        while(iter.hasNext()){
//            NoteMysql c = iter.next();
//            System.out.println(c.getNoteContent());
//            System.out.println(c.getNoteDate());
//        }
        return notes;
    }

    public void updateByPrimaryKey(ColParagraph colParagraph){
        Query query = new Query();
        Update update = new Update();
        query.addCriteria( Criteria.where("_id").is(colParagraph.getId()));
        DBObject dbDoc = new BasicDBObject();
        mongoTemplate.getConverter().write(colParagraph, (Bson) dbDoc );
        update=ContentTemplate.fromDBObjectExcludeNullFields(dbDoc,update);
        mongoTemplate.upsert(query, update, ColParagraph.class);
    }


}