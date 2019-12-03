package com.zhiku.mongo;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.Note;
import com.zhiku.entity.Paragraph;
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
    public void insertColPar(int uid, int paragraphSeq){
        Query query = new Query();
        query.addCriteria(new Criteria("paragraph_seq").is(paragraphSeq));
        Paragraph p = mongoTemplate.findOne(query,Paragraph.class);
        ColParagraph cp = new ColParagraph();
        cp.setColpDate(new Date());
        cp.setParaSeq(p.getId());
        cp.setColpUser(uid);
        mongoTemplate.insert(cp);
    }
    public void removeColPar(int uid,int paragraphSeq){
        Query query = new Query();
        query.addCriteria(new Criteria("paragraph_seq").is(paragraphSeq));
        Paragraph p = mongoTemplate.findOne(query,Paragraph.class);
        Query query1 = new Query();
        query1.addCriteria(new Criteria("colp_para").is(p.getId()).and("colp_user").is(uid));
        mongoTemplate.remove(query1,ColParagraph.class);

    }
}
