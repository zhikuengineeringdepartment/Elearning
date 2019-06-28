package com.zhiku.controller;

import com.zhiku.entity.Major;
import com.zhiku.entity.Preference;
import com.zhiku.service.MajorService;
import com.zhiku.service.PreferenceService;
import com.zhiku.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin(value = "*")
@Controller
public class InfoController {
    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private MajorService majorService;

    //TODO 修改进入首页的方式，删除这个鸡肋请求
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");
        return mv;
    }

    //TODO 待测试，待和对应前端结合
    /**
     * 获得都有偏好标签
     * @return 偏好列表
     */
    @ResponseBody
    @RequestMapping(value = "getAllPrfs")
    public ResponseData getAllPreferences(){
        ResponseData responseData = ResponseData.ok();
        List<Preference> preferences = preferenceService.getAllPreference();
        responseData.putDataValue("preferences",preferences);
        return responseData;
    }

    /**
     * 获得所有的专业
     * 没有很好的学院专业课程关系之前，没有作用
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAllMajors")
    public ResponseData getAllMajors(){
        ResponseData responseData = ResponseData.ok();
        List<Major> majors = majorService.getAllMajors();
        responseData.putDataValue("majors",majors);
        return responseData;
    }
}
