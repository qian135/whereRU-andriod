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
public class HttpCheckStudentId {

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
     * 验证学号用 不知道app里面需要在哪个部分使用
     * @param StudentId
     * @param password
     */
    public static void CheckStudentId(String StudentId,String password){
        String StringUrl = "http://"+Localhost+":3000/api/WUsers/confirmSchool";
        RequestParams params  = new RequestParams();
        params.add("studentId",StudentId);
        params.add("password",password);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(StringUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(mContext, "验证成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(mContext,"验证失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
