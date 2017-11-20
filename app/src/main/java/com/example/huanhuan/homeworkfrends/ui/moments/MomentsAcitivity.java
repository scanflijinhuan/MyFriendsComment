package com.example.huanhuan.homeworkfrends.ui.moments;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicDefaultHeader;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrUIHandler;
import com.chanven.lib.cptr.indicator.PtrIndicator;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.example.huanhuan.homeworkfrends.R;
import com.example.huanhuan.homeworkfrends.adapter.ListViewAdapter;
import com.example.huanhuan.homeworkfrends.business.RequesTweetsManager;
import com.example.huanhuan.homeworkfrends.model.TweetBean;
import com.example.huanhuan.homeworkfrends.model.UserBean;
import com.example.huanhuan.homeworkfrends.ui.base.BaseActivity;
import com.example.huanhuan.homeworkfrends.utils.AnimTools;
import com.example.huanhuan.homeworkfrends.utils.ScreenTools;
import com.example.huanhuan.homeworkfrends.utils.Tools;
import com.example.huanhuan.homeworkfrends.views.MyCommentGroup;
import com.example.huanhuan.homeworkfrends.views.MyDialog;
import com.example.huanhuan.homeworkfrends.views.PhotoGroups;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanhuan22 on 2017/11/16.
 */

public class MomentsAcitivity extends BaseActivity {
    private Context mContext = this;
    private TextView mTitle;
    private View headerView;
    private ImageView profileImg;
    private ImageView avatarImg;
    private TextView userNameTv;
    private TextView loadingTv;
    private RelativeLayout commentRl;
    private PtrClassicFrameLayout mMomentsPtr;
    private ListView mMomentsLv;
    private ListViewAdapter adapter;
    private LinearLayout refshHeaderTextLl;
    private TextView refshHeaderTitleTv;
    private ProgressBar refshHeaderPb;
    private ImageView refeshIcon;
    private PhotoGroups photosGroup;
    private MyCommentGroup commentsGroup;
    private List<TweetBean> mTweetList = new ArrayList<>();
    private List<TweetBean> newAddTweetList = new ArrayList<>();
    private UserBean userBean;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;
    private PopupWindow mMorePopupWindow;
    private PtrFrameLayout mFrame;
    /**
     * 评论，点赞 点击事件的监听回调
     */
    private ViewGroup mCommentGroupLl;
    private Animation circle_anim;
    private boolean isLoadMore = false;
    private int topPositon;

    @Override
    public int getContentViewId() {
        return R.layout.activity_moments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setListener();
        initGetData();
    }

    @Override
    protected void initAllViews() {
        mTitle = findViewById(R.id.title_tv);
        loadingTv = findViewById(R.id.loading_tx);
        commentRl = findViewById(R.id.comment_rl);
        commentRl.setVisibility(View.GONE);
        mRightMenu.setVisibility(View.VISIBLE);
        mMomentsPtr = findViewById(R.id.moments_listview_ptr);
        mMomentsPtr.setLoadMoreEnable(false);
        mMomentsLv = findViewById(R.id.moments_lv);
        headerView = LayoutInflater.from(mContext).inflate(R.layout.listview_header, null);
        profileImg = headerView.findViewById(R.id.header_bg_iv);
        avatarImg = headerView.findViewById(R.id.header_icon);
        userNameTv = headerView.findViewById(R.id.header_username_tv);
        refshHeaderTextLl = ((LinearLayout) findViewById(R.id.ptr_classic_header_rotate_view_header_text));
        refshHeaderTitleTv = ((TextView) findViewById(R.id.ptr_classic_header_rotate_view_header_title));
        refshHeaderPb = ((ProgressBar) findViewById(R.id.ptr_classic_header_rotate_view_progressbar));
        refeshIcon = ((ImageView) findViewById(R.id.moments_refresh_iv));
        photosGroup = ((PhotoGroups) findViewById(R.id.photoes_groups));
        refshHeaderTextLl.setVisibility(View.GONE);
        refshHeaderTitleTv.setVisibility(View.GONE);
        refshHeaderPb.setVisibility(View.GONE);
        refeshIcon.setVisibility(View.GONE);
        mTitle.setText(getResources().getText(R.string.friends_title_name));
    }


    private void bindViews() {
        mMomentsLv.addHeaderView(headerView);
        PtrClassicDefaultHeader ptrHeader = new PtrClassicDefaultHeader(this);
        ptrHeader.setBackgroundColor(getResources().getColor(R.color.dkgray));
        mMomentsPtr.setHeaderView(ptrHeader);
        mMomentsPtr.setLoadMoreEnable(true);
        ptrHeader.setVisibility(View.GONE);

    }

