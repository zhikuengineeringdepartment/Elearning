package com.zhiku.mapper;

import com.zhiku.entity.mysql.NoteMysql;
import com.zhiku.entity.mysql.NoteKey;
import com.zhiku.view.NoteView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface NoteMapper {
    int deleteByPrimaryKey(NoteKey key);

    int insert(NoteMysql record);

    int insertSelective(NoteMysql record);

    NoteMysql selectByPrimaryKey(NoteKey key);

    int updateByPrimaryKeySelective(NoteMysql record);

    int updateByPrimaryKeyWithBLOBs(NoteMysql record);

    int updateByPrimaryKey(NoteMysql record);

//    自定义方法
    List<NoteView> selectBySid(@Param("uid") int uid, @Param("sid") int sid);

    List<NoteView> selectNoteViewByUid(int uid);
}