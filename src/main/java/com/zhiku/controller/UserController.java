package com.zhiku.controller;

import com.zhiku.entity.mysql.File;
import com.zhiku.entity.mysql.Preference;
import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.service.CourseService;
import com.zhiku.service.FileService;
import com.zhiku.service.PreferenceService;
import com.zhiku.service.UserService;
import com.zhiku.util.*;
import com.zhiku.view.ColCourseView;
import com.zhiku.view.FileView;
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
            User user=userService.getUserByUsername(username);
            if(isCover(user)){
                throw new UserNotFoundException();
            }else{
                responseData = ResponseData.badRequest();
                responseData.setMessage("用户名重复");
            }
        }catch (UserNotFoundException unfe){
            try{
                User user=userService.getUserByEmail(email);
                if(isCover(user)){
                    throw new UserNotFoundException();
                }else{
                    responseData = ResponseData.badRequest();
                    responseData.setMessage("邮箱重复");
                }
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
     * @param response 返回，用以添加cookie
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
        if(user != null){
            UserStatus userStatus = userService.checkUser(user);
            if(userStatus == UserStatus.NORMAL){
                if(userService.checkPassword(user,password)){
                    responseData = ResponseData.ok();
                    //签发token并添加到cookie中
                    try{
                        String token = JWTUtil.signToken(user);
                        Cookie cookie = new Cookie("token",token);
                        cookie.setMaxAge(3*60*60);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        if(user.getUserAuth().equals( UserStatus.ROOT.getCode() )
                                ||user.getUserAuth().equals( UserStatus.ADMINISTRATORS.getCode() )){
                            //签发管理员token
                            Cookie cookie2 = new Cookie("tokena",JWTUtil.signToken(user));
                            cookie2.setMaxAge(3*60*60);
                            cookie2.setPath("/");
                            response.addCookie(cookie2);
                        }
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
            }else{
                responseData = ResponseData.badRequest();
                responseData.setMessage(userStatus.getMessage());
            }
        }

        return responseData;
    }

//    @ResponseBody
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
            }else if(code.equals(user.hashCode()+"")){
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
                responseData.setMessage("无效链接或逾期未激活用户名已被占用");
            }
        }catch (UserNotFoundException e){
            responseData = ResponseData.badRequest();
            responseData.setMessage("用户不存在或逾期被删除");
        }
        return "/error.html";
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    /*
    邮件重发
     */
    @ResponseBody
    @RequestMapping(value = "mail/reset",method = RequestMethod.GET)
    public String reset(String username,
                        String code) throws MessagingException {
//        return "forward:/reset";
        User user;
        try{
            user = userService.getUserByUsername(username);
        }catch (UserNotFoundException unfe){
            return "激活超期，邮箱已被他人使用，无法再激活，请重新注册";
        }
        UserStatus userStatus = userService.checkUser(user);
        if(!code.equals(user.hashCode()+"")){
            return "激活超期，用户名已被他人使用，无法再激活，请重新注册";
        }else if(!userStatus.equals(UserStatus.NORMAL)){
            return "您已激活成功，请直接登录";
        }
        userService.resetMailTime(user);
        userService.sendEmail(javaMailSender,user.getUserUsername(),user.getUserEmail(),"active",freemarkerConfig);
        return "发送成功，请注意查收";
    }

    @ResponseBody
    @RequestMapping(value = "mail/resetPassword",method = RequestMethod.POST)
    public ResponseData resetPassword(String username, String code, String newPsw){
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

    /**
     * 返回的视图有点问题
     * @param user 用户
     * @return
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
     */
    @ResponseBody
    @RequestMapping(value = "addPrfs",method = RequestMethod.GET)
    public ResponseData addPrefers(User user, List<Preference> prfs){
        preferenceService.addPrefers(user.getUid(),prfs);
        return ResponseData.ok();
    }

    /**
     * 查看用户的收藏课程以及对应的进度
     * @param user 用户
     * @return
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
     * @param user
     * @param cid
     * @return
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
     * @param user
     * @param cid
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
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getMessages",method = RequestMethod.GET)
    public ResponseData getMessages(User user, int type){
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("myMessages",userService.getMessages(user.getUid(),type));
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
     * 获取文件上传记录
     * @param user 用户
     * @param page 页数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUploadRecords",method = RequestMethod.GET)
    public ResponseData getUploadRecords(User user, int page){
        ResponseData responseData = null;
        responseData = ResponseData.ok();
        List<FileView> files=fileService.getFileUploadRecords(user,page);
        for (File file:files){
            //状态表示转中文
            file.setFileStatus( FileStatus.getName( file.getFileStatus() ) );
        }
        responseData.putDataValue("fileUploadRecords",files);
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "getDownloadRecords",method = RequestMethod.GET)
    public ResponseData getDownloadRecords(User user, int page){
        ResponseData responseData = null;
        responseData = ResponseData.ok();
        responseData.putDataValue("fileDownloadRecords",fileService.getFileDownloadRecords(user,page));
        return responseData;
    }

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
        return "/active.html";
    }

    /**
     * 通过邮件发送验证码
     * @param email 邮箱
     */
    @ResponseBody
    @RequestMapping(value = "mail/sendCode",method = RequestMethod.POST)
    public ResponseData sendCode(String email) throws MessagingException {
        User user=userService.getUserByEmail( email );
        if(user.getUserStatus().equals( UserStatus.UNCHECKED.getCode() )
                ||user.getUserStatus().equals( UserStatus.FORBID.getCode())){
            return ResponseData.customerError();
        }
        if(!userService.sendCode(user,email)){
            return ResponseData.customerError();
        }
        return ResponseData.ok();
    }

    /**
     * 修改密码
     * @param email 邮箱
     * @param password 新密码
     */
    @ResponseBody
    @RequestMapping(value = "setPassword",method = RequestMethod.POST)
    public ResponseData setPassword(String email,String code,String password){
        User user= userService.getUserByEmail( email );
        if(user.getUserStatus().equals( UserStatus.UNCHECKED.getCode() )
                ||user.getUserStatus().equals( UserStatus.FORBID.getCode())){
            return ResponseData.customerError();
        }
        if(!userService.checkCode(user,code)){
            return ResponseData.customerError();
        }
        userService.resetPassword( user, SmallTools.word2Password( password ) );
        return ResponseData.ok();
    }
    //是否可以覆盖账号//可以直接删除原账号
    private boolean isCover(User user){
        boolean isc=user.getUserStatus().equals( UserStatus.UNCHECKED.getCode() )
                &&user.getUserMailtime().before( new Date() );
        if(isc){
            userService.delete( user );
        }
        return isc;
    }

}
