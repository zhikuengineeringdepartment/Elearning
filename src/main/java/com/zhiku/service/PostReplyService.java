package com.zhiku.service;

import com.zhiku.entity.PostFirstReply;
import com.zhiku.entity.PostMultistepReply;
import com.zhiku.entity.PostReply;
import com.zhiku.mongo.ReplyTemplate;
import com.zhiku.util.ResponseData;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostReplyService {
    @Autowired
    private ReplyTemplate replyTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 插入一个一级评论
     * @param pId
     * @param authorId
     * @param content
     * */
    public ResponseData insert(ObjectId pId,Integer authorId,String content){
        PostReply result = replyTemplate.get(pId);
        PostFirstReply postFirstReply = new PostFirstReply();
        postFirstReply.setrId(UUID.randomUUID().toString());
        postFirstReply.setReplyAuthor(authorId);
        postFirstReply.setReplyContent(content);
        List<PostFirstReply> postFirstReplies = new ArrayList<PostFirstReply>();
        if (result == null){
            PostReply postReply = new PostReply();
            postReply.setPid(pId);
            postFirstReplies.add(postFirstReply);
            postReply.setPostFirstReplyList(postFirstReplies);
            replyTemplate.insert(postReply);
            return ResponseData.ok();
        }
        else {
            postFirstReplies = result.getPostFirstReplyList();
            postFirstReplies.add(postFirstReply);
            result.setPostFirstReplyList(postFirstReplies);
            if (replyTemplate.update(pId, result)) {
                return ResponseData.ok();
            } else {
                logger.error("插入一级评论失败");
                return ResponseData.serverInternalError();
            }
        }
    }

    /**
     * 插入二级评论
     * @param pId
     * @param authorId
     * @param repliedAuthor
     * @param replyContent
     * */
    public ResponseData insert(ObjectId pId,int authorId,int repliedAuthor,String replyContent){
        PostReply result = replyTemplate.get(pId);
        PostMultistepReply postMultistepReply = new PostMultistepReply();
        postMultistepReply.setrId(UUID.randomUUID().toString());
        postMultistepReply.setReplyAuthor(authorId);
        postMultistepReply.setReplyedAuthor(repliedAuthor);
        postMultistepReply.setReplyContent(replyContent);
        if (result==null){
            ResponseData responseData = new ResponseData(1001,"article not exist");
            return responseData;
        }
        else {
            List<PostFirstReply> postFirstReplies = result.getPostFirstReplyList();
            int index = -1;
            for(PostFirstReply first:postFirstReplies){
                if (first.getReplyAuthor()==repliedAuthor){
                    index = postFirstReplies.indexOf(first);
                    break;
                }
            }
            if (index==-1){
                ResponseData responseData = new ResponseData(1001,"replied author not found");
                return responseData;
            }
            else {
                postFirstReplies.get(index).getPostMultistepReplyList().add(postMultistepReply);
                result.setPostFirstReplyList(postFirstReplies);
                if (replyTemplate.update(pId,result)){
                    return ResponseData.ok();
                }
                else {
                    logger.error("插入二级评论失败");
                    return ResponseData.serverInternalError();
                }
            }
        }
    }
    /**
     * 删除文章所有评论
     * @param pId
     * */
    public ResponseData delete(ObjectId pId){
        if (replyTemplate.deleteAll(pId)){
            return ResponseData.ok();
        }
        else {
            logger.error("删除所有评论失败");
            return ResponseData.serverInternalError();
        }
    }
    /**
     * 删除一级评论
     * @param pId
     * @param rId
     * */
    public ResponseData delete(ObjectId pId, String rId){
        PostReply result = replyTemplate.get(pId);
        if (result==null){
            ResponseData responseData = new ResponseData(1001,"article not exist");
            return responseData;
        }
        else {
            List<PostFirstReply> postFirstReplies = result.getPostFirstReplyList();
            int index = -1;
            for (PostFirstReply firstReply:postFirstReplies){
                if (firstReply.getrId()==rId){
                    index = postFirstReplies.indexOf(firstReply);
                    break;
                }
            }
            if (index == -1){
                ResponseData responseData = new ResponseData(1001,"reply not found");
                return responseData;
            }
            else {
                postFirstReplies.remove(index);
                result.setPostFirstReplyList(postFirstReplies);
                if (replyTemplate.update(pId,result)){
                    return ResponseData.ok();
                }
                else {
                    logger.error("删除一级评论失败");
                    return ResponseData.serverInternalError();
                }
            }
        }
    }
    /**
     * 删除二级评论
     * @param pId
     * @param rId
     * @param rId2
     * */
    public ResponseData delete(ObjectId pId,String rId,String rId2){
        PostReply result = replyTemplate.get(pId);
        if (result==null){
            ResponseData responseData = new ResponseData(1001,"article not exist");
            return responseData;
        }
        else {
            List<PostFirstReply> postFirstReplies = result.getPostFirstReplyList();
            int index = -1;
            for (PostFirstReply firstReply:postFirstReplies){
                if (firstReply.getrId()==rId){
                    index = postFirstReplies.indexOf(firstReply);
                    break;
                }
            }
            if (index == -1){
                ResponseData responseData = new ResponseData(1001,"reply not found");
                return responseData;
            }
            else {
                List<PostMultistepReply> postMultistepReplies = postFirstReplies.get(index).getPostMultistepReplyList();
                int index2 = -1;
                for (PostMultistepReply multistepReply:postMultistepReplies){
                    if (multistepReply.getrId()==rId2){
                        index2 = postMultistepReplies.indexOf(multistepReply);
                        break;
                    }
                }
                if (index2==-1){
                    ResponseData responseData = new ResponseData(1001,"reply not found");
                    return responseData;
                }
                else {
                    postMultistepReplies.remove(index2);
                    postFirstReplies.get(index).setPostMultistepReplyList(postMultistepReplies);
                    result.setPostFirstReplyList(postFirstReplies);
                    if (replyTemplate.update(pId,result)){
                        return ResponseData.ok();
                    }
                    else {
                        logger.error("删除二级评论失败");
                        return ResponseData.serverInternalError();
                    }
                }
            }
        }
    }
}
