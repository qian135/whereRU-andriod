package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 1/31/2016.
 */
public class MeMyTeamPageActivity extends Activity {

    /*实现 个人中心->我的团队
    5页面ViewPager切换的代码*/

    private View mMeMyTeamMemberPageView,mMeMyTeamActivityPageView,
            mMeMyTeamCompetitionPageView,mMeMyTeamAlbumPageView,mMeMyTeamModificationPageView;

    private ViewPager mMeMyTeamViewPager;//个人中心->我的团队的ViewPager
    //存放 个人中心->我的团队 5个View
    private List<View> mMeMyTeamViews;
    //个人中心->我的团队 的5个TextView
    private TextView mMeMyTeamMemberTextView, mMeMyTeamActivityTextView,
            mMeMyTeamCompetitionTextView,mMeMyTeamAlbumTextView,mMeMyTeamModificationTextView;
    private int mMeMyTeamCurrentIndex = 0;//对应不同的TextView


    /*个人中心->我的团队
     页面返回上一级的箭头*/

    private ImageView mMeMyTeamUpNavigationIconImageView;


    //实现 个人中心->我的团队->团队成员 列表项的代码（有两个列表，一个加入的，一个是申请加入的）

    private ListView mMeMyTeamMemberListView,mMeMyTeamWantJoinListView;

    //实现 校园团队->团队介绍->团队活动 列表项的代码

    private ListView mMeMyTeamActivityListView;

    //实现 校园团队->团队介绍->团队相册 列表项的代码

    private ListView mMeMyTeamAlbumListView;

    //实现 校园团队->团队介绍->信息修改 部门信息修改列表项的代码

