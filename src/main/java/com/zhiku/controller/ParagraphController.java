package com.zhiku.controller;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.Note;
import com.zhiku.service.ParagraphService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.ColParagraphView;
import com.zhiku.view.NoteView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "paragraph")
public class ParagraphController {
    @Autowired
    ParagraphService paragraphService;

    @ResponseBody
    @RequestMapping(value = "getColParagraphBySid" ,method = RequestMethod.GET)
    public ResponseData getColParagraphBySid(int uid,int sid){
        ResponseData responseData = null;
        List<ColParagraph> colParagraphList = paragraphService.getColParagraphs(uid,sid);
        responseData = ResponseData.ok();
        responseData.putDataValue("colParagraphList",colParagraphList);
        return responseData;
    }

    /**
     * 收藏段落请求
     * @param uid 用户id
     * @param pid 段楼id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addColParagraph",method = RequestMethod.POST)
    public ResponseData addColParagraph(int uid,int pid){
        ResponseData responseData = null;
        if(paragraphService.addColParagraph(uid,pid)){
            responseData = ResponseData.ok();
        }else{
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("收藏失败，请稍后重试");
        }
        return responseData;
    }

    /**
     * 移除收藏的段落
     * @param uid 用户id
     * @param pid 段落id
     * @return
     */
    @ResponseBody
    @RequestMapping("removeColParagraph")
    public ResponseData removeColParagraph(int uid,int pid){
        ResponseData responseData = null;
        if(paragraphService.removeColParagraph(uid, pid)){
            responseData = ResponseData.ok();
        }else{
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("删除收藏失败，请稍后重试");
        }
        return responseData;
    }

    /**
     * 获得一个用户一门课的所有收藏
     * 可换页
     * @param uid 用户id
     * @param cid 课程号
     * @param page 想要查看的页数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getColParagraphViews",method = RequestMethod.GET)
    public ResponseData getColParagraphViews(int uid,int cid,int page){
        ResponseData responseData = null;
        List<ColParagraphView> colParagraphViews = paragraphService.getColParagraphViews(uid);
        responseData = ResponseData.ok();
        responseData.putDataValue("colParagraphViews",colParagraphViews);
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "getNoteBySid" ,method = RequestMethod.GET)
    public ResponseData getNoteBySid(int uid,int sid){
        ResponseData responseData = null;
        List<Note> notes = paragraphService.getNotesBySid(uid,sid);
        responseData = ResponseData.ok();
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "removeNote",method = RequestMethod.POST)
    public ResponseData removeNote(int uid,int pid){
        ResponseData responseData = null;
        if(paragraphService.removeNote(uid,pid)){
            responseData = ResponseData.ok();
        }else {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("操作失败，请重试！");
        }
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "addNote",method = RequestMethod.POST)
    public ResponseData addNote(int uid ,int pid ,Note note){
        ResponseData responseData = null;
        note.setNotePara(pid);
        note.setNoteUser(uid);
        if(paragraphService.addNote(note)){
            responseData = ResponseData.ok();
        }else {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("操作失败，请重试！");
        }
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "getNoteViews",method = RequestMethod.GET)
    public ResponseData getNoteViews(int uid,int page,int cid){
        ResponseData responseData = null;
        List<NoteView> noteViews = paragraphService.getNoteViews(uid,cid,page);
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "modifyNote",method = RequestMethod.POST)
    public ResponseData modifyNote(int uid,int pid,Note note){
        ResponseData responseData = null;
        note.setNotePara(pid);
        note.setNoteUser(uid);
        if(paragraphService.modifyNote(note)){
            responseData = ResponseData.ok();
        }else {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("操作失败，请重试！");
        }
        return responseData;
    }
}
