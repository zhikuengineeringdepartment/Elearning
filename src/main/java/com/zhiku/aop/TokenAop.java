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

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Component
@Aspect
public class TokenAop {

    /**
     * token过期还没有很好的处理
     * @param pjp
     * @throws UserNotFoundException
     * @throws UnsupportedEncodingException
     */
    @Before(value = "execution(* com.zhiku.controller.*.*(com.zhiku.entity.User,..))")       //对于controller下的任意带有User user参数的请求进行拦截，带user的请求都是需要需要验证token的
    public void  before(JoinPoint pjp) throws UserNotFoundException , TokenVerifyErrorException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("token");
        User user = ((User)(pjp.getArgs()[0]));
        int userid = JWTUtil.getUid(token);
        if(userid != user.getUid()){
            throw new UserNotFoundException();
        }
    }

}
