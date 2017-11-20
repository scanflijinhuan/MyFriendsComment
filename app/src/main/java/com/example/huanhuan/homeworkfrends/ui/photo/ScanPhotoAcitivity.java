package com.example.huanhuan.homeworkfrends.ui.photo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.adapter.PhotoPagerAdapter;
import com.example.huanhuan.homeworkfrends.model.ImageBean;
import com.example.huanhuan.homeworkfrends.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/14.
 */

public class ScanPhotoAcitivity extends BaseActivity {
    private ImageView mIvFullSceen;
    private ViewPager mPhotoViewPager;
    private Context mContext = this;
    private String url;
    private int itemPosition;
    private ArrayList<String> mImageUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        bindViewData();
        setListener();
    }


    @Override
    protected void initAllViews() {
        mIvFullSceen = findViewById(R.id.scan_photo_fullscreen);
        mPhotoViewPager = (ViewPager) findViewById(R.id.photo_viewpager);
    }

    private void getData() {
        url = getIntent().getStringExtra("url");
        itemPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        mImageUrlList = getIntent().getStringArrayListExtra("imagelist");
    }


    private void setListener() {
    }

    private void bindViewData() {
        PhotoPagerAdapter  pagerAdapter=   new PhotoPagerAdapter(mContext, mImageUrlList);
        mPhotoViewPager.setAdapter(pagerAdapter);
        mPhotoViewPager.setCurrentItem(itemPosition);
        pagerAdapter.setOnFinishListener(new PhotoPagerAdapter.OnFinishListener() {
            @Override
            public void finishPage() {
                finish();
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.scan_photo_activity;
    }


    @Override
    protected void onClickTitleRightMenu() {

    }

    @Override
    protected void onClickTitleLeftMenu() {
        finish();
    }
}
