package com.android.etuan.whereru.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


public class HotCampusActivityViewPagerAdapter extends PagerAdapter {

    private List<NetworkImageView> mHotCampusActivityNetworkImageViewList;

    public HotCampusActivityViewPagerAdapter(List<NetworkImageView>
                                                     hotCampusActivityNetworkImageViewList) {
        mHotCampusActivityNetworkImageViewList = hotCampusActivityNetworkImageViewList;
    }

    @Override
    public int getCount() {
        return mHotCampusActivityNetworkImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mHotCampusActivityNetworkImageViewList.get(position));
        return mHotCampusActivityNetworkImageViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mHotCampusActivityNetworkImageViewList.get(position));
    }

}
