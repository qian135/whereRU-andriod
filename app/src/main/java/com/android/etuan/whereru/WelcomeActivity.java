package com.android.etuan.whereru;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity
        implements ViewPager.OnPageChangeListener {

    private ViewPager mWelcomePageViewPager;
    private WelcomePageViewPagerAdapter mWelcomePageWelcomePageViewPagerAdapter;
    private List<View> mWelcomePageViews;

    // 底部小点图片
    private ImageView[] mWelcomePagePoints;

    //记录当前选中位置
    private int mWelcomePageCurrentPageIndex;

    //改为在WelcomePageViewPagerAdapter中进行设置，直接在这里设置无法进行监听
    //开始使用Where R U按键
//    private Button mStartUseWhereRUButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page_main_layout);

        // 初始化页面
        initWelcomePageViews();

        // 初始化底部小点
        initWelcomePagePointsLinearLayout();



    }

    private void initWelcomePageViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        mWelcomePageViews = new ArrayList<>();
        // 初始化欢迎图片列表
        mWelcomePageViews.add(inflater.inflate(R.layout.welcome_page_one, null));
        mWelcomePageViews.add(inflater.inflate(R.layout.welcome_page_three, null));
        mWelcomePageViews.add(inflater.inflate(R.layout.welcome_page_two, null));
        mWelcomePageViews.add(inflater.inflate(R.layout.welcome_page_four, null));

        // 初始化欢迎界面ViewPagerAdapter
        mWelcomePageWelcomePageViewPagerAdapter = new WelcomePageViewPagerAdapter(mWelcomePageViews, this);

        mWelcomePageViewPager = (ViewPager) findViewById(R.id.viewpager);
        mWelcomePageViewPager.setAdapter(mWelcomePageWelcomePageViewPagerAdapter);

        // 绑定回调
        mWelcomePageViewPager.setOnPageChangeListener(this);

    }

    private void initWelcomePagePointsLinearLayout() {
        LinearLayout welcomePagePointLinearLayout = (LinearLayout)
                findViewById(R.id.welcome_page_point_linear_layout);

        mWelcomePagePoints = new ImageView[mWelcomePageViews.size()];

        // 循环取得小点图片
        for (int i = 0; i < mWelcomePageViews.size(); i++) {
            mWelcomePagePoints[i] = (ImageView) welcomePagePointLinearLayout.getChildAt(i);
        }

        mWelcomePageCurrentPageIndex = 0;
        //初始化第一个点为选中状态
        mWelcomePagePoints[mWelcomePageCurrentPageIndex]
                .setImageResource(R.drawable.welcome_page_point_icon_selected);

    }

    private void setWelcomePageCurrentPoint(int position) {
        if (position < 0 || position > mWelcomePageViews.size() - 1
                || mWelcomePageCurrentPageIndex == position) {
            return;
        }

        // 循环将点设为非选中状态
        for (int i = 0; i < mWelcomePageViews.size(); i++) {
            mWelcomePagePoints[i].setImageResource(R.drawable.welcome_page_point_icon);
        }

        //将当前点设为选中状态
        mWelcomePagePoints[position].setImageResource(R.drawable.welcome_page_point_icon_selected);

        mWelcomePageCurrentPageIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setWelcomePageCurrentPoint(arg0);
    }

}