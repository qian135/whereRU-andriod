package com.android.etuan.whereru;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 1/30/2016.
 */
public class MeHomePageActivity extends Activity {

    /*实现 个人中心->首页 4页面ViewPager切换的代码*/

    private View mMeHomePageMyTeamPageView, mMeHomePageCompetitionHistoryPageView,
            mMeHomePageActivityHistoryPageView,mMeHomePageDataModificationPageView;

    private ViewPager mMeHomePageViewPager;//个人中心->首页 这个大类的主ViewPager
    //存放 个人中心->首页 中的4个View
    private List<View> mMeHomePageViews;
    //个人中心->首页 中滑动用的4个TextView
    private TextView mMeHomePageMyTeamTextView, mMeHomePageCompetitionHistoryTextView,
            mMeHomePageActivityHistoryTextView,mMeHomePageDataModificationTextView;
    private int mMeHomePageTextViewCurrentIndex = 0;//对应不同的TextView

    //实现 个人中心->首页->我的团队 列表项的代码

    private ListView mMeHomePageMyTeamListView;

    //实现 个人中心->首页->竞赛历史 列表项的代码

    private ListView mMeHomePageCompetitionHistoryListView;

    //实现 个人中心->首页->活动历史 列表项的代码

    private ListView mMeHomePageActivityHistoryListView;

    //实现 个人中心->首页->活动历史 列表项的代码

    private ListView mMeHomePageDataModificationListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_home_page);

         /*实现 个人中心->首页 4页面ViewPager切换的代码*/

        //初始化 个人中心->首页 中滑动变化的标题栏的TextView
        initMeHomePageSlideTextView();
        //个人中心->首页ViewPager的初始化
        initMeHomePageViewPager();


        /*实现 个人中心->首页 中各页面列表项的代码*/

        //模仿列表项数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("item" + i);
        }

//        List<MeHomeMyTeamData> mMeHomeMyTeamDataList = new ArrayList<>();
//        MeHomeMyTeamData meHomeMyTeamData = new MeHomeMyTeamData();
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.etuan_logo);
//        meHomeMyTeamData.setmTeamLogo((ImageView)bitmap);
//        mMeHomeMyTeamDataList.add(new MeHomeMyTeamData());

        //个人中心->首页->我的团队 列表项代码
        mMeHomePageMyTeamListView = (ListView) mMeHomePageViews
                .get(0).findViewById(R.id.me_home_page_my_team_page_list_view);
        MyListViewAdapter myMeHomePageMyTeamListViewAdapter = new MyListViewAdapter(list, this, 8);
        mMeHomePageMyTeamListView.setAdapter(myMeHomePageMyTeamListViewAdapter);

        //个人中心->首页->竞赛历史 列表项代码
        mMeHomePageCompetitionHistoryListView = (ListView) mMeHomePageViews
                .get(1).findViewById(R.id.me_home_page_my_competition_history_list_view);
        MyListViewAdapter myMeHomePageCompetitionHistoryListViewAdapter = new MyListViewAdapter(list, this, 9);
        mMeHomePageCompetitionHistoryListView.setAdapter(myMeHomePageCompetitionHistoryListViewAdapter);

        //个人中心->首页->活动历史 列表项代码
        mMeHomePageActivityHistoryListView = (ListView) mMeHomePageViews
                .get(2).findViewById(R.id.me_home_page_activity_history_list_view);
        MyListViewAdapter myMeHomePageActivityHistoryListViewAdapter = new MyListViewAdapter(list, this, 10);
        mMeHomePageActivityHistoryListView.setAdapter(myMeHomePageActivityHistoryListViewAdapter);

    }

    /*实现 个人中心->首页 4页面ViewPager切换的代码*/

    /**
     * 初始化 个人中心->首页
     * 标题栏的TextView
     * （这里主要是实例化，并设置监听）
     */
    private void initMeHomePageSlideTextView() {
        mMeHomePageMyTeamTextView = (TextView)
                findViewById(R.id.me_home_page_my_team_text_view);
        mMeHomePageCompetitionHistoryTextView = (TextView)
                findViewById(R.id.me_home_page_competition_history_text_view);
        mMeHomePageActivityHistoryTextView = (TextView)
                findViewById(R.id.me_home_page_activity_history_text_view);
        mMeHomePageDataModificationTextView = (TextView)
                findViewById(R.id.me_home_page_data_modification_text_view);


        mMeHomePageMyTeamTextView.setOnClickListener(new MyOnClickListener(0));
        mMeHomePageCompetitionHistoryTextView.setOnClickListener(new MyOnClickListener(1));
        mMeHomePageActivityHistoryTextView.setOnClickListener(new MyOnClickListener(2));
        mMeHomePageDataModificationTextView.setOnClickListener(new MyOnClickListener(3));
    }

    /**
     * 个人中心->首页
     * ViewPager的初始化
     */
    private void initMeHomePageViewPager() {

        mMeHomePageViewPager = (ViewPager)
                findViewById(R.id.me_home_page_viewpager);

        mMeHomePageViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mMeHomePageMyTeamPageView = mLayoutInflater
                .inflate(R.layout.me_home_page_my_team_page, null);
        mMeHomePageCompetitionHistoryPageView = mLayoutInflater
                .inflate(R.layout.me_home_page_competition_history_page, null);
        mMeHomePageActivityHistoryPageView = mLayoutInflater
                .inflate(R.layout.me_home_page_activity_history_page, null);
        mMeHomePageDataModificationPageView = mLayoutInflater
                .inflate(R.layout.me_home_page_data_modification_page, null);

        mMeHomePageViews.add(mMeHomePageMyTeamPageView);
        mMeHomePageViews.add(mMeHomePageCompetitionHistoryPageView);
        mMeHomePageViews.add(mMeHomePageActivityHistoryPageView);
        mMeHomePageViews.add(mMeHomePageDataModificationPageView);

        mMeHomePageViewPager
                .setAdapter(new MyViewPagerAdapter(mMeHomePageViews));
        mMeHomePageViewPager.setCurrentItem(0);
        //主要是为 校园团队->团队介绍-> 团队简介 ,部门介绍，团队相册，团队活动，申请加入
        // 这几个TextView的文字变色服务的
        mMeHomePageViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 为 个人中心->首页
     * 的TextView设置选中和未选中时的颜色
     */

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            //先全部设为黑，然后选中哪个，将哪个设为红色

            mMeHomePageMyTeamTextView.setTextColor(Color.BLACK);
            mMeHomePageCompetitionHistoryTextView.setTextColor(Color.BLACK);
            mMeHomePageActivityHistoryTextView.setTextColor(Color.BLACK);
            mMeHomePageDataModificationTextView.setTextColor(Color.BLACK);

            switch (arg0) {
                case 0:
                    mMeHomePageMyTeamTextView.setTextColor(Color.RED);
                    break;
                case 1:
                    mMeHomePageCompetitionHistoryTextView.setTextColor(Color.RED);
                    break;
                case 2:
                    mMeHomePageActivityHistoryTextView.setTextColor(Color.RED);
                    break;
                case 3:
                    mMeHomePageDataModificationTextView.setTextColor(Color.RED);
                    break;
            }

            //记录当前选中哪个页面
            mMeHomePageTextViewCurrentIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

    }

    /**
     * 使点击 个人中心->首页
     * 的TextView也能完成ViewPager的切换
     */

    public class MyOnClickListener implements View.OnClickListener {

        private int index;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mMeHomePageViewPager.setCurrentItem(index);
        }
    }

}
