package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.etuan.whereru.bean.HotCampusActivityInformation;
import com.android.etuan.whereru.utils.ViewFactory;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.androiddevelop.cycleviewpager.lib.HotCampusActivityCycleViewPager;

public class MainActivity extends Activity implements View.OnClickListener {

    /*确保在Activity切换时能正确显示 校园活动->首页->校园活动，校园竞赛，校园团队
    中的某个ViewPager页面*/

    private int mCampusHomeViewPagerCurrentNumber = 0;
    private static final String KEY_CAMPUS_HOME_VIEW_PAGER_NUMBER = "CampusHomeViewPagerNumber";

    //校园，圈子，我 三大主界面的View
    //如果这三个布局用局部变量，每次用到的时候分别实例化，就会出问题，
    // 原因暂时未知(现在可能不会了)
    private View mCampusHomePageView, mCircleHomePageView, mMeHomePageView;

    /*实现圈子,校园和我三者底部导航栏的代码*/

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


    /*实现校园活动，校园竞赛，校园团队三页面ViewPager切换的代码*/

    private View mCampusActivityHomePageView, mCampusCompetitionHomePageView,
            mCampusTeamHomePageView;

    private ViewPager mCampusHomeViewPager;//校园这个大类的主ViewPager
    //存放 校园活动，校园竞赛，校园团队 3个View
    private List<View> mCampusActivityCompetitionTeamViews;
    private ImageView mCampusActivityCompetitionTeamSlideBar;//滑动条
    //校园活动，校园竞赛，校园团队 3个TextView
    private TextView mCampusActivityTextView, mCampusCompetitionTextView, mCampusTeamTextView;
    private int mCampusActivityCompetitionTeamOffset = 0;//偏移量
    private int mCampusActivityCompetitionTeamSlideBarWidth;//Image的宽度

    /*实现热门活动栏代码*/

    private List<ImageView> mHotActivityViews = new ArrayList<>();//存放热门活动的ImageView
    private List<HotCampusActivityInformation> mHotCampusActivityInformation = new ArrayList<>();
    private HotCampusActivityCycleViewPager mHotCampusActivityCycleViewPager;

    private String[] imageUrls = {
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    /*实现校园活动列表项的代码*/

    private ListView mCampusActivityListView; //校园活动列表的ListView

    /*实现校园竞赛列表项的代码*/

    private ListView mCampusCompetitionListView; //校园竞赛列表的ListView

    /*实现校园团队列表项的代码*/

    private ListView mCampusTeamListView; //校园团队列表的ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*确保在Activity切换时能正确显示 校园活动->首页->校园活动，校园竞赛，校园团队
    中的某个ViewPager页面*/

//        if (savedInstanceState != null) {
//            mCampusHomeViewPagerCurrentNumber =
//                    savedInstanceState.getInt(KEY_CAMPUS_HOME_VIEW_PAGER_NUMBER, 0);
//        }

         /*获取其他Activity传来的Intent消息*/

        //指示其他页面跳转到那个ViewPager
        //校园活动，校园竞赛，校园团队 的ViewPager标志位0,1,2（以后默认都是从左到右0,1,2）
//        Intent intent = getIntent();
//        if ((mCampusHomeViewPagerCurrentNumber
//                = intent.getIntExtra("CampusActivityDetailsActivity", -1)) != -1) {
//            System.out.println("intent.getIntExtra(\"CampusActivityDetailsActivity\", -1))");
//
//        } else {
//            if ((mCampusHomeViewPagerCurrentNumber
//                    = intent.getIntExtra("CampusCompetitionDetailsActivity", -1)) != -1) {
//                System.out.println("intent.getIntExtra(\"CampusCompetitionDetailsActivity\", -1))");
//            } else {
//                mCampusHomeViewPagerCurrentNumber = 0;
//            }
//        }

        /*实现 圈子,校园,我 三者底部导航栏的代码*/

        //圈子,校园,我 底部导航栏的初始化
        circleCampusMeNavigationBarInit();
        //初始化 圈子，校园，我 3个主页的ViewPage
        initCircleCampusMeHomePagerViewPager();

        /*实现校园活动，校园竞赛，校园团队三页面ViewPager切换的代码*/

        //初始化 校园活动，校园竞赛，校园团队 下面滑动条的动画
        initCampusActivityCompetitionTeamSlideBarAnimation();
        //初始化 校园活动，校园竞赛，校园团队 标题栏的TextView
        initCampusActivityCompetitionTeamTextView();
        //校园活动，校园竞赛，校园团队 ViewPager的初始化
        initCampusActivityCompetitionTeamViewPager();



         /*实现校园活动，竞赛，团队3个列表项的代码*/

        //模仿列表项数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("item" + i);
        }

