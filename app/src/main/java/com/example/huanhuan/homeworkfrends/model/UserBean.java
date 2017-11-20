package com.example.huanhuan.homeworkfrends.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huanhuan22 on 2017/11/19.
 */

public class UserBean {
    @SerializedName("profile-image")
    private String profileImage;
    private String username;
    private String nick;
    private String avatar;


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
