package com.zhiku.util;

import com.zhiku.entity.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmailUtil {

    private final String from = "m17864154913@163.com";

    /**
     * 发邮件的异常不处理，抛出到上一层
     * @param javaMailSender 邮件发送对象
     * @param act 邮件性质
     * @param user 用户
     * @param subject 邮件主题
     * @param emailAdress 收件人地址
     * @param freemarkerConfig 邮件模板
     * @return
     * @throws MessagingException 发邮件异常
     */
    public String sendMail(JavaMailSender javaMailSender, String act, User user, String subject, String emailAdress, Configuration freemarkerConfig) throws MessagingException {
        MimeMessage mMessage = javaMailSender.createMimeMessage();// 创建邮件对象
        MimeMessageHelper mMessageHelper;
//        Properties prop = new Properties();
        //获得邮件文本
        String text = getText(freemarkerConfig,act,user);
        //try {
//            prop.load(this.getClass().getResourceAsStream("/mail.properties"));
//            String from = prop.get("mail.smtp.username") + "";
            mMessageHelper = new MimeMessageHelper(mMessage, false, "UTF-8");
            // 发件人邮箱
            mMessageHelper.setFrom(from);
            // 收件人邮箱
            mMessageHelper.setTo(emailAdress);
            // 邮件的主题也就是邮件的标题
            mMessageHelper.setSubject(subject);
            // 邮件的文本内容，true表示文本以html格式打开
            mMessageHelper.setText(text, true);


            javaMailSender.send(mMessage);// 发送邮件
        //} catch (MessagingException e) {
        //    e.printStackTrace();
        //}catch (MailSendException mse){
        //    mse.printStackTrace();
        //    //抛出邮件异常
        //}
        return "发送成功";
    }

    /**
     * 依据模板产生邮件内容
     * @param freemarkerConfig 邮件模板
     * @param act 邮件性质
     * @param user 用户
     * @return 邮件内容文本
     */
    private String getText(Configuration freemarkerConfig, String act, User user){
        String text = "";
        try {
            //获得email.ftl模板
            Template template = freemarkerConfig.getTemplate("email.ftl");
            //构建模板参数
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("user",user.getUserUsername());
            map.put("act",act);
            map.put("code",String.valueOf(user.hashCode()));
            //传入参数构建模板文本
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return text;
    }

}
