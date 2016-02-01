package com.android.etuan.whereru;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 1/31/2016.
 */
public class MeMyTeamDataModificationDepartmentActivity extends Activity {

    private ListView mMeMyTeamDepartmentModificationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_team_data_modification_department_data_page);

        //模仿列表项数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("item" + i);
        }

        //个人中心->我的团队->信息修改（各部门要修改信息列表） 列表项代码
        mMeMyTeamDepartmentModificationListView = (ListView)
                findViewById(R.id.my_team_team_album_page_list_view);
        MyListViewAdapter myMeMyTeamDepartmentModificationListViewAdapter =
                new MyListViewAdapter(list, this, 15);
        mMeMyTeamDepartmentModificationListView.setAdapter(
                myMeMyTeamDepartmentModificationListViewAdapter);

    }
}
