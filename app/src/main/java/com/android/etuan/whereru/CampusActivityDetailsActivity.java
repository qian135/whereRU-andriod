package com.android.etuan.whereru;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CampusActivityDetailsActivity extends Activity {


    /*该页面顶部滑动条相关代码*/

    private int mSlideBarDivideNumber = 3;//顶部滑动菜单划分的块数
    private MySlideBarHandler mMySlideBarHandler;//顶部滑动条的处理类
    private ImageView mSlideBar;//滑动条
    private int mSlideBarCurrentIndex = 0;


    /*实现 校园活动详情->活动介绍，活动详情，参与活动 三页面ViewPager切换的代码*/

    private View mCampusActivityIntroducePageView, mCampusActivityDetailPageView,
            mCampusActivityJoinPageView;

    private ViewPager mCampusActivityDetailsViewPager;//校园活动细节这个大类的主ViewPager
    //存放 校园活动详情->活动介绍，活动详情，参与活动 3个View
    private List<View> mCampusActivityDetailsPageViews;

    //校园活动详情->活动介绍，活动详情，参与活动 3个TextView
    private TextView mCampusActivityIntroduceTextView,
            mCampusActivityDetailTextView, mCampusActivityJoinTextView;


    /*校园活动详情->活动介绍，活动详情，参与活动 页面返回上一级的箭头*/

    private ImageView mCampusActivityDetailsUpNavigationIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_activity_details_home_page);

         /*实现 校园活动详情->活动介绍，活动详情，参与活动 三页面ViewPager切换的代码*/

        //初始化 校园活动详情->活动介绍，活动详情，参与活动 下面滑动条的动画
        initCampusActivityIntroduceDetailJoinSlideBarAnimation();
        //初始化 校园活动详情->活动介绍，活动详情，参与活动 标题栏的TextView
        initCampusActivityIntroduceDetailJoinTextView();
        //校园活动详情->活动介绍，活动详情，参与活动 ViewPager的初始化
        initCampusActivityIntroduceDetailJoinViewPager();

        //校园活动详情界面顶部的 返回箭头 的监听代码
        mCampusActivityDetailsUpNavigationIconImageView = (ImageView)
                findViewById(R.id.campus_activity_details_up_navigation_icon_image_view);
        mCampusActivityDetailsUpNavigationIconImageView.
                setOnClickListener(new View.OnClickListener() {
                    //跳转回校园活动界面
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

    }

    /*实现 校园活动详情->活动介绍，活动详情，参与活动 三页面ViewPager切换的代码*/

    /**
     * 初始化 校园活动详情 ：活动介绍，活动详情，参与活动 下面滑动条的动画
     */
    private void initCampusActivityIntroduceDetailJoinSlideBarAnimation() {
        mSlideBar = (ImageView)
                findViewById(R.id.campus_activity_details_home_page_cursor);
        mMySlideBarHandler = new MySlideBarHandler(this,mSlideBarDivideNumber,
                R.drawable.campus_activity_detail_page_slide_bar_icon,mSlideBar);
    }

    /**
     * 初始化 校园活动详情->活动介绍，活动详情，参与活动 标题栏的TextView
     * （这里主要是实例化，并设置监听）
     */
    private void initCampusActivityIntroduceDetailJoinTextView() {
        mCampusActivityIntroduceTextView = (TextView)
                findViewById(R.id.campus_activity_details_home_page_introduce_text_view);
        mCampusActivityDetailTextView = (TextView)
                findViewById(R.id.campus_activity_details_home_page_detail_text_view);
        mCampusActivityJoinTextView = (TextView)
                findViewById(R.id.campus_activity_details_home_page_join_text_view);

        mCampusActivityIntroduceTextView.setOnClickListener(new MyOnClickListener(0));
        mCampusActivityDetailTextView.setOnClickListener(new MyOnClickListener(1));
        mCampusActivityJoinTextView.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * 校园活动详情->活动介绍，活动详情，参与活动 ViewPager的初始化
     */
    private void initCampusActivityIntroduceDetailJoinViewPager() {

        mCampusActivityDetailsViewPager = (ViewPager)
                findViewById(R.id.campus_activity_details_home_pager_viewpager);

        mCampusActivityDetailsPageViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mCampusActivityIntroducePageView = mLayoutInflater
                .inflate(R.layout.campus_activity_details_introduce_page, null);
        mCampusActivityDetailPageView = mLayoutInflater
                .inflate(R.layout.campus_activity_details_detail_page, null);
        mCampusActivityJoinPageView = mLayoutInflater
                .inflate(R.layout.campus_activity_details_join_page, null);

        mCampusActivityDetailsPageViews.add(mCampusActivityIntroducePageView);
        mCampusActivityDetailsPageViews.add(mCampusActivityDetailPageView);
        mCampusActivityDetailsPageViews.add(mCampusActivityJoinPageView);

        mCampusActivityDetailsViewPager
                .setAdapter(new MyViewPagerAdapter(mCampusActivityDetailsPageViews));
        mCampusActivityDetailsViewPager.setCurrentItem(0);


        //主要是为 校园活动详情->活动介绍，活动详情，参与活动 下面那个滑动条服务的
        MyViewPagerOnPageChangeListener myViewPagerOnPageChangeListener =
                new MyViewPagerOnPageChangeListener(mMySlideBarHandler.getSlideBarMoveUnit(),
                        mSlideBarCurrentIndex, mSlideBar);
        mCampusActivityDetailsViewPager.setOnPageChangeListener
                (myViewPagerOnPageChangeListener);

    }

    /**
     * 使点击 校园活动详情->活动介绍，活动详情，参与活动 的TextView也能完成ViewPager的切换
     */

    public class MyOnClickListener implements View.OnClickListener {

        private int index;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mCampusActivityDetailsViewPager.setCurrentItem(index);
        }
    }

}
