package com.android.etuan.whereru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.etuan.whereru.R;
import com.android.etuan.whereru.adapter.data.MeHomeMyTeamData;

import java.util.List;

/**
 * Created by zhang on 1/31/2016.
 */
public class MeHomeMyTeamListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<MeHomeMyTeamData> mMeHomeMyTeamDataList;


    public MeHomeMyTeamListViewAdapter(List<MeHomeMyTeamData> list, Context context) {
        mMeHomeMyTeamDataList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMeHomeMyTeamDataList.size();
    }


    @Override
    public Object getItem(int position) {
        return mMeHomeMyTeamDataList.get(position);
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
        return convertView;
    }

}
