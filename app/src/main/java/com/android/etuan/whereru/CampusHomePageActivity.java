package com.android.etuan.whereru;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.etuan.whereru.utils.DpAndPxSwitch;

import java.util.ArrayList;
import java.util.List;

public class CampusHomePageActivity extends Activity implements ViewPager.OnPageChangeListener {

    //帮助实现在ViewPager上实现Activity的切换
    private LocalActivityManager mLocalActivityManager1;

    /* 顶部布局相关代码 */

    private TextView mDropdownTextView;
    private ImageView mDropdownImageView;

    private PopupWindow mPopupWindow;
    private ListView mCustomSpinnerListView;
    private List<String> mCustomSpinnerListViewContent;

    private ImageButton mTopSearchImageButton;


    /*该页面滑动条相关代码*/

    private View mLinearLayoutView;
    private ImageView mSlideBarImageView;

    private int mScrollState;

    private float mSlideBarWidth;
    private int mSlideBarCurrentIndex;//设置滑动条上字体颜色，省得要遍历
    private int mTopSlideBarDivideNumber = 3;

    //设置动画时间
    private long mAnimationDurationTime = 40;

    private long mStartTime;
    private long mCurrentTime;


    /*实现 校园活动，校园竞赛，校园团队 三页面ViewPager切换的代码*/

    private ViewPager mCampusHomeViewPager;//校园这个大类的主ViewPager
    //存放 校园活动，校园竞赛，校园团队 3个View
    private List<View> mCampusActivityCompetitionTeamViews;
    //校园活动，校园竞赛，校园团队 3个TextView
    private TextView mCampusActivityTextView, mCampusCompetitionTextView, mCampusTeamTextView;

