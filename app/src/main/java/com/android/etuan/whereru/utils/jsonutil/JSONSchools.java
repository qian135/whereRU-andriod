package com.android.etuan.whereru.utils.jsonutil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/30.
 */
public class JSONSchools {

    private static ArrayList<String> schools;

    public static ArrayList<String> getSchoolsFromJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            schools = new ArrayList<>();
            for(int i = 0;i<jsonArray.length();i++){
                schools.add(jsonArray.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schools;
    }
}
