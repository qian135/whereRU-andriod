package com.android.etuan.whereru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.etuan.whereru.R;
import com.android.etuan.whereru.utils.ViewFactory;
import com.android.etuan.whereru.utils.searchclass.Activities;

import java.util.List;

public class ActivityListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Activities> mActivityDataList;

    public ActivityListViewAdapter(List<Activities> list, Context context) {
        mActivityDataList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mActivityDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivityDataList.get(position);
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
                    .inflate(R.layout.campus_activity_home_page_activity_listview_item_layout, null);
        }

        TextView activityTitleTextView = (TextView)
                convertView.findViewById(R.id.activity_title);
        activityTitleTextView.setText(mActivityDataList.get(position).getTitle());
        TextView activitySponsorTextView = (TextView)
                convertView.findViewById(R.id.activity_sponsor);
        activitySponsorTextView.setText(mActivityDataList.get(position).getAuthorName());

        TextView activityCreateTimeTextView = (TextView)
                convertView.findViewById(R.id.activity_create_time);
        activityCreateTimeTextView.setText(mActivityDataList.get(position).getCreated());

        TextView activityKeywordTextView = (TextView)
                convertView.findViewById(R.id.activity_keyword);
        activityKeywordTextView.setText(mActivityDataList.get(position).getKeyword());

        ImageView activityPosterImageView = (ImageView)
                convertView.findViewById(R.id.activity_poster);
        activityPosterImageView = ViewFactory
                .getImageView(mContext, mActivityDataList.get(position).getImgUrl());

        return convertView;

    }


}
