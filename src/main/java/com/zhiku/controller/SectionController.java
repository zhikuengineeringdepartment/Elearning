package com.zhiku.controller;

import com.zhiku.entity.mongodb.Child;
import com.zhiku.service.IndexService;
import com.zhiku.service.SectionService;
import com.zhiku.util.ResponseData;
import com.zhiku.util.spider.SpiderBoot;
import com.zhiku.util.spider.model.TitleAndUrl;
import com.zhiku.view.IndexView;
import com.zhiku.view.SectionContentView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "section")
public class SectionController {
    @Autowired
    SectionService sectionService;
    @Autowired
    IndexService indexService;

    /**
     * 获得指定小节的内容
     * @param sid 节号
     * @return 节-知识点-段落视图
     */
//    @ResponseBody
//    @RequestMapping(value = "getSections",method = RequestMethod.GET)
//    public ResponseData getSections(@RequestParam(value = "sid") int sid){
//        ResponseData responseData = ResponseData.ok();
//        SectionView sectionView = sectionService.getSectionView(sid);
//        responseData.putDataValue("sectionView",sectionView);
//        return  responseData;
//    }
    @ResponseBody
    @RequestMapping(value = "getSection",method = RequestMethod.GET)
    public ResponseData getSection(@RequestParam(value = "sid") int sid,
                                   @RequestParam(required = false) Integer cid,@RequestParam(required = false) String vid){
        ResponseData responseData = ResponseData.ok();
        SectionContentView sectionContentView = indexService.getSecondLevelIndex2(sid,cid,vid);
        responseData.putDataValue("sectionView",sectionContentView);
        return  responseData;
    }

    //TODO 存在推荐为空的情况，推荐内容重复，正常使用爬虫和网络使用爬虫差距巨大

    /**
     * 获得csdn推荐的爬虫
     * @param sid   节号
     * @return csdn推荐列表
     */
    @ResponseBody
    @RequestMapping(value = "getCSDN",method = RequestMethod.GET)
    public ResponseData getCSDN(int sid){
        ResponseData responseData = ResponseData.ok();
        String key = sectionService.getSection(sid).getSectionName();
        // 不全把key中的空格删除，把key的前后的空格删除
        //key = key.replaceAll(" ","").replaceAll("#","").replaceAll("\\*","");
        key = key.trim().replaceAll("#","").replaceAll("\\*","");
        SpiderBoot spiderBoot = new SpiderBoot();


        // key：爬虫爬取的关键字，对key用空格分开，去掉头部的空格
        // 用空格分开，分成两个部分，取出索引是1的部分
        // 如果是通过点分开，而没有通过空格分开，则用点分开，取出最后一个子串
        // 用空格分开一切都不会有问题，但是.不一定的，没有.会返回空
        String[] keySplitWithBlank = key.split(" ");
        key = keySplitWithBlank[keySplitWithBlank.length-1];
        if ( key.indexOf(".") != -1){
            String[] keySplitWithPoint = key.split("\\.");
            key = keySplitWithPoint[keySplitWithPoint.length-1];
        }


        List<TitleAndUrl> re = spiderBoot.bootSpider(key,"blog",1,3);

        if (re.get(0).getTitle().equals(re.get(1).getTitle()))
            re.remove(0);
        if (re.get(0).getTitle().equals(re.get(1).getTitle()))
            re.remove(0);
        responseData.putDataValue("csdn",re);
        return responseData;
    }

    /**
     * 获得csdn推荐的爬虫
     * @param cid 课程号
     * @param vid 版本号
     * @param sid 具体章节对应小节号
     * @return csdn推荐列表
     */
    @ResponseBody
    @RequestMapping(value = "getCSDN2",method = RequestMethod.GET)
    public ResponseData getCSDN2(@RequestParam(value = "cid") Integer cid,
                                 @RequestParam(value = "vid") String vid,
                                 @RequestParam(value = "sid") Integer sid){
        ResponseData responseData = ResponseData.ok();
        Child childSection = sectionService.getLevel2Section(cid, vid, sid);
        if(childSection==null){
            responseData=ResponseData.customerError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        String key=childSection.getSection_name();
        // 不全把key中的空格删除，把key的前后的空格删除
        //key = key.replaceAll(" ","").replaceAll("#","").replaceAll("\\*","");
        key = key.trim().replaceAll("#","").replaceAll("\\*","");
        SpiderBoot spiderBoot = new SpiderBoot();


        // key：爬虫爬取的关键字，对key用空格分开，去掉头部的空格
        // 用空格分开，分成两个部分，取出索引是1的部分
        // 如果是通过点分开，而没有通过空格分开，则用点分开，取出最后一个子串
        // 用空格分开一切都不会有问题，但是.不一定的，没有.会返回空
        String[] keySplitWithBlank = key.split(" ");
        key = keySplitWithBlank[keySplitWithBlank.length-1];
        if ( key.indexOf(".") != -1){
            String[] keySplitWithPoint = key.split("\\.");
            key = keySplitWithPoint[keySplitWithPoint.length-1];
        }


        List<TitleAndUrl> re = spiderBoot.bootSpider(key,"blog",1,3);

        if (re.get(0).getTitle().equals(re.get(1).getTitle()))
            re.remove(0);
        if (re.get(0).getTitle().equals(re.get(1).getTitle()))
            re.remove(0);
        responseData.putDataValue("csdn",re);
        return responseData;
    }
}
