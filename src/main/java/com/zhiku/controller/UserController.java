package com.zhiku.controller;

import com.zhiku.entity.Preference;
import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.service.CourseService;
import com.zhiku.service.FileService;
import com.zhiku.service.PreferenceService;
import com.zhiku.service.UserService;
import com.zhiku.util.JWTUtil;
import com.zhiku.util.ResponseData;
import com.zhiku.util.UserStatus;
import com.zhiku.view.ColCourseView;
import com.zhiku.view.UserBaseInfoView;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 用户控制层，处理和用户相关的大部分请求
 */
@CrossOrigin(value = "*")
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
    private FileService fileService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    Configuration freemarkerConfig;

    //TODO 缺乏对于用户名、邮箱的正确性检查，缺乏对于密码长度的检查
    /**
     * 新用户注册
     * @param request 请求，主要用于获取IP等信息
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 是否注册成功
     * 处理流程
     * ----检查参数是否规范
     * ----检测是否重复
     * ----正常注册并发送邮件
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
            //检查用户名，如果不存在，通过异常机制继续往下执行
            userService.getUserByUsername(username);
            responseData = ResponseData.badRequest();
            responseData.setMessage("用户名重复");
        }catch (UserNotFoundException unfe){
            try{
                //检查邮箱，如果不存在，通过异常机制继续往下执行
                userService.getUserByEmail(email);
                responseData = ResponseData.badRequest();
                responseData.setMessage("邮箱重复");
            }catch (UserNotFoundException e){

                //if(userService.registeUser(username,password,email,request)){
                //    userService.sendEmail(javaMailSender,username,email,"active",freemarkerConfig);
                //    responseData = ResponseData.ok();
                //}else{
                //    responseData = ResponseData.badRequest();
                //    responseData.setMessage("发生了一个预期之外的错误");
                //}

                // 将插入记录和发邮件写成事务
                try {
                    userService.registeUserAndSendEmail(username,password,email,request);
                    //发生异常就不会执行这一句
                    System.out.println("邮件发送成功");
                    responseData = ResponseData.ok();
                }
                catch (Exception ex){
                    System.out.println("邮件发送失败");
                    ex.printStackTrace();
                    responseData = ResponseData.badRequest();
                    responseData.setMessage("发生了一个预期之外的错误");
                }
            }
        }
        return responseData;
    }

    /**
     * 登录请求
     * @param request 获取用户登录的地址信息
     * @param identity 登录的身份：用户名或邮箱
     * @param password 登录密码
     * @param response 响应，用以添加cookie
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public ResponseData login(
            HttpServletRequest request,
            @RequestParam(name = "identity" ) String identity,
            @RequestParam(name = "password" ) String password,
            HttpServletResponse response) {
        ResponseData responseData = null;
        User user = null;
        //检查是否存在该用户
        try{
            user = userService.getUserByUsername(identity);
        }catch (UserNotFoundException e){
            try{
                user = userService.getUserByEmail(identity);
            }catch (UserNotFoundException unfe){
                responseData = ResponseData.badRequest();
                responseData.setMessage("账号不存在");
            }
        }
        //如果用户存在
        if(user != null){
            //检查用户的状态
            UserStatus userStatus = userService.checkUser(user);
            if(userStatus == UserStatus.NORMAL){
                //状态正常，检查密码
                if(userService.checkPassword(user,password)){
                    responseData = ResponseData.ok();
                    //签发token并添加到cookie中
                    try{
                        String token = JWTUtil.signToken(user);
                        Cookie cookie = new Cookie("token",token);
                        cookie.setMaxAge(3*60*60);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        responseData.putDataValue("userIcon",user.getUserAvatar());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //修改用户的上次登录时间和ip
                    user.setUserLasttime(new Date());
                    user.setUserLastip(request.getRemoteAddr());
                    userService.saveUser(user);
                }else {
                    responseData = ResponseData.badRequest();
                    responseData.setMessage("用户名和密码不正确");
                }
            }else{  //用户状态异常，包括被封禁，邮箱未激活等
                responseData = ResponseData.badRequest();
                responseData.setMessage(userStatus.getMessage());
            }
        }

        return responseData;
    }

    /**
     * 邮件激活
     * @param act   因为邮件使用模板，所以想使用act作为区分邮件性质的属性，但实际并没有这么使用，act暂时无用
     * @param username  用户名
     * @param code  用户的哈希值
     * @return  是否激活成功
     */
    @RequestMapping(value = "mail/active",method = RequestMethod.GET)
    public String mailHandler(
            String act,
            String username,
            String code){
        ResponseData responseData = null;
        try{
            User user = userService.getUserByUsername(username);
            UserStatus userStatus = userService.checkUser(user);
            if(userStatus.equals(UserStatus.NORMAL)){
                responseData = ResponseData.ok();
                responseData.setMessage("用户已经激活，可直接登录");
                // TODO 返回已经激活而不是激活成功
                return redirectToActivePage();
            }else if(code.equals(user.hashCode()+"")){  //检查哈希值是否一致
                if(userStatus.equals(UserStatus.UNCHECKED)){
                    userService.activeEmail(user);
                    responseData = ResponseData.ok();
                    // 激活成功跳转
                    return redirectToActivePage();
                }else {
                    responseData = ResponseData.badRequest();
                    responseData.setMessage(userStatus.getMessage());
                }
            }else {
                responseData = ResponseData.customerError();
                responseData.setMessage("无效链接");
            }
        }catch (UserNotFoundException e){
            responseData = ResponseData.badRequest();
            responseData.setMessage("用户不存在");
        }

        // TODO 这个网页还没有
        return "error.html";
    }

    /**
     * 忘记密码的请求
     * 处理逻辑
     * -----用户输入自己的邮箱
     * -----后台根据邮箱找到对应用户
     * -----后台给该邮箱发送重置密码的邮件（邮件内容根据用户信息生成）
     * @param email 邮箱
     * @return 重置密码邮件
     */
    @ResponseBody
    @RequestMapping(value = "mail/forgetPassword",method = RequestMethod.GET)
    public ResponseData forgetPassword(String email){
        ResponseData responseData = null;
        try{
            User user = userService.getUserByEmail(email);
            //发送重置密码邮件
            userService.sendEmail(javaMailSender,user.getUserUsername(),user.getUserEmail(),"reset",freemarkerConfig);
            responseData = ResponseData.ok();
        }catch (UserNotFoundException e){
            responseData = ResponseData.badRequest();
            responseData.setMessage("邮箱不存在");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    /**
     * 返回一个重置密码的页面，配合重置密码的邮件进行重置密码的工作
     * @return
     */
    @RequestMapping(value = "mail/reset",method = RequestMethod.POST)
    public String reset(){
        //TODO 缺乏对应的页面
        return "forward:/reset";
    }

    /**
     * 重置密码的请求
     * @param username  用户名
     * @param code  用户哈希值
     * @param newPsw    新密码
     * @return 是否重置成功
     */
    @ResponseBody
    @RequestMapping(value = "mail/resetPassword",method = RequestMethod.POST)
    public ResponseData resetPassword(String username, String code, String newPsw){
        ResponseData responseData = null;
        try{
            //根据用户名找到该用户
            User user = userService.getUserByUsername(username);
            //比较对应哈希值是否一致
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

    //TODO 偏好功能待进一步开发
    /**
     * 获得用户的偏好
     * @param user 用户
     * @return 用户偏好列表
     */
    @ResponseBody
    @RequestMapping(value = "getPrfs" ,method = RequestMethod.GET)
    public ResponseData getPreferences(User user){
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("prefers",preferenceService.getPrfByUid(user.getUid()));
        return responseData;
    }

    /**
     * 移除用户的偏好
     * @param user 用户
     * @param prfs 偏好列表
     * @return 是否移除成功
     */
    @ResponseBody
    @RequestMapping(value = "removePrfs" ,method = RequestMethod.DELETE)
    public ResponseData removePrefers(User user, List<Preference> prfs){
        preferenceService.removePrefers(user.getUid(),prfs);
        return ResponseData.ok();
    }

    /**
     * 添加用户偏好
     * @param user 用户
     * @param prfs 偏好列表
     * @return 是否添加成功
     */
    @ResponseBody
    @RequestMapping(value = "addPrfs",method = RequestMethod.GET)
    public ResponseData addPrefers(User user, List<Preference> prfs){
        preferenceService.addPrefers(user.getUid(),prfs);
        return ResponseData.ok();
    }

    /**
     * 查看用户的收藏课程以及对应的进度
     * 该功能暂时从个人中心中移除了
     * @param user 用户
     * @return 用户收藏的课程（包括该课程总小节数和已读小节数）列表
     */
    @ResponseBody
    @RequestMapping(value = "getColCourses" ,method = RequestMethod.GET)
    public ResponseData getColCourses(User user ){
        ResponseData responseData = null;
        List<ColCourseView> colCourseViews = courseService.getColCourses(user.getUid());
        responseData = ResponseData.ok();
        responseData.putDataValue("colCourseView",colCourseViews);
        return responseData;
    }

    /**
     * 用户收藏课程
     * @param user 用户
     * @param cid 课程
     * @return 是否收藏成功
     */
    @ResponseBody
    @RequestMapping(value = "colCourse",method = RequestMethod.POST)
    public ResponseData colCourse(User user, int cid){
        ResponseData responseData = null;
        if(courseService.colCourse(user.getUid(),cid)){
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
     * @param user 用户
     * @param cid 课程
     * @return 是否移除成功
     */
    @ResponseBody
    @RequestMapping(value = "removeColCourse",method = RequestMethod.DELETE)
    public ResponseData removeColCourse(User user, int cid){
        courseService.removeColCourse(user.getUid(),cid);
        return ResponseData.ok();
    }

    /**
     * 获得用户的消息列表
     * @param user  用户
     * @param type  类型（收信||写信）
     * @return 信息列表
     */
    @ResponseBody
    @RequestMapping(value = "getMessages",method = RequestMethod.GET)
    public ResponseData getMessages(User user, int type){
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("myMessages",userService.getMessages(user.getUid(),type));
        return responseData;
    }

    /**
     * 标记信息为已读
     * @param mid 信息id
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "readMessage",method = RequestMethod.GET)
    public ResponseData readMessage(int mid){
        userService.readMessage(mid);
        return ResponseData.ok();
    }

    /**
     * 删除信息
     * @param mid 信息id
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "removeMessage",method = RequestMethod.DELETE)
    public ResponseData removeMessage(int mid){
        userService.removeMessage(mid);
        return ResponseData.ok();
    }

    /**
     * 个人中心中获得用户基本信息
     * 包括用户头像，用户邮箱，用户昵称，用户手机号等
     * @param user 用户
     * @return 用户基本信息
     */
    @ResponseBody
    @RequestMapping(value = "getBaseInfo",method = RequestMethod.GET)
    public ResponseData getBaseInfo(User user){
        ResponseData responseData = null;
        UserBaseInfoView userBaseInfoView = userService.getUserBaseInfo(user.getUid());
        responseData = ResponseData.ok();
        responseData.putDataValue("baseInfo",userBaseInfoView);
        return responseData;
    }

    /**
     * 个人中心用户上传记录
     * @param user 用户
     * @param page 第几页
     * @return 上传记录
     */
    @ResponseBody
    @RequestMapping(value = "getUploadRecords",method = RequestMethod.GET)
    public ResponseData getUploadRecords(User user, int page){
        ResponseData responseData = null;
        responseData = ResponseData.ok();
        responseData.putDataValue("fileUploadRecords",fileService.getFileUploadRecords(user,page));
        return responseData;
    }

    /**
     * 个人中心用户下载记录
     * @param user 用户
     * @param page 第几页
     * @return 下载记录
     */
    @ResponseBody
    @RequestMapping(value = "getDownloadRecords",method = RequestMethod.GET)
    public ResponseData getDownloadRecords(User user, int page){
        ResponseData responseData = null;
        responseData = ResponseData.ok();
        responseData.putDataValue("fileDownloadRecords",fileService.getFileDownloadRecords(user,page));
        return responseData;
    }

    /**
     * 修改用户头像
     * @param user 用户
     * @param avatar 头像
     * @return 是否修改成功
     */
    @ResponseBody
    @RequestMapping(value = "modifyAvatar",method = RequestMethod.POST)
    public ResponseData modifyAvatar(User user,String avatar){
        ResponseData responseData = null;
        User u = userService.getUserById(user.getUid());
        u.setUserAvatar(avatar);
        userService.saveUser(u);
        responseData = ResponseData.ok();
        return responseData;
    }


    public String redirectToActivePage(){
        return "/pages/active.html";
    }

}
