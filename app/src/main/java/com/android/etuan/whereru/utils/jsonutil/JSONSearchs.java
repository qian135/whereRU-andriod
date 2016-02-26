package com.android.etuan.whereru.utils.jsonutil;

import com.android.etuan.whereru.utils.jsonjavabean.Coterie;
import com.android.etuan.whereru.utils.jsonjavabean.Race;
import com.android.etuan.whereru.utils.jsonjavabean.Team;
import com.android.etuan.whereru.utils.jsonjavabean.User;
import com.android.etuan.whereru.utils.jsonjavabean.mActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONSearchs {
    //搜索类型 type
    private static final int ACTIVITY = 1;
    private static final int RACE = 2;
    private static final int TEAM = 3;
    private static final int USER = 4;
    private static final int COTERIE = 5;

    //保存搜索得到的信息所封装完成的类数组
    private static ArrayList<mActivity> activities;
    private static ArrayList<Race> races;
    private static ArrayList<Team> teams;
    private static ArrayList<User> users;
    private static ArrayList<Coterie> coteries;

    /**
     * 仅仅用到这个接口来处理数据 之后根据搜索类型返回特定的ArrayList
     * @param json JSON数据
     * @param type 搜索信息的类型
     * @throws JSONException
     */
    public static void GetInfo(String json,int type) throws JSONException  {
        JSONArray jsonArray;
        //根据type分5个不同的方式获取并保存信息
        switch(type){
            case ACTIVITY:
                jsonArray = new JSONObject(json).getJSONArray("activties");
                //new一个新的mActivity类 为了使用GetInfo方法解析数据 这个方法仅仅在这里使用
                //GetInfo方法返回一个ArrayList
                mActivity mActivity = new mActivity();
                activities  = mActivity.GetInfo(jsonArray);;
                break;
            //下面的注释同上
            case RACE:
                jsonArray = new JSONObject(json).getJSONArray("races");
                races = (new Race()).GetInfo(jsonArray);
                break;
            case TEAM:
                jsonArray = new JSONObject(json).getJSONArray("teams");
                teams = (new Team()).GetInfo(jsonArray);
                break;
            case USER:
                jsonArray = new JSONObject(json).getJSONArray("users");
                users = new User().GetInfo(jsonArray);
                break;
            case COTERIE:
                jsonArray = new JSONObject(json).getJSONArray("coteries");
                coteries = new Coterie().GetInfo(jsonArray);
                break;
        }

    }

    //返回处理完成之后的类数组
    public static ArrayList<mActivity> getActivities(){
        return activities;
    }

    public static ArrayList<Race> getRaces(){
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

