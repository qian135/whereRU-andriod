package com.android.etuan.whereru.utils.searchclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class User {
    private static String id;
    private static String name;
    private static String sign;
    private static String headImgUrl;
    private static ArrayList<User> users;

    private static void GetInfoFormJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            name = jsonObject.getString("name");
            sign = jsonObject.getString("sign");
            headImgUrl = jsonObject.getString("headImgUrl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void GetInfo(JSONArray jsonArray) throws JSONException {
        users = new ArrayList<>();
        User temp = new User();
        for (int i = 0; i < jsonArray.length(); i++) {
            temp.GetInfoFormJson(jsonArray.getJSONObject(i));
            users.add(temp);
        }
    }

    public static String getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getSign() {
        return sign;
    }

    public static String getHeadImgUrl() {
        return headImgUrl;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
