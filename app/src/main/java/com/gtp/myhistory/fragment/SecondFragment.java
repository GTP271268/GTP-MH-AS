package com.gtp.myhistory.fragment;

import android.app.Fragment;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.gtp.myhistory.R;
import com.gtp.myhistory.adapter.ImgAdapter;
import com.gtp.myhistory.adapter.LoopViewPagerAdapter;
import com.gtp.myhistory.adapter.MyRecycleAdapter;
import com.gtp.myhistory.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class SecondFragment extends Fragment {
    private ArrayList<String> urls = new ArrayList<>();
    private ViewPager vp;
    private LoopViewPagerAdapter loopVPAdapter;
    private View view;
    private Boolean isLooper;

    private ViewGroup vg;//放置圆点

    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;


    private RecyclerView recyclerView;
    private List<Model> datas;
    private MyRecycleAdapter myRecycleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    Boolean isErr = true;
    int mCurrentCounter;
    int TOTAL_COUNTER;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.second_fragment_layout, container, false);
        //mTextView = (TextView)view.findViewById(R.id.titlename);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        ///mTextView.setText(context);
        //mTextView.setBackgroundColor(20);
        vp = view.findViewById(R.id.second_fragment_viewpager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //vp=view.findViewById(R.id.second_fragment_viewpager);

        //加载底部圆点
        initViewPager();
        initPoint();
        initRecycleView();

    }

    private void initRecycleView() {

        //初始化RecyclerView
        recyclerView = view.findViewById(R.id.myrecycler_view);

        //模拟的数据（实际开发中一般是从网络获取的）
        datas = new ArrayList<>();
        Model model;
        for (int i = 0; i < 15; i++) {
            model = new Model();
            model.setTitle("我是第" + (i + 1) + "条标题");
            model.setContent("第" + (i + 1) + "条内容");
            datas.add(model);
        }

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //创建适配器
        myRecycleAdapter = new MyRecycleAdapter(R.layout.recycle_item_rv, datas);

        //给RecyclerView设置适配器
        recyclerView.setAdapter(myRecycleAdapter);


        //条目点击事件
        myRecycleAdapter.setOnItemClickListener(new BaseItemDraggableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(getActivity(), "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
            }
        });

        //条目长按事件
        myRecycleAdapter.setOnItemLongClickListener(new BaseItemDraggableAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(getActivity(), "长按了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //上拉加载更多
        mCurrentCounter = myRecycleAdapter.getData().size();
        TOTAL_COUNTER = 18;
//        myRecycleAdapter.setEnableLoadMore(true);
//        myRecycleAdapter.disableLoadMoreIfNotFullPage();
        myRecycleAdapter.setOnLoadMoreListener(new BaseItemDraggableAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            // myRecycleAdapter.loadMoreEnd();
                            // myRecycleAdapter.loadMoreEnd(true);
                            myRecycleAdapter.loadMoreComplete();
                        } else {
                            if (isErr) {
                                //成功获取更多数据（可以直接往适配器添加数据）
                                mCurrentCounter = myRecycleAdapter.getData().size();
                                myRecycleAdapter.addData(mCurrentCounter, new Model("下拉加载新增条目", "下拉加载新增内容", ""));
                                mCurrentCounter++;
                                //主动调用加载完成，停止加载
                                Log.d("gtp", "aaa" + mCurrentCounter);
                                myRecycleAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                isErr = true;
                                Toast.makeText(getActivity(), "network_err", Toast.LENGTH_LONG).show();
                                //同理，加载失败也要主动调用加载失败来停止加载（而且该方法会提示加载失败）
                                myRecycleAdapter.loadMoreFail();

                            }
                        }
                        // Toast.makeText(getActivity(), "network_err", Toast.LENGTH_LONG).show();
                        // myRecycleAdapter.setEnableLoadMore(false);
                    }

                }, 1000);
            }
        }, recyclerView);
        myRecycleAdapter.disableLoadMoreIfNotFullPage();

        //下拉刷新
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //这里获取数据的逻辑
                        myRecycleAdapter.addData(0, new Model("上拉刷新新增条目", "下上拉刷新新增内容", ""));
                        swipeRefreshLayout.setRefreshing(false);
                        //下拉刷新后允许上拉加载更多
                        myRecycleAdapter.setEnableLoadMore(true);
                        mCurrentCounter++;
                        Log.d("gtp", "bbb" + mCurrentCounter);
                    }
                }, 1000);
            }
        });


        //添加拖拽、滑动删除
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myRecycleAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // 开启拖拽
        myRecycleAdapter.enableDragItem(itemTouchHelper, R.id.recycle_item_contant, true);
       /* myRecycleAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

            }
        });*/

        // 开启右滑动删除
        myRecycleAdapter.enableSwipeItem();
        myRecycleAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                //myRecycleAdapter.setEnableLoadMore(true);
                mCurrentCounter--;
                Log.d("gtp", "ccc" + mCurrentCounter);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        //添加头部、尾部
        myRecycleAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.myrecycle_header, null));
        myRecycleAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.myrecycle_footer, null));

        //myRecycleAdapter.setHeaderAndEmpty(false);
        // myRecycleAdapter.setHeaderFooterEmpty(false,true);
    }

    private void initViewPager() {

        urls.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3675415932,4054970339&fm=26&gp=0.jpg");
        urls.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1032891202,1418374903&fm=26&gp=0.jpg");
        urls.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3922344982,423380743&fm=26&gp=0.jpg");
        urls.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg");
        urls.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1830914723,3154965800&fm=26&gp=0.jpg");
        urls.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2055446315,3420384081&fm=26&gp=0.jpg");
        loopVPAdapter = new ImgAdapter(view.getContext(), urls, vp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                isLooper = true;
                while (isLooper) {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //这里是设置当前页的下一页
                            vp.setCurrentItem(vp.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
        //设置滑动监听
        //vp.setOnPageChangeListener(this);//过时
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //修改全部的position长度,因为设置无限循环，Viewpager数量多指示点数2
                int newPosition = position - 2;
                //int newPosition = (position-2) % (urls.size()-2);
                // Log.d("gtp", String.valueOf(newPosition));
                if (newPosition < 0) {
                    newPosition = 0;
                } else {
                    newPosition = newPosition + 1;
                    ;
                }
                if (newPosition > 5) {
                    newPosition = 5;
                }
                //循环设置当前页的标记图
                int length = urls.size() - 2;
                for (int i = 0; i < length; i++) {
                    ivPointArray[newPosition].setBackgroundResource(R.mipmap.full_holo);
                    if (newPosition != i) {
                        ivPointArray[i].setBackgroundResource(R.mipmap.empty_holo);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) view.findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[urls.size() - 2];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = urls.size() - 2;
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(getActivity());
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(40, 40));
            iv_point.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.mipmap.full_holo);
            } else {
                iv_point.setBackgroundResource(R.mipmap.empty_holo);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLooper = false;
    }
}