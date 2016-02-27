package com.android.etuan.whereru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.etuan.whereru.R;
import com.android.etuan.whereru.utils.searchclass.Team;

import java.util.List;

public class TeamListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Team> mTeamDataList;

    public TeamListViewAdapter(List<Team> list, Context context) {
        mTeamDataList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTeamDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTeamDataList.get(position);
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
            convertView = layoutInflater
                    .inflate(R.layout.campus_team_home_page_listview_item_layout, null);
        }

//        TextView campusTeamName = (TextView)
//                convertView.findViewById(R.id.campus_team_name);
//        campusTeamName.setText(mTeamDataList.get(position).getName());
//        TextView campusTeamIntroduce = (TextView)
//                convertView.findViewById(R.id.campus_team_introduce);
//        campusTeamIntroduce.setText(mTeamDataList.get(position).getDesc());
//
//        ImageView campusTeamLogo = (ImageView)
//                convertView.findViewById(R.id.campus_team_logo);
//        campusTeamLogo = ViewFactory
//                .getImageView(mContext, mTeamDataList.get(position).getLogoUrl());

        return convertView;

    }

}
