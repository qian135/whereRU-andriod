package com.android.etuan.whereru.utils.httputil;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.android.etuan.whereru.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/1/31.
 */

public class HttpLogin {
    private static Handler mHandler;
    private static Context mContext;
    private final static String Localhost = Constant.LOCALHOST;
    public static void setHandler(Handler handler){
        mHandler = handler;
    }
    public static void setContext(Context context){
        mContext = context;
    }

    public static void Login(RequestParams params){
        String loginUrl = "http://"+Localhost+":3000/api/WUsers/login";
        AsyncHttpClient loginClient = new AsyncHttpClient();
        loginClient.post(loginUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String state = new String(bytes);
                Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0x234);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"登录失败",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0x123);
            }
        });
    }
}
