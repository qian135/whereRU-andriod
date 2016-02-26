package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {

    private static Person sPerson;

    private Person() {
    }

    public static synchronized Person getInstance() {
        if (sPerson == null) {
            sPerson = new Person();
        }
        return sPerson;
    }

    private final String EroMsg = "JSON解析出错 Person";

    private String m_id;//保存ACCESSTOKEN验证信息 重点就这个有用
    private int mTtl;
    private String mCreated;
    private String mUserId;
    private String mName;
    private String mSchool;
    private String mPhone;
    private String mSign;

    public String getM_id() {
        return m_id;
    }

    public String getmCreated() {
        return mCreated;
    }

    public int getmTtl() {
        return mTtl;
    }

    public String getmUserId() {
        return mUserId;
    }

    public String getmName() {
        return mName;
    }

    public String getmSchool() {
        return mSchool;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmSign() {
        return mSign;
    }


    public void getInfoFromJson(JSONObject jsonObject) {
        try {
            m_id = jsonObject.getString("id");
        } catch (JSONException e) {
            System.out.println(EroMsg + "id");
        }
        try {
            mTtl = jsonObject.getInt("ttl");
        } catch (JSONException e) {
            System.out.println(EroMsg + "ttl");
        }
        try {
            mCreated = jsonObject.getString("created");
        } catch (JSONException e) {
            System.out.println(EroMsg + "created");
        }
        try {
            mUserId = jsonObject.getString("userId");
        } catch (JSONException e) {
            System.out.println(EroMsg + "userId");
        }
        try {
            JSONObject jsonUser = jsonObject.getJSONObject("user");
            try {
                mPhone = jsonUser.getString("phone");
            } catch (JSONException e) {
                System.out.println(EroMsg + "phone");
            }
            try {
                mName = jsonUser.getString("name");
            } catch (JSONException e) {
                System.out.println(EroMsg + "name");
            }
            try {
                mSchool = jsonUser.getString("school");
            } catch (JSONException e) {
                System.out.println(EroMsg + "school");
            }
            try {
                mSign = jsonUser.getString("sign");
            } catch (JSONException e) {
                System.out.println(EroMsg + "sign");
            }

        } catch (Exception e) {
            System.out.println("JSON解析出错 User");
            e.printStackTrace();
        }
    }

}
