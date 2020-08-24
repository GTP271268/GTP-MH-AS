package com.gtp.myhistory.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public abstract class LoopViewPagerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {

    //    当前页面
    private int currentPosition = 0;

    protected Context mContext;
    protected ArrayList<View> views;
    protected ViewPager mViewPager;

    public LoopViewPagerAdapter(Context context, ArrayList<T> datas, ViewPager viewPager) {
        this.mContext = context;
        this.views = new ArrayList<>();
//        如果数据大于一条
        if (datas.size() > 1) {
//            添加最后一页到第一页
            datas.add(0, datas.get(datas.size() - 1));
//            添加第一页(经过上行的添加已经是第二页了)到最后一页
            datas.add(datas.get(1));
        }
        for (T data : datas) {
            views.add(getItemView(data));
        }
        this.mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        container.addView(views.get(position));
        View view = views.get(position);
        // ((ViewPager) arg0).addView(imgView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击第" + position + "张轮播图", Toast.LENGTH_SHORT).show();
            }
        });


        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    protected abstract View getItemView(T data);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return;
//        若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(views.size() - 2, false);
        } else if (currentPosition == views.size() - 1) {
//        若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1, false);
        }
    }


}
