package com.zhiku.controller.admin;

import com.zhiku.service.DataStatisticsService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.AccessRecordView;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    public ResponseData getFlow(@RequestParam Date beginDay, @RequestParam Date endDay){
        ResponseData responseData=ResponseData.ok();
        List<AccessRecordView> aDPage=dataStatisticsService.listByDateInterval( beginDay,endDay );
        Map<Date, Pair<Integer,Long> > mapTotal=new TreeMap<>(  );
        for (AccessRecordView accessRecordView:aDPage){
            try {
                Pair<Integer,Long> old=mapTotal.get( accessRecordView.getDate() );
                mapTotal.put( accessRecordView.getDate(),new Pair<>(
                        accessRecordView.getNumber()+old.getKey(),accessRecordView.getStayTime()+old.getValue() ));
            }catch (NullPointerException e){
                mapTotal.put( accessRecordView.getDate(), new Pair<>(
                        accessRecordView.getNumber(), new Long(accessRecordView.getStayTime() ) ) );
            }
        }
        List<Map<String,Object> > aDTotal=new LinkedList<>(  );
        for (Map.Entry<Date, Pair<Integer, Long>> entry : mapTotal.entrySet()) {
            // 获取key、value
            Map<String, Object> t = new HashMap<>();
            t.put( "date", entry.getKey() );
            Pair<Integer, Long> p = entry.getValue();
            t.put( "number", p.getKey() );
            t.put( "stayTime", p.getValue() );
            aDTotal.add( t );
        }
        responseData.putDataValue( "accessData",aDPage );
        responseData.putDataValue( "aDTotal",aDTotal );
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
