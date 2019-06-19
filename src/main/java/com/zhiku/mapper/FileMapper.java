package com.zhiku.mapper;

import com.zhiku.entity.File;
import com.zhiku.entity.User;
import com.zhiku.view.FileView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface FileMapper {
    int deleteByPrimaryKey(Integer fid);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer fid);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKeyWithBLOBs(File record);

    int updateByPrimaryKey(File record);

    File selectBySha(String sha);

    List<FileView> selectFiles(@Param("keyWord") String keyWord, @Param("file") File file, @Param("startLine") int startLine, @Param("pageSize") int pageSize, @Param("order") boolean order,@Param("status")String status);

    List<FileView> selectFileUploadRecords(@Param("user") User user, @Param("startLine") int startLine, @Param("pageSize") int pageSize);
}