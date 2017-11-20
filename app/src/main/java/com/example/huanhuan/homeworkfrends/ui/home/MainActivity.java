package com.example.huanhuan.homeworkfrends.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.ui.base.BaseActivity;
import com.example.huanhuan.homeworkfrends.ui.moments.MomentsAcitivity;



/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class MainActivity extends BaseActivity {

    private Context mContext=this;
    private Button mBtn;
    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViews();
        setListener();
    }

    @Override
    protected void onClickTitleRightMenu() {

    }

    @Override
    protected void onClickTitleLeftMenu() {
        this.finish();
    }


    private void setViews() {
        mTitle.setText(getResources().getText(R.string.home_title_name));
    }

    private void setListener() {
        mBtn.setOnClickListener(listener);
    }

    @Override
    protected void initAllViews() {
        mBtn=findViewById(R.id.btn);

    }



    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn:
                    Intent intent=new Intent(mContext, MomentsAcitivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };
}
