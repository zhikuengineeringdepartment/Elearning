package com.zhiku.aop;

import com.zhiku.exception.UserNotFoundException;
import com.zhiku.util.JWTUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Component
@Aspect
public class TokenAop {

    @Before(value = "execution(* com.zhiku.controller.*.*(..)) && args(uid)")       //对于controller下的任意带有uid参数的请求进行拦截，带uid的请求都是需要需要验证token的
    public void  before(int uid) throws UserNotFoundException , UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("token");
        int userid = JWTUtil.getUid(token);
        if(userid != uid){
            throw new UserNotFoundException();
        }
    }

}
