package com.android.etuan.whereru;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchPage extends Activity {

    /*该页面顶部滑动条相关代码*/

    private MyViewPagerOnPageChangeListener mMyViewPagerOnPageChangeListener;

    private int mSlideBarDivideNumber = 6;  //顶部滑动菜单划分的块数
    private ImageView mSlideBar;//滑动条

    private EditText mSearchBoxEditText;

    /*实现 搜索：全部，活动，竞赛，团队，用户，圈子 6页面ViewPager切换的代码*/

    private View mSearchAllPageView, mSearchActivityPageView, mSearchCompetitionPageView,
            mSearchTeamPageView, mSearchUserPageView, mSearchCirclePageView;

    private ViewPager mSearchPageViewPager;//搜索这个大类的主ViewPager
    //存放 搜索->全部，活动，竞赛，团队，用户，圈子 6个View
    private List<View> mSearchPageViews;

    //搜索->全部，活动，竞赛，团队，用户，圈子 6个TextView
    private TextView mSearchAllTextView, mSearchActivityTextView, mSearchCompetitionTextView,
            mSearchTeamTextView, mSearchUserTextView, mSearchCircleTextView;

    /*搜索->全部，活动，竞赛，团队，用户，圈子 页面返回上一级的箭头*/

    private ImageView mSearchHomePageUpNavigationIconImageView;


    /*实现校园活动列表项的代码*/

    private ListView mCampusActivityListView; //校园活动列表的ListView

    /*实现校园竞赛列表项的代码*/

    private ListView mCampusCompetitionListView; //校园竞赛列表的ListView

    /*实现校园团队列表项的代码*/

    private ListView mCampusTeamListView; //校园团队列表的ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

         /*实现 搜索->全部，活动，竞赛，团队，用户，圈子 6页面ViewPager切换的代码*/

        //初始化 搜索->全部，活动，竞赛，团队，用户，圈子 标题栏的TextView及下面的滑动条
        initTextViewAndSlideBar();
        //搜索->全部，活动，竞赛，团队，用户，圈子 ViewPager的初始化
        initSearchAllActivityCompetitionTeamUserCircleViewPager();

        //搜索主界面顶部的 返回箭头 的监听代码
        mSearchHomePageUpNavigationIconImageView = (ImageView)
                findViewById(R.id.search_page_up_navigation_icon_image_view);
        mSearchHomePageUpNavigationIconImageView.
                setOnClickListener(new View.OnClickListener() {
                    //跳转回点击搜索按钮的界面
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

        mSearchBoxEditText = (EditText) findViewById(R.id.search_box_edit_text);
        mSearchBoxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //开始搜索
                    getStartSearch();
                    return true;
                }
                return false;
            }
        });


        //校园活动列表项代码

        //注意因为在initCampusActivityCompetitionTeamViewPager已经将三个ViewPager的布局文件
        // 都实例化过了，这里不需要再实例化了

//        mCampusActivityListView = (ListView) mSearchPageViews
//                .get(1).findViewById(R.id.campus_activity_home_page_activity_list_view);
//        MyListViewAdapter myCampusActivityListViewAdapter = new MyListViewAdapter(list, this, 0);
//        mCampusActivityListView.setAdapter(myCampusActivityListViewAdapter);
//        //为校园活动ListView每项设置监听器，完成页面跳转
//        mCampusActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(SearchPage.this, CampusActivityDetailsActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    private void getStartSearch() {

        if (mSearchBoxEditText.getText().toString().equals(""))
            Toast.makeText(SearchPage.this, "请输入相关信息", Toast.LENGTH_SHORT).show();
        else {
            final String keyWord = mSearchBoxEditText.getText().toString();
            final int type = mMyViewPagerOnPageChangeListener.getmSlideBarCurrentIndex();
        }

    }

    /*实现 搜索->全部，活动，竞赛，团队，用户，圈子 6页面ViewPager切换的代码*/


    /**
     * 初始化 搜索->全部，活动，竞赛，团队，用户，圈子 标题栏的TextView及下面的滑动条
     * （这里主要是实例化，并设置监听）
     */
    private void initTextViewAndSlideBar() {

        mSearchAllTextView = (TextView)
                findViewById(R.id.search_page_all_text_view);
        mSearchActivityTextView = (TextView)
                findViewById(R.id.search_page_activity_text_view);
        mSearchCompetitionTextView = (TextView)
                findViewById(R.id.search_page_competition_text_view);
        mSearchTeamTextView = (TextView)
                findViewById(R.id.search_page_team_text_view);
        mSearchUserTextView = (TextView)
                findViewById(R.id.search_page_user_text_view);
        mSearchCircleTextView = (TextView)
                findViewById(R.id.search_page_circle_text_view);

        mSearchAllTextView.setOnClickListener(new MyOnClickListener(0));
        mSearchActivityTextView.setOnClickListener(new MyOnClickListener(1));
        mSearchCompetitionTextView.setOnClickListener(new MyOnClickListener(2));
        mSearchTeamTextView.setOnClickListener(new MyOnClickListener(3));
        mSearchUserTextView.setOnClickListener(new MyOnClickListener(4));
        mSearchCircleTextView.setOnClickListener(new MyOnClickListener(5));

        mSlideBar = (ImageView) findViewById(R.id.search_page_slide_bar);

    }

    /**
     * 搜索->全部，活动，竞赛，团队，用户，圈子 ViewPager的初始化
     */
    private void initSearchAllActivityCompetitionTeamUserCircleViewPager() {

        mSearchPageViewPager = (ViewPager)
                findViewById(R.id.search_page_viewpager);

        mSearchPageViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mSearchAllPageView = mLayoutInflater
                .inflate(R.layout.search_all_page, null);
        mSearchActivityPageView = mLayoutInflater//
                .inflate(R.layout.search_activity_page, null);
        mSearchCompetitionPageView = mLayoutInflater//
                .inflate(R.layout.campus_competition_home_page, null);
        mSearchTeamPageView = mLayoutInflater//
                .inflate(R.layout.campus_team_home_page, null);
        mSearchUserPageView = mLayoutInflater
                .inflate(R.layout.search_user_page, null);
        mSearchCirclePageView = mLayoutInflater
                .inflate(R.layout.search_circle_page, null);

        mSearchPageViews.add(mSearchAllPageView);
        mSearchPageViews.add(mSearchActivityPageView);
        mSearchPageViews.add(mSearchCompetitionPageView);
        mSearchPageViews.add(mSearchTeamPageView);
        mSearchPageViews.add(mSearchUserPageView);
        mSearchPageViews.add(mSearchCirclePageView);

        mSearchPageViewPager
                .setAdapter(new MyViewPagerAdapter(mSearchPageViews));
        mSearchPageViewPager.setCurrentItem(0);

        //为 搜索->全部，活动，竞赛，团队，用户，圈子 ViewPager设置滑动的监听器

        //主要是为该页面滑动条服务的

        View view = findViewById(R.id.search_page_text_view_linear_layout);

        mMyViewPagerOnPageChangeListener =
                new MyViewPagerOnPageChangeListener(this, view, mSlideBar, mSlideBarDivideNumber);
        mSearchPageViewPager.addOnPageChangeListener(mMyViewPagerOnPageChangeListener);

    }

    /**
     * 使点击 搜索->全部，活动，竞赛，团队，用户，圈子 的TextView也能完成ViewPager的切换
     */

    public class MyOnClickListener implements View.OnClickListener {

        private int index;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mSearchPageViewPager.setCurrentItem(index);
        }

    }

}
