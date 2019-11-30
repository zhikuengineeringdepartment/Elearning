package com.zhiku;

import com.zhiku.entity.mongodb.Note;
import com.zhiku.mongo.CollectTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Mongo extends ApplicationTest{

    @Autowired
    CollectTemplate collectTemplate;


    @Test
    public void Test1(){
        List<Note> a = collectTemplate.getAllNote();
        for (int i=0;i<a.size();i++){
            System.out.print(a.get(i).getNoteContent());
            System.out.print(" ");
        }
    }

    @Test
    public void Test2(){
//        List<ColParagraph> a = collectTemplate.getAllColPara();
//        for (int i=0;i<a.size();i++){
//        }
    }

}
