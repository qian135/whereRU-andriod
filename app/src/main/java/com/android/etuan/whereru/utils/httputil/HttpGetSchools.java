package com.android.etuan.whereru.utils.httputil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.etuan.whereru.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class HttpGetSchools {
    private static Handler mHandler;
    private static Context mContext;
    private final static String Localhost = Constant.LOCALHOST;
    public static void setHandler(Handler handler){
        mHandler = handler;
    }
    public static void setContext(Context context){
        mContext = context;
    }
    public static void getSchools(){
        String stringURl = "http://"+Localhost+":3000/api/Schools?filter[fields][name]=true";
        AsyncHttpClient getSchoolsClient = new AsyncHttpClient();
        getSchoolsClient.get(stringURl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(mContext, "获取学校信息成功", Toast.LENGTH_SHORT).show();
                String state = new String(bytes);
                Message msg = new Message();
                msg.what = 0x234;
                Bundle bundle = new Bundle();
                bundle.putString("data",state);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"获取学校信息失败",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0x123);
            }
        });
    }
}
