package com.example.huanhuan.homeworkfrends.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageBean   {
    private String url;
    public ImageBean() {
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {

        return "image---->>url="+url;
    }
}