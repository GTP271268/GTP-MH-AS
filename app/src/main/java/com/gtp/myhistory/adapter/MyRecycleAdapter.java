package com.gtp.myhistory.adapter;

import android.graphics.ColorSpace;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gtp.myhistory.R;
import com.gtp.myhistory.model.Model;

import java.util.List;

/**
 * @author Gtp
 * @description:
 * @date :2020/6/1 0001 9:05
 */
public class MyRecycleAdapter extends BaseItemDraggableAdapter<Model, BaseViewHolder> {


    public MyRecycleAdapter(int layoutResId, @Nullable List<Model> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Model item) {
        //可链式调用赋值
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent())
                .setImageResource(R.id.iv_img, R.mipmap.ic_launcher);

        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }
}

