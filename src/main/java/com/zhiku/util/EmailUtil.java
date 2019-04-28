package com.zhiku.util;

import com.zhiku.entity.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EmailUtil {

    private final String from = "m17864154913@163.com";

    public String sendMail(JavaMailSender javaMailSender,String act, User user, String subject, String emailAdress,Configuration freemarkerConfig) {
        MimeMessage mMessage = javaMailSender.createMimeMessage();// 创建邮件对象
        MimeMessageHelper mMessageHelper;
//        Properties prop = new Properties();
        String text = getText(freemarkerConfig,act,user);
        try {
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }catch (MailSendException mse){
            mse.printStackTrace();
            //抛出邮件异常
        }
        return "发送成功";
    }

    private String getText(Configuration freemarkerConfig,String act,User user){
        String text = "";
        try {
            Template template = freemarkerConfig.getTemplate("email.ftl");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("user",user.getUserUsername());
            map.put("act",act);
            map.put("code",user.hashCode());
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return text;
    }
}
