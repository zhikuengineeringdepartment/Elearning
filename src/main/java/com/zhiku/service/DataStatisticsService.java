package com.zhiku.service;

import com.zhiku.entity.mysql.AccessRecord;
import com.zhiku.mapper.AccessRecordMapper;
import com.zhiku.util.SmallTools;
import com.zhiku.view.AccessRecordView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataStatisticsService {
    @Autowired
    private AccessRecordMapper accessRecordMapper;

    /**
     * 访问记录-进入页面//暂不使用
     * @param ip 访问ip
     * @param uri 网页页面标识
     * @param time 访问开始时间
     */
    public void accessStart(String ip, String uri, Date time) throws ParseException {
        Date day= SmallTools.toDay( time );
        AccessRecord accessRecord=accessRecordMapper.selectByIpUriDate( ip,uri,day);
        if(accessRecord==null){
            accessRecord=new AccessRecord();
            accessRecord.setIp( ip );
            accessRecord.setUri( uri );
            accessRecord.setDate( day );
            accessRecord.setNumber( 1 );
            accessRecord.setStayTime( 0 );
            accessRecord.setLatestTime( time );
            accessRecordMapper.insertSelective( accessRecord );
        }else{
            accessRecord.setNumber( accessRecord.getNumber()+1 );
            accessRecord.setLatestTime( time );
            accessRecordMapper.updateByPrimaryKey( accessRecord );
        }
    }

    /**
     * 访问记录-离开页面-设置停留时间//暂不使用
     * @param ip 访问ip
     * @param uri 网页页面标识
     * @param time 离开页面时间
     */
    public void accessEnd(String ip, String uri, Date time) throws ParseException {
        Date day= SmallTools.toDay( time );
        AccessRecord accessRecord=accessRecordMapper.selectByIpUriDate( ip,uri,day);
        if(accessRecord==null){
            return;
        }
        accessRecord.setStayTime( accessRecord.getStayTime()+
                (int) ((time.getTime()-accessRecord.getLatestTime().getTime())/1000) );
        accessRecord.setLatestTime( null );
        accessRecordMapper.updateByPrimaryKey( accessRecord );
    }

    /**
     * 批量查询
     * @param accessRecords 包含ip，uri,date
     */
    public List<AccessRecord> selectByIpUriDateAll(List<AccessRecord> accessRecords){
        return accessRecordMapper.selectByIpUriDateAll( accessRecords );
    }

    /**
     * 批量插入或更新
     * @param accessRecords 访问数据
     */
    public void replaceAll(List<AccessRecord> accessRecords){
        accessRecordMapper.replaceAll( accessRecords );
    }

    /**
     * 获取日期区间的数据
     * @param beginDay 区间开始日期,包含
     * @param endDay 区间末尾日期，包含
     */
    public List<AccessRecordView> listByDateInterval(Date beginDay, Date endDay){
        List<AccessRecordView> accessRecordViews=accessRecordMapper.selectByDateInterval( beginDay,endDay );
        for(AccessRecordView accessRecordView:accessRecordViews){
            accessRecordView.setStayTimeAvg( accessRecordView.getNumber()<=0?0:
                           accessRecordView.getStayTime()/accessRecordView.getNumber() );
        }
        return accessRecordViews;
    }

}