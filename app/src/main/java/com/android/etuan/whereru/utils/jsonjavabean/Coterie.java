package com.android.etuan.whereru.utils.jsonjavabean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 这个类里面所用得到的只有获取成员变量的那几个方法
 * Created by TonyJiang on 2016/1/31.
 */
public class Coterie {
    private String name;
    private String logoUrl;
    private String id;

    //这个方法是私有的对使用者是透明的 作用是解析JSON数据
    private void GetInfoFromJson(JSONObject jsonObject){
        try{
            name = jsonObject.getString("name");
        }catch(JSONException e1){
            System.out.println("JSON解析出错 Coterie name");
        }
        try {
            logoUrl = jsonObject.getString("logoUrl");
        }catch (JSONException e2){
            System.out.println("JSON解析出错 Coterie logoUrl");
        }
        try{
            id = jsonObject.getString("id");
        }catch (JSONException e){
            System.out.println("JSON解析出错 Coterie id");
            e.printStackTrace();
        }
    }
    //这个方法仅仅被JSONSearchs调用 在外部不需要使用 也就是对使用者是透明的
    public ArrayList<Coterie> GetInfo(JSONArray jsonArray) throws JSONException {
        ArrayList<Coterie> coteries = new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            Coterie temp = new Coterie();
            temp.GetInfoFromJson(jsonArray.getJSONObject(i));
            coteries.add(i,temp);
        }
        return coteries;
    }

    //使用者要用到的只有下面几个方法 用来获取成员变量
    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getId() {
        return id;
    }
}
