package com.example.huanhuan.homeworkfrends.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.model.ImageBean;
import com.example.huanhuan.homeworkfrends.ui.photo.ScanPhotoAcitivity;
import com.example.huanhuan.homeworkfrends.utils.ScreenTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/13.
 */

public class PhotoGroups extends ViewGroup {

    private int gap = 5; //间隔
    private int columns;
    private int rows;
    private ArrayList<String> listData; //image data
    private ArrayList<String>  mImageList =new ArrayList<String>();
    private int totalWidth; //控件宽度
    private Context mContext;
    public PhotoGroups(Context context) {
        super(context);
    }

    public PhotoGroups(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        ScreenTools screenTools = ScreenTools.instance(getContext());
        totalWidth = screenTools.getScreenWidth() - screenTools.dip2px(80);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
    //为子view  布局位置
    private void layoutChildView() {
        int childrenCount = listData.size();

        int singleWidth = (totalWidth - gap * (3 - 1)) / 3;
        int singleHeight = singleWidth;

        //根据子view数量确定高度
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = singleHeight * rows + gap * (rows - 1);
        setLayoutParams(params);

        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
             final   String url = listData.get(i);
            final int viewPosition = i;
            childrenView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext.getApplicationContext(), ScanPhotoAcitivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("position", viewPosition+"");
                    intent.putStringArrayListExtra("imagelist", mImageList);
                    mContext.startActivity(intent);
                }
            });
            Picasso.with(getContext()).load(url).placeholder(R.mipmap.loading).error(R.mipmap.loading) .into(childrenView);
            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1];
            int top = (singleHeight + gap) * position[0];
            int right = left + singleWidth;
            int bottom = top + singleHeight;

            childrenView.layout(left, top, right, bottom);
        }

    }

    //获取子View 所在的行列信息
    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    /**
     * 布局每一种情况的布局
     */
    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            rows = 1;
            columns = length;
        } else if (length <= 6) {
            rows = 2;
            columns = 3;
            if (length == 4) {
                columns = 2;
            }
        } else {
            rows = 3;
            columns = 3;
        }
    }

    /**
     * 对外绑定数据接口
     */

    public void setImagesData(ArrayList<String> lists) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
     this.mImageList=lists;
        //布局每一种情况
        generateChildrenLayout(lists.size());
        //这里做一个重用view的处理
        if (listData == null) {
            int i = 0;
            while (i < lists.size()) {
                ImageView iv = generateImageView();
                addView(iv, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldViewCount = listData.size();
            int newViewCount = lists.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount - 1, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = 0; i < newViewCount - oldViewCount; i++) {
                    ImageView iv = generateImageView();
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
        listData = lists;
        layoutChildView();
    }

    /**
     * 生成单个子View
     **/
    private ImageView generateImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv.setBackgroundColor(Color.parseColor("#f5f5f5"));
        return iv;
    }


    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

}
