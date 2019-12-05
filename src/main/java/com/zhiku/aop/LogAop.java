package com.zhiku.aop;


import com.google.gson.Gson;
import com.zhiku.entity.mysql.LogEntity;
import com.zhiku.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户日志行为的切面
 */
@Component
@Aspect
public class LogAop {
    private static final Logger logger = LogManager.getLogger("log");

    @After(value = "execution(* com.zhiku.controller.*.*(*)))")
    public void after(JoinPoint pjp){
        //获得request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //创建日志记录对象
        LogEntity logEntity = new LogEntity();
        logEntity.setIp(request.getRemoteAddr());
        Object[] args = pjp.getArgs();
        //如果
        if(args.length >0 && args[0] instanceof User){
            logEntity.setUser((User)args[0]);
        }
        Gson gson = new Gson();
        System.out.println(gson.toJson(args));
        logEntity.setRequestName(pjp.getSignature().getName());
        //打印日志
        logger.info(logEntity);
    }
}
