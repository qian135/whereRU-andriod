package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.etuan.whereru.adapter.CampusCompetitionListViewAdapter;
import com.android.etuan.whereru.utils.InterfaceConstant;
import com.android.etuan.whereru.utils.jsonjavabean.CampusCompetition;
import com.android.etuan.whereru.utils.jsonjavabean.Person;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class CampusCompetitionHomePageActivity extends Activity {

     /*实现校园竞赛列表项的代码*/

    private ListView mCampusCompetitionListView; //校园竞赛列表的ListView
    private List<CampusCompetition> mCampusCompetitionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_competition_home_page);

        //校园竞赛列表项代码

        mCampusCompetitionList = new ArrayList<>();

        mCampusCompetitionListView = (ListView) findViewById(R.id.campus_competition_list_view);

        JsonObjectRequest jsonObjectRequest = null;
        try {
            jsonObjectRequest = new JsonObjectRequest(
                    InterfaceConstant.SCHOOL_COMPETITION_URL +
                            URLEncoder.encode(Person.getInstance().getmSchool(), "UTF-8"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            mCampusCompetitionList = new CampusCompetition()
                                    .getInformationFromJson(jsonObject);
                            mCampusCompetitionListView.setAdapter(
                                    new CampusCompetitionListViewAdapter(
                                            CampusCompetitionHomePageActivity.this, mCampusCompetitionList));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MyVolleySingleton.getInstance(this.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);

        //为校园竞赛ListView每项设置监听器，完成页面跳转
        mCampusCompetitionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new
                        Intent(CampusCompetitionHomePageActivity.this,
                        CampusCompetitionDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

}
