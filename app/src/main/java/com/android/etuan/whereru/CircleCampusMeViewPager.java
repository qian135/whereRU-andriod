package com.android.etuan.whereru;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhang on 1/25/2016.
 */
public class CircleCampusMeViewPager extends ViewPager {

    public CircleCampusMeViewPager(Context context) {
        super(context);
    }

    public CircleCampusMeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

     /*下面两段代码禁用MyViewPaper的滑动功能*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
//        return super.onTouchEvent(ev);
    }
}
