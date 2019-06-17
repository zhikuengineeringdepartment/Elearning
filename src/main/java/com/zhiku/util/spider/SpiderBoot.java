package com.zhiku.util.spider;


import com.zhiku.util.spider.model.TitleAndUrl;

import java.util.ArrayList;
import java.util.List;

public class SpiderBoot {
    public List<TitleAndUrl> bootSpider(String key, String type, int pages, int needs)  {
        //实例化spider对象
        SpiderBuilder spiderBuilder = new SpiderBuilder();
        //爬取多个页面的爬虫
        SpiderForMorePages spider = new SpiderForMorePages();
        //设置爬取的页数
        spider.setPageNumbers(pages);
        //获取爬取结果：二维数组
        ArrayList<ArrayList<String>> arrResults = spider.startSpider(spiderBuilder,key,type);
        //存放需要的结果
        ArrayList<ArrayList<String>> needsResults = new ArrayList<ArrayList<String>>();

        ArrayList<TitleAndUrl> re = new ArrayList<TitleAndUrl>();

        for (int i=0; i<=needs-1; i++){
            TitleAndUrl tau = new TitleAndUrl();
            tau.setTitle(arrResults.get(i).get(0));
            tau.setUrl(arrResults.get(i).get(1));
            re.add(tau);
        }
        //数据清洗：不用了
        //new DataCleanUtil().dataClean(results);
        //二维数组->json
//        Gson gson = new Gson();
//        String jsonResults = gson.toJson(re);
        //返回json
//        return jsonResults;
        return re;
    }

    //单独测试爬虫的效果
    public static void main(String[] args){
        SpiderBoot spiderBoot = new SpiderBoot();
        List<TitleAndUrl> re = spiderBoot.bootSpider("6.2同步量","blog",1,3);
        System.out.println(re.get(0).getTitle());
        System.out.println(re.get(1).getTitle());
        System.out.println(re.get(2).getTitle());
    }

}
