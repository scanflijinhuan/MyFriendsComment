package com.example.huanhuan.homeworkfrends.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanhuan.homeworkfrends.R;

import java.util.ArrayList;

/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class CommCustomAdapter extends BaseAdapter {
    private ArrayList<String> commentNameList;
    private ArrayList<String> replyCommentNameList;
    private ArrayList<String> commenContentList;
    private Context context;


    public CommCustomAdapter(ArrayList<String> commentNameList, ArrayList<String> replyCommentNameList, ArrayList<String> commenContentList, Context context) {
        this.commentNameList = commentNameList;
        this.replyCommentNameList = replyCommentNameList;
        this.commenContentList = commenContentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (commentNameList != null && commentNameList.size() != 0) {
            return commentNameList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, null);
            holder.commentItemTv = convertView.findViewById(R.id.item_comment_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        setViewDataAndSpan(holder,position);
        setViewDataAndSpan2(holder, position);

        return convertView;
    }

    static class ViewHolder {
        public TextView commentItemTv;
    }


    public void setViewDataAndSpan2(final ViewHolder holder, int position) {
        SpannableString commentNameSs = null;
        SpannableString replyCommentNameSs = null;
        SpannableString contentSs = null;
        SpannableStringBuilder multiWord = new SpannableStringBuilder();
        if (commentNameList != null && commentNameList.size() != 0) {
            final String commentNameStr = commentNameList.get(position).toString();
            final String replyCommentNameStr = replyCommentNameList.get(position).toString();
            final String contentStr = commenContentList.get(position).toString();


            commentNameSs = new SpannableString(commentNameStr);
            //设置点击
            commentNameSs.setSpan(new ViewClickSpan(new ViewClickSpan.OnSpanTextClickListener() {
                @Override
                public void onSpanClick(View v) {
                    Toast.makeText(context, "点击了" + commentNameStr, Toast.LENGTH_SHORT).show();
                }
            }), 0, commentNameSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置颜色
            commentNameSs.setSpan(new ForegroundColorSpan(Color.BLUE), 0, commentNameSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            multiWord.append(commentNameSs);
            //回复别人
            if (replyCommentNameList != null && replyCommentNameList.size() != 0) {
                replyCommentNameSs = new SpannableString(replyCommentNameStr);
                replyCommentNameSs.setSpan(new ViewClickSpan(new ViewClickSpan.OnSpanTextClickListener() {
                    @Override
                    public void onSpanClick(View v) {
                        Toast.makeText(context, "点击了" + replyCommentNameStr, Toast.LENGTH_SHORT).show();
                    }
                }), 0, replyCommentNameSs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //设置颜色
                replyCommentNameSs.setSpan(new ForegroundColorSpan(Color.GREEN), 0, replyCommentNameStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                multiWord.append("回复");
                multiWord.append(replyCommentNameSs);
            }
            //回复内容
            if (commenContentList != null && commenContentList.size() != 0) {
                contentSs = new SpannableString(contentStr);
                contentSs.setSpan(new ViewClickSpan(new ViewClickSpan.OnSpanTextClickListener() {
                    @Override
                    public void onSpanClick(View v) {
                        int[] location = new  int[2] ;
                       holder.commentItemTv.getLocationOnScreen(location);
                        Toast.makeText(context, "当前坐标 x=" + location[0]+" y="+location[1], Toast.LENGTH_SHORT).show();
                    }
                }), 0, contentStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                multiWord.append(":");
                multiWord.append(contentSs);
            }

        }

        //设置整个View的内容
        holder.commentItemTv.setText(multiWord); //textview设置span后，那边回调的view就是这个view,内容就是text
        holder.commentItemTv.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
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
            this.onSpanTextClickListener=onSpanTextClickListener;
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
}

///**
// * 实现Span点击和设置
// */
//static class FeedTextViewURLSpan extends ClickableSpan {
//    private String clickString;
//    private Context context;
//    private String name;
//    private String toName;
//    private String content;
//
//    public FeedTextViewURLSpan(String clickString, Context context, String name, String toName, String content) {
//        this.clickString = clickString;
//        this.context = context;
//        this.name = name;
//        this.toName = toName;
//        this.content = content;
//    }
//
//    @Override
//    public void updateDrawState(TextPaint ds) {
//        ds.setUnderlineText(false);
////            if (clickString.equals("toName")) {//如果可点击的部分之一是回复者的名字，设置蓝色
////                ds.setColor(context.getResources().getColor(R.color.blue));
////            } else if (clickString.equals("name")) {
////                ds.setColor(context.getResources().getColor(R.color.blue));
////            }
//    }
//
//    @Override
//    public void onClick(View widget) {
//        String commentName = ((TextView) widget).getText().toString();
//        Toast.makeText(context, "点击了" + commentName, Toast.LENGTH_SHORT).show();
////            if (clickString.equals("toName")) {
//////                Toast.makeText(context, "点击了" + toName, Toast.LENGTH_SHORT).show();
////            } else if (clickString.equals("name")) {
//////                Toast.makeText(context, "点击了" + name, Toast.LENGTH_SHORT).show();
////            } else if (clickString.equals("content")) {
//////                Toast.makeText(context, "再回复，启动弹编辑框" + content, Toast.LENGTH_SHORT).show();
////            }
//    }
//}
//
//
//    public void setViewDataAndSpan(CommentAdapter.ViewHolder holder, int position) {
//        // 读取评论内容，并设置
//        if (commentNameList != null && commentNameList.size() != 0) {
//            StringBuilder actionText = new StringBuilder();
//            actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='red'>"
//                    + commentNameList.get(position).trim() + "</font></a>");
//            if (replyCommentNameList.get(position) != null && replyCommentNameList.get(position).length() > 0) {
//                actionText.append("回复");
//                actionText.append("<font color='red'><a style=\"text-decoration:none;\" href='toName'>"
//                        + replyCommentNameList.get(position).trim() + "" + "</a></font>");
//            }
//            //content
//            actionText.append("<font color='#484848'><a style=\"text-decoration:none;\" href='content'>"
//                    + ":" + commenContentList.get(position) + "" + "</a></font>");
//            holder.commentItemTv.setText(Html.fromHtml(actionText.toString().trim()));//显示字符串str对应的html格式的文本
//            holder.commentItemTv.setMovementMethod(LinkMovementMethod.getInstance());//开启超链接，可点击
//            holder.commentItemTv.setHighlightColor(Color.TRANSPARENT);
//
//            CharSequence text = holder.commentItemTv.getText().toString().trim();
//            int ends = text.length();
//            Spannable spannable = (Spannable) holder.commentItemTv.getText(); //整体作为一个span
//            URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);//url spans
//            SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
////            stylesBuilder.clearSpans();
//
//            for (URLSpan url : urlspan) {
//                FeedTextViewURLSpan myURLSpan = new FeedTextViewURLSpan(url.getURL(),
//                        context, commentNameList.get(position), replyCommentNameList.get(position), commenContentList.get(position));
//                stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
//                        spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//        }
//    }



