package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by zhang on 1/29/2016.
 */

public class MySlideBarHandler {

    private Context mContext;

    private int mSlideBarOffset;
    private int mSlideBarWidth;
    private int mTopSlideBarDivideNumber;
    private int mSlideBarIconID;

    private ImageView mSlideBarImageView;


    MySlideBarHandler(Context context, int topSlideBarDivideNumber,int slideBarIconID,
                      ImageView slideBarImageView) {
        mContext = context;
        mTopSlideBarDivideNumber = topSlideBarDivideNumber;
        mSlideBarIconID = slideBarIconID;
        mSlideBarImageView = slideBarImageView;
        initSlideBarSlideBarAnimation();
    }

    /**
     * 初始化滑动条的动画
     */
    private void initSlideBarSlideBarAnimation() {
        //获取滑动条的宽度
        mSlideBarWidth = BitmapFactory.
                decodeResource(mContext.getResources(),
                        mSlideBarIconID).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        //因为getWindowManager()是Activity的方法，所以从Activity传一个Context过来，然后进行强制
        //类型转换成Activity
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        mSlideBarOffset =
                (screenW / mTopSlideBarDivideNumber - mSlideBarWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(mSlideBarOffset, 0);
        mSlideBarImageView.setImageMatrix(matrix);// 设置动画初始位置

    }

    public int getSlideBarMoveUnit() {
        return mSlideBarOffset * 2 + mSlideBarWidth;
    }

}
