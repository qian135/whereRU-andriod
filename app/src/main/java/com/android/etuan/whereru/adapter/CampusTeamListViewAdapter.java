package com.android.etuan.whereru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.etuan.whereru.MyVolleySingleton;
import com.android.etuan.whereru.R;
import com.android.etuan.whereru.utils.jsonjavabean.CampusTeam;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class CampusTeamListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CampusTeam> mCampusTeamList;
    private LayoutInflater mLayoutInflater;


    public CampusTeamListViewAdapter(Context context,List<CampusTeam> campusTeamList) {
        mContext = context;
        mCampusTeamList = campusTeamList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mCampusTeamList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCampusTeamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView teamNameTextView;
        public TextView teamDescriptionTextView;

        //注意不使用NetworkImageView可能会出现图片错位
        public NetworkImageView teamLogoNetworkImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            convertView = mLayoutInflater
                    .inflate(R.layout.campus_team_home_page_listview_item_layout, null);

            viewHolder = new ViewHolder();

            viewHolder.teamNameTextView = (TextView)
                    convertView.findViewById(R.id.campus_team_name);
            viewHolder.teamDescriptionTextView = (TextView)
                    convertView.findViewById(R.id.campus_team_description);

            viewHolder.teamLogoNetworkImageView = (NetworkImageView)
                    convertView.findViewById(R.id.campus_team_logo);

            //设置加载过程中的默认图片
            viewHolder.teamLogoNetworkImageView.setDefaultImageResId(R.drawable.loading_image);
            //设置加载出错时的图片
            viewHolder.teamLogoNetworkImageView.setErrorImageResId(R.drawable.loading_image_error);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.teamNameTextView.setText(mCampusTeamList.get(position).getmName());
        viewHolder.teamDescriptionTextView.setText(mCampusTeamList.get(position).getmDesc());

        //这里使用Volley来进行图片的加载，图片放在NetworkImageView中
        viewHolder.teamLogoNetworkImageView.setImageUrl(
                mCampusTeamList.get(position).getmLogoUrl(),
                MyVolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader());

        return convertView;

    }

}
