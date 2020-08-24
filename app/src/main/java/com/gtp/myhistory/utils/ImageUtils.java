package com.gtp.myhistory.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Administrator on 2016/8/29.
 */
public class ImageUtils {

    /**
     * 加载图片
     * //DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * //DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
     * //DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
     * //DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     */
    public static void loadImage(Context context, String url, final ImageView imageView) {
        if (imageView == null) return;
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Activity activity, String url, final ImageView imageView) {
        if (imageView == null) return;
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Fragment fragment, String url, final ImageView imageView) {
        if (imageView == null) return;
        Glide.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Activity activity, int res, ImageView imageView) {
        if (imageView == null) return;
        Glide.with(activity).load(res).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

}
