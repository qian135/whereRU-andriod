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
import com.android.etuan.whereru.utils.jsonjavabean.CampusCompetition;
import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CampusCompetitionListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CampusCompetition> mCampusCompetitionList;
    private LayoutInflater mLayoutInflater;

    private SimpleDateFormat mSimpleDateFormat;

    public CampusCompetitionListViewAdapter(Context context, List<CampusCompetition> campusCompetitionList) {
        mContext = context;
        mCampusCompetitionList = campusCompetitionList;
        mLayoutInflater = LayoutInflater.from(mContext);

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    }

    @Override
    public int getCount() {
        return mCampusCompetitionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCampusCompetitionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView competitionTitleTextView;
        public TextView competitionSponsorTextView;
        public TextView competitionCreateTimeTextView;
        public TextView competitionEnrollStartTimeTextView;
        public TextView competitionEnrollEndTimeTextView;

        //注意不使用NetworkImageView可能会出现图片错位
        public NetworkImageView competitionPosterImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            convertView = mLayoutInflater
                    .inflate(R.layout.campus_competition_home_page_listview_item_layout, null);

            viewHolder = new ViewHolder();

            viewHolder.competitionTitleTextView = (TextView)
                    convertView.findViewById(R.id.competition_title);
            viewHolder.competitionSponsorTextView = (TextView)
                    convertView.findViewById(R.id.competition_sponsor);
            viewHolder.competitionCreateTimeTextView = (TextView)
                    convertView.findViewById(R.id.competition_create_time);
            viewHolder.competitionEnrollStartTimeTextView = (TextView)
                    convertView.findViewById(R.id.competition_enroll_start_time);
            viewHolder.competitionEnrollEndTimeTextView = (TextView)
                    convertView.findViewById(R.id.competition_enroll_end_time);

            viewHolder.competitionPosterImageView = (NetworkImageView)
                    convertView.findViewById(R.id.competition_poster);

            //设置加载过程中的默认图片
            viewHolder.competitionPosterImageView.setDefaultImageResId(R.drawable.loading_image);
            //设置加载出错时的图片
            viewHolder.competitionPosterImageView.setErrorImageResId(R.drawable.loading_image_error);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.competitionTitleTextView.setText(
                mCampusCompetitionList.get(position).getmTitle());
        viewHolder.competitionSponsorTextView.setText(
                mCampusCompetitionList.get(position).getmAuthorName());
        viewHolder.competitionCreateTimeTextView.setText(
                mSimpleDateFormat.format(
                        DateUtils.parseDate(mCampusCompetitionList.get(position).getmCreated())));
        viewHolder.competitionEnrollStartTimeTextView.setText(
                mSimpleDateFormat.format(
                        DateUtils.parseDate(mCampusCompetitionList.get(position).getmStatrted())));
        viewHolder.competitionEnrollEndTimeTextView.setText(
                mSimpleDateFormat.format(
                        DateUtils.parseDate(mCampusCompetitionList.get(position).getmEnded())));

        //这里使用Volley来进行图片的加载，图片放在NetworkImageView中
        viewHolder.competitionPosterImageView.setImageUrl(
                mCampusCompetitionList.get(position).getmImageURL(),
                MyVolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader());

        return convertView;

    }

}
