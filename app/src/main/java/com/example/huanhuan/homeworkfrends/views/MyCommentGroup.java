package com.example.huanhuan.homeworkfrends.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.model.CommentBean;
import com.example.huanhuan.homeworkfrends.model.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/18.
 */

public class MyCommentGroup extends LinearLayout {
    private Context mContext;
    private OnCommentItemClickListener onCommentItemClickListener;
    private BaseAdapter adapter;

    //监听器
    public interface OnCommentItemClickListener {
        public void onItemClicked(View view, int position);

    }

    public void setOnCommentItemClickListener(OnCommentItemClickListener onCommentItemClickListener) {
        this.onCommentItemClickListener = onCommentItemClickListener;
    }


    public MyCommentGroup(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyCommentGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


//================================================================================

    private List<CommentBean> mDatas;

    /**
     * 方案二：普通传递数据进来，再与view方式绑定
     */


    public void setList(List<CommentBean> list) {
        this.mDatas = list;
    }

    /**
     * 方案二：数据刷新接口，notify一般是交给adapter去做
     */
    List<View> viewList;

    public void notifyDataSetChanged() {
        viewList = new ArrayList<>();
        removeAllViews();
        if (mDatas == null || mDatas.size() <= 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        for (int i = 0; i < mDatas.size(); i++) {
            View view = getView(i);
            if (view == null) {
                throw new NullPointerException("listview 数据为空");
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            addView(view);
        }
    }


    /**
     * @param position
     * @return
     */
    public View getView(int position) {
        View root = null;
        ViewHolder holder = null;
        if (root == null) {
            holder = new ViewHolder();
            root = LayoutInflater.from(mContext).inflate(R.layout.comment_item_layout, null);
            holder.textView = root.findViewById(R.id.item_comment_tv);
            root.setTag(holder);
        } else {
            holder = (ViewHolder) root.getTag();
        }
        setViewDataAndSpan(holder.textView, position);
        return holder.textView;
    }

    static class ViewHolder {
        public TextView textView;
    }

    public void setViewDataAndSpan(final TextView commentItemTv, final int position) {
        SpannableString commentNameSs = null;
        SpannableString replyCommentNameSs = null;
        SpannableString contentSs = null;
        SpannableStringBuilder multiWord = new SpannableStringBuilder();
        if (mDatas != null && mDatas.size() != 0) {
            CommentBean commentItem = mDatas.get(position);
            final String commentNameStr = "";
            String replyCommentNameStr = "";
            String contentStr="";
            if (commentItem != null) {
                UserBean sender = commentItem.getSender();
                if (sender != null) {
                    replyCommentNameStr = commentItem.getSender().getUsername();
                }
               contentStr = commentItem.getContent();
            }
            if (commentNameStr != null && !commentNameStr.equals("")) {
                commentNameSs = new SpannableString(commentNameStr);
                //设置点击
                commentNameSs.setSpan(new ViewClickSpan(new ViewClickSpan.OnSpanTextClickListener() {
                    @Override
                    public void onSpanClick(View v) {
                        Toast.makeText(mContext, "点击了" + commentNameStr, Toast.LENGTH_SHORT).show();
                    }
                }), 0, commentNameSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //设置颜色
                commentNameSs.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.comment_name_color)), 0, commentNameSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                multiWord.append(commentNameSs+" ");
            }
            //回复别人
            if (replyCommentNameStr != null && !replyCommentNameStr.equals("")) {
                replyCommentNameSs = new SpannableString(" "+replyCommentNameStr);
                final String finalReplyCommentNameStr = replyCommentNameStr;
                replyCommentNameSs.setSpan(new ViewClickSpan(new ViewClickSpan.OnSpanTextClickListener() {
                    @Override
                    public void onSpanClick(View v) {
                        Toast.makeText(mContext, "点击了" + finalReplyCommentNameStr, Toast.LENGTH_SHORT).show();

                    }
                }), 0, replyCommentNameSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //设置颜色
                replyCommentNameSs.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.comment_name_color)), 0, replyCommentNameStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (commentNameSs != null && !commentNameSs.equals("")) {
                    multiWord.append("回复");
                }
                multiWord.append(replyCommentNameSs);
            }
            //回复内容
            if (contentStr != null && !contentStr.equals("")) {
                contentSs = new SpannableString(contentStr);
                contentSs.setSpan(new ViewClickSpan(new ViewClickSpan.OnSpanTextClickListener() {
                    @Override
                    public void onSpanClick(View v) {
                        int[] location = new int[2];
                        commentItemTv.getLocationOnScreen(location);
                        if (onCommentItemClickListener != null) {
                            onCommentItemClickListener.onItemClicked(v, position);
                        }
                    }
                }), 0, contentStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentSs.setSpan(new ForegroundColorSpan(Color.BLACK), 0, contentSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                multiWord.append(" :");
                multiWord.append(contentSs);
            }

        }

        //设置整个View的内容
        commentItemTv.setText(multiWord); //textview设置span后，那边回调的view就是这个view,内容就是text
        commentItemTv.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
    }

    static class ViewClickSpan extends ClickableSpan {
        private Context context;
        private String commentNameTx;
        private String replyNameTx;
        private String contentTx;

        private OnSpanTextClickListener onSpanTextClickListener;

        public interface OnSpanTextClickListener {
            void onSpanClick(View v);
        }

        public ViewClickSpan(OnSpanTextClickListener onSpanTextClickListener) {
            this.onSpanTextClickListener = onSpanTextClickListener;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            onSpanTextClickListener.onSpanClick(widget);
        }


    }


    //============================================================================================
    private List<? extends Object> list;

    /**
     * 自定义数据刷新
     */
    public interface NotifyDataSetChanged {
        public void changed();
    }

    /**
     * 方案一:adapter模式 绑定数据。 可以传递adapter 或者自定义adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public void bindViewData() {
        if (adapter == null) {
            return;
        } else {
            for (int i = 0; i < adapter.getCount(); i++) {
//                View v = adapter.getView(i);
            }

        }

    }

    public NotifyDataSetChanged notifyDataSetChanged = new NotifyDataSetChanged() {
        @Override
        public void changed() {
            removeAllViews();
            bindViewData();
        }
    };

}
