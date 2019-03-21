package com.zhiku.controller;

import com.zhiku.entity.Preference;
import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.service.CourseService;
import com.zhiku.service.PreferenceService;
import com.zhiku.service.UserService;
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
    @RequestMapping(value = "registe" ,method = RequestMethod.POST)
    public ModelAndView registe(
            HttpServletRequest request,
            @RequestParam(name = "username" ) String username,
            @RequestParam(name = "password" ) String password,
            @RequestParam(name = "email" ) String email){
        ModelAndView modelAndView = new ModelAndView();
        try{
            userService.getUserByUsername(username);
            modelAndView.setStatus(HttpStatus.NOT_ACCEPTABLE);
            modelAndView.setViewName("/registe");
            modelAndView.addObject("message","用户名重复");
        }catch (UserNotFoundException unfe){
            try{
                userService.getUserByEmail(email);
                modelAndView.setStatus(HttpStatus.NOT_ACCEPTABLE);
                modelAndView.setViewName("/registe");
                modelAndView.addObject("message","邮箱重复");
            }catch (UserNotFoundException e){
                if(userService.registeUser(username,password,email,request)){
                    userService.sendEmail(javaMailSender,username,email,"active",freemarkerConfig);
                    modelAndView.setViewName("/registe");
                    modelAndView.addObject("message","激活邮件已发送，请到邮箱查看激活");
                }else{
                    modelAndView.setViewName("/registe");
                    modelAndView.addObject("message","发生了一个预期之外的错误，请重试");
                }
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public ModelAndView login(
            @RequestParam(name = "identity" ) String identity,
            @RequestParam(name = "password" ) String password) throws UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserByUsername(identity);
        if(userService.checkPassword(user,password)){
            modelAndView.setViewName("redirect:/index");
        }else{
            modelAndView.setStatus(HttpStatus.NOT_ACCEPTABLE);
            modelAndView.setViewName("login");
            modelAndView.addObject("Message","用户名或密码不正确");
        }
        return modelAndView;
    }

    @RequestMapping(value = "mail/active",method = RequestMethod.GET)
    public ModelAndView mailHandler(
            String act,
            String username,
            String code){
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

        return modelAndView;
    }

    @RequestMapping(value = "mail/forgetPassword",method = RequestMethod.GET)
    public ModelAndView forgetPassword(String email){
        ModelAndView modelAndView = new ModelAndView();
        try{
            User user = userService.getUserByEmail(email);
            userService.sendEmail(javaMailSender,user.getUserUsername(),user.getUserEmail(),"reset",freemarkerConfig);
        }catch (UserNotFoundException e){
            modelAndView.setViewName("Error");
            modelAndView.addObject("message","该邮箱尚未被注册");
        }
        return modelAndView;
    }
    @RequestMapping(value = "mail/reset",method = RequestMethod.POST)
    public String reset(){
        return "forward:/reset";
    }

        @RequestMapping(value = "mail/resetPassword",method = RequestMethod.POST)
        public ModelAndView resetPassword(String username,String code,String newPsw){
            ModelAndView modelAndView = new ModelAndView();
            try{
                User user = userService.getUserByUsername(username);
                if(code.equals(user.hashCode()+"")){
                    userService.resetPassword(user,newPsw);
                }else{
                    modelAndView.setViewName("Error");
                    modelAndView.addObject("message","无效链接");
                }
            }catch (UserNotFoundException e){
                e.printStackTrace();
            }
            return modelAndView;
    }

    @RequestMapping(value = "getPrfs" ,method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getPreferences(int uid){
        Map<String,Object> rMessage = new HashMap<>();
        try{
            User user = userService.getUserById(uid);
            rMessage.put("prefers",preferenceService.getPrfByUid(uid));
        }catch (UserNotFoundException unfe){
            rMessage.put("message","用户不存在");
        }
        return rMessage;
    }

    /**
     * 移除用户的偏好
     * @param uid 用户id
     * @param prfs 偏好列表
     */
    @RequestMapping(value = "removePrfs" ,method = RequestMethod.DELETE)
    public void removePrefers(int uid, List<Preference> prfs){
        preferenceService.removePrefers(uid,prfs);
    }

    /**
     * 添加用户偏好
     * @param uid 用户id
     * @param prfs 偏好列表
     */
    @RequestMapping(value = "addPrfs",method = RequestMethod.GET)
    public void addPrefers(int uid,List<Preference> prfs){
        preferenceService.addPrefers(uid,prfs);
    }

    /**
     * 查看用户的收藏课程以及对应的进度
     * @param uid 用户id
     * @return
     */
    @RequestMapping(value = "getColCourses" ,method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getColCourses(int uid ){
        Map<String,Object> rMessage = new HashMap<>();
        try{
            User user = userService.getUserById(uid);
            List<ColCourseView> colCourseViews = courseService.getColCourses(uid);
            rMessage.put("colCoureses",colCourseViews);
        }catch (UserNotFoundException unfe){
            rMessage.put("message","用户不存在");
        }
        return rMessage;
    }

    /**
     * 用户收藏课程
     * @param uid
     * @param cid
     * @return
     */
    public @ResponseBody Map<String,Object> colCourse(int uid,int cid){
        Map<String,Object> rMessage = new HashMap<>();
        try{
            User user = userService.getUserById(uid);
            if(courseService.colCourse(uid,cid)){
                rMessage.put("message","ok");
            }else{
                rMessage.put("message","收藏失败，请重试！");
            }
        }catch(UserNotFoundException unfe){
            rMessage.put("message","用户不存在");
        }
        return rMessage;
    }

    /**
     * 用户清除自己的收藏课程
     * 同时会清除对应课程的学习进度
     * @param uid
     * @param cid
     */
    @RequestMapping(value = "removeColCourse",method = RequestMethod.DELETE)
    public void removeColCourse(int uid,int cid){
        courseService.removeColCourse(uid,cid);
    }

}
