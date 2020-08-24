package com.gtp.myhistory.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gtp.myhistory.model.Model;
import com.gtp.myhistory.utils.ImageUtils;

import java.util.ArrayList;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class ImgAdapter extends LoopViewPagerAdapter<String> {
    // 线程变量

    ImageView imageView;
    Context mcontex;
    String url;
    private ViewGroup.LayoutParams layoutParams;

    public ImgAdapter(Context context, ArrayList<String> datas, ViewPager viewPager) {
        super(context, datas, viewPager);
        this.mcontex = context;
    }


    @Override
    protected View getItemView(String data) {
        this.url = data;
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageUtils.loadImage(mContext, data, imageView);


        return imageView;
    }


}
