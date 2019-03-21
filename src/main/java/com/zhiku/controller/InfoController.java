package com.zhiku.controller;

import com.zhiku.entity.Preference;
import com.zhiku.service.PreferenceService;
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

    @RequestMapping(value = "getAllPrfs")
    public @ResponseBody Map<String,Object> getAllPreferences(){
        Map<String,Object> rMessage = new HashMap<>();
        List<Preference> preferences = preferenceService.getAllPreference();
        rMessage.put("preferences",preferences);
        return rMessage;
    }
}
