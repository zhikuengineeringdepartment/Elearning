package com.zhiku.mapper;

import com.zhiku.entity.Picture;
import com.zhiku.view.PictureView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PictureMapper {
    int deleteByPrimaryKey(Integer picId);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Integer picId);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);

    //自定义
    List<PictureView> selectByUid(Integer uid,Integer m,Integer n);
}