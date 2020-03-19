package com.zhiku.controller.admin;

import com.zhiku.service.DataStatisticsService;
import com.zhiku.util.ResponseData;
import com.zhiku.util.SmallTools;
import com.zhiku.util.monitor.VisitStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@CrossOrigin(value = "*")
@Controller
@RequestMapping("admin/dataStatistics")
public class DataStatisticsAController {

    @Autowired
    private DataStatisticsService dataStatisticsService;

    /**
     * 获取访问统计信息
     * @param beginDay
     * @param endDay
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFlow",method = RequestMethod.GET)
    public ResponseData getFlow(Date beginDay, Date endDay){
        ResponseData responseData=ResponseData.ok();
        responseData.putDataValue( "accessData",dataStatisticsService.listByDateInterval( beginDay,endDay ) );
        return responseData;
    }
    /**
     * 获取注册统计信息
     * --仅已邮箱激活的算注册用户
     * @param beginDay
     * @param endDay
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getRegistration",method = RequestMethod.GET)
    public ResponseData getRegistration(@RequestParam Date beginDay, @RequestParam Date endDay){
        ResponseData responseData=ResponseData.ok();
        List<Map<String,Object> > count=dataStatisticsService.listRegistrationByDateInterval( beginDay,endDay );
        long sum=0;
        for(Map<String,Object> map:count){
            sum+=Long.parseLong( map.get( "number" ).toString());
        }
        responseData.putDataValue( "registers",count);
        responseData.putDataValue( "sum_number",sum );

        return responseData;
    }


}
