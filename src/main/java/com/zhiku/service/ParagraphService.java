package com.zhiku.service;

import com.zhiku.entity.*;
import com.zhiku.mapper.ColParagraphMapper;
import com.zhiku.mapper.NoteMapper;
import com.zhiku.mapper.ParagraphMapper;
import com.zhiku.view.ColParagraphSectionView;
import com.zhiku.view.ColParagraphView;
import com.zhiku.view.NoteView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParagraphService {
    @Autowired
    ParagraphMapper paragraphMapper;
    @Autowired
    ColParagraphMapper colParagraphMapper;
    @Autowired
    NoteMapper noteMapper;

    /**
     * 获取某一节的收藏段落
     * @param uid 用户id
     * @param sid 节号
     * @return 一个收藏段落的列表
     */
    public List<ColParagraphSectionView> getColParagraphs(int uid, int sid){
        return colParagraphMapper.selectBySid(uid,sid);
    }

    /**
     * 获取一个用户的所有收藏段落
     * @param uid 用户名
     * @return
     */
    public List<ColParagraphView> getColParagraphViews(int uid){
        return colParagraphMapper.selectParagraphView(uid);
    }

    /**
     * 收藏一个段落
     * @param uid
     * @param paragraphSeq
     * @return
     */
    public boolean addColParagraph(int uid, int paragraphSeq) {
        ColParagraph colParagraph = new ColParagraph();
        Paragraph paragraph = paragraphMapper.selectByParagraphSeq(paragraphSeq);
        colParagraph.setColpPara(paragraph.getPid());
        colParagraph.setColpUser(uid);
        colParagraph.setColpDate(new Date());
        if(colParagraphMapper.insert(colParagraph)>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除一个收藏的段落
     * @param uid
     * @param paragraphSeq
     * @return
     */
    public boolean removeColParagraph(int uid,int paragraphSeq){
        ColParagraphKey colParagraphKey = new ColParagraphKey();
        Paragraph paragraph = paragraphMapper.selectByParagraphSeq(paragraphSeq);
        colParagraphKey.setColpUser(uid);
        colParagraphKey.setColpPara(paragraph.getPid());
        if(colParagraphMapper.deleteByPrimaryKey(colParagraphKey)>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获得某用户对应小节的全部笔记
     * @param uid
     * @param sid
     * @return
     */
    public List<NoteView> getNotesBySid(int uid, int sid){
        return noteMapper.selectBySid(uid,sid);
    }

    /**
     * 依据note的联合主键找到对应的note
     * @param user  联合主键中的uid
     * @param paragraphSeq  联合主键中pid
     * @return
     */
    public Note getNoteByNoteKey(User user, int paragraphSeq){
        NoteKey noteKey = new NoteKey();
        Paragraph paragraph = paragraphMapper.selectByParagraphSeq(paragraphSeq);
        noteKey.setNotePara(paragraph.getPid());
        noteKey.setNoteUser(user.getUid());
        return noteMapper.selectByPrimaryKey(noteKey);
    }

    /**
     * 添加一条笔记
     *
     * @param user
     * @param note 笔记
     * @param paragraphSeq
     * @return 是否添加成功
     */
    public boolean addNote(User user, Note note, int paragraphSeq){
        note.setNoteUser(user.getUid());
        Paragraph paragraph = paragraphMapper.selectByParagraphSeq(paragraphSeq);
        note.setNotePara(paragraph.getPid());
        note.setNoteDate(new Date());
        if(noteMapper.insert(note)>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除一条笔记
     * @param noteKey
     * @return 是否删除成功
     */
    public boolean removeNote(NoteKey noteKey){
        if(noteMapper.deleteByPrimaryKey(noteKey)>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 修改已有笔记的内容
     * @param note
     * @return
     */
    public boolean modifyNote(Note note){
        note.setNoteDate(new Date());
        if(noteMapper.updateByPrimaryKeyWithBLOBs(note)>0){
            return true;
        }else{
            return false;
        }
    }

    public List<NoteView> getNoteViews(int uid , int cid, int page){
        return noteMapper.selectNoteViewByUid(uid);
    }

}
