package com.example.huanhuan.homeworkfrends.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.model.CommentBean;
import com.example.huanhuan.homeworkfrends.model.ImageBean;
import com.example.huanhuan.homeworkfrends.model.TweetBean;
import com.example.huanhuan.homeworkfrends.views.MyCommentGroup;
import com.example.huanhuan.homeworkfrends.views.PhotoGroups;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<TweetBean> mTweetList;
    private ArrayList<String> imagesList = new ArrayList<>();
    public List<TweetBean> getmTweetList() {
        return mTweetList;
    }
    public void setmTweetList(List<TweetBean> mTweetList) {
        this.mTweetList = mTweetList;
    }

    public ListViewAdapter(Context context) {

        this.context = context;
    }
    public ListViewAdapter(Context context, List<TweetBean> mTweetList) {

        this.context = context;
        this.mTweetList = mTweetList;
    }

    MyCommentGroup.OnCommentItemClickListener commentItemClickListener;

    public void setCommentItemClick(MyCommentGroup.OnCommentItemClickListener listener) {
        this.commentItemClickListener = listener;
    }


    @Override
    public int getCount() {
        return mTweetList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTweetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        TweetBean tweetBean = mTweetList.get(position);
        List<ImageBean> imags=tweetBean.getImages();
        ArrayList<String> imagsUrlList=new ArrayList<>();
        if(imags!=null&&imags.size()>0){
            for (int i=0;i<imags.size();i++){
                imagsUrlList.add(imags.get(i).getUrl());
            }
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_layout, null);
            holder.profileIv = convertView.findViewById(R.id.item_left_iv);
            holder.nameTv = convertView.findViewById(R.id.item_name_tv);
            holder.contentTv = convertView.findViewById(R.id.item_content_tv);
            holder.photoGroups = convertView.findViewById(R.id.photoes_groups);
            holder.commentAndlikeBtn = ((Button) convertView.findViewById(R.id.item_comment_like_btn));
            holder.commentListLl = ((MyCommentGroup) convertView.findViewById(R.id.item_comment_list_ll));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        mCommentViewGroup=holder.commentListLl;

        if (tweetBean != null) {

            if (tweetBean.getSender() != null) {
                if (tweetBean.getSender().getAvatar() != null && !tweetBean.getSender().getAvatar().equals("")) {
                    Picasso.with(context).load(tweetBean.getSender().getAvatar()).placeholder(R.mipmap.profile_fail).error(R.mipmap.profile_fail) .into(holder.profileIv);
                }
            }
            if (tweetBean.getSender() != null) {
                if (tweetBean.getSender().getNick() != null && !tweetBean.getSender().getNick().equals("")) {
                    holder.nameTv.setText(tweetBean.getSender().getNick());
                } else {
                    holder.nameTv.setText(tweetBean.getSender().getUsername());//每条消息的发送者名字
                }
            }
            if (tweetBean.getContent() != null && !tweetBean.getContent().equals("")) {
                holder.contentTv.setText(tweetBean.getContent());//发送的内容
            }
        }
        //设置图片列表数据，
        holder.photoGroups.setImagesData(imagsUrlList);

        //方案一：评论列表listview:获取评论所有数据，并对每个position的Item进行绑定
//        adapter = new CommentAdapter(name, toName, content, context);
//        holder.commentLv.setAdapter(adapter);
        //方案二：评论列表LinearLayout，自定义为适配器模式

        List<CommentBean> commentList=tweetBean.getComments();
        if(commentList!=null&&commentList.size()>0){
            holder.commentListLl.setVisibility(View.VISIBLE);
            holder.commentListLl.setList(commentList);
            holder.commentListLl.notifyDataSetChanged();
            holder.commentListLl.setOnCommentItemClickListener(commentItemClickListener);
        }else{
            holder.commentListLl.setVisibility(View.GONE);
        }
        //按钮监听
        holder.commentAndlikeBtn.setOnClickListener(new PopClickListener(holder.commentAndlikeBtn, holder.commentListLl));
        return convertView;
    }

    static class ViewHolder {
        public ImageView profileIv;
        public TextView nameTv;
        public TextView contentTv;
        public PhotoGroups photoGroups;
        public Button commentAndlikeBtn;
        public MyCommentGroup commentListLl;

    }

    public class PopClickListener implements View.OnClickListener {
        View v;
        ViewGroup viewGroup;

        //v:所点击的按钮， viewGroup：评论列表容器
        public PopClickListener(View v, ViewGroup viewGroup) {
            this.v = v;
            this.viewGroup = viewGroup;
        }

        public void onClick(View v) {

            showPopWindowListener.onShowPop(this.v, this.viewGroup);
        }
    }

    ;

    ShowPopWindowListener showPopWindowListener;

    public interface ShowPopWindowListener {
        void onShowPop(View v, ViewGroup viewGroup);

    }

    public void setShowPopWindowListener(ShowPopWindowListener showPopWindowListener) {
        this.showPopWindowListener = showPopWindowListener;
    }

   private  ViewGroup  mCommentViewGroup;
    public ViewGroup  getCommentGroup(){
        return mCommentViewGroup;
    }
    public void  setCommentGroup(ViewGroup  mCommentViewGroup){
       this. mCommentViewGroup= mCommentViewGroup;
    }
}
