package com.example.huanhuan.homeworkfrends.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.example.huanhuan.homeworkfrends.views.MyDialog;

/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class Tools {
    static MyDialog inputDialog;

    public static MyDialog getInputDialog(Activity context) {
        if (inputDialog == null) {
            inputDialog = MyDialog.getDialog(context);
        }
        return inputDialog;
    }

    public static void showInputDialog(Activity context) {
        inputDialog = MyDialog.getDialog(context);
        inputDialog.startShowKeyBord();
        inputDialog.show();
    }

    public static void closeInputDialog(Activity context) {
        inputDialog = MyDialog.getDialog(context);
        if (inputDialog != null || inputDialog.isShowing()) {
            inputDialog.dismiss();
        }
    }

    public static String getInputContent() {
        if (inputDialog != null || inputDialog.isShowing()) {
            return inputDialog.getInputContentFromEd();
        }
        return "";
    }

    public static int[] getInputViewLoacation() {
        final int[] locations = new int[2];
        if (inputDialog != null || inputDialog.isShowing()) {
            final EditText inputView = (EditText) inputDialog.getInputView();
            inputView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputView.getLocationInWindow(locations);
                }
            }, 500);
        }
        return locations;
    }

    public static View getInputView() {
        View inputView = null;
        if (inputDialog != null || inputDialog.isShowing()) {
            inputView = inputDialog.getInputView();
        }
        return inputView;
    }

    public static int getKeybordHight(final View v) {
        final int[] softInputHeight = new int[1];
        v.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        v.getWindowVisibleDisplayFrame(r);
                        int screenHeight = v.getRootView().getHeight();
                        //软键盘高度  softInputHeight
                         softInputHeight[0] = screenHeight - (r.bottom);
                    }
                });
        return softInputHeight[0];
    }
}
