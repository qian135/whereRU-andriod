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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CampusCompetitionDetailsActivity extends Activity {

    /*定义的某些字符串常量*/

    //
    private static final String VIEWPAGER_JUMP_FLAG =
            "CampusCompetitionDetailsActivity";


    /*实现 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4页面ViewPager切换的代码*/

    private View mCampusCompetitionNoticePageView, mCampusCompetitionDetailPageView,
            mCampusCompetitionTeamPageView, mCampusCompetitionJoinPageView;

    private ViewPager mCampusCompetitionDetailsViewPager;//校园活动细节这个大类的主ViewPager
    //存放 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4个View
    private List<View> mCampusCompetitionNoticeDetailTeamJoinViews;
    private ImageView mCampusCompetitionNoticeDetailTeamJoinSlideBar;//滑动条
    //校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4个TextView
    private TextView mCampusCompetitionNoticeTextView, mCampusCompetitionDetailTextView,
            mCampusCompetitionTeamTextView, mCampusCompetitionJoinTextView;
    private int mCampusCompetitionNoticeDetailTeamJoinOffset = 0;//偏移量
    private int mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex = 0;//对应不同的TextView
    private int mCampusCompetitionNoticeDetailTeamJoinSlideBarWidth;//Image的宽度


    /*校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 页面返回上一级的箭头*/

    private ImageView mCampusCompetitionDetailsUpNavigationIconImageView;

    /*实现 校园竞赛->竞赛详情->竞赛通知 列表项的代码*/

    private ListView mCampusCompetitionDetailsNoticeListView; //校园活动列表的ListView

    /*实现 校园竞赛->竞赛详情->参赛团队 列表项的代码*/

    private ListView mCampusCompetitionDetailsTeamListView; //校园竞赛列表的ListView

    /*实现 校园竞赛->竞赛详情->参与竞赛 列表项的代码*/

    private ListView mCampusCompetitionDetailsJoinListView; //校园团队列表的ListView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_competition_details_home_page);

         /*实现 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4页面ViewPager切换的代码*/

        //初始化 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 下面滑动条的动画
        initCampusCompetitionNoticeDetailTeamJoinSlideBarAnimation();
        //初始化 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 标题栏的TextView
        initCampusCompetitionNoticeDetailTeamJoinTextView();
        //校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 ViewPager的初始化
        initCampusCompetitionNoticeDetailTeamJoinViewPager();

        mCampusCompetitionDetailsUpNavigationIconImageView = (ImageView)
                findViewById(R.id.campus_competition_details_up_navigation_icon_image_view);
        mCampusCompetitionDetailsUpNavigationIconImageView.
                setOnClickListener(new View.OnClickListener() {
                    //跳转回校园竞赛界面
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new
//                                Intent(CampusCompetitionDetailsActivity.this, MainActivity.class);
//                        intent.putExtra(VIEWPAGER_JUMP_FLAG, 1);
//                        System.out.println("mCampusCompetitionDetailsUpNavigationIconImageView");
//                        startActivity(intent);

                        finish();
                    }
                });

        /*实现 校园竞赛详情 ：竞赛通知，参赛团队，参与竞赛 3个列表项的代码
        (注：其中 竞赛详情 无列表项)*/

        //模仿列表项数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("item" + i);
        }

        //校园竞赛->竞赛详情->竞赛通知 列表项代码

        //注意因为在initCampusCompetitionNoticeDetailTeamJoinViewPager已经将三个ViewPager的
        // 布局文件都实例化过了，这里不需要再实例化了
        mCampusCompetitionDetailsNoticeListView = (ListView) mCampusCompetitionNoticeDetailTeamJoinViews
                .get(0).findViewById(R.id.campus_competition_details_notice_page_list_view);
        MyListViewAdapter myCampusActivityListViewAdapter = new MyListViewAdapter(list, this, 3);
        mCampusCompetitionDetailsNoticeListView.setAdapter(myCampusActivityListViewAdapter);
        //为 校园竞赛->竞赛详情->竞赛通知 ListView每项设置监听器，完成页面跳转
//        mCampusCompetitionDetailsNoticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this,CampusActivityDetailsActivity.class);
//                startActivity(intent);
//            }
//        });

        //校园竞赛->竞赛详情->参赛团队 列表项代码
        mCampusCompetitionDetailsTeamListView = (ListView) mCampusCompetitionNoticeDetailTeamJoinViews
                .get(2).findViewById(R.id.campus_competition_details_team_page_list_view);
        MyListViewAdapter myCampusCompetitionListViewAdapter = new MyListViewAdapter(list, this, 4);
        mCampusCompetitionDetailsTeamListView.setAdapter(myCampusCompetitionListViewAdapter);
        //为 校园竞赛->竞赛详情->参赛团队 ListView每项设置监听器，完成页面跳转
//        mCampusCompetitionDetailsTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new
//                        Intent(MainActivity.this, CampusCompetitionDetailsActivity.class);
//                startActivity(intent);
//            }
//        });

        //校园竞赛->竞赛详情->参与竞赛 列表项代码
        mCampusCompetitionDetailsJoinListView = (ListView) mCampusCompetitionNoticeDetailTeamJoinViews
                .get(3).findViewById(R.id.campus_competition_details_join_page_list_view);
        MyListViewAdapter myCampusTeamListViewAdapter = new MyListViewAdapter(list, this, 5);
        mCampusCompetitionDetailsJoinListView.setAdapter(myCampusTeamListViewAdapter);

    }

    /*实现 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 4页面ViewPager切换的代码*/

    /**
     * 初始化 校园校园活动详情 ：活动介绍，活动详情，参与活动 下面滑动条的动画
     */
    private void initCampusCompetitionNoticeDetailTeamJoinSlideBarAnimation() {
        mCampusCompetitionNoticeDetailTeamJoinSlideBar = (ImageView)
                findViewById(R.id.campus_competition_details_home_page_cursor);
        //获取 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 下面滑动条的宽度
        mCampusCompetitionNoticeDetailTeamJoinSlideBarWidth = BitmapFactory.
                decodeResource(getResources(),
                        R.drawable.campus_activity_competition_team_slide_bar).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        mCampusCompetitionNoticeDetailTeamJoinOffset =
                (screenW / 4 - mCampusCompetitionNoticeDetailTeamJoinSlideBarWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(mCampusCompetitionNoticeDetailTeamJoinOffset, 0);
        mCampusCompetitionNoticeDetailTeamJoinSlideBar.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 初始化 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 标题栏的TextView
     * （这里主要是实例化，并设置监听）
     */
    private void initCampusCompetitionNoticeDetailTeamJoinTextView() {
        mCampusCompetitionNoticeTextView = (TextView)
                findViewById(R.id.campus_competition_details_home_page_notice_text_view);
        mCampusCompetitionDetailTextView = (TextView)
                findViewById(R.id.campus_competition_details_home_page_detail_text_view);
        mCampusCompetitionTeamTextView = (TextView)
                findViewById(R.id.campus_competition_details_home_page_team_text_view);
        mCampusCompetitionJoinTextView = (TextView)
                findViewById(R.id.campus_competition_details_home_page_join_text_view);

        mCampusCompetitionNoticeTextView.setOnClickListener(new MyOnClickListener(0));
        mCampusCompetitionDetailTextView.setOnClickListener(new MyOnClickListener(1));
        mCampusCompetitionTeamTextView.setOnClickListener(new MyOnClickListener(2));
        mCampusCompetitionJoinTextView.setOnClickListener(new MyOnClickListener(3));
    }

    /**
     * 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 ViewPager的初始化
     */
    private void initCampusCompetitionNoticeDetailTeamJoinViewPager() {

        mCampusCompetitionDetailsViewPager = (ViewPager)
                findViewById(R.id.campus_competition_details_home_page_viewpager);

        mCampusCompetitionNoticeDetailTeamJoinViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mCampusCompetitionNoticePageView = mLayoutInflater
                .inflate(R.layout.campus_competition_details_notice_page, null);
        mCampusCompetitionDetailPageView = mLayoutInflater
                .inflate(R.layout.campus_competition_details_detail_page, null);
        mCampusCompetitionTeamPageView = mLayoutInflater
                .inflate(R.layout.campus_competition_details_team_page, null);
        mCampusCompetitionJoinPageView = mLayoutInflater
                .inflate(R.layout.campus_competition_details_join_page, null);

        mCampusCompetitionNoticeDetailTeamJoinViews.add(mCampusCompetitionNoticePageView);
        mCampusCompetitionNoticeDetailTeamJoinViews.add(mCampusCompetitionDetailPageView);
        mCampusCompetitionNoticeDetailTeamJoinViews.add(mCampusCompetitionTeamPageView);
        mCampusCompetitionNoticeDetailTeamJoinViews.add(mCampusCompetitionJoinPageView);

        mCampusCompetitionDetailsViewPager
                .setAdapter(new MyViewPagerAdapter(mCampusCompetitionNoticeDetailTeamJoinViews));
//        mCampusCompetitionDetailsViewPager.setCurrentItem(0);
        //主要是为 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 下面那个滑动条服务的
        mCampusCompetitionDetailsViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 为 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 ViewPager设置滑动的监听器
     */

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = mCampusCompetitionNoticeDetailTeamJoinOffset * 2
                + mCampusCompetitionNoticeDetailTeamJoinSlideBarWidth;
        int two = one * 2;
        int three = one * 3;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 1) {
/**
 * 设置在X轴方向的动画。
 * 第一个参数fromXDelta。Image的左上角为(0,0),该参数表示在X轴方向的动画开始位置距离Image左上角的距离
 * 第二个参数toXDelta。该参数表示在X轴方向动画的结束位置距离Image左上角的距离。
 * 第三和第四参数同上
 * 位置的开始点是Image的原始位置，而不是setFillAfter(true)之后的位置
 */
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 3) {
                        animation = new TranslateAnimation(three, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 0) {
                        animation = new TranslateAnimation(0, one, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 3) {
                        animation = new TranslateAnimation(three, one, 0, 0);
                    }
                    break;
                case 2:
                    if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 0) {
                        animation = new TranslateAnimation(0, two, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 3) {
                        animation = new TranslateAnimation(three, two, 0, 0);
                    }
                    break;
                case 3:
                    if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 0) {
                        animation = new TranslateAnimation(0, three, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 1) {
                        animation = new TranslateAnimation(one, three, 0, 0);
                    } else if (mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex == 2) {
                        animation = new TranslateAnimation(two,three, 0, 0);
                    }
                    break;
            }
            mCampusCompetitionNoticeDetailTeamJoinTextViewCurrentIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(500);
            mCampusCompetitionNoticeDetailTeamJoinSlideBar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 使点击 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛
     * 的TextView也能完成ViewPager的切换
     */

    public class MyOnClickListener implements View.OnClickListener {

        private int index;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mCampusCompetitionDetailsViewPager.setCurrentItem(index);
        }

    }

}
