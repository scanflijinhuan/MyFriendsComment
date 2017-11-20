package com.example.huanhuan.homeworkfrends.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huanhuan.homeworkfrends.R;


/**
 * Created by huanhuan22 on 2017/11/16..
 */

public abstract class BaseActivity extends Activity {
    protected  TextView mTitle;
    protected  RelativeLayout  mLeftMenu,mRightMenu;
    protected ImageView mLeftMenuIv,mRightMenuIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initTitle();
        initAllViews();
    }

    protected  void initTitle(){
        mTitle=findViewById(R.id.title_tv);
        mLeftMenu=findViewById(R.id.title_left_rl);
        mRightMenu=findViewById(R.id.title_right_rl);
        mRightMenuIv=findViewById(R.id.title_right_iv);
        mLeftMenuIv=findViewById(R.id.title_left_iv);
        mLeftMenuIv.setOnClickListener(onClickListener);
        mRightMenuIv.setOnClickListener(onClickListener);
        mLeftMenu.setOnClickListener(onClickListener);
        mRightMenu.setOnClickListener(onClickListener);
        mRightMenu.setVisibility(View.GONE);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_iv:
            case R.id.title_left_rl:
                onClickTitleLeftMenu();
                break;
            case R.id.title_right_iv:
            case R.id.title_right_rl:
                onClickTitleRightMenu();


                break;
        }
        }
    };



    public abstract int getContentViewId();

    protected abstract void initAllViews();
    protected abstract void onClickTitleRightMenu();

    protected abstract void onClickTitleLeftMenu();
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
