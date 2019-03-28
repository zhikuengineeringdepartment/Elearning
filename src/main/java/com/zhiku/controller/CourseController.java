package com.zhiku.controller;

import com.zhiku.entity.Course;
import com.zhiku.service.CourseService;
import com.zhiku.service.SectionService;
import com.zhiku.view.CourseView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;

    @RequestMapping(value = "first",method = RequestMethod.GET)
    public ModelAndView getFirst(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("first");
        return mv;
    }

    @RequestMapping(value = "getAllCourse" ,method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getAllCourse(){
        Map<String,Object> Rmessage = new HashMap<>();
        List<Course> courses = courseService.getAllCourse();
        Rmessage.put("courses",courses);
        return Rmessage;
    }

    @RequestMapping(value = "getCourseDetails")
    public ModelAndView getCourseDetails(@RequestParam(value = "cid") int cid){
        ModelAndView modelAndView = new ModelAndView();
        CourseView courseView = courseService.getCourseDetails(cid);
        SectionView sectionView = sectionService.getIntroduce(courseView.getCid());
        System.out.println(sectionView.getKnowledgeViews().get(0).getParagraphs().get(1).getParagraphContent());
        modelAndView.addObject("courseView",courseView);
        modelAndView.addObject("sectionView",sectionView);
        modelAndView.setViewName("course_index");
        return modelAndView;
    }
}
