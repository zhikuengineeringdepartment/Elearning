package com.zhiku.util.spider;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.ArrayList;

public class CsdnSpider extends Spider {

    SpiderUtil spiderUtil = new SpiderUtil();

    /**
     *包内可见
     * @param question
     * @param type
     * @param page
     * @return
     */
    void setUrl(String question, String type, String page) {
        StringBuilder baseUrl = new StringBuilder("https://so.csdn.net/so/search/s.do?");
        baseUrl.append("q=" + question + "&");
        baseUrl.append("t=" + type + "&");
        baseUrl.append("p=" + page );
        setUrl(baseUrl.toString());
    }

    /**
     * 包内可见
     * @return
     */
    String setInfo() {
        return "csdn爬虫，输入关键字，返回文章的简介";
    }

    /**
     * 输入的参数：html文本的class关键字
     * 获得文章简介的方法
     * @return 包含文章简介的二维数组
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> getArticleInfo(String classKey) throws IOException {
        //获得https的连接,设置连接参数
        //System.out.println(this.getUrl());

        HttpsURLConnection conn = spiderUtil.setConnParam(spiderUtil.getConn(this.getUrl()));
        //获得html文本
        String html = spiderUtil.getHtml(conn);
        //解析html代码，针对csdn的关键字不是author-time
        //是search-list J_search
        ArrayList<ArrayList<String>> result = spiderUtil.getArticleKeyInfo(html);
        return result;
    }
}
