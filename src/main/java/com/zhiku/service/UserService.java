package com.zhiku.service;

import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.mapper.UserMapper;
import com.zhiku.util.EmailUtil;
import com.zhiku.util.UserStatus;
import freemarker.template.Configuration;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    private EmailUtil emailUtil = new EmailUtil();

    /**
     * 通过用户id获得用户信息
     * @param uid
     * @return 用户信息
     * @throws UserNotFoundException 找不到具体用户时抛出
     */
    public User getUserById(int uid) throws UserNotFoundException{
        User user = userMapper.selectByPrimaryKey(uid);
        if(user == null){
            UserNotFoundException userNotFoundException = new UserNotFoundException();
            throw userNotFoundException;
        }else{
            return user;
        }
    }

    /**
     * 根据用户邮箱查找用户
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public User getUserByEmail(String email) throws UserNotFoundException{
        User user = userMapper.selectByEmail(email);
        if(user == null){
            UserNotFoundException userNotFoundException = new UserNotFoundException();
            throw userNotFoundException;
        }else{
            return user;
        }
    }

    /**
     * 依据用户名查找用户
     * @param username
     * @return
     * @throws UserNotFoundException
     */
    public User getUserByUsername(String username) throws UserNotFoundException{
        User user = userMapper.selectByUsername(username);
        if(user == null){
            UserNotFoundException userNotFoundException = new UserNotFoundException();
            throw userNotFoundException;
        }else{
            return user;
        }
    }

    /**
     * 检查用户的用户填写的密码与数据库是否一致
     * @param user 用户
     * @param password 用户密码
     * @return
     */
    public boolean checkPassword(User user ,String password){
        if(user.getUserPassword().equals(DigestUtils.md5(password))){
            return true;
        }else{
            return false;
        }
    }

    public boolean registeUser(String username, String password, String email, HttpServletRequest request){
        User user = new User();
        user.setUserUsername(username);
        user.setUserNick(username);
        user.setUserAuth("u");      //设置权限为user
        user.setUserGender("u");    //设置性别为unknown
        user.setUserLastip(request.getRemoteAddr());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        user.setUserLasttime(current);
        user.setUserMailtime(calendar.getTime());
        user.setUserRegip(request.getRemoteAddr());
        user.setUserRegtime(new Date());
        user.setUserEmail(email);
        user.setUserPassword(new String(DigestUtils.md5(password)));
        user.setUserStatus("u");    //设置状态为unchecked
        user.setUserUploadCount(0);
        user.setUserDownloadCount(0);
        int code = userMapper.insertSelective(user);
        if (code < 0){
            return false;
        }else {
            return true;
        }
    }

    public void sendEmail(JavaMailSender javaMailSender, String username, String email, String act, Configuration freemarkerConfig){
        User user = userMapper.selectByUsername(username);
        emailUtil.sendMail(javaMailSender,act,user,"智库邮件",email,freemarkerConfig);
    }

    public UserStatus checkUser(User user){
        if(user.getUserStatus().equals(UserStatus.UNCHECKED.getCode())){
            if(user.getUserMailtime().after(new Date())){
                return UserStatus.EMAIL_TIMEOUT;
            }
            return UserStatus.UNCHECKED;
        }
        if(user.getUserStatus().equals(UserStatus.FORBID.getCode())){
            return  UserStatus.FORBID;
        }else{
            return UserStatus.NORMAL;
        }
    }

    public void activeEmail(User user) {
        user.setUserStatus(UserStatus.NORMAL.getCode());
        user.setUserMailtime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void resetPassword(User user, String newPsw) {
        user.setUserPassword(newPsw);
        user.setUserMailtime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }
}
