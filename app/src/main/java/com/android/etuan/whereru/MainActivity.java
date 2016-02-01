package com.android.etuan.whereru;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    //帮助实现在ViewPager上实现Activity的切换
    private LocalActivityManager mLocalActivityManager;

    //顶部搜索图标
    private ImageView mTopSearch;

    /*实现 圈子,校园和我 三者底部导航栏的代码*/

    //这是圈子，校园，我 那个层面的ViewPaper，通过自定义使之不允许滑动
    private CircleCampusMeViewPager mCircleCampusMeViewPager;
    private PagerAdapter mCircleCampusMeViewPagerAdapter;
    private List<View> mCircleCampusMeViews;//存放 圈子，校园，我 3个View
    //圈子，校园，我 导航栏的布局
    private LinearLayout mCircleNavigationBar;
    private LinearLayout mCampusNavigationBar;
    private LinearLayout mMeNavigationBar;
    //圈子，校园，我 上放的3个ImageButton
    private ImageButton mCircleNavigationBarImageButton;
    private ImageButton mCampusNavigationBarImageButton;
    private ImageButton mMeNavigationBarImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //帮助实现在ViewPager上实现Activity的切换
        mLocalActivityManager = new LocalActivityManager(this, true);
        mLocalActivityManager.dispatchCreate(savedInstanceState);

        //设置顶部搜索图标的监听
        mTopSearch = (ImageView) findViewById(R.id.top_search);
        mTopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchPage.class);
                startActivity(intent);
            }
        });

        /*实现 圈子,校园,我 三者底部导航栏的代码*/

        //圈子,校园,我 底部导航栏的初始化
        circleCampusMeNavigationBarInit();
        //初始化 圈子，校园，我 3个主页的ViewPage
        initCircleCampusMeHomePagerViewPager();

    }

    /*实现圈子,校园和我三者底部导航栏的代码*/

    /**
     * 初始化设置
     */
    private void circleCampusMeNavigationBarInit() {
        //获取 圈子，校园，我 底部导航栏的LinearLayout
        mCircleNavigationBar = (LinearLayout)
                findViewById(R.id.circle_navigation_bar_linear_layout);
        mCampusNavigationBar = (LinearLayout)
                findViewById(R.id.campus_navigation_bar_linear_layout);
        mMeNavigationBar = (LinearLayout)
                findViewById(R.id.me_navigation_bar_linear_layout);

        //为 圈子，校园，我 底部导航栏的LinearLayout设置监听
        mCircleNavigationBar.setOnClickListener(this);
        mCampusNavigationBar.setOnClickListener(this);
        mMeNavigationBar.setOnClickListener(this);

        //获取 圈子，校园，我 导航栏的ImageButton
        mCircleNavigationBarImageButton = (ImageButton)
                findViewById(R.id.campus_navigation_bar_image_button);
        mCampusNavigationBarImageButton = (ImageButton)
                findViewById(R.id.circle_navigation_bar_image_button);
        mMeNavigationBarImageButton = (ImageButton)
                findViewById(R.id.me_navigation_bar_image_button);
    }

    /**
     * 初始化 圈子，校园，我 3个主页的ViewPage
     */

    private void initCircleCampusMeHomePagerViewPager() {

        //获取圈子，校园，我 那个层面的ViewPaper（其通过自定义使之不允许滑动）
        mCircleCampusMeViewPager = (CircleCampusMeViewPager)
                findViewById(R.id.circle_campus_me_viewpager);

        //将圈子，校园，我 3个主页的View添加到mCircleCampusMeViews
        mCircleCampusMeViews = new ArrayList<>();

        Intent intent0 = new Intent(this, CircleHomePageActivity.class);
        mCircleCampusMeViews.add(getView("CircleHomePageActivity", intent0));

        Intent intent1 = new Intent(this, CampusHomePageActivity.class);
        mCircleCampusMeViews.add(getView("CampusHomePageActivity", intent1));

        //跳转到个人中心首页
        Intent intent2 = new Intent(this, MeHomePageActivity.class);
        mCircleCampusMeViews.add(getView("MeHomePageActivity", intent2));


        mCircleCampusMeViewPagerAdapter = new MyViewPagerAdapter(mCircleCampusMeViews);
        mCircleCampusMeViewPager.setAdapter(mCircleCampusMeViewPagerAdapter);

        //设置首先显示的是校园界面
        mCircleCampusMeViewPager.setCurrentItem(1);

    }

    //帮助实现在ViewPager上实现Activity的切换
    private View getView(String id, Intent intent) {
        return mLocalActivityManager.startActivity(id, intent).getDecorView();
    }

    /**
     * 监听的是底部菜单的触摸事件，根据触摸的控件，改变自身的亮度、改变ViewPage显示的内容
     */
    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.circle_navigation_bar_linear_layout:
                mCircleCampusMeViewPager.setCurrentItem(0);
                changeCircleCampusMeNavigationBarImageButtonToNormal();
                mCampusNavigationBarImageButton
                        .setImageResource(R.drawable.circle_navigation_bar_image_button_selected);
                break;
            case R.id.campus_navigation_bar_linear_layout:
                mCircleCampusMeViewPager.setCurrentItem(1);
                changeCircleCampusMeNavigationBarImageButtonToNormal();
                mCircleNavigationBarImageButton
                        .setImageResource(R.drawable.campus_navigation_bar_image_button_selected);
                break;
            case R.id.me_navigation_bar_linear_layout:
                mCircleCampusMeViewPager.setCurrentItem(2);
                //先将其全部设置为未选中状态，然后将点击的设为选中状态
                changeCircleCampusMeNavigationBarImageButtonToNormal();
                mMeNavigationBarImageButton
                        .setImageResource(R.drawable.me_navigation_bar_image_button_selected);
                break;
            default:
                break;
        }
    }

    /**
     * 把 圈子，校园，我 的导航栏ImageButton图片置为未选中状态
     */
    private void changeCircleCampusMeNavigationBarImageButtonToNormal() {
        mCircleNavigationBarImageButton
                .setImageResource(R.drawable.campus_navigation_bar_image_button_normal);
        mCampusNavigationBarImageButton
                .setImageResource(R.drawable.circle_navigation_bar_image_button_normal);
        mMeNavigationBarImageButton
                .setImageResource(R.drawable.me_navigation_bar_image_button_normal);
    }

}
