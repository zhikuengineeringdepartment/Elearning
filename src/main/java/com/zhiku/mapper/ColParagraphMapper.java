package com.zhiku.mapper;

import com.zhiku.entity.mysql.ColParagraphMysql;
import com.zhiku.entity.mysql.ColParagraphKey;
import com.zhiku.view.ColParagraphSectionView;
import com.zhiku.view.ColParagraphView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ColParagraphMapper {
    int deleteByPrimaryKey(ColParagraphKey key);

    int insert(ColParagraphMysql record);

    int insertSelective(ColParagraphMysql record);

    ColParagraphMysql selectByPrimaryKey(ColParagraphKey key);

    int updateByPrimaryKeySelective(ColParagraphMysql record);

    int updateByPrimaryKey(ColParagraphMysql record);

    List<ColParagraphSectionView> selectBySid(@Param("uid") int uid, @Param("sid") int sid);

    List<ColParagraphView> selectParagraphView(int uid);

    /**
     * 按条件查询
     * @param uid 用户id
     * @param cid 课程id  =null表示不限制课程
     * @param ordtime 按时间排序，<0升序，=0不排序，>0降序
     * @param page 页偏移量 ==null不分页
     * @param pagesize 读取行数 ==null不分页
     */
    List<ColParagraphView> selectParagraphViewByWhere(@Param("uid") Integer uid, @Param("cid") Integer cid, @Param("ordtime")Integer ordtime,
                                                      @Param("page")Integer page,@Param("pagesize")Integer pagesize);
}