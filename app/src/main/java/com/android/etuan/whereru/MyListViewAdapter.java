package com.android.etuan.whereru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;
    //选择判断加载的列表项
    private int mFlag;

    public MyListViewAdapter(Context context) {
        mContext = context;
    }

    public MyListViewAdapter(List<String> list, Context context, int flag) {
        mList = list;
        mContext = context;
        mFlag = flag;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (mFlag) {
                //校园活动首页的ListView部分
                case 0:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_activity_home_page_listview_item_layout, null);
                    break;
                //校园竞赛首页的ListView
                case 1:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_competition_home_page_listview_item_layout, null);
                    break;
                //校园团队首页的ListView
                case 2:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_team_home_page_listview_item_layout, null);
                    break;

                //校园竞赛->竞赛详情->竞赛通知 页面的ListView
                case 3:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_competition_details_notice_page_listview_item_layout, null);
                    break;
                //校园竞赛->竞赛详情->竞赛团队 页面的ListView
                case 4:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_competition_details_team_page_listview_item_layout, null);
                    break;
                //校园竞赛->竞赛详情->参与竞赛 页面的ListView
                case 5:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_competition_details_join_page_listview_item_layout, null);
                    break;

                //校园团队->团队介绍->团队相册 页面的ListView
                case 6:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_team_details_album_page_listview_item_layout, null);
                    break;
                //校园团队->团队介绍->团队活动 页面的ListView
                case 7:
                    convertView = layoutInflater
                            .inflate(R.layout.campus_team_details_activity_page_listview_item_layout, null);
                    break;

                //个人中心->首页->我的团队
                case 8:
                    convertView = layoutInflater
                            .inflate(R.layout.me_home_page_my_team_page_listview_item_layout, null);
                    break;
                //个人中心->首页->竞赛历史
                case 9:
                    convertView = layoutInflater
                            .inflate(R.layout.me_home_page_activity_history_page_listview_item_layout, null);
                    break;
                //个人中心->首页->活动历史
                case 10:
                    convertView = layoutInflater
                            .inflate(R.layout.me_home_page_activity_history_page_listview_item_layout, null);
                    break;

                //个人中心->我的团队->团队成员(已加入团队)
                case 11:
                    convertView = layoutInflater
                            .inflate(R.layout.my_team_team_member_page_member_listview_item_layout, null);
                    break;
                //个人中心->我的团队->团队成员(申请加入团队)
                case 12:
                    convertView = layoutInflater
                            .inflate(R.layout.my_team_team_member_page_want_join_listview_item_layout, null);
                    break;
                //个人中心->我的团队->团队活动
                case 13:
                    convertView = layoutInflater
                            .inflate(R.layout.my_team_team_activity_page_listview_item_layout, null);
                    break;
                //个人中心->我的团队->团队相册
                case 14:
                    convertView = layoutInflater
                            .inflate(R.layout.my_team_team_album_page_listview_item_layout, null);
                    break;
                //个人中心->我的团队->信息修改（各部门要修改信息列表）
                case 15:
                    convertView = layoutInflater
                            .inflate(R.layout.my_team_data_modification_department_data_page_listview_item_layout, null);
                    break;
            }
        }
        return convertView;
    }

}
