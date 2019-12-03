package com.zhiku.mongo;

import com.zhiku.entity.ColParagraph;
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
public class ContentTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Paragraph> getAll(){
        Query query = new Query();
        List<Paragraph> courses = mongoTemplate.find(query, Paragraph.class);
        ListIterator<Paragraph> iter = courses.listIterator();
        while(iter.hasNext()){
            Paragraph c = iter.next();
            System.out.println(c.getParagraphContent());
            System.out.println(c.getParagraphKnowledge());
            System.out.println(c.getClass());
        }
        return courses;
    }



}
