package com.zhiku.util.spider;

public abstract class Spider {
    //爬虫爬取的基本url
    private String url;
    //该爬虫的说明
    private String info;

    abstract void setUrl(String va12, String var2, String var3);
    abstract String setInfo();

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return info;
    }
}
