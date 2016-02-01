package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
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

/**
 * Created by zhang on 1/30/2016.
 */
public class CampusHomePageActivity extends Activity {

    private View mCampusHomePageView;

    /*该页面顶部滑动条相关代码*/
    private int mSlideBarDivideNumber = 3;  //顶部滑动菜单划分的块数
    private MySlideBarHandler mMySlideBarHandler;//顶部滑动条的处理类
    private ImageView mSlideBar;//滑动条
    private int mSlideBarCurrentIndex = 0;

    private ImageButton mTopSearchImageButton;
    
    /*实现校园活动，校园竞赛，校园团队三页面ViewPager切换的代码*/

    private View mCampusActivityHomePageView, mCampusCompetitionHomePageView,
            mCampusTeamHomePageView;

    private ViewPager mCampusHomeViewPager;//校园这个大类的主ViewPager
    //存放 校园活动，校园竞赛，校园团队 3个View
    private List<View> mCampusActivityCompetitionTeamViews;
    //校园活动，校园竞赛，校园团队 3个TextView
    private TextView mCampusActivityTextView, mCampusCompetitionTextView, mCampusTeamTextView;

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
        setContentView(R.layout.campus_home_page);


         /*实现 校园活动，校园竞赛，校园团队 三页面ViewPager切换的代码*/

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
                Intent intent = new Intent(CampusHomePageActivity.this, CampusActivityDetailsActivity.class);
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
                        Intent(CampusHomePageActivity.this, CampusCompetitionDetailsActivity.class);
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
                        Intent(CampusHomePageActivity.this, CampusTeamDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化 校园活动，校园竞赛，校园团队 下面滑动条的动画
     */
    private void initCampusActivityCompetitionTeamSlideBarAnimation() {

        //注意CampusHomePageActivity并不是校园大类的主页，它是 圈子，校园，我 这个层面的页面
        mSlideBar = (ImageView) findViewById
                (R.id.campus_home_page_slide_bar);
        mMySlideBarHandler = new MySlideBarHandler(this,mSlideBarDivideNumber,
                R.drawable.campus_home_page_slide_bar_icon,mSlideBar);

    }


    /**
     * 初始化 校园活动，校园竞赛，校园团队 标题栏的TextView（这里主要是实例化，并设置监听）
     */
    private void initCampusActivityCompetitionTeamTextView() {
        mCampusActivityTextView = (TextView) findViewById(R.id.campus_activity_text_view);
        mCampusCompetitionTextView = (TextView) findViewById(R.id.campus_competition_text_view);
        mCampusTeamTextView = (TextView) findViewById(R.id.campus_team_text_view);

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

        mCampusHomeViewPager = (ViewPager) findViewById(R.id.campus_home_viewpager);

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

        //主要是为该页面滑动条服务的
        MyViewPagerOnPageChangeListener myViewPagerOnPageChangeListener =
                new MyViewPagerOnPageChangeListener(mMySlideBarHandler.getSlideBarMoveUnit(),
                        mSlideBarCurrentIndex, mSlideBar);
        mCampusHomeViewPager.setOnPageChangeListener(myViewPagerOnPageChangeListener);

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
                        Toast.makeText(CampusHomePageActivity.this,
                                "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                                .show();
                        //跳转到活动详情的主页面
                        Intent intent = new
                                Intent(CampusHomePageActivity.this, CampusActivityDetailsActivity.class);
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
