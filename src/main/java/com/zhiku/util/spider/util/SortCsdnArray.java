package com.zhiku.util.spider.util;

import java.util.ArrayList;

public class SortCsdnArray {
    public void sort(ArrayList<ArrayList<String>> arrs){
        //子数组的长度
        int len = arrs.get(0).size();
        int tempJ;
        int tempK;
        int max = 0;
        ArrayList<String> temp = null;
        for (int j=0 ; j<=len-1-1; j++){
            for (int k=j+1; k<=len-1; k++){
               {
                   tempJ = Integer.valueOf(arrs.get(j).get(3));
                   tempK = Integer.valueOf(arrs.get(k).get(3));
                   if(tempK>max)
                    //temp中储存阅读量最大的数组
                       max = tempK;
                }
            }
            //将最大的放在最前面
            arrs.set(1,temp);
        }

    }
}
