package com.example.huanhuan.homeworkfrends.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.example.huanhuan.homeworkfrends.R;

/**
 * Created by huanhuan22 on 2017/11/21.
 */

public class AnimTools {
    public static void setRotation(View view, int angle) {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        view.setRotation(angle);
    }

    public static Animation startRotation(Context mContext, View view) {
        Animation circle_anim=null;
        if (circle_anim == null) {
            circle_anim = AnimationUtils.loadAnimation(mContext, R.anim.anim_round_rotate);
        }
        LinearInterpolator interpolator = new LinearInterpolator();
        circle_anim.setInterpolator(interpolator);
        if (circle_anim != null) {
            view.startAnimation(circle_anim);

        }
        return circle_anim;
    }

    public static void startBottomToTopAnim(Context mContext,View view) {
        view.setVisibility(View.VISIBLE);
        AnimationSet translateAnimation = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.anim_bottom_to_up);
        view.startAnimation(translateAnimation);

    }
}
