package com.zhiku.mapper;

import com.zhiku.entity.Note;
import com.zhiku.entity.NoteKey;

public interface NoteMapper {
    int deleteByPrimaryKey(NoteKey key);

    int insert(Note record);

    int insertSelective(Note record);

    Note selectByPrimaryKey(NoteKey key);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKeyWithBLOBs(Note record);

    int updateByPrimaryKey(Note record);
}