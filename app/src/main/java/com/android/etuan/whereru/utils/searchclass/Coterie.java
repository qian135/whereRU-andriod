package com.android.etuan.whereru.utils.searchclass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TonyJiang on 2016/1/31.
 */
public class Coterie {
    private static String name;
    private static String logoUrl;
    private static String id;
    private static ArrayList<Coterie> coteries;

    private static void GetInfoFromJson(JSONObject jsonObject){
        try{
            name = jsonObject.getString("name");
            logoUrl = jsonObject.getString("logoUrl");
            id = jsonObject.getString("id");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void GetInfo(JSONArray jsonArray){
        coteries = new ArrayList<>();
        Coterie temp = new Coterie();
        for(int i=0;i<jsonArray.length();i++){
            temp.GetInfo(jsonArray);
            coteries.add(temp);
        }
    }

    public static String getName() {
        return name;
    }

    public static String getLogoUrl() {
        return logoUrl;
    }

    public static String getId() {
        return id;
    }

    public static ArrayList<Coterie> getCoteries() {
        return coteries;
    }
}
