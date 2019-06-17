package com.zhiku.util.spider;

import java.io.IOException;
import java.util.ArrayList;

public class SpiderForMorePages {

    int pageNumbers = 1;

    public ArrayList<ArrayList<String>> startSpider(SpiderBuilder sb, String key, String type){
        SpiderBuilder spiderBuilder = sb;
        //储存所有数据的二维数组
        ArrayList<ArrayList<String>> allresults = new ArrayList<ArrayList<String>>();
        for (int i = 1;i<=pageNumbers;i++){
            CsdnSpider csdnSpider = (CsdnSpider)spiderBuilder.getCsdnSpider(key,type,""+i);
            ArrayList<ArrayList<String>> results = null;
            try {
                //更大的节是：search-list J_search
                //results = csdnSpider.getArticleInfo("author-time");
                results = csdnSpider.getArticleInfo("search-list J_search");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //把一页的结果加到结果集中
            allresults.addAll(results);
        }
        //返回所有结果
        return allresults;


    }

    public int getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(int pageNumbers) {
        this.pageNumbers = pageNumbers;
    }
}
