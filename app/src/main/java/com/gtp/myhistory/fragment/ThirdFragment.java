package com.gtp.myhistory.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import com.gtp.myhistory.R;
import com.gtp.myhistory.activity.PayDemoActivity;
import com.gtp.myhistory.activity.ZxingActivity;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class ThirdFragment extends Fragment {

    private View view;
    private Button payBtn;
    private Button saoBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.third_fragment_layout, container, false);
        //mTextView = (TextView)view.findViewById(R.id.titlename);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        ///mTextView.setText(context);
        //mTextView.setBackgroundColor(20);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //vp=view.findViewById(R.id.second_fragment_viewpager);

        PayTask payTask = new PayTask(getActivity());
        String version = payTask.getVersion();
        Toast.makeText(getActivity(), "支付版本：" + version, Toast.LENGTH_SHORT).show();
        payBtn = view.findViewById(R.id.third_fragment_paybtn);
        saoBtn = view.findViewById(R.id.third_fragment_saobtn);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayDemoActivity.class);
                startActivity(intent);

            }
        });

        saoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZxingActivity.class);
                startActivity(intent);

            }
        });

    }


}