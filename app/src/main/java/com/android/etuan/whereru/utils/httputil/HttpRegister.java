package com.android.etuan.whereru.utils.httputil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.etuan.whereru.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class HttpRegister {
    private static Handler mHandler;
    private static Context mContext;
    private final static String Localhost = Constant.LOCALHOST;
    public static void setHandler(Handler handler){
        mHandler = handler;
    }
    public static void setContext(Context context){
        mContext = context;
    }

    /**
     * 注册功能 需要传下列数据
     * params.add("phone",phone);
     * params.add("password",password);
     * params.add("school",school);
     * params.add("name",name);
     * params.add("sex",sex);
     * @param params
     */
    public static void Register(RequestParams params){
        String SingupUrl = "http://"+Localhost+":3000/api/WUsers/";
        AsyncHttpClient SingupClient = new AsyncHttpClient();
        SingupClient.post(SingupUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String state = new String(bytes);
                Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                Message msg = new Message();
                msg.what = 0x234;
                Bundle bundle = new Bundle();
                bundle.putString("data",state);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                String state = new String(bytes);
                Toast.makeText(mContext,"注册失败",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0x123);
            }
        });
    }
}
