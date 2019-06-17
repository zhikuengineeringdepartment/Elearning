package com.zhiku.util.spider.util;

import java.util.ArrayList;

public class PrintUtil {
    public static void printArrayString2(ArrayList<ArrayList<String>> arrs){
        for (ArrayList<String> arr :arrs){
            for (String s : arr){
                System.out.println(s);
            }
            System.out.println("---------------------");
        }
    }
}
