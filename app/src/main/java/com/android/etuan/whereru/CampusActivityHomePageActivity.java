package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.etuan.whereru.adapter.CampusActivityListViewAdapter;
import com.android.etuan.whereru.adapter.HotCampusActivityViewPagerAdapter;
import com.android.etuan.whereru.utils.InterfaceConstant;
import com.android.etuan.whereru.utils.jsonjavabean.CampusActivity;
import com.android.etuan.whereru.utils.jsonjavabean.HotCampusActivity;
import com.android.etuan.whereru.utils.jsonjavabean.Person;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class CampusActivityHomePageActivity extends Activity
        implements ViewPager.OnPageChangeListener{

    /*实现热门活动栏代码*/

    private ViewPager mHotCampusActivityViewPager;
    //只将相应的NetworkImageView头尾增加了两个，HotCampusActivity未增加
    private List<HotCampusActivity> mHotCampusActivityList;
    private List<NetworkImageView> mHotCampusActivityNetworkImageViewList;
    private int mHotCampusActivityPageIndex;
    private ViewGroup mHotCampusActivityIndicatorPointLinearLayout;
    private HotCampusActivityHandler mHotCampusActivityHandler;
    private int mHotCampusActivitySwitchTime = 5000;

    /*实现校园活动列表项的代码*/

    private ListView mCampusActivityListView; //校园活动列表的ListView
    private List<CampusActivity> mCampusActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_activity_home_page);

        /*实现校园活动热门活动栏代码*/

        HotCampusActivityCycleViewPager();

        //校园活动列表项代码

        mCampusActivityList = new ArrayList<>();

        mCampusActivityListView = (ListView)
                findViewById(R.id.campus_activity_home_page_activity_list_view);

        JsonObjectRequest jsonObjectRequest = null;
        try {
            jsonObjectRequest = new JsonObjectRequest(
                    InterfaceConstant.SCHOOL_ACTIVITY_URL +
                            URLEncoder.encode(Person.getInstance().getmSchool(), "UTF-8"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            mCampusActivityList = new CampusActivity()
                                    .getInformationFromJson(jsonObject);
                            mCampusActivityListView.setAdapter(new CampusActivityListViewAdapter(
                                    CampusActivityHomePageActivity.this, mCampusActivityList));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MyVolleySingleton.getInstance(this.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);

        //为校园活动ListView每项设置监听器，完成页面跳转
        mCampusActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CampusActivityHomePageActivity.this,
                        CampusActivityDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

    /*实现热门校园活动栏代码*/

    private void HotCampusActivityCycleViewPager() {

        mHotCampusActivityViewPager = (ViewPager) findViewById(R.id.hot_campus_activity_view_pager);
        mHotCampusActivityIndicatorPointLinearLayout = (ViewGroup) findViewById(
                R.id.hot_campus_activity_indicator_point_linear_layout);

        JsonObjectRequest jsonObjectRequest = null;
        try {
            jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    InterfaceConstant.HOT_SCHOOL_ACTIVITY_URL +
                            URLEncoder.encode(Person.getInstance().getmSchool(), "UTF-8"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            mHotCampusActivityList = new ArrayList<>();
                            mHotCampusActivityList = new HotCampusActivity().getInformationFromJson(response);

                            mHotCampusActivityNetworkImageViewList = new ArrayList<>();

                            NetworkImageView networkImageView0 =
                                    new NetworkImageView(CampusActivityHomePageActivity.this);
                            networkImageView0.setScaleType(ImageView.ScaleType.FIT_XY);
                            networkImageView0.setDefaultImageResId(R.drawable.loading_image);
                            networkImageView0.setErrorImageResId(R.drawable.loading_image_error);
                            networkImageView0.setImageUrl(
                                    mHotCampusActivityList.get(4).getmImageURL(),
                                    MyVolleySingleton.getInstance(
                                            CampusActivityHomePageActivity.this.getApplicationContext())
                                            .getImageLoader());
                            mHotCampusActivityNetworkImageViewList.add(networkImageView0);

                            //热门活动5个
                            for (int i = 0; i < 5; i++) {
                                NetworkImageView networkImageView = new NetworkImageView(
                                        CampusActivityHomePageActivity.this);
                                networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                networkImageView.setDefaultImageResId(R.drawable.loading_image);
                                networkImageView.setErrorImageResId(R.drawable.loading_image_error);
                                networkImageView.setImageUrl(
                                        mHotCampusActivityList.get(i).getmImageURL(),
                                        MyVolleySingleton.getInstance(
                                                CampusActivityHomePageActivity.this.getApplicationContext())
                                                .getImageLoader());
                                mHotCampusActivityNetworkImageViewList.add(networkImageView);
                            }

                            NetworkImageView networkImageView6 = new NetworkImageView(
                                    CampusActivityHomePageActivity.this);
                            networkImageView6.setScaleType(ImageView.ScaleType.FIT_XY);
                            networkImageView6.setDefaultImageResId(R.drawable.loading_image);
                            networkImageView6.setErrorImageResId(R.drawable.loading_image_error);
                            networkImageView6.setImageUrl(
                                    mHotCampusActivityList.get(0).getmImageURL(),
                                    MyVolleySingleton.getInstance(
                                            CampusActivityHomePageActivity.this.getApplicationContext())
                                            .getImageLoader());
                            mHotCampusActivityNetworkImageViewList.add(networkImageView6);


                            mHotCampusActivityViewPager.setAdapter(
                                    new HotCampusActivityViewPagerAdapter(
                                            mHotCampusActivityNetworkImageViewList));
                            mHotCampusActivityViewPager.addOnPageChangeListener(
                                    CampusActivityHomePageActivity.this);
                            mHotCampusActivityViewPager.setCurrentItem(1);
                            mHotCampusActivityPageIndex = 1;

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MyVolleySingleton.getInstance(this.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);


        mHotCampusActivityIndicatorPointLinearLayout.getChildAt(0).setBackgroundResource(
                R.drawable.indicator_point_selected);

        mHotCampusActivityHandler = new HotCampusActivityHandler(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mHotCampusActivityHandler.postDelayed(this, mHotCampusActivitySwitchTime);
                Message message = new Message();
                message.what = 1;
                mHotCampusActivityHandler.sendMessage(message);
            }
        };
        mHotCampusActivityHandler.postDelayed(runnable, mHotCampusActivitySwitchTime);

    }

    //防内存泄露
    static class HotCampusActivityHandler extends Handler {

        WeakReference<CampusActivityHomePageActivity> mMainActivityWeakReference;

        HotCampusActivityHandler(CampusActivityHomePageActivity campusActivityHomePageActivity) {
            mMainActivityWeakReference = new WeakReference<>(campusActivityHomePageActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int pageIndex = ++mMainActivityWeakReference.get()
                            .mHotCampusActivityPageIndex;//使用者所想要滑向的页面
                    //只将相应的NetworkImageView头尾增加了两个，HotCampusActivity未增加
                    List<NetworkImageView> list = mMainActivityWeakReference.get()
                            .mHotCampusActivityNetworkImageViewList;
                    ViewPager viewPager = mMainActivityWeakReference.get()
                            .mHotCampusActivityViewPager;
                    ViewGroup indicatorPointLinearLayout = mMainActivityWeakReference.get()
                            .mHotCampusActivityIndicatorPointLinearLayout;
                    if (pageIndex == 0) {
                        pageIndex = list.size() - 2;
                    } else {
                        if (pageIndex == list.size() - 1) {
                            pageIndex = 1;
                        }
                    }
                    viewPager.setCurrentItem(pageIndex);
                    for (int i = 0; i < indicatorPointLinearLayout.getChildCount(); i++) {
                        indicatorPointLinearLayout.getChildAt(i).setBackgroundResource(
                                R.drawable.indicator_point_normal);
                    }
                    //知道了实现这ViewPager循环的原理，就知道为什么这里为什么要sPageIndex - 1了，
                    //因为ViewPager添加的View是7个，而点的View是5个
                    indicatorPointLinearLayout.getChildAt(pageIndex - 1).setBackgroundResource(
                            R.drawable.indicator_point_selected);
                    break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position == 0) {
            position = mHotCampusActivityNetworkImageViewList.size() - 2;
        } else {
            if (position == mHotCampusActivityNetworkImageViewList.size() - 1) {
                position = 1;
            }
        }
        mHotCampusActivityViewPager.setCurrentItem(position);

        mHotCampusActivityPageIndex = position;//记录当前滑到的页面

        for (int j = 0; j < mHotCampusActivityIndicatorPointLinearLayout.getChildCount(); j++) {
            mHotCampusActivityIndicatorPointLinearLayout.getChildAt(j).setBackgroundResource(
                    R.drawable.indicator_point_normal);
        }
        //知道了实现这ViewPager循环的原理，就知道为什么这里为什么要i - 1了，
        //因为ViewPager添加的View是7个，而点的View是5个
        mHotCampusActivityIndicatorPointLinearLayout.getChildAt(position - 1).setBackgroundResource(
                R.drawable.indicator_point_selected);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
