package com.zhiku.view;

import com.zhiku.entity.Post;

public class PostView extends Post {
    private String userNick;
    private String userAvatar;

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
    
    public void addFather(Post post){
        setAgreeCount( post.getAgreeCount() );
        setCourseId( post.getCourseId() );
        setAuthor( post.getAuthor() );
        setContent( post.getContent() );
        setCreateTime( post.getCreateTime() );
        setDelete( post.isDelete() );
        setAgreeUsers( post.getAgreeUsers() );
        setDisagreeUsers( post.getDisagreeUsers() );
        setReplyCount( post.getReplyCount() );
        setTitle( post.getTitle() );
        setUpdateTime( post.getUpdateTime() );
        setDisagreeCount( post.getDisagreeCount() );
        setPid( post.getPid() );
    }
}
