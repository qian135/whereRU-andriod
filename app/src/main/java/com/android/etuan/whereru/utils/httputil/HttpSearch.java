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
 * Created by TonyJiang on 2016/1/30.
 */
public class HttpSearch {
    private static final int ACTIVITY = 1;
    private static final int RACE = 2;
    private static final int TEAM = 3;
    private static final int USER = 4;
    private static final int COTERIE = 5;

    private static String LocalHost = Constant.LOCALHOST;
    private static String StringURL;
    private static Context mContext;
    private static Handler mHandler;
    public static void setContext(Context context){
        mContext = context;
    }
    public static void setHandler(Handler handler){
        mHandler = handler;
    }
    public static void getSearch(String KeyWord,int type){
        switch(type){
            case ACTIVITY:
                StringURL = "http://"+LocalHost+":3000/api/Activities/search?keyword="+KeyWord;
                break;
            case RACE:
                StringURL = "http://"+LocalHost+":3000/api/Races/search?keyword="+KeyWord;
                break;
            case TEAM:
                StringURL = "http://"+LocalHost+":3000/api/Teams/search?keyword="+KeyWord;
                break;
            case USER:
                StringURL = "http://"+LocalHost+":3000/api/WUsers/search?keyword="+KeyWord;
                break;
            case COTERIE:
                StringURL = "http://"+LocalHost+":3000/api/Coteries/search?keyword="+KeyWord;
                break;
        }
        AsyncHttpClient getSearchClient = new AsyncHttpClient();
        getSearchClient.get(StringURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String state = new String(bytes);
                Bundle bundle = new Bundle();
                bundle.putString("data",state);
                Message msg = new Message();
                msg.what = 0x234;
                msg.setData(bundle);
                Toast.makeText(mContext,"获取成功",Toast.LENGTH_SHORT).show();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"获取失败",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0x123);
            }
        });
    }
}
