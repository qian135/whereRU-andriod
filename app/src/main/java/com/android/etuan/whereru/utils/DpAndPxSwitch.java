package com.android.etuan.whereru.utils;

import android.content.Context;

/**
 * Created by zhang on 2/26/2016.
 */
public class DpAndPxSwitch {

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


}
