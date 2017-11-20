package com.example.huanhuan.homeworkfrends.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.model.ImageBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mImageUrlList;
    private LayoutInflater mInflater;
    private SparseArray<View> mPageCache = new SparseArray<View>();

    public PhotoPagerAdapter(Context context, ArrayList<String> imageUrlList) {
        this.mContext = context;
        this.mImageUrlList = imageUrlList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImageUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = mPageCache.get(position);
        String itemUrl = mImageUrlList.get(position);
        ImageView imgView = null;
        if (page == null) {
            page = mInflater.inflate(R.layout.scan_photo_viewpager_item_layout, container, false);
             imgView = (ImageView) page.findViewById(R.id.scan_photo_fullscreen);
            Picasso.with(mContext).load(itemUrl).placeholder(R.mipmap.loading).into(imgView);
            mPageCache.append(position, page);
            imgView.setOnClickListener(onClickListener);
        }
        container.addView(page);
        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    View.OnClickListener  onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishListener.finishPage();
        }
    };

    public interface OnFinishListener{
        void finishPage();
    }
    OnFinishListener finishListener;
    public void setOnFinishListener(OnFinishListener finishListener){
        this.finishListener=finishListener;
    }
}
