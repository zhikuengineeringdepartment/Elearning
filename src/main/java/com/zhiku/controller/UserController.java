package com.zhiku.controller;

import com.zhiku.entity.Preference;
import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.service.CourseService;
import com.zhiku.service.PreferenceService;
import com.zhiku.service.UserService;
import com.zhiku.util.ResponseData;
import com.zhiku.util.UserStatus;
import com.zhiku.view.ColCourseView;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private CourseService courseService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    Configuration freemarkerConfig;

    /**
     * 新用户注册
     * @param request 请求
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return
     * 处理流程
     * ----检查参数是否规范
     * ----检测是否重复
     * ----正常注册
     */
    @ResponseBody
    @RequestMapping(value = "registe" ,method = RequestMethod.POST)
    public ResponseData registe(
            HttpServletRequest request,
            @RequestParam(name = "username" ) String username,
            @RequestParam(name = "password" ) String password,
            @RequestParam(name = "email" ) String email){
        ResponseData responseData = null;
        try{
            userService.getUserByUsername(username);
            responseData = ResponseData.badRequest();
            responseData.setMessage("用户名重复");
        }catch (UserNotFoundException unfe){
            try{
                userService.getUserByEmail(email);
                responseData = ResponseData.badRequest();
                responseData.setMessage("邮箱重复");
            }catch (UserNotFoundException e){
                if(userService.registeUser(username,password,email,request)){
                    userService.sendEmail(javaMailSender,username,email,"active",freemarkerConfig);
                    responseData = ResponseData.ok();
                }else{
                    responseData = ResponseData.badRequest();
                    responseData.setMessage("发生了一个预期之外的错误");
                }
            }
        }
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public ResponseData login(
            @RequestParam(name = "identity" ) String identity,
            @RequestParam(name = "password" ) String password) {
        ResponseData responseData = null;
        User user = null;
        try{
            user = userService.getUserByUsername(identity);
        }catch (UserNotFoundException e){
            try{
                user = userService.getUserByEmail(identity);
                UserStatus userStatus = userService.checkUser(user);
                if(userStatus == UserStatus.NORMAL){
                    if(userService.checkPassword(user,password)){
                        responseData = ResponseData.ok();
                        //签发token
                    }else {
                        responseData = ResponseData.badRequest();
                        responseData.setMessage("用户名和密码不正确");
                    }
                }else{
                    responseData = ResponseData.badRequest();
                    responseData.setMessage(userStatus.getMessage());
                }
                //签发token
            }catch (UserNotFoundException unfe){
                responseData = ResponseData.badRequest();
                responseData.setMessage("账号不存在");
            }
        }

        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "mail/active",method = RequestMethod.GET)
    public ResponseData mailHandler(
            String act,
            String username,
            String code){
        ResponseData responseData = null;
        ModelAndView modelAndView = new ModelAndView();
        try{
            User user = userService.getUserByUsername(username);
            UserStatus userStatus = userService.checkUser(user);
            if(userStatus.equals(UserStatus.UNCHECKED)){
                userService.activeEmail(user);
                modelAndView.setViewName("login");
            }else if(userStatus.equals(UserStatus.NORMAL)){
                modelAndView.setViewName("redirect:Elearing/index");
            }else if(code.equals(user.hashCode()+"")){
                modelAndView.setViewName("Error");
                modelAndView.addObject("message",userStatus.getMessage());
            }else {
                modelAndView.setViewName("Error");
                modelAndView.addObject("message","无效链接");
            }
        }catch (UserNotFoundException e){
            modelAndView.setViewName("Error");
            modelAndView.addObject("message","用户不存在！");
        }

        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "mail/forgetPassword",method = RequestMethod.GET)
    public ResponseData forgetPassword(String email){
        ResponseData responseData = null;
        try{
            User user = userService.getUserByEmail(email);
            userService.sendEmail(javaMailSender,user.getUserUsername(),user.getUserEmail(),"reset",freemarkerConfig);
            responseData = ResponseData.ok();
        }catch (UserNotFoundException e){
            responseData = ResponseData.badRequest();
            responseData.setMessage("邮箱不存在");
        }
        return responseData;
    }

    @RequestMapping(value = "mail/reset",method = RequestMethod.POST)
    public String reset(){
        return "forward:/reset";
    }

    @ResponseBody
    @RequestMapping(value = "mail/resetPassword",method = RequestMethod.POST)
    public ResponseData resetPassword(String username,String code,String newPsw){
        ResponseData responseData = null;
        try{
            User user = userService.getUserByUsername(username);
            if(code.equals(user.hashCode()+"")){
                userService.resetPassword(user,newPsw);
                responseData = ResponseData.ok();
            }else{
                responseData = ResponseData.badRequest();
                responseData.setMessage("无效链接");
            }
        }catch (UserNotFoundException e){
            responseData = ResponseData.badRequest();
            responseData.setMessage("无效链接");
        }
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "getPrfs" ,method = RequestMethod.GET)
    public ResponseData getPreferences(int uid){
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("prefers",preferenceService.getPrfByUid(uid));
        return responseData;
    }

    /**
     * 移除用户的偏好
     * @param uid 用户id
     * @param prfs 偏好列表
     */
    @ResponseBody
    @RequestMapping(value = "removePrfs" ,method = RequestMethod.DELETE)
    public ResponseData removePrefers(int uid, List<Preference> prfs){
        preferenceService.removePrefers(uid,prfs);
        return ResponseData.ok();
    }

    /**
     * 添加用户偏好
     * @param uid 用户id
     * @param prfs 偏好列表
     */
    @ResponseBody
    @RequestMapping(value = "addPrfs",method = RequestMethod.GET)
    public ResponseData addPrefers(int uid,List<Preference> prfs){
        preferenceService.addPrefers(uid,prfs);
        return ResponseData.ok();
    }

    /**
     * 查看用户的收藏课程以及对应的进度
     * @param uid 用户id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getColCourses" ,method = RequestMethod.GET)
    public ResponseData getColCourses(int uid ){
        ResponseData responseData = null;
        List<ColCourseView> colCourseViews = courseService.getColCourses(uid);
        responseData = ResponseData.ok();
        responseData.putDataValue("colCourseView",colCourseViews);
        return responseData;
    }

    /**
     * 用户收藏课程
     * @param uid
     * @param cid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "colCourse",method = RequestMethod.POST)
    public ResponseData colCourse(int uid,int cid){
        ResponseData responseData = null;
        if(courseService.colCourse(uid,cid)){
            responseData = ResponseData.ok();
        }else{
            responseData = ResponseData.badRequest();
            responseData.setMessage("收藏失败，请重试!");
        }
        return responseData;
    }

    /**
     * 用户清除自己的收藏课程
     * 同时会清除对应课程的学习进度
     * @param uid
     * @param cid
     */
    @ResponseBody
    @RequestMapping(value = "removeColCourse",method = RequestMethod.DELETE)
    public ResponseData removeColCourse(int uid,int cid){
        courseService.removeColCourse(uid,cid);
        return ResponseData.ok();
    }

    @ResponseBody
    @RequestMapping(value = "getMessages",method = RequestMethod.GET)
    public ResponseData getMessages(int uid,int type){
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("myMessages",userService.getMessages(uid,type));
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "readMessage",method = RequestMethod.GET)
    public ResponseData readMessage(int mid){
        userService.readMessage(mid);
        return ResponseData.ok();
    }

    @ResponseBody
    @RequestMapping(value = "removeMessage",method = RequestMethod.DELETE)
    public ResponseData removeMessage(int mid){
        userService.removeMessage(mid);
        return ResponseData.ok();
    }

}
