package com.zhiku.controller;

import com.zhiku.entity.Note;
import com.zhiku.entity.User;
import com.zhiku.service.ParagraphService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.ColParagraphSectionView;
import com.zhiku.view.ColParagraphView;
import com.zhiku.view.NoteView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin(value = "*")
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
    @RequestMapping(value = "getColParagraphBySid" ,method = RequestMethod.POST)
    public ResponseData getColParagraphBySid(User user, int sid){
        ResponseData responseData = null;
        List<ColParagraphSectionView> colParagraphList = paragraphService.getColParagraphs(user.getUid(),sid);
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
    public ResponseData addColParagraph(User user, int paragraphSeq){
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
     * @param paragraphSeq 段落序列
     * @return 是否移除成功
     */
    @ResponseBody
    @RequestMapping("removeColParagraph")
    public ResponseData removeColParagraph(User user, int paragraphSeq){
        ResponseData responseData = null;
        if(paragraphService.removeColParagraph(user.getUid(), paragraphSeq)){
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
    public ResponseData getColParagraphViews(User user, Integer cid, Integer page){
//        //todo:删除下面测试
//        System.out.println("查询课程："+cid);////////////////
        ResponseData responseData = null;
        List<ColParagraphView> colParagraphViews = paragraphService.getColParagraphViews(user.getUid(),cid,1,page,null);
        responseData = ResponseData.ok();
        responseData.putDataValue("colParagraphViews",colParagraphViews);
//        //todo:删除下面测试
//        for(ColParagraphView cpv:colParagraphViews){
//            System.out.println("查询段落："+cpv.getCourseName()+"|||"+cpv.getParagraphContent());////////////////
//        }

        return responseData;
    }

    /**
     * 获得用户对应小节的所有笔记
     * @param user 用户
     * @param sid 节号
     * @return 笔记列表
     */
    @ResponseBody
    @RequestMapping(value = "getNoteBySid" ,method = RequestMethod.POST)
    public ResponseData getNoteBySid(User user, int sid){
        ResponseData responseData = null;
        List<NoteView> notes = paragraphService.getNotesBySid(user.getUid(),sid);
        responseData = ResponseData.ok();
        responseData.putDataValue("noteViews",notes);
        return responseData;
    }

    /**
     * 编辑笔记，包括添加笔记和修改笔记和删除笔记
     * @param user
     * @param paragraphSeq
     * @param note
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "editNote",method = RequestMethod.POST)
    public ResponseData editNote(User user, int paragraphSeq , Note note){
        ResponseData responseData = null;
        Note my_note = paragraphService.getNoteByNoteKey(user,paragraphSeq);
        if(note.getNoteContent().equals("<p><br></p>")){    //判断是否为空
            paragraphService.removeNote(my_note);
        }else if(my_note != null){
            my_note.setNoteContent(note.getNoteContent());
            paragraphService.modifyNote(my_note);
        }else{
            paragraphService.addNote(user,note,paragraphSeq);
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
    public ResponseData getNoteViews(User user, int page, int cid){
        ResponseData responseData = null;
        List<NoteView> noteViews = paragraphService.getNoteViews(user.getUid(),cid,page);
        responseData = ResponseData.ok();
        responseData.putDataValue("noteViews",noteViews);
        return responseData;
    }

}