        //实现校园活动热门活动栏代码
        configUniversalImageLoader();
        initHotCampusActivityCycleViewPager();

        //校园活动列表项代码

        //注意因为在initCampusActivityCompetitionTeamViewPager已经将三个ViewPager的布局文件
        // 都实例化过了，这里不需要再实例化了
        mCampusActivityListView = (ListView) mCampusActivityCompetitionTeamViews
                .get(0).findViewById(R.id.campus_activity_home_page_activity_list_view);
        MyListViewAdapter myCampusActivityListViewAdapter = new MyListViewAdapter(list, this, 0);
        mCampusActivityListView.setAdapter(myCampusActivityListViewAdapter);
        //为校园活动ListView每项设置监听器，完成页面跳转
        mCampusActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, CampusActivityDetailsActivity.class);
                startActivity(intent);
            }
        });



        //校园竞赛列表项代码
        mCampusCompetitionListView = (ListView) mCampusActivityCompetitionTeamViews
                .get(1).findViewById(R.id.campus_competition_list_view);
        MyListViewAdapter myCampusCompetitionListViewAdapter = new MyListViewAdapter(list, this, 1);
        mCampusCompetitionListView.setAdapter(myCampusCompetitionListViewAdapter);
        //为校园竞赛ListView每项设置监听器，完成页面跳转
        mCampusCompetitionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new
                        Intent(MainActivity.this, CampusCompetitionDetailsActivity.class);
                startActivity(intent);
            }
        });

        //校园团队列表项代码
        mCampusTeamListView = (ListView) mCampusActivityCompetitionTeamViews
                .get(2).findViewById(R.id.campus_team_home_page_list_view);
        MyListViewAdapter myCampusTeamListViewAdapter = new MyListViewAdapter(list, this, 2);
        mCampusTeamListView.setAdapter(myCampusTeamListViewAdapter);

        mCampusTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new
                        Intent(MainActivity.this, CampusTeamDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(KEY_CAMPUS_HOME_VIEW_PAGER_NUMBER, mCampusHomeViewPagerCurrentNumber);
//    }

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

        //实例化圈子，校园，我 3个主页的布局
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        mCircleHomePageView = mLayoutInflater.inflate(R.layout.circle_home_page, null);
        mCampusHomePageView = mLayoutInflater.inflate(R.layout.campus_home_page, null);
        mMeHomePageView = mLayoutInflater.inflate(R.layout.me_home_page, null);

        //将圈子，校园，我 3个主页的View添加到mCircleCampusMeViews
        mCircleCampusMeViews = new ArrayList<>();
        mCircleCampusMeViews.add(mCircleHomePageView);
        mCircleCampusMeViews.add(mCampusHomePageView);
        mCircleCampusMeViews.add(mMeHomePageView);

        mCircleCampusMeViewPagerAdapter = new MyViewPagerAdapter(mCircleCampusMeViews);
        mCircleCampusMeViewPager.setAdapter(mCircleCampusMeViewPagerAdapter);

        //设置首先显示的是校园界面
        mCircleCampusMeViewPager.setCurrentItem(1);

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

    /*实现校园活动，校园竞赛，校园团队三页面ViewPager切换的代码*/

    /**
     * 初始化 校园活动，校园竞赛，校园团队 下面滑动条的动画
     */
    private void initCampusActivityCompetitionTeamSlideBarAnimation() {
        mCampusActivityCompetitionTeamSlideBar = (ImageView) mCampusHomePageView
                .findViewById(R.id.campus_home_page_cursor);
        //获取校园活动，校园竞赛，校园团队下面滑动条的宽度
        mCampusActivityCompetitionTeamSlideBarWidth = BitmapFactory.
                decodeResource(getResources(), R.drawable.campus_activity_competition_team_slide_bar).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        mCampusActivityCompetitionTeamOffset = (screenW / 3 - mCampusActivityCompetitionTeamSlideBarWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();

     /*确保在Activity切换时能正确显示 校园活动->首页->校园活动，校园竞赛，校园团队
    下的SlideBar*/

        int one = mCampusActivityCompetitionTeamOffset * 2
                + mCampusActivityCompetitionTeamSlideBarWidth;
        int two = one * 2;

        switch (mCampusHomeViewPagerCurrentNumber) {
            case 0:
                matrix.postTranslate(mCampusActivityCompetitionTeamOffset, 0);
                break;
            case 1:
                matrix.postTranslate(one, 0);
                break;
            case 2:
                matrix.postTranslate(two, 0);
                break;
        }

        mCampusActivityCompetitionTeamSlideBar.setImageMatrix(matrix);// 设置动画初始位置

    }


    /**
     * 初始化 校园活动，校园竞赛，校园团队 标题栏的TextView（这里主要是实例化，并设置监听）
     */
    private void initCampusActivityCompetitionTeamTextView() {
        mCampusActivityTextView = (TextView) mCampusHomePageView
                .findViewById(R.id.campus_activity_text_view);
        mCampusCompetitionTextView = (TextView) mCampusHomePageView
                .findViewById(R.id.campus_competition_text_view);
        mCampusTeamTextView = (TextView) mCampusHomePageView
                .findViewById(R.id.campus_team_text_view);

        //给 校园活动，校园竞赛，校园团队 标题栏的TextView 赋值其对应的ViewPager中View页面的编号，
        //这样当点击这些TextView中的文字的时候，将该编号值赋给mCampusHomeViewPagerCurrentNumber，
        //然后ViewPage.setCurrentItem(mCampusHomeViewPagerCurrentNumber)即可

        mCampusActivityTextView
                .setOnClickListener(new MyOnClickListener(0));
        mCampusCompetitionTextView
                .setOnClickListener(new MyOnClickListener(1));
        mCampusTeamTextView
                .setOnClickListener(new MyOnClickListener(2));

    }

    /**
     * 校园活动，校园竞赛，校园团队 ViewPager的初始化
     */
    private void initCampusActivityCompetitionTeamViewPager() {

        mCampusHomeViewPager = (ViewPager) mCampusHomePageView
                .findViewById(R.id.campus_home_viewpager);

        mCampusActivityCompetitionTeamViews = new ArrayList<>();

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        mCampusActivityHomePageView = mLayoutInflater
                .inflate(R.layout.campus_activity_home_page, null);
        mCampusCompetitionHomePageView = mLayoutInflater
                .inflate(R.layout.campus_competition_home_page, null);
        mCampusTeamHomePageView = mLayoutInflater
                .inflate(R.layout.campus_team_home_page, null);

        mCampusActivityCompetitionTeamViews.add(mCampusActivityHomePageView);
        mCampusActivityCompetitionTeamViews.add(mCampusCompetitionHomePageView);
        mCampusActivityCompetitionTeamViews.add(mCampusTeamHomePageView);

        mCampusHomeViewPager
                .setAdapter(new MyViewPagerAdapter(mCampusActivityCompetitionTeamViews));

        //主要是为 校园活动，校园竞赛，校园团队 下面那个滑动条服务的
        mCampusHomeViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    /**
     * 为 校园活动，校园竞赛，校园团队 ViewPager设置滑动的监听器
     */

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = mCampusActivityCompetitionTeamOffset * 2
                + mCampusActivityCompetitionTeamSlideBarWidth;
        int two = one * 2;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            System.out.println("arg0 " + arg0 + " come from onPageSelected");
            System.out.println("mCampusHomeViewPagerCurrentNumber " + mCampusHomeViewPagerCurrentNumber + " come from onPageSelected");
            switch (arg0) {
                case 0:
                    if (mCampusHomeViewPagerCurrentNumber == 1) {
/**
 * 设置在X轴方向的动画。
 * 第一个参数fromXDelta。Image的左上角为(0,0),该参数表示在X轴方向的动画开始位置距离Image左上角的距离
 * 第二个参数toXDelta。该参数表示在X轴方向动画的结束位置距离Image左上角的距离。
 * 第三和第四参数同上
 * 位置的开始点是Image的原始位置，而不是setFillAfter(true)之后的位置
 */
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (mCampusHomeViewPagerCurrentNumber == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }



                    break;
                case 1:
                    if (mCampusHomeViewPagerCurrentNumber == 0) {
                        animation = new TranslateAnimation(0, one, 0, 0);
                    } else if (mCampusHomeViewPagerCurrentNumber == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }

                    break;
                case 2:
                    if (mCampusHomeViewPagerCurrentNumber == 0) {
                        animation = new TranslateAnimation(0, two, 0, 0);
                    } else if (mCampusHomeViewPagerCurrentNumber == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }

            mCampusHomeViewPagerCurrentNumber = arg0;
            animation.setFillAfter(true);
            animation.setDuration(500);
            mCampusActivityCompetitionTeamSlideBar.startAnimation(animation);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 使点击 校园活动，校园竞赛，校园团队 的TextView也能完成ViewPager的切换
     */

    public class MyOnClickListener implements View.OnClickListener {

        private int mViewPagerNumber;

        public MyOnClickListener(int viewPagerNumber) {
            mViewPagerNumber = viewPagerNumber;
        }

        @Override
        public void onClick(View v) {

            //下面这句代码是不必要的，因为执行了ViewPager.setCurrentItem()，自然会去执行
            //ViewPager.OnPageChangeListener中的onPageSelected(int arg0)，而且很有可能arg0的值就是
            //从我这里传过去的mViewPagerNumber获取的，而在那个方法里我执行了
            // mCampusHomeViewPagerCurrentNumber = arg0了

            //mCampusHomeViewPagerCurrentNumber = mViewPagerNumber;

            mCampusHomeViewPager
                    .setCurrentItem(mViewPagerNumber);
        }

    }


    /*实现广告栏代码*/

    //    @SuppressLint("NewApi")
    private void initHotCampusActivityCycleViewPager() {

        mHotCampusActivityCycleViewPager = (HotCampusActivityCycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        if (mHotCampusActivityCycleViewPager == null) {
            System.out.println("HotCampusActivityCycleViewPager == null");
        }

        for (int i = 0; i < imageUrls.length; i++) {
            HotCampusActivityInformation info = new HotCampusActivityInformation();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            mHotCampusActivityInformation.add(info);
        }

        // 将最后一个ImageView添加进来
        mHotActivityViews.add(ViewFactory.getImageView(this, mHotCampusActivityInformation
                .get(mHotCampusActivityInformation.size() - 1).getUrl()));
        for (int i = 0; i < mHotCampusActivityInformation.size(); i++) {
            mHotActivityViews.add(ViewFactory.getImageView(this, mHotCampusActivityInformation
                    .get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        mHotActivityViews.add(ViewFactory.getImageView(this, mHotCampusActivityInformation
                .get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        mHotCampusActivityCycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        mHotCampusActivityCycleViewPager.setData(mHotActivityViews, mHotCampusActivityInformation, mAdCycleViewListener);
        //设置轮播
        mHotCampusActivityCycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        mHotCampusActivityCycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        mHotCampusActivityCycleViewPager.setIndicatorCenter();

    }

    private HotCampusActivityCycleViewPager.ImageCycleViewListener mAdCycleViewListener =
            new HotCampusActivityCycleViewPager.ImageCycleViewListener() {

                @Override
                public void onImageClick(HotCampusActivityInformation info,
                                         int position, View imageView) {
                    if (mHotCampusActivityCycleViewPager.isCycle()) {
                        Toast.makeText(MainActivity.this,
                                "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                                .show();
                        //跳转到活动详情的主页面
                        Intent intent = new
                                Intent(MainActivity.this, CampusActivityDetailsActivity.class);
                        startActivity(intent);
                    }

                }

            };

    /**
     * 配置UniversalImageLoader(注：这是一个异步加载图片的开源框架)
     */
    private void configUniversalImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

}
