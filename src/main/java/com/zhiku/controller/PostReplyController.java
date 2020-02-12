package com.zhiku.controller;

import com.zhiku.entity.User;
import com.zhiku.entity.mysql.Report;
import com.zhiku.service.PostReplyService;
import com.zhiku.service.PostService;
import com.zhiku.service.ReportService;
import com.zhiku.util.ResponseData;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/*
* 评论的controller
* 第一个接口负责处理一级评论
* */

@RestController
@RequestMapping("/reply")
public class PostReplyController {

    @Autowired
    private PostReplyService postReplyService;

    @Autowired
    private ReportService reportService;

    /**
    * 一级评论接口
    * 传入参数:
    * @param pId 1.帖子id
    * @param authorId 2.回复者id
    * @param replyContent 3.回复内容
    * */
    @RequestMapping("/first")
    public ResponseData firstReply(@RequestParam("pId") ObjectId pId, @RequestParam("authorId") int authorId,
                                   @RequestParam("replyContent") String replyContent) {
        if (pId == null || authorId < 0 || replyContent == "") {
            return ResponseData.badRequest();
        } else {
            return postReplyService.insert(pId, authorId, replyContent);
        }
    }
    /**
    * 多级评论接口
    * 传入参数:
    * 1.帖子id
    * 2.回复者id
    * 3.被回复者id
    * 4.回复内容
    * */
    @RequestMapping("/second")
    public ResponseData secondReply(@RequestParam("pId") ObjectId pId,@RequestParam("authorId") int authorId,
                                    @RequestParam("replyContent") String replyContent,@RequestParam("repliedAuthor") int repliedAuthor){
        if (pId == null || authorId < 0 || repliedAuthor < 0 || replyContent == "") {
            return ResponseData.badRequest();
        }
        else {
            return postReplyService.insert(pId,authorId,repliedAuthor,replyContent);
        }
    }

    /**
     * 评论点赞接口
     * 传入参数:
     * @param type 1.评论类型。1表示一级评论，2表示多级评论
     * @param rId 2.评论id，
     * @param rId2 如果为多级评论则还有一个参数
     * @param agreeId 3.点赞者id
     * */
    @RequestMapping("/agree")
    public ResponseData agreeReply(@RequestParam("type") int type,@RequestParam("rId") ObjectId rId,
                                   @RequestParam("rId2") ObjectId rId2,@RequestParam("agreeId") int agreeId){
        return null;
    }

    /**
     * 评论点赞取消接口
     * 传入参数：
     * @param type 1.评论类型
     * @param rId 2.评论id
     * @param rId2 如果是二级评论，则有两个
     * 返回情况
     * 未找到点赞者
     * 找到并取消
     * */
    @RequestMapping("/cancelagree")
    public ResponseData cancelAgreeReply(@RequestParam("type") int type,@RequestParam("rId") ObjectId rId,
                                         @RequestParam("rId2") ObjectId rId2,@RequestParam("cancelId") int cancelId){
        return null;
    }

    /**
     * 删除评论接口
     * 传入参数:
     * @param type 1.删除类型，1表示删除一篇文章所有评论，2表示删除单条评论
     * @param pId 2.文章id
     * @param rId 3.评论id
     * @param rId2 如果是二级评论，则有两个
     * 返回情况:
     * 评论不存在
     * 删除成功
     * */
    @RequestMapping("/deletereply")
    public ResponseData deleteReply(@RequestParam("type") int type, @RequestParam("pId") ObjectId pId,
                                    @RequestParam("rId") String rId, @RequestParam("rId2") String rId2){
        if (type==1){
            return postReplyService.delete(pId);
        }
        else if (type==2){
            if (rId == ""){
                return ResponseData.badRequest();
            }
            else {
                if (rId2 == ""){
                    return postReplyService.delete(pId,rId);
                }
                else {
                    return postReplyService.delete(pId,rId,rId2);
                }
            }
        }else {
            return ResponseData.badRequest();
        }
    }

    /**
     * 查询评论接口
     * 传入参数:
     * @param pId
     *
     * 返回数据:
     * postfirstreply对象数组
     * */
    @RequestMapping("/getreply")
    public ResponseData getReply(@RequestParam("pId") int pId){
        return null;
    }

    /**
     * 举报一级回答
     * pid 帖子id
     * reid 回答id
     * uid 举报人id
     * reason 举报理由
     */
    @RequestMapping(value = "/reportFirst",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData reportFirst(User user,Report report){
        report.setUid( user.getUid() );
        report.setType( Report.TYPE_FIRST_REPLY );
        report.setState( Report.STATE_NULL );
        report.setDate( new Date(  ) );
        reportService.add( report );
        return ResponseData.ok();
    }

    /**
     * 举报多级回复
     * pid 帖子id
     * reid 回答id
     * uid 举报人id
     * reason 举报理由
     */
    @RequestMapping(value = "/reportSecond",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData reportSecond(User user, Report report){
        report.setUid( user.getUid() );
        report.setType( Report.TYPE_SECOND_REPLY );
        report.setState( Report.STATE_NULL );
        report.setDate( new Date(  ) );
        reportService.add( report );
        return ResponseData.ok();
    }
}
