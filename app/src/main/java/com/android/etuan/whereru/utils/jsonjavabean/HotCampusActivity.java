package com.android.etuan.whereru.utils.jsonjavabean;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HotCampusActivity {

    private static final String ErrorMessage = "JSON解析出错 HotCampusActivity ";

    private List<HotCampusActivity> mHotCampusActivityList;

    private String mID;
    private String mTitle;
    private String mImageURL;

    public String getmID() {
        return mID;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public List<HotCampusActivity>  getInformationFromJson(JSONObject jsonObject) {

        mHotCampusActivityList = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("hotActivities");
        } catch (JSONException e) {
            System.out.println(ErrorMessage + "hotActivities");
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            HotCampusActivity hotCampusActivity = new HotCampusActivity();
            try {
                hotCampusActivity.mID = jsonArray.getJSONObject(i).getString("id");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "id");
            }
            try {
                hotCampusActivity.mTitle = jsonArray.getJSONObject(i).getString("title");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "title");
            }
            try {
                hotCampusActivity.mImageURL = jsonArray.getJSONObject(i).getString("imgUrl");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "imgUrl");
            }
            mHotCampusActivityList.add(hotCampusActivity);
        }

        return mHotCampusActivityList;

    }

}
