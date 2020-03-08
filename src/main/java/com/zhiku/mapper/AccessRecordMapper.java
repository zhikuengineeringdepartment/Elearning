package com.zhiku.mapper;

import com.zhiku.entity.mysql.AccessRecord;
import com.zhiku.view.AccessRecordView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface AccessRecordMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(AccessRecord record);

    int insertSelective(AccessRecord record);

    AccessRecord selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(AccessRecord record);

    int updateByPrimaryKey(AccessRecord record);

    /*自定义方法*/

    AccessRecord selectByIpUriDate(@Param( "ip" )String ip,@Param( "uri" )String uri,
                                   @Param( "date" ) Date date);
    //批量查询
    List<AccessRecord> selectByIpUriDateAll(List<AccessRecord> accessRecords);
    //批量插入或更新
    int replaceAll(List<AccessRecord> accessRecords);

    List<AccessRecordView> selectByDateInterval(@Param( "begin" ) Date begin,@Param( "end" ) Date end);
}