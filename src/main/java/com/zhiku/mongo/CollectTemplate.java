package com.zhiku.mongo;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.Note;
import com.zhiku.entity.Paragraph;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
@Component
public class CollectTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

//    public List<ColParagraph> getAllColPara(){
//        Query query = new Query();
//        List<ColParagraph> courses = mongoTemplate.find(query, ColParagraph.class);
//        ListIterator<ColParagraph> iter = courses.listIterator();
//        while(iter.hasNext()){
//            ColParagraph c = iter.next();
//            System.out.println(c.getColpPara());
//            System.out.println(c.getColpUser());
//            System.out.println(c.getClass());
//        }
////        return courses;
//    }

    public List<Note> getAllNote(){
        Query query = new Query();
        List<Note> courses = mongoTemplate.find(query, Note.class);
        ListIterator<Note> iter = courses.listIterator();
        while(iter.hasNext()){
            Note c = iter.next();
            System.out.println(c.getNoteContent());
            System.out.println(c.getNoteDate());
            System.out.println(c.getParaSeq());
        }
        return courses;
    }
    public boolean insertColPar(int uid, ObjectId paragraphSeq){
        ColParagraph cp = new ColParagraph();
        cp.setColpDate(new Date());
        cp.setParaSeq(paragraphSeq);
        cp.setColpUser(uid);
        mongoTemplate.insert(cp);
        return true;
    }
    public boolean removeColPar(int uid,ObjectId paragraphSeq){
        Query query = new Query();
        query.addCriteria(new Criteria("colp_para").is(paragraphSeq).and("colp_user").is(uid));
        mongoTemplate.remove(query,ColParagraph.class);
        return true;
    }
}
