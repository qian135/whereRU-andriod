package com.android.etuan.whereru.utils.jsonjavabean;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CampusActivity {

    private static final String ErrorMessage = "JSON解析出错 CampusActivity ";

    private List<CampusActivity> mCampusActivityList;

    private String mTitle;
    private String mAuthorName;
    private String mID;
    private String mAuthorId;
    private String mKeyword;
    private String mImageURL;
    private String mCreated;
    private String mStatrted;
    private String mEnded;

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public List<CampusActivity> getmCampusActivityList() {
        return mCampusActivityList;
    }

    public String getmCreated() {
        return mCreated;
    }

    public String getmEnded() {
        return mEnded;
    }

    public String getmID() {
        return mID;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmKeyword() {
        return mKeyword;
    }

    public String getmStatrted() {
        return mStatrted;
    }

    public String getmTitle() {
        return mTitle;
    }

    public List<CampusActivity>  getInformationFromJson(JSONObject jsonObject) {

        mCampusActivityList = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("activties");
        } catch (JSONException e) {
            System.out.println(ErrorMessage + "activties");
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            CampusActivity campusActivity = new CampusActivity();
            try {
                campusActivity.mTitle = jsonArray.getJSONObject(i).getString("title");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "title");
            }
            try {
                campusActivity.mAuthorName = jsonArray.getJSONObject(i).getString("authorName");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "authorName");
            }
            try {
                campusActivity.mID = jsonArray.getJSONObject(i).getString("id");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "id");
            }
            try {
                campusActivity.mAuthorId = jsonArray.getJSONObject(i).getString("authorId");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "authorId");
            }
            try {
                campusActivity.mKeyword = jsonArray.getJSONObject(i).getString("keyword");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "keyword");
            }
            try {
                campusActivity.mImageURL = jsonArray.getJSONObject(i).getString("imgUrl");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "imgUrl");
            }
            try {
                campusActivity.mCreated = jsonArray.getJSONObject(i).getString("created");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "created");
            }
            try {
                campusActivity.mStatrted = jsonArray.getJSONObject(i).getString("statrted");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "statrted");
            }
            try {
                campusActivity.mEnded = jsonArray.getJSONObject(i).getString("ended");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "ended");
            }

            mCampusActivityList.add(campusActivity);
        }

        return mCampusActivityList;

    }

}
