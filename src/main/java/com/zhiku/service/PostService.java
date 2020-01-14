package com.zhiku.service;

import com.sun.star.uno.RuntimeException;
import com.zhiku.entity.Post;
import com.zhiku.entity.User;
import com.zhiku.mapper.UserMapper;
import com.zhiku.mongo.PostTemplate;
//import com.zhiku.util.SmallTools;
import com.zhiku.view.PostView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.ArrayList;
import java.util.Date;

@Service
public class PostService {

    @Autowired
    private PostTemplate postTemplate;
    @Autowired
    private UserMapper userMapper;

    //排序规则：不排|更新时间倒序|点赞数倒序
    private int[] orderRule={0,1,2};


    public Post get(String postId) {
        return  postTemplate.get(postId);
    }


    public void deleteOne(String postId) {
        long num = postTemplate.deleteOne(postId);
        if(num!=1){
            throw new RuntimeException("删除异常");
        }
    }

    public void add(Integer uid, String postTitle, String postContent,Integer courseId) {

        Date currentDate=new Date();
        Post post=new Post();
        post.setContent(postContent);
        post.setTitle(postTitle);
        post.setAuthor(uid);
        post.setDelete(false);
        post.setReplyCount(0);
        post.setAgreeCount(0);
        post.setDisagreeUsers(new ArrayList<>());
        post.setUpdateTime(currentDate);
        post.setCreateTime(currentDate);
        post.setAgreeUsers(new ArrayList<>());
        if(courseId==null){
            post.setCourseId(0);
        }else{
            post.setCourseId(courseId);
        }
        postTemplate.add(post);
    }

    public List<PostView> list(Integer page, Integer pageSize,Integer order){
        Integer orderByTime=0;
        Integer orderByAgree=0;
        if(order==orderRule[1]){
            orderByTime=-1;
        }else if(order==orderRule[2]){
            orderByAgree=-1;
        }
        List<Post> posts=postTemplate.pageList( page,pageSize,orderByTime,orderByAgree );
        if(posts==null||posts.size()==0)
            return null;
        Set<Integer> uids=new HashSet<>(  );
        for(Post post:posts){
            uids.add(post.getAuthor());
        }
        List<User>  users=userMapper.selectByUids(new ArrayList<>( uids ) );
        HashMap<Integer,User> uid2User=new HashMap<>(  );
        if(users!=null){
            for(User user:users){
                uid2User.put(user.getUid(),user);
            }
        }

        List<PostView> postViews=new ArrayList<>(  );
        for(Post post:posts){
            PostView postView=new PostView();
//            SmallTools.fatherToChild( post,postView );
            postView.addFather( post );
            User user=uid2User.get(post.getAuthor());
            postView.setUserNick( user.getUserNick() );
            postView.setUserAvatar( user.getUserAvatar() );
            postViews.add( postView );
        }

        return postViews;
    }
}
