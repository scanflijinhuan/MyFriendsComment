package com.example.huanhuan.homeworkfrends.views;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by huanhuan22 on 2017/11/20.
 */

public class ContentExpandTextView extends LinearLayout {
    private TextView mTextView;
    private TextView mExpandTipView;
    private boolean isOpen = false;
    private int toExpandLines = 3;
    private int lineCounts;
    public ContentExpandTextView(Context context) {
        this(context, null);
    }

    public ContentExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(mTextView==null || mExpandTipView==null){
            mTextView = (TextView)getChildAt(0);
            mExpandTipView=(TextView)  getChildAt(1);
        }

    }
    private void initView() {

        lineCounts = mTextView.getLineCount();
        if(lineCounts <= toExpandLines){
            mExpandTipView.setVisibility(GONE);
        }else{
            mExpandTipView.setVisibility(VISIBLE);
            mExpandTipView.setText("展开");
        }
        if(isOpen && mTextView.getHeight() != lineCounts * mTextView.getLineHeight()){
            mTextView.setHeight(mTextView.getLineHeight() * mTextView.getLineCount());
            mExpandTipView.setText("收起");
        }else if(!isOpen && mTextView.getHeight() != toExpandLines * mTextView.getLineHeight()){
            mTextView.setHeight(mTextView.getLineHeight() * toExpandLines);
            mExpandTipView.setText("全文");
        }
        if(lineCounts <= toExpandLines){
            mExpandTipView.setText("全文");
            mExpandTipView.setVisibility(GONE);
            mTextView.setHeight(mTextView.getLineHeight() * mTextView.getLineCount());
        }
        mExpandTipView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    mTextView.setHeight(mTextView.getLineHeight() * toExpandLines);
                    mExpandTipView.setText("收起");
                    isOpen = false;

                }else{
                    mTextView.setHeight(mTextView.getLineHeight() * mTextView.getLineCount());
                    mExpandTipView.setText("全文");
                    isOpen = true;

                }
            }
        });
    }
}