    private ListView mMeMyTeamDepartmentModificationListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_home_page_my_team_page);

         /*实现 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
         5页面ViewPager切换的代码*/

        //初始化 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // 标题栏的TextView
        initCampusTeamIntroduceDepartmentAlbumActivityJoinTextView();
        //校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // ViewPager的初始化
        initCampusTeamIntroduceDepartmentAlbumActivityJoinViewPager();

        //校园活动详情界面顶部的 返回箭头 的监听代码
        mMeMyTeamUpNavigationIconImageView = (ImageView)
                findViewById(R.id.campus_team_details_up_navigation_icon_image_view);
        mMeMyTeamUpNavigationIconImageView.
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

        //个人中心->我的团队->团队成员 列表项的代码（有两个列表，一个加入的，一个是申请加入的）
        mMeMyTeamMemberListView = (ListView) mMeMyTeamViews
                .get(0).findViewById(R.id.my_team_team_member_page_member_list_view);
        MyListViewAdapter myMeMyTeamMemberListViewAdapter = new MyListViewAdapter(list, this, 11);
        mMeMyTeamMemberListView.setAdapter(myMeMyTeamMemberListViewAdapter);

        mMeMyTeamWantJoinListView = (ListView) mMeMyTeamViews
                .get(0).findViewById(R.id.my_team_team_member_page_want_join_list_view);
        MyListViewAdapter myMeMyTeamWantJoinListViewAdapter = new MyListViewAdapter(list, this, 12);
        mMeMyTeamMemberListView.setAdapter(myMeMyTeamWantJoinListViewAdapter);


        //个人中心->我的团队->团队活动 列表项代码
        mMeMyTeamActivityListView = (ListView) mMeMyTeamViews
                .get(1).findViewById(R.id.my_team_team_activity_page_list_view);
        MyListViewAdapter myMeMyTeamActivityListViewAdapter = new MyListViewAdapter(list, this, 13);
        mMeMyTeamActivityListView.setAdapter(myMeMyTeamActivityListViewAdapter);

        //个人中心->我的团队->团队相册 列表项代码
        mMeMyTeamAlbumListView = (ListView) mMeMyTeamViews
                .get(3).findViewById(R.id.my_team_team_album_page_list_view);
        MyListViewAdapter myMeMyTeamAlbumListViewAdapter = new MyListViewAdapter(list, this, 14);
        mMeMyTeamActivityListView.setAdapter(myMeMyTeamAlbumListViewAdapter);

        //跳转到 个人中心->我的团队->信息修改（各部门要修改信息列表） Activity
        RelativeLayout departmentRelativeLayout = (RelativeLayout) mMeMyTeamViews
                .get(4).findViewById(R.id.department);
        departmentRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeMyTeamPageActivity.this,
                        MeMyTeamDataModificationDepartmentActivity.class);
                startActivity(intent);
            }
        });

    }

    /*实现 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
    5页面ViewPager切换的代码*/

    /**
     * 初始化 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * 标题栏的TextView
     * （这里主要是实例化，并设置监听）
     */
    private void initCampusTeamIntroduceDepartmentAlbumActivityJoinTextView() {
        mMeMyTeamMemberTextView = (TextView)
                findViewById(R.id.me_my_team_member_text_view);
        mMeMyTeamActivityTextView = (TextView)
                findViewById(R.id.me_my_team_activity_text_view);
        mMeMyTeamCompetitionTextView = (TextView)
                findViewById(R.id.me_my_team_competition_text_view);
        mMeMyTeamAlbumTextView = (TextView)
                findViewById(R.id.me_my_team_album_text_view);
        mMeMyTeamModificationTextView = (TextView)
                findViewById(R.id.me_my_team_modification_text_view);

        mMeMyTeamMemberTextView.setOnClickListener(new MyOnClickListener(0));
        mMeMyTeamActivityTextView.setOnClickListener(new MyOnClickListener(1));
        mMeMyTeamCompetitionTextView.setOnClickListener(new MyOnClickListener(2));
        mMeMyTeamAlbumTextView.setOnClickListener(new MyOnClickListener(3));
        mMeMyTeamModificationTextView.setOnClickListener(new MyOnClickListener(4));
    }

    /**
     * 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * ViewPager的初始化
     */
    private void initCampusTeamIntroduceDepartmentAlbumActivityJoinViewPager() {

        mMeMyTeamViewPager = (ViewPager)
                findViewById(R.id.me_my_team_viewpager);

        mMeMyTeamViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mMeMyTeamMemberPageView = mLayoutInflater
                .inflate(R.layout.my_team_team_member_page, null);
        mMeMyTeamActivityPageView = mLayoutInflater
                .inflate(R.layout.my_team_team_activity_page, null);
        mMeMyTeamCompetitionPageView = mLayoutInflater
                .inflate(R.layout.my_team_team_competition_page, null);
        mMeMyTeamAlbumPageView = mLayoutInflater
                .inflate(R.layout.my_team_team_album_page, null);
        mMeMyTeamModificationPageView = mLayoutInflater
                .inflate(R.layout.my_team_data_modification_page, null);

        mMeMyTeamViews.add(mMeMyTeamMemberPageView);
        mMeMyTeamViews.add(mMeMyTeamActivityPageView);
        mMeMyTeamViews.add(mMeMyTeamCompetitionPageView);
        mMeMyTeamViews.add(mMeMyTeamAlbumPageView);
        mMeMyTeamViews.add(mMeMyTeamModificationPageView);

        mMeMyTeamViewPager
                .setAdapter(new MyViewPagerAdapter(mMeMyTeamViews));
        mMeMyTeamViewPager.setCurrentItem(0);
        //主要是为 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // 这几个TextView的文字变色服务的
        mMeMyTeamViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 为 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
     * 的TextView设置选中和未选中时的颜色
     */

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            //先全部设为黑，然后选中哪个，将哪个设为红色

            mMeMyTeamMemberTextView.setTextColor(Color.BLACK);
            mMeMyTeamActivityTextView.setTextColor(Color.BLACK);
            mMeMyTeamCompetitionTextView.setTextColor(Color.BLACK);
            mMeMyTeamAlbumTextView.setTextColor(Color.BLACK);
            mMeMyTeamModificationTextView.setTextColor(Color.BLACK);

            switch (arg0) {
                case 0:
                    mMeMyTeamMemberTextView.setTextColor(Color.RED);
                    break;
                case 1:
                    mMeMyTeamActivityTextView.setTextColor(Color.RED);
                    break;
                case 2:
                    mMeMyTeamCompetitionTextView.setTextColor(Color.RED);
                    break;
                case 3:
                    mMeMyTeamAlbumTextView.setTextColor(Color.RED);
                    break;
                case 4:
                    mMeMyTeamModificationTextView.setTextColor(Color.RED);
                    break;
            }

            //记录当前选中哪个页面
            mMeMyTeamCurrentIndex = arg0;
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
            mMeMyTeamViewPager.setCurrentItem(index);
        }
    }

}
