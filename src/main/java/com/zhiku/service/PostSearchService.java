package com.zhiku.service;

import com.zhiku.entity.Post;
import com.zhiku.entity.User;
import com.zhiku.mapper.UserMapper;
import com.zhiku.mongo.PostTemplate;
import com.zhiku.util.FileStatus;
import com.zhiku.view.PostView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostSearchService {
    @Autowired
    private PostTemplate postTemplate;
    @Autowired
    private UserMapper userMapper;

    public List<PostView> search(String keywords,Integer page, Integer pageSize,Integer order){
        List<Post> posts=postTemplate.searchByOneWord(keywords,page,pageSize,order );
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
            postView.addFather( post );
            User user=uid2User.get(post.getAuthor());
            postView.setUserNick( user.getUserNick() );
            postView.setUserAvatar( user.getUserAvatar() );
            postViews.add( postView );
        }

        return postViews;
    }

    public enum PostSearchStatus {
        ORDER_BY_UPDATEDATE(1,"按更新时间排序"),
        ORDER_BY_HEAT(2,"按热度排序"),
        ORDER_BY_SIMILAR(3,"按相似度排序");
        private final int code;  //标识码
        private final String name;  //描述

        private PostSearchStatus(int code,String name){
            this.code = code;
            this.name = name;
        }

        public int getCode(){ return this.code; }
        public String getName(){
            return this.name;
        }

        public static String getName(int code) {
            for (PostSearchStatus c : PostSearchStatus.values()) {
                if (c.getCode()==code ) {
                    return c.name;
                }
            }
            return null;
        }
    }

}
