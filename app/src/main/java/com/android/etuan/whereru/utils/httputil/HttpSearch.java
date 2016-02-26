package com.android.etuan.whereru.utils.httputil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.etuan.whereru.utils.InterfaceConstant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class HttpSearch {

    private static String LocalHost = InterfaceConstant.LOCALHOST;
    private static String StringURL;

    private static Context mContext;

    private static Handler mHandler;

    public static void setmContext(Context context) {
        mContext = context;
    }

    public static void setmHandler(Handler handler) {
        mHandler = handler;
    }

    public static void search(String KeyWord,int type){
        switch(type){
            case InterfaceConstant.ACTIVITY:
                StringURL = "http://"+LocalHost+":3000/api/Activities/search?keyword="+KeyWord;
                break;
            case InterfaceConstant.RACE:
                StringURL = "http://"+LocalHost+":3000/api/Races/search?keyword="+KeyWord;
                break;
            case InterfaceConstant.TEAM:
                StringURL = "http://"+LocalHost+":3000/api/Teams/search?keyword="+KeyWord;
                break;
            case InterfaceConstant.USER:
                StringURL = "http://"+LocalHost+":3000/api/WUsers/search?keyword="+KeyWord;
                break;
            case InterfaceConstant.COTERIE:
                StringURL = "http://"+LocalHost+":3000/api/Coteries/search?keyword="+KeyWord;
                break;
        }
        AsyncHttpClient getSearchClient = new AsyncHttpClient();
        getSearchClient.get(StringURL, new AsyncHttpResponseHandler() {

            /**
             * 这里onSuccess通过Handler返回从服务器取得的string数据
             * 之后用外部的JSONSearchs类来解析JSON数据
             */
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String state = new String(bytes);
                Bundle bundle = new Bundle();
                bundle.putString("data", state);
                Message msg = new Message();
                msg.what = InterfaceConstant.GET_SUCCESS;
                msg.setData(bundle);
                Toast.makeText(mContext, "获取成功", Toast.LENGTH_SHORT).show();
                System.out.println(new String(bytes));
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext, "获取失败", Toast.LENGTH_SHORT).show();
                System.out.println(new String(bytes));
                Message msg = new Message();
                msg.what = InterfaceConstant.GET_FAIL;
                Bundle bundle = new Bundle();
                bundle.putString("data",new String(bytes));
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        });
    }

}
