package com.android.etuan.whereru.utils.personclass;

/**
 * Created by zhang on 2/1/2016.
 */

import org.json.JSONObject;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class Person {
    private static String _id;//保存ACCESSTOKEN验证信息 重点就这个有用
    private static String created;
    private static String userId;
    private static String name;
    private static String school;
    private static String phone;
    private static String sign;
    private static int ttl;

    public static void GetInfoFromJson(JSONObject jsonObject) {
        try {
            _id = jsonObject.getString("id");
            created = jsonObject.getString("created");
            userId = jsonObject.getString("userId");
            JSONObject jsonUser = jsonObject.getJSONObject("user");
            name = jsonUser.getString("name");
            school = jsonUser.getString("school");
            phone = jsonUser.getString("phone");
            sign = jsonUser.getString("sign");
            ttl = jsonUser.getInt("ttl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int geTtl() {
        return ttl;
    }

    public static String get_id() {
        return _id;
    }

    public static String getCreated() {
        return created;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getName() {
        return name;
    }

    public static String getSchool() {
        return school;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getSign() {
        return sign;
    }
}

