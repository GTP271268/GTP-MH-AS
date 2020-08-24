package com.gtp.myhistory.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gtp.myhistory.activity.LoginActivity;
import com.gtp.myhistory.R;
import com.gtp.myhistory.adapter.RecycleAdapterDome;
import com.gtp.myhistory.utils.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class FirstFragment extends Fragment {

    //private RecyclerView recyclerView;//声明RecyclerView
    private RecycleAdapterDome adapterDome;//声明适配器
    //private Context context;
    private List<String> list;

    private View view;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.first_fragment_layout, container, false);
        /*
        mTextView = (TextView)view.findViewById(R.id.titlename);
        mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        /mTextView.setText(context);
        mTextView.setBackgroundColor(20);
        */
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = view.findViewById(R.id.first_fragment_textview);
        Button messagebtn = view.findViewById(R.id.first_fragment_message);
        Button photobtn = view.findViewById(R.id.first_fragment_photo);
        Button videobtn = view.findViewById(R.id.first_fragment_video);

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.first_fragment_recyclerView);
        //final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        //recyclerView.setLayoutManager(layoutManager);
        //LinearLayoutManager类似listview
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //GridLayoutManager类似于gridview
        // recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        //StaggeredGridLayoutManager又是类似瀑布流
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
        initRecycleItemData();//初始化数据

        //传递适配器数据
        adapterDome = new RecycleAdapterDome(view.getContext(), list);
        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recycle设置适配器
        recyclerView.setAdapter(adapterDome);
        //recycle设置点击
        adapterDome.setOnitemClickLintener(new RecycleAdapterDome.OnitemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "点击了一下" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //recycle设置长按
        adapterDome.setOnLongClickListener(new RecycleAdapterDome.OnLongClick() {
            @Override
            public void onLongClick(int position) {
                Toast.makeText(getActivity(), "长按了一下" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    //初始化数据
    private void initRecycleItemData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "个测试");
        }
        String url = "https://www.httpbin.org/get?id=1";

        OkHttpUtils.getAsync(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("success");
                    }
                });
//
//                String string= response.body().string();
//                Log.d("gtp1",""+string);
//                if (!TextUtils.isEmpty(string)) {
//
//                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //这个地方的handler最好封装 防止内存泄漏
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                String res = (String) msg.obj;
                textView.setText(res);
                Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            }
        }
    };


}