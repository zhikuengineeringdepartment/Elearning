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

    /**
     * 获得所有的课
     * 后期修改为可按照专业搜课
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAllCourse" ,method = RequestMethod.GET)
    public ResponseData getAllCourse(){
       ResponseData responseData = ResponseData.ok();
        List<Course> courses = courseService.getAllCourse();
        responseData.putDataValue("courses",courses);
        return responseData;
    }

    /**
     * 获得一个课程的详情
     * 建议拆分成两部分
     * -- -- 获得目录结构
     * -- -- 获得课程introduction部分
     * @param cid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCourseDetails",method = RequestMethod.GET)
    public ResponseData getCourseDetails(@RequestParam(value = "cid") int cid){
        ResponseData responseData = ResponseData.ok();
        CourseView courseView = courseService.getCourseDetails(cid);
        SectionView sectionView = sectionService.getIntroduce(courseView.getCid());
        responseData.putDataValue("courseView",courseView);
        responseData.putDataValue("sectionView",sectionView);
        return responseData;
    }
}
