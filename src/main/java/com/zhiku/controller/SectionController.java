package com.zhiku.controller;

import com.zhiku.service.SectionService;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    @RequestMapping(value = "getSection",method = RequestMethod.GET)
    public String getSection(Model model,
                                   @RequestParam(value = "sid") int sid){
        SectionView sectionView = sectionService.getSection(sid);
        model.addAttribute("sectionView",sectionView);
        return  "course_index";
    }
}
