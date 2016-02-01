package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.etuan.whereru.adapter.ActivityListViewAdapter;
import com.android.etuan.whereru.adapter.CompetitionListViewAdapter;
import com.android.etuan.whereru.adapter.TeamListViewAdapter;
import com.android.etuan.whereru.utils.httputil.HttpSearch;
import com.android.etuan.whereru.utils.jsonutil.JSONSearchs;
import com.android.etuan.whereru.utils.searchclass.Activities;
import com.android.etuan.whereru.utils.searchclass.Coterie;
import com.android.etuan.whereru.utils.searchclass.Race;
import com.android.etuan.whereru.utils.searchclass.Team;
import com.android.etuan.whereru.utils.searchclass.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class SearchPage extends Activity {

    /*接口提供的相关代码*/

    private static final int ACTIVITY = 1;
    private static final int RACE = 2;
    private static final int TEAM = 3;
    private static final int USER = 4;
    private static final int COTERIE = 5;

    private ArrayList<Activities> activities;
    private ArrayList<Race> races;
    private static ArrayList<Team> teams;
    private static ArrayList<User> users;
    private static ArrayList<Coterie> coteries;

    /*该页面顶部滑动条相关代码*/

    private MyViewPagerOnPageChangeListener mMyViewPagerOnPageChangeListener;

    private int mSlideBarDivideNumber = 6;  //顶部滑动菜单划分的块数
    private MySlideBarHandler mMySlideBarHandler;//顶部滑动条的处理类
    private ImageView mSlideBar;//滑动条
    private int mSlideBarCurrentIndex = 0;

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

        //初始化 搜索->全部，活动，竞赛，团队，用户，圈子 下面滑动条的动画
        initSearchAllActivityCompetitionTeamUserCircleSlideBarSlideBarAnimation();
        //初始化 搜索->全部，活动，竞赛，团队，用户，圈子 标题栏的TextView
        initSearchAllActivityCompetitionTeamUserCircleTextView();
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


        //模仿列表项数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("item" + i);
        }
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
        HttpSearch.setContext(SearchPage.this);

        if (mSearchBoxEditText.getText().toString().equals(""))
            Toast.makeText(SearchPage.this, "请输入相关信息", Toast.LENGTH_SHORT).show();
        else {
            final String keyWord = mSearchBoxEditText.getText().toString();
            final int type = mMyViewPagerOnPageChangeListener.getmSlideBarCurrentIndex();
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String s = "";
                    if (msg.what == 0x123) {
                        System.out.println("获取搜索信息失败");
                    } else if (msg.what == 0x234) {
                        String result = msg.getData().getString("data");

                        try {
                            JSONSearchs.GetInfo(result, type);
//                            System.out.println(type);
                            int size = 0;
                            switch (type) {
                                case ACTIVITY:
                                    activities = JSONSearchs.getActivities();
                                    for (int i = 0; i < activities.size(); i++) {
                                        System.out.println(activities.get(i).getTitle());
                                        System.out.println(activities.get(i).getAuthorName());
                                        System.out.println(activities.get(i).getCreated());
                                        System.out.println(activities.get(i).getEnded());
                                        System.out.println(activities.get(i).getImgUrl());
                                        System.out.println(activities.get(i).getStarted());
                                    }
                                    //加载校园活动列表项代码
                                    mCampusActivityListView = (ListView) mSearchPageViews
                                            .get(1).findViewById(R.id.campus_activity_home_page_activity_list_view);
                                    ActivityListViewAdapter activityListViewAdapter = new
                                            ActivityListViewAdapter(activities, SearchPage.this);
                                    mCampusActivityListView.setAdapter(activityListViewAdapter);
                                    //为校园活动ListView每项设置监听器，完成页面跳转
                                    mCampusActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new
                                                    Intent(SearchPage.this, CampusActivityDetailsActivity.class);
                                            startActivity(intent);
                                        }
                                    });


                                    break;
                                case RACE:
                                    races = JSONSearchs.getRaces();
                                    //加载校园竞赛列表项代码
                                    mCampusCompetitionListView = (ListView) mSearchPageViews
                                            .get(2).findViewById(R.id.campus_competition_list_view);
                                    CompetitionListViewAdapter competitionListViewAdapter = new
                                            CompetitionListViewAdapter(races, SearchPage.this);
                                    mCampusCompetitionListView.setAdapter(competitionListViewAdapter);
                                    //为校园竞赛ListView每项设置监听器，完成页面跳转
                                    mCampusCompetitionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new
                                                    Intent(SearchPage.this, CampusCompetitionDetailsActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                    break;
                                case TEAM:

                                    teams = JSONSearchs.getTeams();
                                    //加载校园团队列表项代码
                                    mCampusTeamListView = (ListView) mSearchPageViews
                                            .get(3).findViewById(R.id.campus_team_home_page_list_view);
                                    TeamListViewAdapter teamListViewAdapter = new
                                            TeamListViewAdapter(teams, SearchPage.this);
                                    mCampusTeamListView.setAdapter(teamListViewAdapter);
                                    //为校园竞赛ListView每项设置监听器，完成页面跳转
                                    mCampusTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new
                                                    Intent(SearchPage.this, CampusTeamDetailsActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                    break;
                                case USER:
                                    users = JSONSearchs.getUsers();
                                    size = users.size();
                                    for (int i = 0; i < users.size(); i++) {
                                        s += users.get(i).getName();
                                    }
                                    break;
                                case COTERIE:
                                    coteries = JSONSearchs.getCoteries();
                                    size = coteries.size();
                                    for (int i = 0; i < coteries.size(); i++) {
                                        s += coteries.get(i).getName();
                                    }
                                    break;
                            }
                            if (size != 0) {
                                //txt_searchInfo.setText(s);
                            } else {
//                                txt_searchInfo
//                                        .setText("在 " + searchType + " 中没有找到与" + keyWord + "相关的信息");
                            }
                            //System.out.println("JSON解析完成 内容长度为"+activity.length+"内容是"+s);
                        } catch (JSONException e) {
//                            txt_searchInfo.setText("JSON解析失败");
                            e.printStackTrace();
                        }
                    }
                    super.handleMessage(msg);
                }
            };
            HttpSearch.setHandler(handler);
            HttpSearch.getSearch(keyWord, type);
        }
    }

    /*实现 搜索->全部，活动，竞赛，团队，用户，圈子 6页面ViewPager切换的代码*/

    /**
     * 初始化 搜索->全部，活动，竞赛，团队，用户，圈子 下面滑动条的动画
     */

    private void initSearchAllActivityCompetitionTeamUserCircleSlideBarSlideBarAnimation() {
        mSlideBar = (ImageView) findViewById(R.id.search_page_slide_bar);
        mMySlideBarHandler = new MySlideBarHandler(this, mSlideBarDivideNumber,
                R.drawable.search_page_slide_bar_icon, mSlideBar);
    }

    /**
     * 初始化 搜索->全部，活动，竞赛，团队，用户，圈子 标题栏的TextView
     * （这里主要是实例化，并设置监听）
     */
    private void initSearchAllActivityCompetitionTeamUserCircleTextView() {
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
                .inflate(R.layout.campus_activity_home_page, null);
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

        mMyViewPagerOnPageChangeListener =
                new MyViewPagerOnPageChangeListener(mMySlideBarHandler.getSlideBarMoveUnit(),
                        mSlideBarCurrentIndex, mSlideBar);
        mSearchPageViewPager.setOnPageChangeListener(mMyViewPagerOnPageChangeListener);

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
