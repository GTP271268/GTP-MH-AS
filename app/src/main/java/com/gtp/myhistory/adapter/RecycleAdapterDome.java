package com.gtp.myhistory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtp.myhistory.R;

import java.util.List;


/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class RecycleAdapterDome extends RecyclerView.Adapter<RecycleAdapterDome.MyViewHolder> {
    private Context context;
    private List<String> list;
    private View inflater;
    private OnitemClick onitemClick;   //定义点击事件接口
    private OnLongClick onLongClick;  //定义长按事件接口

    //构造方法，传入数据
    public RecycleAdapterDome(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener(OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }

    //定义设置长按事件监听的方法
    public void setOnLongClickListener(OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    //定义一个长按事件的接口
    public interface OnLongClick {
        void onLongClick(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item_main_activity_rv, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //将数据和控件绑定
        holder.textView.setText(list.get(position));
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
        final int layoutPosition = holder.getLayoutPosition();
        if (onitemClick != null) {
            holder.recycleItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position);
                }
            });
        }

        if (onLongClick != null) {
            holder.recycleItemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //在TextView的地方进行长按事件的监听，并实现长按接口
                    onLongClick.onLongClick(position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycleItemLayout;
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            recycleItemLayout = itemView.findViewById(R.id.recycle_item_layout);
            textView = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.iv_meizhi);
        }
    }
}
