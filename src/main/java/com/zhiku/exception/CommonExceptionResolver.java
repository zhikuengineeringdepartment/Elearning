package com.zhiku.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        if((e instanceof UserNotFoundException) || (e instanceof TokenVerifyErrorException)){   //拦截用户验证登录失败的异常
            modelAndView.setViewName("login");
        }else if(e instanceof DataAccessException){     //拦截数据库操作的异常
            modelAndView.setViewName("jsp/Error");
            modelAndView.addObject("message","操作失误，请重试");
        }else{
            modelAndView.setViewName("jsp/Error");
            e.printStackTrace();
        }
        return modelAndView;
    }
}
