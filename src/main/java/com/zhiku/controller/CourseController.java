package com.zhiku.controller;

import com.zhiku.entity.Course;
import com.zhiku.service.CourseService;
import com.zhiku.service.SectionService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.CourseView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        mv.setViewName("index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "getAllCourse" ,method = RequestMethod.GET)
    public ResponseData getAllCourse(){
       ResponseData responseData = ResponseData.ok();
        List<Course> courses = courseService.getAllCourse();
        responseData.putDataValue("courses",courses);
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "getCourseDetails")
    public ResponseData getCourseDetails(@RequestParam(value = "cid") int cid){
        ResponseData responseData = ResponseData.ok();
        CourseView courseView = courseService.getCourseDetails(cid);
        SectionView sectionView = sectionService.getIntroduce(courseView.getCid());
        responseData.putDataValue("courseView",courseView);
        responseData.putDataValue("sectionView",sectionView);
        return responseData;
    }
}
