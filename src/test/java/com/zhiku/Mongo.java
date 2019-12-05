package com.zhiku;

import com.zhiku.entity.mongodb.Note;
import com.zhiku.mongo.CollectTemplate;
import com.zhiku.mongo.NoteTemplate;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Mongo extends ApplicationTest{

    @Autowired
    CollectTemplate collectTemplate;
    @Autowired
    NoteTemplate noteTemplate;


    @Test
    public void Test1(){
        System.out.println(123);
        ObjectId o = new ObjectId("5de239e7475f436c9ad0a3f1");
        Note n = noteTemplate.selectNote(10087,o);
        n.setNoteContent("这是一个测试语句!");
        noteTemplate.modifyNote(n);
//        n.set_id(null);
//        noteTemplate.removeNote(10116,new ObjectId("5de239e7475f436c9ad0a3f1"));
//        System.out.println(n.getNoteContent());
    }

    @Test
    public void Test2(){
//        List<ColParagraph> a = collectTemplate.getAllColPara();
//        for (int i=0;i<a.size();i++){
//        }
    }

}
