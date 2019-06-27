package com.zhiku.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO 缺乏数据异常的处理
//TODO 异常处理没法返回页面
@RestControllerAdvice
public class CommonExceptionResolver {

    //处理UserNotFoundException异常
    @ExceptionHandler(value = UserNotFoundException.class)
    public String handlerUserNotFoundException(UserNotFoundException e) {
        return "/test.html";
    }

    //处理TokenVerifyErrorException异常
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
