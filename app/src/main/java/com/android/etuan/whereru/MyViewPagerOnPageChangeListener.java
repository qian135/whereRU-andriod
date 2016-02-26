package com.android.etuan.whereru;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class MyViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private View mLinearLayoutView;
    private ImageView mSlideBarImageView;

    private int mScrollState;

    private float mSlideBarWidth;
    private int mSlideBarCurrentIndex;//设置滑动条上字体颜色，省得要遍历
    private int mTopSlideBarDivideNumber;

    //设置动画时间
    private long mAnimationDurationTime = 40;

    private long mStartTime;
    private long mCurrentTime;


    //这里的linearLayoutView是滑动条上文字线性布局的View(布局是LinearLayout)
    MyViewPagerOnPageChangeListener(Context context, View linearLayoutView,
                                    ImageView slideBarImageView, int topSlideBarDivideNumber) {
        mContext = context;
        mLinearLayoutView = linearLayoutView;
        mSlideBarImageView = slideBarImageView;
        mTopSlideBarDivideNumber = topSlideBarDivideNumber;

        //计算滑动条宽度
        calculateSlideBarWidth();
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

        //最终选中哪个页面就将滑动条移到哪个页面去，不过单靠这个动画是不够的，因为如果滑动幅度不大，
        //没切换到下一个Tab页，是不会执行onPageSelected这个方法的，这样滑动条就可能停在半路了，所
        //以在onPageScrollStateChanged再执行了一次动画帮助滑动条滑回原来的位置
        ObjectAnimator.ofFloat(mSlideBarImageView, "translationX",
                mSlideBarImageView.getTranslationX(), position * mSlideBarWidth)
                .setDuration(mAnimationDurationTime).start();

        ViewGroup viewGroup = (ViewGroup) mLinearLayoutView;
        TextView textView1 = (TextView) viewGroup.getChildAt(mSlideBarCurrentIndex);
        textView1.setTextColor(ContextCompat.getColor(mContext, R.color.black));//设置原先所在字体的颜色
        TextView textView2 = (TextView) viewGroup.getChildAt(position);
        textView2.setTextColor(ContextCompat.getColor(mContext, R.color.blue));//设置要滑向的字体的颜色

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
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度

        //计算滑动条的宽度
        ViewGroup.LayoutParams slideBarImageViewLayoutParams = mSlideBarImageView.getLayoutParams();
        mSlideBarWidth = slideBarImageViewLayoutParams.width =
                ((screenW - 2 * dp2px(mContext, 10)) / mTopSlideBarDivideNumber);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}