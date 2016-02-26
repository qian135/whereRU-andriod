package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mActivity {

    private final String EroMsg = "JSON解析出错 mActivity";

    private String title;
    private String authorName;
    private String explainUrl;
    private String verifyRule;
    private String school;
    private int readers;
    private String actType;
    private boolean hidden;
    private boolean issue;
    private boolean deleted;
    private String teamId;
    private String id;
    private String authorId;
    private String keyword;
    private String imgUrl;
    private String created;
    private String started;
    private String ended;

    //以下方法返回成员变量

    public String getExplainUrl() {
        return explainUrl;
    }

    public String getVerifyRule() {
        return verifyRule;
    }

    public String getSchool() {
        return school;
    }

    public int getReaders() {
        return readers;
    }

    public String getActType() {
        return actType;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean issue() {
        return issue;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCreated() {
        return created;
    }

    public String getStarted() {
        return started;
    }

    public String getEnded() {
        return ended;
    }

    //这个方法对使用者透明 作用是通过JSONSearchs的内部调用 解析JSON数据
    public ArrayList<mActivity> GetInfo(JSONArray jsonArray) throws JSONException {
        ArrayList<mActivity> activities = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            mActivity temp = new mActivity();
            temp.GetInfoFromJson(jsonArray.getJSONObject(i));
            activities.add(i, temp);
        }
        return activities;
    }

    //这个方法对使用者透明 作用是解析JSON数据
    private void GetInfoFromJson(JSONObject jsonObject) throws JSONException {

        try {
            explainUrl = jsonObject.getString("explainUrl");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " explainUrl");
        }
        try {
            verifyRule = jsonObject.getString("verifyRule");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " verifyRule");
        }
        try {
            school = jsonObject.getString("school");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " school");
        }
        try {
            readers = jsonObject.getInt("readers");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " readers");
        }
        try {
            actType = jsonObject.getString("actType");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " actType");
        }
        try {
            teamId = jsonObject.getString("teamId");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " teamId");
        }
        try {
            hidden = jsonObject.getBoolean("hidden");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " hidden");
        }
        try {
            issue = jsonObject.getBoolean("issue");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " issue");
        }
        try {
            deleted = jsonObject.getBoolean("deleted");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " deleted");
        }

        try {
            title = jsonObject.getString("title");
        } catch (JSONException e1) {
            System.out.println(EroMsg + " title");
        }
        try {
            authorName = jsonObject.getString("authorName");
        } catch (JSONException e2) {
            System.out.println(EroMsg + " authorName");
        }
        try {
            id = jsonObject.getString("id");
        } catch (JSONException e2) {
            System.out.println(EroMsg + " id");
        }
        try {
            authorId = jsonObject.getString("authorId");
        } catch (JSONException e) {
            System.out.println(EroMsg + " authorId");
        }
        try {
            keyword = jsonObject.getString("keyword");
        } catch (JSONException e) {
            System.out.println(EroMsg + " keyword");
        }
        try {
            imgUrl = jsonObject.getString("imgUrl");
        } catch (JSONException e) {
            System.out.println(EroMsg + " imgUrl");
        }
        try {
            created = jsonObject.getString("created");
        } catch (JSONException e) {
            System.out.println(EroMsg + " created");
        }
        try {
            started = jsonObject.getString("started");
        } catch (JSONException e) {
            System.out.println(EroMsg + " started");
        }
        try {
            ended = jsonObject.getString("ended");
        } catch (JSONException e) {
            System.out.println(EroMsg + " ended");
            e.printStackTrace();
        }
    }
}
