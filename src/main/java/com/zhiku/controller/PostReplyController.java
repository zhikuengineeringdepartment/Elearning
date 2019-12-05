package com.zhiku.controller;

import com.zhiku.util.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* 评论的controller
* 第一个接口负责处理一级评论
* */

@RestController
@RequestMapping("/reply")
public class PostReplyController {


    /**
    * 一级评论接口
    * 传入参数:
    * 1.帖子id
    * 2.回复者id
    * 3.回复内容
    * */
    @RequestMapping("/first")
    public ResponseData firstReply(@RequestParam("rId") int rId,@RequestParam("authorId") int authorId,
                                   @RequestParam("replyContent") String replyContent){
        return null;
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
    public ResponseData secondReply(@RequestParam("rId") int rId,@RequestParam("authorId") int authorId,
                                    @RequestParam("replyContent") String replyContent,@RequestParam("repliedAuthor") int repliedAuthor){
        return null;
    }

    /**
     * 评论点赞接口
     * 传入参数:
     * 1.评论类型。1表示一级评论，2表示多级评论
     * 2.评论id
     * 3.点赞者id
     * */
    @RequestMapping("/agree")
    public ResponseData agreeReply(@RequestParam("type") int type,@RequestParam("rId") int rId,
                                   @RequestParam("agreeId") int agreeId){
        return null;
    }

    /**
     * 评论点赞取消接口
     * 传入参数：
     * 1.评论类型
     * 2.评论id
     * 3.点赞者id
     * 返回情况
     * 未找到点赞者
     * 找到并取消
     * */
    @RequestMapping("/cancelagree")
    public ResponseData cancelAgreeReply(@RequestParam("type") int type,@RequestParam("rId") int rId,
                                         @RequestParam("agreeId") int agreeId){
        return null;
    }

    /**
     * 删除评论接口
     * 传入参数:
     * 1.评论类型
     * 2.评论id
     * 返回情况:
     * 评论不存在
     * 删除成功
     * */
    @RequestMapping("/deletereply")
    public ResponseData deleteReply(@RequestParam("type") int type,@RequestParam("rId") int rId){
        return null;
    }

    /**
     * 查询评论接口
     * 传入参数:
     * 1.帖子id
     * 返回数据:
     * postfirstreply对象数组
     * */
    @RequestMapping("/getreply")
    public ResponseData getReply(@RequestParam("rId") int rId){
        return null;
    }

}
