package com.zhiku.service;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.ColParagraphKey;
import com.zhiku.mapper.ColParagraphMapper;
import com.zhiku.mapper.ParagraphMapper;
import com.zhiku.view.ColParagraphView;
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

    /**
     * 获取某一节的收藏段落
     * @param uid 用户id
     * @param sid 节号
     * @return 一个收藏段落的列表
     */
    public List<ColParagraph> getColParagraphs(int uid,int sid){
        return colParagraphMapper.selectBySid(uid,sid);
    }

    /**
     * 获取一个用户的所有收藏段落
     * @param uid 用户名
     * @return
     */
    public List<ColParagraphView> getColParagraphViews(int uid){
        return colParagraphMapper.selectViewBySid(uid);
    }

    /**
     * 收藏一个段落
     * @param uid
     * @param pid
     * @return
     */
    public boolean addColParagraph(int uid, int pid) {
        ColParagraph colParagraph = new ColParagraph();
        colParagraph.setColpPara(pid);
        colParagraph.setColpUser(uid);
        colParagraph.setColpDate(new Date());
        if(colParagraphMapper.insert(colParagraph)>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean removeColParagraph(int uid,int pid){
        ColParagraphKey colParagraphKey = new ColParagraphKey();
        colParagraphKey.setColpUser(uid);
        colParagraphKey.setColpPara(pid);
        if(colParagraphMapper.deleteByPrimaryKey(colParagraphKey)>0){
            return true;
        }else {
            return false;
        }
    }
}
