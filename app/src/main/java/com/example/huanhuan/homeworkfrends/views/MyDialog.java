package com.example.huanhuan.homeworkfrends.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.huanhuan.homeworkfrends.R;


/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class MyDialog extends Dialog {
    private Window dialogWindow;
    // private Context context;
    private static Activity mContext;
    private String mTitle;
    private WindowManager.LayoutParams dialogLayoutParams;
    /****/
    private EditText inputEd;

    // ------------------------Dialog-----------------------------------------------
    private MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private MyDialog(Context context, int theme) {
        super(context, theme);
        initDialog();
    }

    public MyDialog(Context context) {
        this(context, R.style.myInputDialog);

    }

    public static MyDialog dialog;

    public static MyDialog getDialog(Activity context) {
        if (dialog == null || mContext != context) {
            dialog = new MyDialog(context);
        }
        mContext = context;
        return dialog;
    }

    private void initDialog() {
        dialogWindow = getWindow();
        dialogLayoutParams = dialogWindow.getAttributes();
        setCanceledOnTouchOutside(true);
        setDialogGravity(DialogGravity.CENTERBOTTOM);
        initLayout();
        setListener();


        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(dialogLayoutParams);
    }

    private void setListener() {
        inputEd.setOnKeyListener(onKeyListener);
    }

    private void initLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.input_edit_dialog_layout, null);
        inputEd = (EditText) view.findViewById(R.id.dialog_input_ed);
        inputEd.setLongClickable(false);
        setContentView(view);
    }

    public void startShowKeyBord() {
        if (dialog != null || dialog.isShowing()) {
            dialog.dismiss();
        }
        inputEd.setText("");
        mHandler.sendEmptyMessageDelayed(0, 50);

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showKeyboard(inputEd);
                    break;
            }
        }
    };

    public View  getInputView(){
        if(inputEd!=null){
            return inputEd;
        }
        return null;
    }

    /**
     */
    public void setDialogLayout(View layoutView) {
        dialogWindow.setContentView(layoutView);
    }

    /**
     */
    public void setDialogLayout(int layoutId) {
        dialogWindow.setContentView(layoutId);
    }

    /**
     */
    public void setLayoutHeightWidth(int height, int width) {
        dialogLayoutParams.height = height;
        dialogLayoutParams.width = width;
        dialogWindow.setAttributes(dialogLayoutParams);
    }

    /**
     */
    public void setLayoutXY(int x, int y) {
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        dialogLayoutParams.x = x;
        dialogLayoutParams.y = y;
        dialogWindow.setAttributes(dialogLayoutParams);
    }

    /***
     */
    public void setLayoutGravity(View layoutView, DialogGravity dialogGravity) {
        setDialogLayout(layoutView);
        setDialogGravity(dialogGravity);
    }

    /***
     */
    public void setLayout(View layoutView, DialogGravity dialogGravity, int height, int width) {
        setLayoutGravity(layoutView, dialogGravity);
        setLayoutHeightWidth(height, width);
    }

    /**
     */
    public static enum DialogGravity {
        LEFTTOP, RIGHTTOP, CENTERTOP, CENTER, LEFTBOTTOM, RIGHTBOTTOM, CENTERBOTTOM
    }

    /**
     */
    public void setDialogGravity(DialogGravity dialogGravity) {

        switch (dialogGravity) {
            case LEFTTOP:
                dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case RIGHTTOP:
                dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case CENTERTOP:
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                break;
            case CENTER:
                dialogWindow.setGravity(Gravity.CENTER);
                break;
            case LEFTBOTTOM:
                dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case RIGHTBOTTOM:
                dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
            case CENTERBOTTOM:
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                break;
            default:
                break;
        }
    }

    public void showKeyboard(EditText ed) {
        if (ed != null) {
            ed.setFocusable(true);
            ed.setFocusableInTouchMode(true);
            ed.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) ed.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(ed, 0);
        }
    }

    public String  getInputContentFromEd(){
       String content="";
        if (inputEd != null) {
            content=inputEd.getText().toString();
       }
       return content;
    }


    View.OnKeyListener onKeyListener=new  View.OnKeyListener() {


        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {


            onKeyOkListener.onClickOkBtn(v,keyCode,event);

            return true;
        }
    };
   public interface  OnKeyOkListener{
        void  onClickOkBtn(View v, int keyCode, KeyEvent event);
    }
    OnKeyOkListener onKeyOkListener;
    public void setOnKeyOkListener(OnKeyOkListener onKeyOkListener){
        this.onKeyOkListener=onKeyOkListener;
    }
}
