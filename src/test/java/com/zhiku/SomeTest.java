package com.zhiku;

import org.junit.Test;

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
}
