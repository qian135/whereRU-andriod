package com.android.etuan.whereru.utils.searchclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/30.
 */
public class Activities {

    private static String title;
    private static String authorName;
    private static String id;
    private static String authorId;
    private static String keyword;
    private static String imgUrl;
    private static String created;
    private static String started;
    private static String ended;
    private static ArrayList<Activities> activities;

    public static String getTitle() {
        return title;
    }

    public static String getAuthorName() {
        return authorName;
    }

    public static String getId() {
        return id;
    }

    public static String getAuthorId() {
        return authorId;
    }

    public static String getKeyword() {
        return keyword;
    }

    public static String getImgUrl() {
        return imgUrl;
    }

    public static String getCreated() {
        return created;
    }

    public static String getStarted() {
        return started;
    }

    public static String getEnded() {
        return ended;
    }

    public static ArrayList<Activities> getActivities() {
        return activities;
    }


    public static void GetInfo(JSONArray jsonArray) throws JSONException {
        activities = new ArrayList<>();
        Activities temp = new Activities();
        for (int i = 0; i < jsonArray.length(); i++) {
            temp.GetInfoFromJson(jsonArray.getJSONObject(i));
            activities.add(temp);
        }
    }

    private static void GetInfoFromJson(JSONObject jsonObject) throws JSONException {
        try {
            title = jsonObject.getString("title");
            authorName = jsonObject.getString("authorName");
            id = jsonObject.getString("id");
            authorId = jsonObject.getString("authorId");
            keyword = jsonObject.getString("keyword");
            imgUrl = jsonObject.getString("imgUrl");
            created = jsonObject.getString("created");
            started = jsonObject.getString("started");
            ended = jsonObject.getString("ended");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
