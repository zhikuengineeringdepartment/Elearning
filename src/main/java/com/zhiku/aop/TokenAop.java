package com.zhiku.aop;

import com.zhiku.entity.User;
import com.zhiku.exception.TokenVerifyErrorException;
import com.zhiku.exception.UserNotFoundException;
import com.zhiku.util.JWTUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Component
@Aspect
public class TokenAop {

    /**
     *用户登录检测的切面
     * @param pjp 切点
     * @throws UserNotFoundException 未找到用户异常
     * @throws TokenVerifyErrorException token验证失败异常
     */
    @Before(value = "execution(* com.zhiku.controller.*.*(com.zhiku.entity.User,..)))")       //对于controller下的任意带有User user参数的请求进行拦截，带user的请求都是需要需要验证token的
    public void  before(JoinPoint pjp) throws UserNotFoundException , TokenVerifyErrorException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getCookieByName("token",request.getCookies());
        User user = ((User)(pjp.getArgs()[0]));     //获取切面的第一个参数对象user
        user.setUid(JWTUtil.getUid(token));
        user.setUserUsername(JWTUtil.getUserName(token));
    }

    //TODO 添加日志记录的切面，主要记录用户的各种点击行为


    /**
     * 从request的Cookies中提取出相应的cookie的值
     * @param name 对应cookie的名字
     * @param cookies request的cookies列表
     * @return 对应name的cookie值
     */
    private String getCookieByName(String name, Cookie[] cookies){
        String value = "";
        for(int i=0;i<cookies.length;i++){
            if(name.equals(cookies[i].getName())){
                value = cookies[i].getValue();
            }
        }
        return value;
    }

}
