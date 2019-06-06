package com.zhiku.controller;

import com.zhiku.service.SectionService;
import com.zhiku.util.ResponseData;
import com.zhiku.util.spider.SpiderBoot;
import com.zhiku.util.spider.model.TitleAndUrl;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    /**
     * 获得指定小节的内容
     * @param sid 节号
     * @return 节-知识点-段落视图
     */
    @ResponseBody
    @RequestMapping(value = "getSection",method = RequestMethod.GET)
    public ResponseData getSection(@RequestParam(value = "sid") int sid){
        ResponseData responseData = ResponseData.ok();
        SectionView sectionView = sectionService.getSectionView(sid);
        responseData.putDataValue("sectionView",sectionView);
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
        key = key.replaceAll(" ","").replaceAll("#","").replaceAll("\\*","");
        System.out.println(key);
        SpiderBoot spiderBoot = new SpiderBoot();
        List<TitleAndUrl> re = spiderBoot.bootSpider(key,"blog",1,3);
        System.out.println(re.get(0).getTitle());
        System.out.println(re.get(1).getTitle());
        System.out.println(re.get(2).getTitle());
        responseData.putDataValue("csdn",re);
        return responseData;
    }
}
