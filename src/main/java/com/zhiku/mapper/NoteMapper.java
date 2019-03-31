package com.zhiku.mapper;

import com.zhiku.entity.Note;
import com.zhiku.entity.NoteKey;
import com.zhiku.view.NoteView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteMapper {
    int deleteByPrimaryKey(NoteKey key);

    int insert(Note record);

    int insertSelective(Note record);

    Note selectByPrimaryKey(NoteKey key);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKeyWithBLOBs(Note record);

    int updateByPrimaryKey(Note record);

//    自定义方法
    List<Note> selectBySid(@Param("uid")int uid,@Param("sid")int sid);

    List<NoteView> selectNoteViewByUid(int uid);
}