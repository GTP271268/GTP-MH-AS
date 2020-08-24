package com.gtp.myhistory.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class FragmentTest extends Fragment {

    private String context = "xxxxxxxxxxxxx";
    private TextView mTextView;
    //要显示的页面
    private int FragmentPage;

    public static FragmentTest newInstance(String context, int iFragmentPage) {

        FragmentTest myFragment = new FragmentTest();
        myFragment.context = context;
        myFragment.FragmentPage = iFragmentPage;
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = context;
        View view = inflater.inflate(FragmentPage, container, false);
        //mTextView = (TextView)view.findViewById(R.id.titlename);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        ///mTextView.setText(context);
        //mTextView.setBackgroundColor(20);
        return view;
    }


}