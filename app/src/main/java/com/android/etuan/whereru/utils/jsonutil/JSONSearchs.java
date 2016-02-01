package com.android.etuan.whereru.utils.jsonutil;

import com.android.etuan.whereru.utils.searchclass.Coterie;
import com.android.etuan.whereru.utils.searchclass.Race;
import com.android.etuan.whereru.utils.searchclass.Team;
import com.android.etuan.whereru.utils.searchclass.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/30.
 */
public class JSONSearchs {
    private static final int ACTIVITY = 1;
    private static final int RACE = 2;
    private static final int TEAM = 3;
    private static final int USER = 4;
    private static final int COTERIE = 5;
//    private static Activities[]activities;
    //private static Race []races;
//    private static Team []teams;
//    private static User []users;
//    private static Coterie []coteries;

    private static ArrayList<com.android.etuan.whereru.utils.searchclass.Activities> activities;
    private static ArrayList<Race> races;
    private static ArrayList<Team> teams;
    private static ArrayList<User> users;
    private static ArrayList<Coterie> coteries;

    public static void GetInfo(String json, int type) throws JSONException {
        JSONArray jsonArray;
        switch (type) {
            case ACTIVITY:
                jsonArray = com.android.etuan.whereru.utils.jsonutil.Activities.getJSON(json);
                com.android.etuan.whereru.utils.searchclass.Activities.GetInfo(jsonArray);
                activities = com.android.etuan.whereru.utils.searchclass.Activities.getActivities();
                break;
            case RACE:
                jsonArray = Races.getJSON(json);
                Race.GetInfo(jsonArray);
                races = Race.getRaces();
                break;
            case TEAM:
                jsonArray = Teams.getJson(json);
                Team.GetInfo(jsonArray);
                teams = Team.getTeams();
                break;
            case USER:
                jsonArray = Users.getJson(json);
                User.GetInfo(jsonArray);
                users = User.getUsers();
                break;
            case COTERIE:
                jsonArray = Coteries.getJson(json);
                Coterie.GetInfo(jsonArray);
                coteries = Coterie.getCoteries();
                break;
        }

    }


    public static ArrayList<com.android.etuan.whereru.utils.searchclass.Activities> getActivities() {
        return activities;
    }

    public static ArrayList<Race> getRaces() {
        return races;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<Coterie> getCoteries() {
        return coteries;
    }
}


class Activities {
    private static JSONArray jsonArray;

    public static JSONArray getJSON(String json) throws JSONException {
        JSONObject mainJSON = new JSONObject(json);
        jsonArray = mainJSON.getJSONArray("activties");
        return jsonArray;
    }
}

//class Activities{
//    private String title;
//    private String authorName;
//    private String id;
//    private String authorId;
//    private String keyword;
//    private String imgUrl;
//    private String created;
//    private String started;
//    private String ended;
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getAuthorName() {
//        return authorName;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getAuthorId() {
//        return authorId;
//    }
//
//    public String getKeyword() {
//        return keyword;
//    }
//
//    public String getImgUrl() {
//        return imgUrl;
//    }
//
//    public String getCreated() {
//        return created;
//    }
//
//    public String getStarted() {
//        return started;
//    }
//
//    public String getEnded() {
//        return ended;
//    }
//
//    public void GetInfo(JSONObject jsonObject) throws JSONException {
//        title = jsonObject.getString("title");
//        authorName = jsonObject.getString("authorName");
//        id = jsonObject.getString("id");
//        authorId  = jsonObject.getString("authorId");
//        keyword = jsonObject.getString("keyword");
//        imgUrl = jsonObject.getString("imgUrl");
//        created = jsonObject.getString("created");
//        started = jsonObject.getString("started");
//        ended = jsonObject.getString("ended");
//    }
//}
class Races {
    private static JSONArray jsonArray;

    public static JSONArray getJSON(String json) throws JSONException {
        jsonArray = new JSONObject(json).getJSONArray("races");
        return jsonArray;
    }
}

//class Race{
//    private String name;
//    private String imageUrl;
//    private String started;
//    private String id;
//    private String authorId;
//    private String ended;
//    private String created;
//
//}
class Teams {
    private static JSONArray jsonArray;

    public static JSONArray getJson(String json) throws JSONException {
        jsonArray = new JSONObject(json).getJSONArray("teams");
        return jsonArray;
    }
}

//class Team{
//    private String id;
//    private String userId;
//    private String name;
//    private String logoUrl;
//    private String desc;
//    private String status;
//}
class Users {
    private static JSONArray jsonArray;

    public static JSONArray getJson(String json) throws JSONException {
        jsonArray = new JSONObject(json).getJSONArray("users");
        return jsonArray;
    }
}

//class User{
//    private String id;
//    private String name;
//    private String sign;
//    private String headImgUrl;
//}
class Coteries {
    private static JSONArray jsonArray;

    public static JSONArray getJson(String json) throws JSONException {
        jsonArray = new JSONObject(json).getJSONArray("coteries");
        return jsonArray;
    }
}
//class Coterie{
//    private String name;
//    private String logoUrl;
//    private String id;
//}