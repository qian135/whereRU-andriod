package com.android.etuan.whereru.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.android.etuan.whereru.MyVolleySingleton;
import com.android.etuan.whereru.R;
import com.android.volley.toolbox.NetworkImageView;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

    public static ImageView getImageView(Context context, String url) {

        NetworkImageView imageView = (NetworkImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);

        //设置加载过程中的默认图片
        imageView.setDefaultImageResId(R.drawable.loading_image);
        //设置加载出错时的图片
        imageView.setErrorImageResId(R.drawable.loading_image_error);

        imageView.setImageUrl(url,
                MyVolleySingleton.getInstance(context.getApplicationContext()).getImageLoader());

        return imageView;

    }

}
