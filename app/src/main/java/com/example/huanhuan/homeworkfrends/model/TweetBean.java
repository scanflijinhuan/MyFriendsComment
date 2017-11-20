package com.example.huanhuan.homeworkfrends.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/19.
 */

public class TweetBean {
    private String  content;
    private List<ImageBean> images;
    private UserBean  sender;
    private List<CommentBean>  comments;
    private String error;
    @SerializedName("unknown error")
    private String unknownError;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImageBean> getImages() {
        return images;
    }

    public void setImages(List<ImageBean> images) {
        this.images = images;
    }

    public UserBean getSender() {
        return sender;
    }

    public void setSender(UserBean sender) {
        this.sender = sender;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUnknownError() {
        return unknownError;
    }

    public void setUnknownError(String unknownError) {
        this.unknownError = unknownError;
    }
}
