package com.android.etuan.whereru;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 1/28/2016.
 */
public class CampusTeamDetailsActivity extends Activity {


    /*实现 校园团队->团队介绍->团队简介 ,部门介绍，团队相册，团队活动，申请加入
    5页面ViewPager切换的代码*/

    private View mCampusTeamIntroducePageView, mCampusTeamDepartmentPageView,
            mCampusTeamAlbumPageView,mCampusTeamActivityPageView,mCampusTeamJoinPageView;

    private ViewPager mCampusTeamDetailsViewPager;//校园活动细节这个大类的主ViewPager
    //存放 校园团队->团队介绍->团队简介 ,部门介绍，团队相册，团队活动，申请加入 5个View
    private List<View> mCampusTeamIntroduceDepartmentAlbumActivityJoinViews;
//    private ImageView mCampusActivityIntroduceDetailJoinSlideBar;//滑动条
    //校园团队->团队介绍->团队简介 ,部门介绍，团队相册，团队活动，申请加入 3个TextView
    private TextView mCampusTeamIntroduceTextView, mCampusTeamDepartmentTextView,
        mCampusTeamAlbumTextView,mCampusTeamActivityTextView,mCampusTeamJoinTextView;;
//    private int mCampusActivityIntroduceDetailJoinOffset = 0;//偏移量
    private int mCampusActivityIntroduceDetailJoinTextViewCurrentIndex = 0;//对应不同的TextView
//    private int mCampusActivityIntroduceDetailJoinSlideBarWidth;//Image的宽度


    /*校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     页面返回上一级的箭头*/

    private ImageView mCampusTeamDetailsUpNavigationIconImageView;


    //实现 校园团队->团队介绍->团队相册 列表项的代码

    private ListView mCampusTeamDetailsAlbumListView;

    //实现 校园团队->团队介绍->团队活动 列表项的代码

    private ListView mCampusTeamDetailsActivityListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_team_details_home_page);

         /*实现 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
         5页面ViewPager切换的代码*/

        //初始化 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // 标题栏的TextView
        initCampusTeamIntroduceDepartmentAlbumActivityJoinTextView();
        //校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // ViewPager的初始化
        initCampusTeamIntroduceDepartmentAlbumActivityJoinViewPager();

        //校园活动详情界面顶部的 返回箭头 的监听代码
        mCampusTeamDetailsUpNavigationIconImageView = (ImageView)
                findViewById(R.id.campus_team_details_up_navigation_icon_image_view);
        mCampusTeamDetailsUpNavigationIconImageView.
                setOnClickListener(new View.OnClickListener() {
                    //跳转回校园活动界面
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });


        /*实现 校园团队->团队介绍-> 团队相册，团队活动 2个列表项的代码
        (注：其中 团队简介 ,部门介绍，申请加入 3个无列表项)*/

        //模仿列表项数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("item" + i);
        }

        //校园团队->团队介绍->团队相册 列表项代码
        //注意因为在initCampusTeamIntroduceDepartmentAlbumActivityJoinViewPager已经将三个ViewPager的
        // 布局文件都实例化过了，这里不需要再实例化了
        mCampusTeamDetailsAlbumListView = (ListView) mCampusTeamIntroduceDepartmentAlbumActivityJoinViews
                .get(2).findViewById(R.id.campus_team_details_album_page_list_view);
        MyListViewAdapter myCampusActivityListViewAdapter = new MyListViewAdapter(list, this, 6);
        mCampusTeamDetailsAlbumListView.setAdapter(myCampusActivityListViewAdapter);

        //校园团队->团队介绍->团队活动 列表项代码
        mCampusTeamDetailsActivityListView = (ListView) mCampusTeamIntroduceDepartmentAlbumActivityJoinViews
                .get(3).findViewById(R.id.campus_team_details_activity_page_list_view);
        MyListViewAdapter myCampusCompetitionListViewAdapter = new MyListViewAdapter(list, this, 7);
        mCampusTeamDetailsActivityListView.setAdapter(myCampusCompetitionListViewAdapter);

    }

    /*实现 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
    5页面ViewPager切换的代码*/

    /**
     * 初始化 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * 标题栏的TextView
     * （这里主要是实例化，并设置监听）
     */
    private void initCampusTeamIntroduceDepartmentAlbumActivityJoinTextView() {
        mCampusTeamIntroduceTextView = (TextView)
                findViewById(R.id.campus_team_details_home_page_introduce_text_view);
        mCampusTeamDepartmentTextView = (TextView)
                findViewById(R.id.campus_team_details_home_page_department_text_view);
        mCampusTeamAlbumTextView = (TextView)
                findViewById(R.id.campus_team_details_home_page_album_text_view);
        mCampusTeamActivityTextView = (TextView)
                findViewById(R.id.campus_team_details_home_page_activity_text_view);
        mCampusTeamJoinTextView = (TextView)
                findViewById(R.id.campus_team_details_home_page_join_text_view);

        mCampusTeamIntroduceTextView.setOnClickListener(new MyOnClickListener(0));
        mCampusTeamDepartmentTextView.setOnClickListener(new MyOnClickListener(1));
        mCampusTeamAlbumTextView.setOnClickListener(new MyOnClickListener(2));
        mCampusTeamActivityTextView.setOnClickListener(new MyOnClickListener(3));
        mCampusTeamJoinTextView.setOnClickListener(new MyOnClickListener(4));
    }

    /**
     * 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * ViewPager的初始化
     */
    private void initCampusTeamIntroduceDepartmentAlbumActivityJoinViewPager() {

        mCampusTeamDetailsViewPager = (ViewPager)
                findViewById(R.id.campus_team_details_home_page_viewpager);

        mCampusTeamIntroduceDepartmentAlbumActivityJoinViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mCampusTeamIntroducePageView = mLayoutInflater
                .inflate(R.layout.campus_team_details_introduce_page, null);
        mCampusTeamDepartmentPageView = mLayoutInflater
                .inflate(R.layout.campus_team_details_department_page, null);
        mCampusTeamAlbumPageView = mLayoutInflater
                .inflate(R.layout.campus_team_details_album_page, null);
        mCampusTeamActivityPageView = mLayoutInflater
                .inflate(R.layout.campus_team_details_activity_page, null);
        mCampusTeamJoinPageView = mLayoutInflater
                .inflate(R.layout.campus_team_details_join_page, null);

        mCampusTeamIntroduceDepartmentAlbumActivityJoinViews.add(mCampusTeamIntroducePageView);
        mCampusTeamIntroduceDepartmentAlbumActivityJoinViews.add(mCampusTeamDepartmentPageView);
        mCampusTeamIntroduceDepartmentAlbumActivityJoinViews.add(mCampusTeamAlbumPageView);
        mCampusTeamIntroduceDepartmentAlbumActivityJoinViews.add(mCampusTeamActivityPageView);
        mCampusTeamIntroduceDepartmentAlbumActivityJoinViews.add(mCampusTeamJoinPageView);

        mCampusTeamDetailsViewPager
                .setAdapter(new MyViewPagerAdapter(mCampusTeamIntroduceDepartmentAlbumActivityJoinViews));
        mCampusTeamDetailsViewPager.setCurrentItem(0);
        //主要是为 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // 这几个TextView的文字变色服务的
        mCampusTeamDetailsViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 为 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * 的TextView设置选中和未选中时的颜色
     */

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            //先全部设为黑，然后选中哪个，将哪个设为红色

            mCampusTeamIntroduceTextView.setTextColor(Color.BLACK);
            mCampusTeamDepartmentTextView.setTextColor(Color.BLACK);
            mCampusTeamAlbumTextView.setTextColor(Color.BLACK);
            mCampusTeamActivityTextView.setTextColor(Color.BLACK);
            mCampusTeamJoinTextView.setTextColor(Color.BLACK);

            switch (arg0) {
                case 0:
                    mCampusTeamIntroduceTextView.setTextColor(Color.RED);
                    break;
                case 1:
                    mCampusTeamDepartmentTextView.setTextColor(Color.RED);
                    break;
                case 2:
                    mCampusTeamAlbumTextView.setTextColor(Color.RED);
                    break;
                case 3:
                    mCampusTeamActivityTextView.setTextColor(Color.RED);
                    break;
                case 4:
                    mCampusTeamJoinTextView.setTextColor(Color.RED);
                    break;
            }

            //记录当前选中哪个页面
            mCampusActivityIntroduceDetailJoinTextViewCurrentIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

    }

    /**
     * 使点击 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * 的TextView也能完成ViewPager的切换
     */

    public class MyOnClickListener implements View.OnClickListener {

        private int index;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mCampusTeamDetailsViewPager.setCurrentItem(index);
        }
    }

}
