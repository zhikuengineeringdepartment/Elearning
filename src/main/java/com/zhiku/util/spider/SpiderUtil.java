package com.zhiku.util.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SpiderUtil {


    /**
     * 获得请求的网站的链接
     * @param u
     */
    public HttpsURLConnection getConn(String u) throws IOException {
        // 先弄一个URL对象
        URL url = new URL(u);
        // 打开链接，强转成HttpURLConnection
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
        //通过对conn对象进行设置连接的参数
        return httpsURLConnection;
    }

    /**
     * 设置连接的参数
     * @param conn
     */
       public HttpsURLConnection setConnParam(HttpsURLConnection conn) throws IOException {
        //参数要求都是大写字母
        conn.setRequestMethod("GET");
        //允许输入输出流
        conn.setDoInput(true);
        conn.setDoOutput(true);
        // 打开连接
        conn.connect();
        return conn;
    }

    /**
     * 获取请求的网站的链接的html内容
     * @param conn
     */
    public String getHtml(HttpsURLConnection conn) throws IOException {
            InputStream is = conn.getInputStream();
            //通过缓存读取
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String temp = null;
            //一行一行读取，读到null就没了
            StringBuilder html = new StringBuilder();
            while ((temp=br.readLine())!=null){
                html.append(temp);
            }
            return html.toString();
    }

    /**
     * 通过html获取需要的获取的文章的简要信息如:
     * url,作者，点击量
     * 这里引用Jsoup
     * Jsoup的使用可以参考API，一些用法如下
     * getElementById 通过id获取一块
     * getElementsByTag 通过tag获得指定标签
     * get（index） 通过下标获得第几个标签
     * attr（value） 通过value获得标签的属性值
     * getElementsContainingText 通过包含关系筛选标签内的文本，返回的是整个标签
     * 上面的方法还有一个通过正则的方法
     * text 获得标签内的文本,如果文本里还有标签，不会获得对应的标签，只会获得文本内容
     * @param html
     */
    public ArrayList<ArrayList<String>> getArticleKeyInfo(String html){
        //使用Jsoup解析html Document类注意是Jsoup的
        Document doc = Jsoup.parse(html);
        //这里的key是我们要找的class的名称,这里测试csdn的时候是：author-time
        //更大的节是：search-list J_search
        Elements elements = doc.getElementsByClass("search-list J_search");



        ArrayList<ArrayList<String>> articleInfos = new ArrayList<ArrayList<String>>();
        //ArrayList<String> articleInfo = new ArrayList<String>();
        for (Element ele: elements){
            //只有2个值，所以我们设置数组的大小是2：标题+链接
            ArrayList<String> articleInfo = new ArrayList<String>(2);

            //获得标题和链接：取limit_width得class，取第一个，取a标签的，取第一个
            Element titleAndLink =ele.getElementsByClass("limit_width").
                    get(0).getElementsByTag("a").get(0);
            //取第一个部分,用空格分开,即为标题
            String title = titleAndLink.text().split(" ")[0];
            //取属性href，即为链接
            String link = titleAndLink.attr("href");
            //将属性值加入到数组中
            articleInfo.add(title);
            articleInfo.add(link);

            //将一篇文章的info加到infos中
            articleInfos.add(articleInfo);

            //清空info数组：look：这里的清空会把元素清空，而二维数组存的是引用！！
            //意味着得到的二维数组也会是全空的
            //articleInfo.clear();

        }
        return articleInfos;
    }


}