    private MyViewPagerOnPageChangeListener mMyViewPagerOnPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_home_page);

        //帮助实现在ViewPager上实现Activity的切换
        mLocalActivityManager1 = new LocalActivityManager(this, true);
        mLocalActivityManager1.dispatchCreate(savedInstanceState);

        /* 顶部布局相关代码 */


        initCustomSpinner();


        //设置顶部搜索图标的监听
        mTopSearchImageButton = (ImageButton) findViewById(R.id.top_search);
        mTopSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampusHomePageActivity.this, SearchPage.class);
                startActivity(intent);
            }
        });

        /*实现 校园活动，校园竞赛，校园团队 三页面ViewPager切换的代码*/

        //初始化 校园活动，校园竞赛，校园团队 标题栏的TextView及下面的滑动条
        initTextViewAndSlideBar();
        //校园活动，校园竞赛，校园团队 ViewPager的初始化
        initCampusActivityCompetitionTeamViewPager();

    }

    private void initCustomSpinner() {

        mDropdownTextView = (TextView) findViewById(R.id.dropdown_text_view);
        mDropdownImageView = (ImageView) findViewById(R.id.dropdown_image_view);

        mCustomSpinnerListView = (ListView) getLayoutInflater().inflate(
                R.layout.custom_spinner_list_view, null).findViewById(R.id.custom_spinner_list_view);

        mCustomSpinnerListViewContent = new ArrayList<>();

        int popupWindowWidth = mDropdownTextView.getLayoutParams().width;

        mPopupWindow = new PopupWindow(mCustomSpinnerListView,
                popupWindowWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.transparent));

        mDropdownTextView = (TextView) findViewById(R.id.dropdown_text_view);
        mDropdownImageView = (ImageView) findViewById(R.id.dropdown_image_view);

        mDropdownImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(mDropdownTextView);
            }
        });

        mCustomSpinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDropdownTextView.setText(mCustomSpinnerListView
                        .getItemAtPosition(position).toString());
                mPopupWindow.dismiss();
            }
        });

        mCustomSpinnerListViewContent.add("全部");
        mCustomSpinnerListViewContent.add("正在进行");
        mCustomSpinnerListViewContent.add("即将开始");
        mCustomSpinnerListViewContent.add("已结束");
        mCustomSpinnerListView.setAdapter(
                new CustomSpinnerAdapter(this, mCustomSpinnerListViewContent));

    }


    /**
     * 初始化 校园活动，校园竞赛，校园团队 标题栏的TextView及下面的滑动条
     * （这里主要是实例化，并设置监听）
     */
    private void initTextViewAndSlideBar() {

        mCampusActivityTextView = (TextView) findViewById(R.id.campus_activity_text_view);
        mCampusCompetitionTextView = (TextView) findViewById(R.id.campus_competition_text_view);
        mCampusTeamTextView = (TextView) findViewById(R.id.campus_team_text_view);

        //给 校园活动，校园竞赛，校园团队 标题栏的TextView赋值其对应的ViewPager中View页面的编号，
        //这样当点击这些TextView中的文字的时候，将该编号值赋给mCampusHomeViewPagerCurrentNumber，
        //然后ViewPage.setCurrentItem(mCampusHomeViewPagerCurrentNumber)即可

        mCampusActivityTextView
                .setOnClickListener(new MyOnClickListener(0));
        mCampusCompetitionTextView
                .setOnClickListener(new MyOnClickListener(1));
        mCampusTeamTextView
                .setOnClickListener(new MyOnClickListener(2));

        //主要是为该页面滑动条服务的

        mLinearLayoutView = findViewById(R.id.campus_home_page_text_view_linear_layout);
        mSlideBarImageView = (ImageView) findViewById(R.id.campus_home_page_slide_bar);
        calculateSlideBarWidth(); //计算滑动条宽度

    }

    /**
     * 校园活动，校园竞赛，校园团队 ViewPager的初始化
     */
    private void initCampusActivityCompetitionTeamViewPager() {

        mCampusHomeViewPager = (ViewPager) findViewById(R.id.campus_home_viewpager);

        mCampusActivityCompetitionTeamViews = new ArrayList<>();


        Intent intent0 = new Intent(this, CampusActivityHomePageActivity.class);
        mCampusActivityCompetitionTeamViews.add(getView("CampusActivityHomePageActivity", intent0));

        Intent intent1 = new Intent(this, CampusCompetitionHomePageActivity.class);
        mCampusActivityCompetitionTeamViews.add(getView("CampusCompetitionHomePageActivity", intent1));

        Intent intent2 = new Intent(this, CampusTeamHomePageActivity.class);
        mCampusActivityCompetitionTeamViews.add(getView("CampusTeamHomePageActivity", intent2));

        mCampusHomeViewPager
                .setAdapter(new MyViewPagerAdapter(mCampusActivityCompetitionTeamViews));
        mCampusHomeViewPager.addOnPageChangeListener(this);

    }

    //帮助在ViewPager上实现Activity的切换
    private View getView(String id, Intent intent) {
        return mLocalActivityManager1.startActivity(id, intent).getDecorView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //防止动画触发频率太高
//        mCurrentTime = System.currentTimeMillis();
//        if (mScrollState == ViewPager.SCROLL_STATE_DRAGGING && (mCurrentTime - mStartTime > 250)) {
//            ObjectAnimator.ofFloat(mSlideBarImageView, "translationX",
//                    mSlideBarImageView.getTranslationX(),
//                    position * mSlideBarWidth + positionOffset * mSlideBarWidth)
//                    .setDuration(mAnimationDurationTime).start();
//            mStartTime = mCurrentTime;
//        }

        //正在拖动界面，执行动画使滑动条与手一起移动
        if (mScrollState == ViewPager.SCROLL_STATE_DRAGGING) {
            ObjectAnimator.ofFloat(mSlideBarImageView, "translationX",
                    mSlideBarImageView.getTranslationX(),
                    position * mSlideBarWidth + positionOffset * mSlideBarWidth)
                    .setDuration(mAnimationDurationTime).start();
            mStartTime = mCurrentTime;
        }

    }

    @Override
    public void onPageSelected(int position) {

        refreshDropdownListContent(position);

        //最终选中哪个页面就将滑动条移到哪个页面去，不过单靠这个动画是不够的，因为如果滑动幅度不大，
        //没切换到下一个Tab页，是不会执行onPageSelected这个方法的，这样滑动条就可能停在半路了，所
        //以在onPageScrollStateChanged再执行了一次动画帮助滑动条滑回原来的位置
        ObjectAnimator.ofFloat(mSlideBarImageView, "translationX",
                mSlideBarImageView.getTranslationX(), position * mSlideBarWidth)
                .setDuration(mAnimationDurationTime).start();

        ViewGroup viewGroup = (ViewGroup) mLinearLayoutView;
        TextView textView1 = (TextView) viewGroup.getChildAt(mSlideBarCurrentIndex);
        textView1.setTextColor(ContextCompat.getColor(this, R.color.black));//设置原先所在字体的颜色
        TextView textView2 = (TextView) viewGroup.getChildAt(position);
        textView2.setTextColor(ContextCompat.getColor(this, R.color.blue));//设置要滑向的字体的颜色

        mSlideBarCurrentIndex = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        mScrollState = state;
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            ObjectAnimator.ofFloat(mSlideBarImageView, "translationX",
                    mSlideBarImageView.getTranslationX(), mSlideBarCurrentIndex * mSlideBarWidth)
                    .setDuration(mAnimationDurationTime).start();
        }

    }

    int getmSlideBarCurrentIndex() {
        return mSlideBarCurrentIndex;
    }

    /**
     * 计算滑动条宽度
     * 左右两边各留10dp在布局文件 campus_home_page.xml 中写死
     */
    private void calculateSlideBarWidth() {

        DisplayMetrics dm = new DisplayMetrics();
        //因为getWindowManager()是Activity的方法，所以从Activity传一个Context过来，然后进行强制
        //类型转换成Activity
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度

        //计算滑动条的宽度
        ViewGroup.LayoutParams slideBarImageViewLayoutParams = mSlideBarImageView.getLayoutParams();
        mSlideBarWidth = slideBarImageViewLayoutParams.width =
                ((screenW - 2 * DpAndPxSwitch.dp2px(this, 10)) / mTopSlideBarDivideNumber);

    }


    /**
     * 当在 校园活动，校园竞赛，校园团队 三者间切换时，界面顶部下拉列表的内容能做相应变化
     */
    private void refreshDropdownListContent(int position) {

        mCustomSpinnerListViewContent.clear();

        if (position == 0 || position == 1) {
            mDropdownTextView.setText("全部");
            mCustomSpinnerListViewContent.add("全部");
            mCustomSpinnerListViewContent.add("正在进行");
            mCustomSpinnerListViewContent.add("即将开始");
            mCustomSpinnerListViewContent.add("已结束");
        } else {
            mDropdownTextView.setText("校园组织");
            mCustomSpinnerListViewContent.add("校园组织");
            mCustomSpinnerListViewContent.add("校园社团");
            mCustomSpinnerListViewContent.add("兴趣团队");
            mCustomSpinnerListViewContent.add("竞赛团队");
            mCustomSpinnerListViewContent.add("创业团队");
        }

        mCustomSpinnerListView.setAdapter(
                new CustomSpinnerAdapter(this, mCustomSpinnerListViewContent));

    }

    class CustomSpinnerAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mList;

        private LayoutInflater mLayoutInflater;

        public CustomSpinnerAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.custom_spinner_list_view_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) convertView.findViewById(
                        R.id.custom_spinner_list_view_item_text_view);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mTextView.setText(mList.get(position));
            return convertView;
        }
    }

    public class ViewHolder {
        public TextView mTextView;
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
            mCampusHomeViewPager.setCurrentItem(mViewPagerNumber);
        }

    }

}
