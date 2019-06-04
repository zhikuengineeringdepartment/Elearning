package com.zhiku.util.spider.util;

import java.util.ArrayList;

public class DataCleanUtil {
    public void dataClean(ArrayList<ArrayList<String>> arrs) throws Exception {
        //Pattern pattern = Pattern.compile("[0-9]*");
        ////Matcher matcher = pattern.matcher(a);
        //for (ArrayList<String> arr : arrs){
        //    //前4个不要
        //    arr.set(0,arr.get(0).substring(4).trim());
        //    //前3个不要
        //    arr.set(1,arr.get(1).substring(3).trim());
        //    //去掉空格
        //    arr.set(2,arr.get(2).trim());
        //
        //    //group默认返回group0，就是第一个元素
        //    //正则匹配数字
        //    Matcher matcher = pattern.matcher(arr.get(3).trim());
        //    matcher.find();
        //    arr.set(3,matcher.group());
        //
        //}
        throw new Exception("我建议不要清洗了");

    }
}
