package com.zhiku.service;

import com.zhiku.entity.mysql.Message;
import com.zhiku.entity.User;
import com.zhiku.exception.UserNotFoundException;
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
    JavaMailSender javaMailSender;
    @Autowired
    Configuration freemarkerConfig;
    private EmailUtil emailUtil = new EmailUtil();

    /**
     * 通过用户id获得用户信息
     * @param uid 用户id
     * @return 用户实例
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
     * @param email 邮箱
     * @return 用户实例
     * @throws UserNotFoundException 用户找不到异常
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
     * @param username 用户名
     * @return 用户实例
     * @throws UserNotFoundException 用户找不到异常
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
     * @return 用户名密码是否一致
     */
    public boolean checkPassword(User user , String password){
        if(user.getUserPassword().equals(DigestUtils.md5Hex(password))){
            return true;
        }else{
            return false;
        }
    }

    /**
     * z注册用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param request 请求
     * @return 是否注册成功
     */
    public boolean registeUser(String username, String password, String email, HttpServletRequest request){
        User user = new User();
        user.setUserUsername(username);
        user.setUserNick(username);
        user.setUserAuth("u");      //设置权限为user
        user.setUserGender("u");    //设置性别为unknown
        user.setUserAvatar("img/default.png");  //设置默认头像
        user.setUserLastip(request.getRemoteAddr());
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        user.setUserLasttime(current);
        user.setUserMailtime(calendar.getTime());   //邮箱激活到期时间为注册时间后一天
        user.setUserRegip(request.getRemoteAddr());
        user.setUserRegtime(new Date());
        user.setUserEmail(email);
        user.setUserPassword(DigestUtils.md5Hex(password)); //密码md5加密后保存
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

    //保存用户
    public void saveUser(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 发邮件，异常不处理，抛到上一层
     * @param javaMailSender 邮件发送对象
     * @param username 用户名
     * @param email 邮箱
     * @param act 邮件行为（激活，重置密码等）
     * @param freemarkerConfig 邮件模板
     * @throws MessagingException 发邮件异常
     */
    public void sendEmail(JavaMailSender javaMailSender, String username, String email, String act, Configuration freemarkerConfig) throws MessagingException {
        User user = userMapper.selectByUsername(username);
        emailUtil.sendMail(javaMailSender,act,user,"智库邮件",email,freemarkerConfig);
    }

    /**
     * 检查用户状态
     * @param user 用户
     * @return UserStatus枚举类
     */
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

    /**
     * 激活邮件
     * @param user 用户
     */
    public void activeEmail(User user) {
        user.setUserStatus(UserStatus.NORMAL.getCode());
        user.setUserMailtime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 重置密码
     * @param user 用户
     * @param newPsw 新密码
     */
    public void resetPassword(User user, String newPsw) {
        user.setUserPassword(newPsw);
        user.setUserMailtime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 获得消息列表
     * @param uid 用户
     * @param type 类型(写信还是收信)
     * @return 信息列表
     */
    public List<MessageView> getMessages(int uid, int type) {
        Message message = new Message();
        if(type == 0){
            message.setMessageTo(uid);
        }else{
            message.setMessageFrom(uid);
        }
        return messageMapper.selectMessagesByUser(message);
    }

    /**
     * 将信息标为已读
     * @param mid 消息id
     */
    public void readMessage(int mid) {
        Message message = messageMapper.selectByPrimaryKey(mid);
        message.setMessageRead("r");
        messageMapper.updateByPrimaryKeySelective(message);
    }

    /**
     * 删除信息
     * @param mid 消息id
     */
    public void removeMessage(int mid) {
        messageMapper.deleteByPrimaryKey(mid);
    }

    /**
     * 获得用户基本信息
     * @param uid 用户id
     * @return 用户基本信息视图
     */
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

    public void resetMailTime(User user){
        User user1=new User();
        user1.setUid( user.getUid() );
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(  ));
        calendar.add(Calendar.DAY_OF_MONTH,1);
        user1.setUserMailtime(calendar.getTime());   //邮箱激活到期时间为注册时间后1天
        userMapper.updateByPrimaryKeySelective( user1 );
    }
}
