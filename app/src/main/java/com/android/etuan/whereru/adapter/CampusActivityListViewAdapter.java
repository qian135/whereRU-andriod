package com.android.etuan.whereru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.etuan.whereru.MyVolleySingleton;
import com.android.etuan.whereru.R;
import com.android.etuan.whereru.utils.DateUtils;
import com.android.etuan.whereru.utils.jsonjavabean.CampusActivity;
import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CampusActivityListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CampusActivity> mCampusActivityList;
    private LayoutInflater mLayoutInflater;

    private SimpleDateFormat mSimpleDateFormat;

    public CampusActivityListViewAdapter(Context context,List<CampusActivity> campusActivityList) {
        mContext = context;
        mCampusActivityList = campusActivityList;
        mLayoutInflater = LayoutInflater.from(mContext);

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    }

    @Override
    public int getCount() {
        return mCampusActivityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCampusActivityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView activityTitleTextView;
        public TextView activitySponsorTextView;
        public TextView activityCreateTimeTextView;
        public TextView activityKeywordTextView;

        //注意不使用NetworkImageView可能会出现图片错位
        public NetworkImageView activityPosterImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            convertView = mLayoutInflater
                    .inflate(R.layout.campus_activity_home_page_listview_item_layout, null);

            viewHolder = new ViewHolder();

            viewHolder.activityTitleTextView = (TextView)
                    convertView.findViewById(R.id.activity_title);
            viewHolder.activitySponsorTextView = (TextView)
                    convertView.findViewById(R.id.activity_sponsor);
            viewHolder.activityCreateTimeTextView = (TextView)
                    convertView.findViewById(R.id.activity_create_time);
            viewHolder.activityKeywordTextView = (TextView)
                    convertView.findViewById(R.id.activity_keyword);

            viewHolder.activityPosterImageView = (NetworkImageView)
                    convertView.findViewById(R.id.activity_poster);

            //设置加载过程中的默认图片
            viewHolder.activityPosterImageView.setDefaultImageResId(R.drawable.loading_image);
            //设置加载出错时的图片
            viewHolder.activityPosterImageView.setErrorImageResId(R.drawable.loading_image_error);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.activityTitleTextView.setText(mCampusActivityList.get(position).getmTitle());
        viewHolder.activitySponsorTextView.setText(mCampusActivityList.get(position).getmAuthorName());
        viewHolder.activityCreateTimeTextView.setText(
                mSimpleDateFormat.format(
                        DateUtils.parseDate(mCampusActivityList.get(position).getmCreated())));
        viewHolder.activityKeywordTextView.setText(mCampusActivityList.get(position).getmKeyword());

        //这里使用Volley来进行图片的加载，图片放在NetworkImageView中
        viewHolder.activityPosterImageView.setImageUrl(
                mCampusActivityList.get(position).getmImageURL(),
                MyVolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader());

        return convertView;

    }

}
