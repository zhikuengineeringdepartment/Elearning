package com.zhiku.mongo;

<<<<<<< HEAD
import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.Note;
import com.zhiku.entity.Paragraph;
=======
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhiku.entity.mongodb.ColParagraph;
import com.zhiku.entity.mongodb.Note;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
>>>>>>> 404e7eaaf9b693438ceb396fbdb452562dc01fb4
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
import java.util.Date;
=======
import java.util.ArrayList;
>>>>>>> 404e7eaaf9b693438ceb396fbdb452562dc01fb4
import java.util.List;
import java.util.Map;

@Component
public class CollectTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

<<<<<<< HEAD
//    public List<ColParagraph> getAllColPara(){
//        Query query = new Query();
//        List<ColParagraph> courses = mongoTemplate.find(query, ColParagraph.class);
//        ListIterator<ColParagraph> iter = courses.listIterator();
//        while(iter.hasNext()){
//            ColParagraph c = iter.next();
=======
    public List<ColParagraph> getAllColPara(){
        Query query = new Query();
        List<ColParagraph> paragraphs = mongoTemplate.find(query, ColParagraph.class);
//        ListIterator<ColParagraphMysql> iter = courses.listIterator();
//        while(iter.hasNext()){
//            ColParagraphMysql c = iter.next();
>>>>>>> 404e7eaaf9b693438ceb396fbdb452562dc01fb4
//            System.out.println(c.getColpPara());
//            System.out.println(c.getColpUser());
//            System.out.println(c.getClass());
//        }
<<<<<<< HEAD
////        return courses;
//    }
=======
        return paragraphs;
    }
>>>>>>> 404e7eaaf9b693438ceb396fbdb452562dc01fb4

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
<<<<<<< HEAD
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
=======


}
>>>>>>> 404e7eaaf9b693438ceb396fbdb452562dc01fb4
