package com.android.etuan.whereru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.etuan.whereru.R;
import com.android.etuan.whereru.utils.DateUtils;
import com.android.etuan.whereru.utils.ViewFactory;
import com.android.etuan.whereru.utils.searchclass.Race;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompetitionListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Race> mCompetitionDataList;

    public CompetitionListViewAdapter(List<Race> list, Context context) {
        mCompetitionDataList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCompetitionDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCompetitionDataList.get(position);
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
                    .inflate(R.layout.campus_competition_home_page_listview_item_layout, null);
        }

        TextView competitionTitleTextView = (TextView)
                convertView.findViewById(R.id.competition_title);
        competitionTitleTextView.setText(mCompetitionDataList.get(position).getName());
        TextView competitionSponsorTextView = (TextView)
                convertView.findViewById(R.id.competition_sponsor);
        competitionSponsorTextView.setText(mCompetitionDataList.get(position).getAuthorName());

        //将ISO8601标准的时间的转化为Java Date对象（下同）
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = DateUtils.parseDate(mCompetitionDataList.get(position).getCreated());
        TextView competitionCreateTimeTextView = (TextView)
                convertView.findViewById(R.id.competition_create_time);
        competitionCreateTimeTextView.setText(simpleDateFormat.format(date));

        date = DateUtils.parseDate(mCompetitionDataList.get(position).getStarted());
        TextView competitionEnrollStartTimeTextView = (TextView)
                convertView.findViewById(R.id.competition_enroll_start_time);
        competitionEnrollStartTimeTextView.setText(simpleDateFormat.format(date));

        date = DateUtils.parseDate(mCompetitionDataList.get(position).getEnded());
        TextView competitionEnrollEndTimeTextView = (TextView)
                convertView.findViewById(R.id.competition_enroll_end_time);
        competitionEnrollEndTimeTextView.setText(simpleDateFormat.format(date));

        ImageView competitionPosterImageView = (ImageView)
                convertView.findViewById(R.id.competition_poster);
        competitionPosterImageView = ViewFactory
                .getImageView(mContext,mCompetitionDataList.get(position).getImageUrl());

        return convertView;

    }

}
