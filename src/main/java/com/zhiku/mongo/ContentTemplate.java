package com.zhiku.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhiku.entity.mongodb.Paragraph;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContentTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    private List<Paragraph> getAll(){
        Query query = new Query();
        List<Paragraph> courses = mongoTemplate.find(query, Paragraph.class);
//        ListIterator<Paragraph> iter = courses.listIterator();
//        while(iter.hasNext()){
//            Paragraph c = iter.next();
//            System.out.println(c.getParagraphContent());
//            System.out.println(c.getParagraphKnowledge());
//            System.out.println(c.getClass());
//        }
        return courses;
    }

    /**
     * 查询多个知识点的段落，有顺序
     * @param kids 知识点列表
     */
    public List<Paragraph> findByKids(List<Integer> kids){
        Query query=new Query(  );
        query.addCriteria( Criteria.where("paragraph_knowledge").in(kids) );
        query.with( Sort.by( "paragraph_knowledge","paragraph_seq" ) );
        List<Paragraph> paragraphs=mongoTemplate.find(query, Paragraph.class);
        ///////////////
        for(Paragraph paragraph:paragraphs){
            System.out.println(paragraph.getParagraphContent());/////////////////////
        }

        ////////////////////


//        //排序
//        Paragraph[] paragraphsA=new Paragraph[paragraphs.size()];
//        Map<Integer,Integer> kid2L=new HashMap<>( kids.size()*3/2 );//每个知识点段落数
//        for(Paragraph paragraph:paragraphs){
//            Integer n=kid2L.get(paragraph.getParagraphKnowledge());
//            if(n==null)
//                n=0;
//            kid2L.put( paragraph.getParagraphKnowledge(),n+1 );
//        }
//        Map<Integer,Integer> kid2i=new HashMap<>( kids.size()*3/2 );//每个知识点段落数
//        int oldi=0;
//        for(Integer kid:kids){
//            kid2i.put( kid,oldi );
//            oldi+= kid2L.get(kid)==null ? 0:kid2L.get( kid );
//        }
//        for(Paragraph paragraph:paragraphs){
//            Integer i=kid2i.get(paragraph.getParagraphKnowledge());
//            if(i==null)
//                continue;
//            paragraphsA[i]=paragraph;
//            kid2i.put(paragraph.getParagraphKnowledge(),i+1);
//        }

        return paragraphs;
    }

    public void updateByPrimaryKey(Paragraph paragraph){
        Query query = new Query();
        Update update = new Update();
        query.addCriteria( Criteria.where("_id").is(paragraph.getPid()));
        DBObject dbDoc = new BasicDBObject();
        mongoTemplate.getConverter().write(paragraph, (Bson) dbDoc );
        update=fromDBObjectExcludeNullFields(dbDoc,update);
        mongoTemplate.upsert(query, update, Paragraph.class);
    }

    public void instertAll(List<Paragraph> paragraphs){
        mongoTemplate.insert( paragraphs,Paragraph.class);
    }

    //不为null的字段更新//不含_class
    public static Update fromDBObjectExcludeNullFields(DBObject object,Update update) {
        for (String key : object.keySet()) {
            Object value = object.get( key );
            if (value != null&&!key.equals( "_class" )) {
                update.set( key, value );
            }
        }
        return update;
    }
}
