package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class User {
    private final String EroMsg = "JSON解析出错 User";
    private String id;
    private String name;
    private String sign;
    private String headImgUrl;

    //对使用者透明 作用是解析JSON数据
    private void GetInfoFormJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
        } catch (JSONException e) {
            System.out.println(EroMsg + "id");
        }
        try {
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            System.out.println(EroMsg + "name");
        }
        try {
            sign = jsonObject.getString("sign");
        } catch (JSONException e) {
            System.out.println(EroMsg + "sign");
        }
        try {
            headImgUrl = jsonObject.getString("headImgUrl");
        } catch (JSONException e) {
            System.out.println(EroMsg + "headImgUrl");
            e.printStackTrace();
        }
    }

    //这个方法对使用者是透明的 作用是解析JSON数据 仅仅在JSONSearchs里面调用
    public ArrayList<User> GetInfo(JSONArray jsonArray) throws JSONException {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            User temp = new User();
            temp.GetInfoFormJson(jsonArray.getJSONObject(i));
            users.add(i, temp);
        }
        return users;
    }

    //以下是使用者所要使用的方法 获取成员变量
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }
}
