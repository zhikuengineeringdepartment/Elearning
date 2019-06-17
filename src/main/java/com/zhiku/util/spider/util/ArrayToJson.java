package com.zhiku.util.spider.util;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ArrayToJson {
    public static String toJson(ArrayList<ArrayList<String>> arrs){
        Gson gson = new Gson();
        String arrsJson = gson.toJson(arrs);
        return arrsJson;
    }
}
