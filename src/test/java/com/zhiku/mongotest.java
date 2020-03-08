package com.zhiku;

import com.zhiku.entity.PostFirstReply;
import com.zhiku.mongo.ReplyTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

public class mongotest extends ApplicationTest {
    @Autowired
    ReplyTemplate replyTemplate;

    @Test
    public void testGetPagedFirstReply(){
        //replyTemplate.getPagedFirstReply(1,1,1);
        replyTemplate.getPagedFirstReply(1,2,1);
//        Iterator<PostFirstReply> iterator = postFirstReplies.iterator();
////        while(iterator.hasNext()){
////            System.out.println(iterator.next().getReplyContent());
////        }
    }

    @Test
    public void test3(){
        replyTemplate.setFirstReplyAgreeCount(101, 3);
    }
}
