package com.android.etuan.whereru.utils.searchclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class Team {
    private static String id;
    private static String userId;
    private static String name;
    private static String logoUrl;
    private static String desc;
    private static String status;

    private static ArrayList<Team> teams;

    private static void GetInfoFormJson(JSONObject jsonObject) throws JSONException {
        try {
            id = jsonObject.getString("id");

            userId = jsonObject.getString("userId");
            name = jsonObject.getString("name");
            logoUrl = jsonObject.getString("logoUrl");
            desc = jsonObject.getString("desc");
            status = jsonObject.getString("status");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void GetInfo(JSONArray jsonArray) throws JSONException {
        teams = new ArrayList<>();
        Team temp = new Team();
        for(int i=0;i<jsonArray.length();i++){
            temp.GetInfoFormJson(jsonArray.getJSONObject(i));
            teams.add(temp);
        }
    }

    public static String getId() {
        return id;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getName() {
        return name;
    }

    public static String getLogoUrl() {
        return logoUrl;
    }

    public static String getDesc() {
        return desc;
    }

    public static String getStatus() {
        return status;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }
}
