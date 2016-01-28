package com.android.etuan.whereru;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CampusActivityDetailsActivity extends Activity {

    /*定义的某些字符串常量*/

    //
    private static final String VIEWPAGER_JUMP_FLAG =
            "CampusActivityDetailsActivity";


    /*实现 校园活动详情 ：活动介绍，活动详情，参与活动 三页面ViewPager切换的代码*/

    private View mCampusActivityIntroducePageView, mCampusActivityDetailPageView,
            mCampusActivityJoinPageView;

    private ViewPager mCampusActivityDetailsViewPager;//校园活动细节这个大类的主ViewPager
    //存放 校园活动详情 ：活动介绍，活动详情，参与活动 3个View
    private List<View> mCampusActivityIntroduceDetailJoinViews;
    private ImageView mCampusActivityIntroduceDetailJoinSlideBar;//滑动条
    //校园活动详情 ：活动介绍，活动详情，参与活动 3个TextView
    private TextView mCampusActivityIntroduceTextView,
            mCampusActivityDetailTextView, mCampusActivityJoinTextView;
    private int mCampusActivityIntroduceDetailJoinOffset = 0;//偏移量
    private int mCampusActivityIntroduceDetailJoinTextViewCurrentIndex = 0;//对应不同的TextView
    private int mCampusActivityIntroduceDetailJoinSlideBarWidth;//Image的宽度


    /*校园活动详情 ：活动介绍，活动详情，参与活动 页面返回上一级的箭头*/

    private ImageView mCampusActivityDetailsUpNavigationIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_activity_details_home_page);

         /*实现 校园活动详情 ：活动介绍，活动详情，参与活动 三页面ViewPager切换的代码*/

        //初始化 校园活动详情 ：活动介绍，活动详情，参与活动 下面滑动条的动画
        initCampusActivityIntroduceDetailJoinSlideBarAnimation();
        //初始化 校园活动详情 ：活动介绍，活动详情，参与活动 标题栏的TextView
        initCampusActivityIntroduceDetailJoinTextView();
        //校园活动详情 ：活动介绍，活动详情，参与活动 ViewPager的初始化
        initCampusActivityIntroduceDetailJoinViewPager();

        //校园活动详情界面顶部的 返回箭头 的监听代码
        mCampusActivityDetailsUpNavigationIconImageView = (ImageView)
                findViewById(R.id.campus_activity_details_up_navigation_icon_image_view);
        mCampusActivityDetailsUpNavigationIconImageView.
                setOnClickListener(new View.OnClickListener() {
                    //跳转回校园活动界面
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new
//                                Intent(CampusActivityDetailsActivity.this,MainActivity.class);
//                        intent.putExtra(VIEWPAGER_JUMP_FLAG,0);
//                        startActivity(intent);

                        finish();
                    }
                });

    }

    /*实现 校园活动详情 ：活动介绍，活动详情，参与活动 三页面ViewPager切换的代码*/

    /**
     * 初始化 校园活动详情 ：活动介绍，活动详情，参与活动 下面滑动条的动画
     */
    private void initCampusActivityIntroduceDetailJoinSlideBarAnimation() {
        mCampusActivityIntroduceDetailJoinSlideBar = (ImageView)
                findViewById(R.id.campus_activity_details_home_page_cursor);
        //获取 校园活动详情 ：活动介绍，活动详情，参与活动 下面滑动条的宽度
        mCampusActivityIntroduceDetailJoinSlideBarWidth = BitmapFactory.
                decodeResource(getResources(),
                        R.drawable.campus_activity_competition_team_slide_bar).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        mCampusActivityIntroduceDetailJoinOffset =
                (screenW / 3 - mCampusActivityIntroduceDetailJoinSlideBarWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(mCampusActivityIntroduceDetailJoinOffset, 0);
        mCampusActivityIntroduceDetailJoinSlideBar.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 初始化 校园活动详情 ：活动介绍，活动详情，参与活动 标题栏的TextView
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
     * 校园活动详情 ：活动介绍，活动详情，参与活动 ViewPager的初始化
     */
    private void initCampusActivityIntroduceDetailJoinViewPager() {

        mCampusActivityDetailsViewPager = (ViewPager)
                findViewById(R.id.campus_activity_details_home_pager_viewpager);

        mCampusActivityIntroduceDetailJoinViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mCampusActivityIntroducePageView = mLayoutInflater
                .inflate(R.layout.campus_activity_details_introduce_page, null);
        mCampusActivityDetailPageView = mLayoutInflater
                .inflate(R.layout.campus_activity_details_detail_page, null);
        mCampusActivityJoinPageView = mLayoutInflater
                .inflate(R.layout.campus_activity_details_join_page, null);

        mCampusActivityIntroduceDetailJoinViews.add(mCampusActivityIntroducePageView);
        mCampusActivityIntroduceDetailJoinViews.add(mCampusActivityDetailPageView);
        mCampusActivityIntroduceDetailJoinViews.add(mCampusActivityJoinPageView);

        mCampusActivityDetailsViewPager
                .setAdapter(new MyViewPagerAdapter(mCampusActivityIntroduceDetailJoinViews));
        mCampusActivityDetailsViewPager.setCurrentItem(0);
        //主要是为 校园活动详情 ：活动介绍，活动详情，参与活动 下面那个滑动条服务的
        mCampusActivityDetailsViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 为 校园活动详情 ：活动介绍，活动详情，参与活动 ViewPager设置滑动的监听器
     */

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = mCampusActivityIntroduceDetailJoinOffset * 2
                + mCampusActivityIntroduceDetailJoinSlideBarWidth;
        int two = one * 2;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (mCampusActivityIntroduceDetailJoinTextViewCurrentIndex == 1) {
/**
 * 设置在X轴方向的动画。
 * 第一个参数fromXDelta。Image的左上角为(0,0),该参数表示在X轴方向的动画开始位置距离Image左上角的距离
 * 第二个参数toXDelta。该参数表示在X轴方向动画的结束位置距离Image左上角的距离。
 * 第三和第四参数同上
 * 位置的开始点是Image的原始位置，而不是setFillAfter(true)之后的位置
 */
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (mCampusActivityIntroduceDetailJoinTextViewCurrentIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (mCampusActivityIntroduceDetailJoinTextViewCurrentIndex == 0) {
                        animation = new TranslateAnimation(0, one, 0, 0);
                    } else if (mCampusActivityIntroduceDetailJoinTextViewCurrentIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (mCampusActivityIntroduceDetailJoinTextViewCurrentIndex == 0) {
                        animation = new TranslateAnimation(0, two, 0, 0);
                    } else if (mCampusActivityIntroduceDetailJoinTextViewCurrentIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            mCampusActivityIntroduceDetailJoinTextViewCurrentIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(500);
            mCampusActivityIntroduceDetailJoinSlideBar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 使点击 校园活动详情 ：活动介绍，活动详情，参与活动 的TextView也能完成ViewPager的切换
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
