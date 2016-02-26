package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.etuan.whereru.adapter.CampusTeamListViewAdapter;
import com.android.etuan.whereru.utils.InterfaceConstant;
import com.android.etuan.whereru.utils.jsonjavabean.CampusTeam;
import com.android.etuan.whereru.utils.jsonjavabean.Person;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class CampusTeamHomePageActivity extends Activity {

    /*实现校园团队列表项的代码*/

    private ListView mCampusTeamListView; //校园团队列表的ListView
    private List<CampusTeam> mCampusTeamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_team_home_page);

        //校园团队列表项代码

        mCampusTeamList = new ArrayList<>();

        mCampusTeamListView = (ListView) findViewById(R.id.campus_team_home_page_list_view);

        try {
            System.out.println(InterfaceConstant.SCHOOL_TEAM_URL +
                    URLEncoder.encode(Person.getInstance().getmSchool(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonArrayRequest = null;
        try {
            jsonArrayRequest = new JsonArrayRequest(
                    InterfaceConstant.SCHOOL_TEAM_URL +
                            URLEncoder.encode(Person.getInstance().getmSchool(), "UTF-8"),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            System.out.println(jsonArray);
                            mCampusTeamList = new CampusTeam()
                                    .getInformationFromJson(jsonArray);
                            mCampusTeamListView.setAdapter(new CampusTeamListViewAdapter(
                                    CampusTeamHomePageActivity.this, mCampusTeamList));
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
                .addToRequestQueue(jsonArrayRequest);

        mCampusTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new
                        Intent(CampusTeamHomePageActivity.this, CampusTeamDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

}
