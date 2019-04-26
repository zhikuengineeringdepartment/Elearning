package com.zhiku.controller;

import com.zhiku.entity.ColParagraph;
import com.zhiku.entity.Note;
import com.zhiku.entity.User;
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

    /**
     *获得某个用户在某一节的所有收藏段落
     * @param user 用户
     * @param sid section主键
     * @return 返回收藏段落列表
     */
    @ResponseBody
    @RequestMapping(value = "getColParagraphBySid" ,method = RequestMethod.GET)
    public ResponseData getColParagraphBySid(User user,int sid){
        ResponseData responseData = null;
        List<ColParagraph> colParagraphList = paragraphService.getColParagraphs(user.getUid(),sid);
        responseData = ResponseData.ok();
        responseData.putDataValue("colParagraphList",colParagraphList);
        return responseData;
    }

    /**
     * 收藏段落请求
     * 缺乏对于不存在段落的异常处理
     * @param user 用户
     * @param paragraphSeq 段落序列
     * @return 是否收藏成功
     */
    @ResponseBody
    @RequestMapping(value = "addColParagraph",method = RequestMethod.POST)
    public ResponseData addColParagraph(User user,int paragraphSeq){
        ResponseData responseData = null;
        if(paragraphService.addColParagraph(user.getUid(),paragraphSeq)){
            responseData = ResponseData.ok();
        }else{
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("收藏失败，请稍后重试");
        }
        return responseData;
    }

    /**
     * 移除收藏的段落
     * @param user 用户
     * @param pid 段落id
     * @return 是否移除成功
     */
    @ResponseBody
    @RequestMapping("removeColParagraph")
    public ResponseData removeColParagraph(User user,int pid){
        ResponseData responseData = null;
        if(paragraphService.removeColParagraph(user.getUid(), pid)){
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
     * 暂时还不支持cid和page属性
     * @param user 用户
     * @param cid 课程号
     * @param page 想要查看的页数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getColParagraphViews",method = RequestMethod.GET)
    public ResponseData getColParagraphViews(User user, int cid, int page){
        ResponseData responseData = null;
        List<ColParagraphView> colParagraphViews = paragraphService.getColParagraphViews(user.getUid());
        responseData = ResponseData.ok();
        responseData.putDataValue("colParagraphViews",colParagraphViews);
        return responseData;
    }

    /**
     * 获得用户对应小节的所有笔记
     * @param user 用户
     * @param sid 节号
     * @return 笔记列表
     */
    @ResponseBody
    @RequestMapping(value = "getNoteBySid" ,method = RequestMethod.GET)
    public ResponseData getNoteBySid(User user,int sid){
        ResponseData responseData = null;
        List<Note> notes = paragraphService.getNotesBySid(user.getUid(),sid);
        responseData = ResponseData.ok();
        responseData.putDataValue("notes",notes);
        return responseData;
    }

    /**
     * 删除用户对某个段落的笔记
     * @param user 用户
     * @param pid 段落
     * @return 是否删除成功
     */
    @ResponseBody
    @RequestMapping(value = "removeNote",method = RequestMethod.POST)
    public ResponseData removeNote(User user,int pid){
        ResponseData responseData = null;
        if(paragraphService.removeNote(user.getUid(),pid)){
            responseData = ResponseData.ok();
        }else {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("操作失败，请重试！");
        }
        return responseData;
    }

    /**
     * 用户为一个段落添加一条笔记
     * @param user 用户
     * @param paragraphSeq 段落序列
     * @param note 笔记
     * @return 是否添加成功
     */
    @ResponseBody
    @RequestMapping(value = "addNote",method = RequestMethod.POST)
    public ResponseData addNote(User user ,int paragraphSeq ,Note note){
        ResponseData responseData = null;
        if(paragraphService.addNote(user,note,paragraphSeq)){
            responseData = ResponseData.ok();
        }else {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("操作失败，请重试！");
        }
        return responseData;
    }

    /**
     * 获得用户某个课的所有笔记
     * 可换页
     * 暂时没有使用cid和page属性
     * @param user
     * @param page
     * @param cid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getNoteViews",method = RequestMethod.GET)
    public ResponseData getNoteViews(User user,int page,int cid){
        ResponseData responseData = null;
        List<NoteView> noteViews = paragraphService.getNoteViews(user.getUid(),cid,page);
        responseData = ResponseData.ok();
        responseData.putDataValue("noteViews",noteViews);
        return responseData;
    }

    /**
     * 用户修改对应段落笔记内容
     * @param user 用户
     * @param pid 段落
     * @param note 笔记
     * @return 是否返回成功
     */
    @ResponseBody
    @RequestMapping(value = "modifyNote",method = RequestMethod.POST)
    public ResponseData modifyNote(User user,int pid,Note note){
        ResponseData responseData = null;
        note.setNotePara(pid);
        note.setNoteUser(user.getUid());
        if(paragraphService.modifyNote(note)){
            responseData = ResponseData.ok();
        }else {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("操作失败，请重试！");
        }
        return responseData;
    }
}
