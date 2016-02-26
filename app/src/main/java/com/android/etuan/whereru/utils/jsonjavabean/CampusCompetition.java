package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CampusCompetition {

    private static final String ErrorMessage = "JSON解析出错 CampusCompetition ";

    private List<CampusCompetition> mCampusCompetitionList;

    private String mTitle;
    private String mImageURL;
    private String mStatrted;
    private String mID;
    private String mAuthorName;
    private String mAuthorId;
    private String mEnded;
    private String mCreated;

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public List<CampusCompetition> getmCampusCompetitionList() {
        return mCampusCompetitionList;
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

    public String getmTitle() {
        return mTitle;
    }

    public String getmStatrted() {
        return mStatrted;
    }

    public List<CampusCompetition>  getInformationFromJson(JSONObject jsonObject) {

        mCampusCompetitionList = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("races");
        } catch (JSONException e) {
            System.out.println(ErrorMessage + "races");
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            CampusCompetition campusCompetition = new CampusCompetition();
            try {
                campusCompetition.mTitle = jsonArray.getJSONObject(i).getString("name");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "name");
            }
            try {
                campusCompetition.mImageURL = jsonArray.getJSONObject(i).getString("imgUrl");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "imgUrl");
            }
            try {
                campusCompetition.mStatrted = jsonArray.getJSONObject(i).getString("started");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "started");
            }
            try {
                campusCompetition.mID = jsonArray.getJSONObject(i).getString("id");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "id");
            }
            try {
                campusCompetition.mAuthorName = jsonArray.getJSONObject(i).getString("authorName");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "authorName");
            }
            try {
                campusCompetition.mAuthorId = jsonArray.getJSONObject(i).getString("authorId");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "authorId");
            }
            try {
                campusCompetition.mEnded = jsonArray.getJSONObject(i).getString("ended");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "ended");
            }
            try {
                campusCompetition.mCreated = jsonArray.getJSONObject(i).getString("created");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "created");
            }

            mCampusCompetitionList.add(campusCompetition);
        }

        return mCampusCompetitionList;

    }

}
