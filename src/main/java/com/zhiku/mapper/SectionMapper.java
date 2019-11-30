package com.zhiku.mapper;

import com.zhiku.entity.mysql.Section;
import com.zhiku.view.SectionView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SectionMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);

    //自定义方法
    int insertAll(List<Section> record);

    SectionView getSectionViewBySid(Integer sid);

    Integer selectSectionID(@Param( "sectionName" ) String sectionName, @Param( "sectionCourse" ) Integer sectionCourse);

    Integer selectSectionMaxID(Integer sectionCourse);

    int deleteByCourseId(Integer cid);

}