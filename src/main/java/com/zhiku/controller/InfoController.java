package com.zhiku.controller;

import com.zhiku.entity.Preference;
import com.zhiku.service.PreferenceService;
import com.zhiku.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InfoController {
    @Autowired
    private PreferenceService preferenceService;

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
}
