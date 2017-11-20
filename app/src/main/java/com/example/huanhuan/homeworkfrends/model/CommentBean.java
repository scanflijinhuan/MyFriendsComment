package com.example.huanhuan.homeworkfrends.model;

/**
 * Created by huanhuan22 on 2017/11/19.
 */

public class CommentBean {
    private String  content;
    private UserBean sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getSender() {
        return sender;
    }

    public void setSender(UserBean sender) {
        this.sender = sender;
    }
}
