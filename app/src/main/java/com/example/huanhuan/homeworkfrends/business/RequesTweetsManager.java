package com.example.huanhuan.homeworkfrends.business;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.huanhuan.homeworkfrends.model.CommentBean;
import com.example.huanhuan.homeworkfrends.model.TweetBean;
import com.example.huanhuan.homeworkfrends.model.UserBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by huanhuan22 on 2017/11/19.
 */

public class RequesTweetsManager {
    public static final String BASE_URL = "http://thoughtworks-ios.herokuapp.com/";
    public static RequesTweetsManager mInstance = null;
    public static final int SUCCESS=200;
    public static final int FAILED=500;

    private Context mContext;
    private RequesTweetsManager() {
    }

    private RequesTweetsManager(Context context) {
        this.mContext = context;
    }

    public static RequesTweetsManager getmInstance(Context context) {
        if (mInstance == null) {
            synchronized (RequesTweetsManager.class) {
                if (mInstance == null) {
                    mInstance = new RequesTweetsManager(context);
                }
            }
        }
        return mInstance;
    }

    //==============================================

    public interface RequestTweetsService {

        @GET("user/{user}")
        Call<UserBean> getUserInfo(@Path("user") String userName);

        @GET("user/{user}/{type}")
        Call<List<TweetBean>> getUserTweets(@Path("user") String userName,@Path("type") String type);

    }



    //===============================================
    private Retrofit getRetrofitBase() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    public void getUserInfo(final Handler handler) {
        RequestTweetsService  tweetsService = getRetrofitBase().create(RequestTweetsService.class);
        Call<UserBean> call= tweetsService.getUserInfo("jsmith");
        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                Message msg= new Message();
                msg.obj=(UserBean)response.body();
                msg.what=200;
                handler.sendMessage(msg);

            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                call.cancel();
                Message msg= new Message();
                msg.what=500;
                handler.sendMessage(msg);
            }
        });
    }

    public void getUserTweets(final Handler handler){
        RequestTweetsService  tweetsService = getRetrofitBase().create(RequestTweetsService.class);
        Call<List<TweetBean>> call= tweetsService.getUserTweets("jsmith","tweets");
        call.enqueue(new Callback<List<TweetBean>>() {
            @Override
            public void onResponse(Call<List<TweetBean>> call, Response<List<TweetBean>> response) {
                Message msg= new Message();
                msg.obj=(List<TweetBean>)response.body();
                msg.what=200;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<TweetBean>> call, Throwable t) {
                call.cancel();
                Message msg= new Message();
                msg.what=500;
                handler.sendMessage(msg);
            }
        });
    }



}
