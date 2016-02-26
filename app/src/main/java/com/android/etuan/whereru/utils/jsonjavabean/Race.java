package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class Race {
    private final String EroMsg="JSON解析出错 Race ";
    private String name;
    private String imageUrl;
    private String started;
    private String id;
    private String authorId;
    private String ended;
    private String created;
    //这个方法对使用者是透明的 作用是解析JSON数据 仅仅在JSONSearchs里面调用
    public ArrayList<Race> GetInfo(JSONArray jsonArray) throws JSONException {
        ArrayList<Race> races = new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            Race temp = new Race();
            temp.GetInfoFromJson(jsonArray.getJSONObject(i));
            races.add(i,temp);
        }
        return races;
    }
    //对使用者透明 作用是解析JSON数据
    private void GetInfoFromJson(JSONObject jsonObject) throws JSONException {
        try {
            name = jsonObject.getString("name");
        }catch (JSONException e){
            System.out.println(EroMsg+"name");
        }
        try {
            imageUrl = jsonObject.getString("imgUrl");
        }catch (JSONException e){
            System.out.println(EroMsg+"imgUrl");
        }
        try {
            started = jsonObject.getString("started");
        }catch (JSONException e){
            System.out.println(EroMsg+"started");
        }
        try {
            id = jsonObject.getString("id");
        }catch (JSONException e){
            System.out.println(EroMsg+"id");
        }
        try {
            authorId = jsonObject.getString("authorId");
        }catch (JSONException e){
            System.out.println(EroMsg+"authorId");
        }
        try {
            ended = jsonObject.getString("ended");
        }catch (JSONException e){
            System.out.println(EroMsg+"ended");
        }
        try{
            created = jsonObject.getString("created");
        } catch (JSONException e) {
            System.out.println("JSON解析出错 RACE"+" created");
            e.printStackTrace();
        }
    }

    //以下是使用者所要使用的方法 获取成员变量
    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStarted() {
        return started;
    }

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getEnded() {
        return ended;
    }

    public String getCreated() {
        return created;
    }
}
