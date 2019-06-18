package com.zhiku.service;

import com.zhiku.entity.Message;
import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.mapper.FileopMapper;
import com.zhiku.mapper.MessageMapper;
import com.zhiku.mapper.UserMapper;
import com.zhiku.util.EmailUtil;
import com.zhiku.util.UserStatus;
import com.zhiku.view.MessageView;
import com.zhiku.view.UserBaseInfoView;
import freemarker.template.Configuration;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private FileopMapper fileopMapper;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    Configuration freemarkerConfig;
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
    public boolean checkPassword(User user , String password){
        if(user.getUserPassword().equals(DigestUtils.md5Hex(password))){
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
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        user.setUserLasttime(current);
        user.setUserMailtime(calendar.getTime());
        user.setUserRegip(request.getRemoteAddr());
        user.setUserRegtime(new Date());
        user.setUserEmail(email);
        System.out.println(DigestUtils.md5Hex(password));
        user.setUserPassword(DigestUtils.md5Hex(password));
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

    public void saveUser(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 发邮件，异常不处理，抛到上一层
     * @param javaMailSender
     * @param username
     * @param email
     * @param act
     * @param freemarkerConfig
     * @throws MessagingException
     */
    public void sendEmail(JavaMailSender javaMailSender, String username, String email, String act, Configuration freemarkerConfig) throws MessagingException {
        User user = userMapper.selectByUsername(username);
        emailUtil.sendMail(javaMailSender,act,user,"智库邮件",email,freemarkerConfig);
    }

    public UserStatus checkUser(User user){
        if(user.getUserStatus().equals(UserStatus.UNCHECKED.getCode())){
            Date current = new Date();
            if(user.getUserMailtime().before(current)){
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


    public List<MessageView> getMessages(int uid, int type) {
        Message message = new Message();
        if(type == 0){
            message.setMessageTo(uid);
        }else{
            message.setMessageFrom(uid);
        }
        return messageMapper.selectMessagesByUser(message);
    }

    public void readMessage(int mid) {
        Message message = messageMapper.selectByPrimaryKey(mid);
        message.setMessageRead("r");
        messageMapper.updateByPrimaryKeySelective(message);
    }

    public void removeMessage(int mid) {
        messageMapper.deleteByPrimaryKey(mid);
    }

    public UserBaseInfoView getUserBaseInfo(int uid){
        return userMapper.selectBaseInfo(uid);
    }

    /**
     * 插入数据库记录和发邮件，事务
     * 发生问题会抛出异常，抛出异常会回滚
     * @param username
     * @param password
     * @param email
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void registeUserAndSendEmail(String username,String password,String email,HttpServletRequest request) throws MessagingException {
        registeUser(username,password,email,request);
        sendEmail(javaMailSender,username,email,"active",freemarkerConfig);
    }
}
