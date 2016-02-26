package com.android.etuan.whereru;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CampusCompetitionDetailsActivity extends Activity {

    /*该页面顶部滑动条相关代码*/
    private int mSlideBarDivideNumber = 4;  //顶部滑动菜单划分的块数
    private ImageView mSlideBar;//滑动条

    /*实现 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4页面ViewPager切换的代码*/

    private View mCampusCompetitionNoticePageView, mCampusCompetitionDetailPageView,
            mCampusCompetitionTeamPageView, mCampusCompetitionJoinPageView;

    private ViewPager mCampusCompetitionDetailsViewPager;//校园活动细节这个大类的主ViewPager
    //存放 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4个View
    private List<View> mCampusCompetitionNoticeDetailTeamJoinViews;

    //校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4个TextView
    private TextView mCampusCompetitionNoticeTextView, mCampusCompetitionDetailTextView,
            mCampusCompetitionTeamTextView, mCampusCompetitionJoinTextView;


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
        setContentView(R.layout.campus_competition_details_page);

         /*实现 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 4页面ViewPager切换的代码*/

        //初始化 校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 标题栏的TextView
        //及下面的滑动条
        initTextViewAndSlideBar();
        //校园竞赛->竞赛详情->竞赛通知，竞赛详情，参赛团队，参与竞赛 ViewPager的初始化
        initCampusCompetitionNoticeDetailTeamJoinViewPager();

        mCampusCompetitionDetailsUpNavigationIconImageView = (ImageView)
                findViewById(R.id.campus_competition_details_up_navigation_icon_image_view);
        mCampusCompetitionDetailsUpNavigationIconImageView.
                setOnClickListener(new View.OnClickListener() {
                    //跳转回校园竞赛界面
                    @Override
                    public void onClick(View view) {
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

        //校园竞赛->竞赛详情->参赛团队 列表项代码
        mCampusCompetitionDetailsTeamListView = (ListView) mCampusCompetitionNoticeDetailTeamJoinViews
                .get(2).findViewById(R.id.campus_competition_details_team_page_list_view);
        MyListViewAdapter myCampusCompetitionListViewAdapter = new MyListViewAdapter(list, this, 4);
        mCampusCompetitionDetailsTeamListView.setAdapter(myCampusCompetitionListViewAdapter);

        //校园竞赛->竞赛详情->参与竞赛 列表项代码
        mCampusCompetitionDetailsJoinListView = (ListView) mCampusCompetitionNoticeDetailTeamJoinViews
                .get(3).findViewById(R.id.campus_competition_details_join_page_list_view);
        MyListViewAdapter myCampusTeamListViewAdapter = new MyListViewAdapter(list, this, 5);
        mCampusCompetitionDetailsJoinListView.setAdapter(myCampusTeamListViewAdapter);

    }

    /*实现 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 4页面ViewPager切换的代码*/


    /**
     * 初始化 校园竞赛详情 ：竞赛通知，竞赛详情，参赛团队，参与竞赛 标题栏的TextView及下面的滑动条
     * （这里主要是实例化，并设置监听）
     */
    private void initTextViewAndSlideBar() {
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

        mSlideBar = (ImageView) findViewById(R.id.campus_competition_details_page_slide_bar);

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

        //主要是为该页面滑动条服务的
        View view = findViewById(R.id.campus_competition_details_page_text_view_linear_layout);
        MyViewPagerOnPageChangeListener myViewPagerOnPageChangeListener =
                new MyViewPagerOnPageChangeListener(this, view, mSlideBar, mSlideBarDivideNumber);
        mCampusCompetitionDetailsViewPager.addOnPageChangeListener(myViewPagerOnPageChangeListener);
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
