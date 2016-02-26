package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class CampusTeam {

    private static final String ErrorMessage = "JSON解析出错 CampusTeam ";

    private List<CampusTeam> mCampusTeamList;

    private String mID;
    private String mUserId;
    private String mName;
    private String mLogoUrl;
    private String mDesc;
    private String mStatus;

    public List<CampusTeam> getmCampusTeamList() {
        return mCampusTeamList;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmID() {
        return mID;
    }

    public String getmLogoUrl() {
        return mLogoUrl;
    }

    public String getmName() {
        return mName;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmUserId() {
        return mUserId;
    }

    public List<CampusTeam>  getInformationFromJson(JSONArray jsonArray) {

        mCampusTeamList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            CampusTeam campusTeam = new CampusTeam();
            try {
                campusTeam.mID = jsonArray.getJSONObject(i).getString("id");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "id");
            }
            try {
                campusTeam.mUserId = jsonArray.getJSONObject(i).getString("userId");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "userId");
            }
            try {
                campusTeam.mName = jsonArray.getJSONObject(i).getString("name");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "name");
            }
            try {
                campusTeam.mLogoUrl = jsonArray.getJSONObject(i).getString("logoUrl");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "logoUrl");
            }
            try {
                campusTeam.mDesc = jsonArray.getJSONObject(i).getString("desc");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "desc");
            }
            try {
                campusTeam.mStatus = jsonArray.getJSONObject(i).getString("status");
            } catch (JSONException e) {
                System.out.println(ErrorMessage + "status");
            }

            mCampusTeamList.add(campusTeam);
        }

        return mCampusTeamList;

    }

}