    @Override
    protected void onClickTitleRightMenu() {
        Toast.makeText(getApplicationContext(), "功能入口暂未开放", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onClickTitleLeftMenu() {
        this.finish();
    }


    private void setListener() {
        mMomentsPtr.setPtrHandler(ptrDefaultHandler);
        mMomentsPtr.setOnLoadMoreListener(loadMoreListener);
        mMomentsPtr.addPtrUIHandler(mPtrUIHandler);
        Tools.getInputDialog(MomentsAcitivity.this).setOnKeyOkListener(onKeyOkListener);
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHight = rect.bottom - rect.top;
                int hight = decorView.getHeight();
                int keyboardHeight = hight - displayHight;


            }
        });

    }

    /**
     * 第一次拉取数据
     */
    private void initGetData() {
        RequesTweetsManager requesTweetsManager = RequesTweetsManager.getmInstance(mContext.getApplicationContext());
        requesTweetsManager.getUserTweets(mPullHandler);
        requesTweetsManager.getUserInfo(userInfoHandler);
    }
    /**下拉数据*/
    public void addNewDataToHeader(List<TweetBean> newAddTweetList) {
        for (int i = 0; i < newAddTweetList.size(); i++) {
            if (newAddTweetList.get(i).getError() != null) {
                newAddTweetList.remove(newAddTweetList.get(i));
            }
        }
        mTweetList.addAll(0, newAddTweetList);
    }
    /**上拉数据*/
    public void addNewDataToFooter(List<TweetBean> moreTweets) {
        for (int i = 0; i < moreTweets.size(); i++) {
            if (moreTweets.get(i).getError() != null) {
                moreTweets.remove(moreTweets.get(i));
            }
        }
        mTweetList.addAll(moreTweets);
    }
    /**更新数据*/
    public void updateData() {
        if (adapter == null) {
            adapter = new ListViewAdapter(this, mTweetList);
            mMomentsLv.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
        adapter.setShowPopWindowListener(new ListViewAdapter.ShowPopWindowListener() {
            @Override
            public void onShowPop(View v, ViewGroup viewGroup) {
                mCommentGroupLl = viewGroup;
                showMore(v);
            }
        });
        adapter.setCommentItemClick(new MyCommentGroup.OnCommentItemClickListener() {
            @Override
            public void onItemClicked(final View view, int position) {
                Tools.showInputDialog(MomentsAcitivity.this);
                final int[] location = new int[2];
                view.getLocationOnScreen(location);
                // int keybordHight=Tools.getKeybordHight(Tools.getInputView());
                int keybordHight = 827;
                Tools.getInputView().measure(0, 0);
                int inputHight = Tools.getInputView().getMeasuredHeight();
                int editViewY = ScreenTools.instance(mContext).getScreenHeight() - (keybordHight + inputHight);
                int distanceY = editViewY - (location[1] + view.getMeasuredHeight());
                mMomentsLv.smoothScrollByOffset(-distanceY);
            }
        });

    }

    /**
     * 打开评论，点赞
     */
    private void showMore(View moreBtnView) {

        if (mMorePopupWindow == null) {

            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = li.inflate(R.layout.like_and_comment_layout, null, false);

            mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMorePopupWindow.setOutsideTouchable(true);
            mMorePopupWindow.setTouchable(true);

            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getMeasuredHeight();

            View parent = mMorePopupWindow.getContentView();

            Button like = (Button) parent.findViewById(R.id.like_btn);
            Button comment = (Button) parent.findViewById(R.id.comment_btn);

            // 点赞的监听器
            comment.setOnClickListener(popWindowClickListener);
            like.setOnClickListener(popWindowClickListener);
        }

        if (mMorePopupWindow.isShowing()) {
            mMorePopupWindow.dismiss();
        } else {
            int heightMoreBtnView = moreBtnView.getHeight();

            mMorePopupWindow.showAsDropDown(moreBtnView, -mShowMorePopupWindowWidth,
                    -(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
        }
    }

    /**
     * 关闭
     */
    public void closePopWindow() {
        mMorePopupWindow.dismiss();

    }

    /**
     * 默认下拉刷新监听
     **/

    boolean isPull = false;
    PtrDefaultHandler ptrDefaultHandler = new PtrDefaultHandler() {

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            isPull = true;
            mFrame = frame;
            refeshIcon.setVisibility(View.VISIBLE);
            RequesTweetsManager.getmInstance(mContext.getApplicationContext()).getUserTweets(mPullHandler);
        }

    };
    /***
     * 上拉加载监听
     */
    OnLoadMoreListener loadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            isLoadMore = true;
            RequesTweetsManager.getmInstance(mContext.getApplicationContext()).getUserTweets(mLoadMoreHandler);


        }
    };
    /**
     * 下拉刷新监听
     **/
    PtrUIHandler mPtrUIHandler = new PtrUIHandler() {

        @Override
        public void onUIReset(PtrFrameLayout frame) {

        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {
            refeshIcon.setVisibility(View.VISIBLE);
        }

        @Override
        public void onUIRefreshBegin(final PtrFrameLayout frame) {
        }

        @Override
        public void onUIRefreshComplete(PtrFrameLayout frame) {
            adapter.notifyDataSetChanged();
           AnimTools. startBottomToTopAnim(mContext,refeshIcon);
        }

        @Override
        public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
            topPositon = ptrIndicator.getCurrentPosY();
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) refeshIcon.getLayoutParams();
            if (topPositon < 20) {
                refeshIcon.setAlpha(0.1f);
            } else {
                refeshIcon.setAlpha(1.0f);
                refeshIcon.setVisibility(View.VISIBLE);
            }
            if (topPositon > 200) {
                lp.topMargin = 200;
                refeshIcon.setVisibility(View.VISIBLE);
            } else {
                lp.topMargin = topPositon;
            }
            AnimTools.setRotation(refeshIcon, -topPositon); //逆时针旋转

            if (frame.isRefreshing()) {
                lp.topMargin = 200;
                refeshIcon.setAlpha(1.0f);
                circle_anim=  AnimTools.startRotation(mContext,refeshIcon);
                refeshIcon.setVisibility(View.VISIBLE);
            }
            refeshIcon.setLayoutParams(lp);
            if (isUnderTouch) {
                refeshIcon.clearAnimation();
                if (circle_anim != null) {
                    circle_anim.cancel();
                }
            }
        }
    };
    /**
     * 点赞，评论对话框监听
     * */
    View.OnClickListener popWindowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.like_btn:
                    Toast.makeText(getApplicationContext(), "点赞功能入口暂时未开放", Toast.LENGTH_SHORT).show();
                    closePopWindow();
                    break;
                case R.id.comment_btn:
                    Tools.showInputDialog(MomentsAcitivity.this);
                    closePopWindow();
                    break;
            }
        }
    };


    //===============由于Demo的缘故，图方便，暂不考虑避免泄露的写法================================
    Handler mPullHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RequesTweetsManager.SUCCESS:
                    if (msg.obj != null) {
                        commentRl.setVisibility(View.VISIBLE);
                        loadingTv.setVisibility(View.GONE);
                        newAddTweetList = (List<TweetBean>) msg.obj;
                        addNewDataToHeader(newAddTweetList);
                        updateData();
                        if (isPull) {
                            mFrame.refreshComplete();
                            isPull = false;
                        }
                    }
                    break;
                case RequesTweetsManager.FAILED:
                    break;
                default:
                    break;
            }
        }
    };
    //========由于Demo的缘故，图方便，暂不考虑避免泄露的写法===========
    Handler mLoadMoreHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RequesTweetsManager.SUCCESS:
                    if (msg.obj != null) {
                        commentRl.setVisibility(View.VISIBLE);
                        loadingTv.setVisibility(View.GONE);
                        newAddTweetList = (List<TweetBean>) msg.obj;
                        addNewDataToFooter(newAddTweetList);
                        updateData();
                        if (isLoadMore) {
                            mMomentsPtr.loadMoreComplete(true);
                            isLoadMore = false;
                        }
                    }
                    break;
                case RequesTweetsManager.FAILED:
                    break;
                default:
                    break;
            }
        }
    };

    //========由于Demo的缘故，图方便，暂不考虑避免泄露的写法===========
    private Handler userInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RequesTweetsManager.SUCCESS:
                    if (msg.obj != null) {
                        commentRl.setVisibility(View.VISIBLE);
                        loadingTv.setVisibility(View.GONE);
                        userBean = (UserBean) msg.obj;
                        setProfileInfo(userBean);
                    }
                    break;
                case RequesTweetsManager.FAILED:
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 用户信息
     */
    private void setProfileInfo(UserBean userBean) {
        String userProfileImagUrl = userBean.getProfileImage();
        String userAvatarUrl = userBean.getAvatar();
        String userName = userBean.getUsername();
        String userNick = userBean.getNick();
        if (userName != null && !userName.equals("")) {
            userNameTv.setText(userName.trim());
        }
        if (userAvatarUrl != null && !userAvatarUrl.equals("")) {
            Picasso.with(mContext).load(userAvatarUrl).placeholder(R.mipmap.profile_fail).error(R.mipmap.profile_fail).into(avatarImg);
        }
        if (userProfileImagUrl != null && !userProfileImagUrl.equals("")) {
            Picasso.with(mContext).load(userProfileImagUrl).placeholder(R.mipmap.header).error(R.mipmap.header).into(profileImg);
        }
    }

    //=============================================================
    MyDialog.OnKeyOkListener onKeyOkListener = new MyDialog.OnKeyOkListener() {

        @Override
        public void onClickOkBtn(View v, int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                String commentStr = Tools.getInputContent();
                if (commentStr != null && !commentStr.equals("")) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 0, 10);
                    TextView view = new TextView(mContext);
                    view.setText(commentStr);
                     adapter.getCommentGroup().addView(view);
                    Tools.closeInputDialog(MomentsAcitivity.this);
                }
            }
        }
    };

}
