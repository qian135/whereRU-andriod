package com.android.etuan.whereru;

import android.support.v4.view.ViewPager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by zhang on 1/29/2016.
 */
public class MyViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private int mSlideBarMoveUnit,mSlideBarCurrentIndex;

    private ImageView mSlideBarImageView;

    MyViewPagerOnPageChangeListener(int slideBarMoveUnit, int searchPageTextViewCurrentIndex,
                                    ImageView slideBarImageView) {
        mSlideBarMoveUnit = slideBarMoveUnit;
        mSlideBarCurrentIndex = searchPageTextViewCurrentIndex;
        mSlideBarImageView = slideBarImageView;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {

        /**
         * 设置在X轴方向的动画。
         * 第一个参数fromXDelta。Image的左上角为(0,0),该参数表示在X轴方向的动画开始位置距离Image左上角的距离
         * 第二个参数toXDelta。该参数表示在X轴方向动画的结束位置距离Image左上角的距离。
         * 第三和第四参数同上
         * 位置的开始点是Image的原始位置，而不是setFillAfter(true)之后的位置
         */
        Animation animation;
        animation = new TranslateAnimation
                (mSlideBarCurrentIndex*mSlideBarMoveUnit, 
                        position*mSlideBarMoveUnit, 0, 0);
        mSlideBarCurrentIndex = position;
        animation.setFillAfter(true);
        animation.setDuration(500);
        mSlideBarImageView.startAnimation(animation);

    }

    int getmSlideBarCurrentIndex() {
        return mSlideBarCurrentIndex;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
