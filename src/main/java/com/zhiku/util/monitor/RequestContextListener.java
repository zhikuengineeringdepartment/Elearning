//package com.zhiku.util.monitor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletRequestEvent;
//import javax.servlet.ServletRequestListener;
//import javax.servlet.http.HttpServletRequest;
//
//@Component
//public class RequestContextListener implements ServletRequestListener {
//    /**
//     * 监听每日用户访问量和网站点击量，以IP地址为标识
//     * @param sre
//     */
//    @Override
//    public void requestInitialized(ServletRequestEvent sre) {
//        try {
//            ServletRequest servletRequest = sre.getServletRequest();
//            //获取ip,存入ServletContext
//            String ip = servletRequest.getRemoteAddr();
//            VisitStatistics.add( ip );
//        }catch (Exception e){//出错不能影响网站访问
//            e.printStackTrace();
//        }
//    }
//
//}
//原统计访问量，监听Request，已弃用