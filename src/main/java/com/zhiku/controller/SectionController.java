package com.zhiku.controller;

import com.zhiku.service.SectionService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    @ResponseBody
    @RequestMapping(value = "getSection",method = RequestMethod.GET)
    public ResponseData getSection(@RequestParam(value = "sid") int sid){
        ResponseData responseData = ResponseData.ok();
        SectionView sectionView = sectionService.getSection(sid);
        responseData.putDataValue("sectionView",sectionView);
        return  responseData;
    }
}
