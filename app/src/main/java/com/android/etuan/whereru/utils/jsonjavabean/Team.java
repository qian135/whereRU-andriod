package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class Team {
    private final String EroMsg = "JSON解析出错 Team ";
    private String id;
    private String userId;
    private String name;
    private String logoUrl;
    private String desc;
    private String status;


    //对使用者透明 作用是解析JSON数据
    private void GetInfoFormJson(JSONObject jsonObject) throws JSONException {
        try {
            id = jsonObject.getString("id");
        }catch (JSONException e){
            System.out.println(EroMsg+"id");
        }
        try {
            userId = jsonObject.getString("userId");
        }catch (JSONException e){
            System.out.println(EroMsg+"userId");
        }
        try {
            name = jsonObject.getString("name");
        }catch (JSONException e){
            System.out.println(EroMsg+"name");
        }
        try {
            logoUrl = jsonObject.getString("logoUrl");
        }catch (JSONException e){
            System.out.println(EroMsg+"logoUrl");
        }
        try {
            desc = jsonObject.getString("desc");
        }catch (JSONException e){
            System.out.println(EroMsg+"desc");
        }
        try{
            status = jsonObject.getString("status");
        }catch (JSONException e){
            System.out.println(EroMsg+"status");
            e.printStackTrace();
        }
    }

    //这个方法对使用者是透明的 作用是解析JSON数据 仅仅在JSONSearchs里面调用
    public ArrayList<Team> GetInfo(JSONArray jsonArray) throws JSONException {
        ArrayList<Team> teams = new ArrayList<>();
        
        for(int i=0;i<jsonArray.length();i++){
            Team temp = new Team();
            temp.GetInfoFormJson(jsonArray.getJSONObject(i));
            teams.add(i,temp);
        }
        return teams;
    }

    //以下是使用者所要使用的方法 获取成员变量
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getDesc() {
        return desc;
    }

    public String getStatus() {
        return status;
    }
}
