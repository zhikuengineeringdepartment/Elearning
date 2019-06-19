package com.zhiku.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class CommonExceptionResolver {

    @ExceptionHandler(value = UserNotFoundException.class)
    public String handlerUserNotFoundException(UserNotFoundException e){
        return "/test.html";
    }
    @ExceptionHandler(value = TokenVerifyErrorException.class)
    public String handlerTokenVerifyErrorException(TokenVerifyErrorException e){
        return "/test.html";
    }

//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        ModelAndView modelAndView = new ModelAndView();
//        if((e instanceof UserNotFoundException) || (e instanceof TokenVerifyErrorException)){   //拦截用户验证登录失败的异常
//            System.out.println("登录异常");
//            modelAndView.setViewName("jsp/login.jsp");
//        }else if(e instanceof DataAccessException){     //拦截数据库操作的异常
//            modelAndView.setViewName("jsp/Error.jsp");
//            modelAndView.addObject("message","操作失误，请重试");
//        }else{
//            modelAndView.setViewName("jsp/Error.jsp");
//            e.printStackTrace();
//        }
//        return modelAndView;
//    }
}
