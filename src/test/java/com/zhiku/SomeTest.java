package com.zhiku;

import com.zhiku.entity.PostFirstReply;
import com.zhiku.entity.PostReply;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

public class SomeTest {

    @Test
    public void test(){
        String key = "2.1.关系数据库的结构";
        int index = key.indexOf(".");
        System.out.println("index: " + index);
        String[] keys = key.split("\\.");
        for (String str:keys)
            System.out.println(str);
        // 如果不能成功分词是不能取到1号索引的
        //System.out.println(keys[1]);
    }
    @Test
    public void t(){
        PostFirstReply a = new PostFirstReply();
        PostFirstReply b = new PostFirstReply();
        a.setrId("1");
        b.setrId("2");
        PostFirstReply t = new PostFirstReply();
        List<PostFirstReply> postFirstReplyList = new ArrayList<>();
        postFirstReplyList.add(a);
        postFirstReplyList.add(b);
        for (PostFirstReply s:postFirstReplyList){
            if (s.getrId()=="1"){
                t = s;
            }
        }
        t.setrId("3");
        for (PostFirstReply s:postFirstReplyList){
            System.out.println(s.getrId());
        }
    }
}
