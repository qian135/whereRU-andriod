package com.android.etuan.whereru.utils.searchclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class Race {
    public static String getAuthorName() {
        return authorName;
    }

    public static void setAuthorName(String authorName) {
        Race.authorName = authorName;
    }

    private static String authorName;
    private static String name;
    private static String imageUrl;
    private static String started;
    private static String id;
    private static String authorId;
    private static String ended;
    private static String created;
    private static ArrayList<Race> races;
    public static void GetInfo(JSONArray jsonArray) throws JSONException {
        races = new ArrayList<>();
        Race temp = new Race();
        for(int i=0;i<jsonArray.length();i++){
            temp.GetInfoFromJson(jsonArray.getJSONObject(i));
            races.add(temp);
        }
    }

    private static void GetInfoFromJson(JSONObject jsonObject) throws JSONException {
        try {
            name = jsonObject.getString("name");
            authorName=jsonObject.getString("authorName");
            imageUrl = jsonObject.getString("imgUrl");
            started = jsonObject.getString("started");
            id = jsonObject.getString("id");
            authorId = jsonObject.getString("authorId");
            ended = jsonObject.getString("ended");
            created = jsonObject.getString("created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getName() {
        return name;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static String getStarted() {
        return started;
    }

    public static String getId() {
        return id;
    }

    public static String getAuthorId() {
        return authorId;
    }

    public static String getEnded() {
        return ended;
    }

    public static String getCreated() {
        return created;
    }

    public static ArrayList<Race> getRaces() {
        return races;
    }

}
