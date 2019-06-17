package com.zhiku.mapper;

import com.zhiku.entity.Fileop;
import com.zhiku.entity.User;
import com.zhiku.view.FileDownloadRecordView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface FileopMapper {
    int deleteByPrimaryKey(Integer fopid);

    int insert(Fileop record);

    int insertSelective(Fileop record);

    Fileop selectByPrimaryKey(Integer fopid);

    int updateByPrimaryKeySelective(Fileop record);

    int updateByPrimaryKey(Fileop record);

    List<FileDownloadRecordView> selectFileDownloadRecords(@Param("user") User user, @Param("startLine") int startLine, @Param("pageSize") int pageSize);
}