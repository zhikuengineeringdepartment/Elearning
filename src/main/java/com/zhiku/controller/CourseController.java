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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;

    @RequestMapping(value = "getAllCourse" ,method = RequestMethod.GET)
    public ModelAndView getAllCourse(@RequestParam(value = "majorId")int majorId){
        ModelAndView modelAndView = new ModelAndView();
        List<Course> courses = courseService.getAllCourse(majorId);
        modelAndView.addObject("courses",courses);
        modelAndView.setViewName("forward:/Elearing/index");
        return  modelAndView;
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
