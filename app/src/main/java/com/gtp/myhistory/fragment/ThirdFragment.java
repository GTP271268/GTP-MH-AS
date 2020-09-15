package com.gtp.myhistory.fragment;


import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import com.gtp.myhistory.R;
import com.gtp.myhistory.activity.PayDemoActivity;
import com.gtp.myhistory.activity.SMSActivity;
import com.gtp.myhistory.activity.ZxingActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class ThirdFragment extends Fragment {

    private View view;
    private Button payBtn;
    private Button saoBtn;
    private Button smsBtn;
    private Button shareBtn;


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
        smsBtn = view.findViewById(R.id.third_fragment_smsbtn);
        shareBtn = view.findViewById(R.id.third_fragment_sharebtn);

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

        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SMSActivity.class);
                startActivity(intent);

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getUriListForImages());
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
                intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "请选择"));

            }
        });

    }

    /**
     * 设置需要分享的照片放入Uri类型的集合里
     */
    private ArrayList<Uri> getUriListForImages() {
        ArrayList<Uri> myList = new ArrayList<Uri>();
        String imageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File imageDirectory = new File(imageDirectoryPath);
        String[] fileList = imageDirectory.list();
        if(fileList.length != 0) {
            for(int i=0; i<5; i++){
                try{
                    ContentValues values = new ContentValues(7);
                    values.put(MediaStore.Images.Media.TITLE, fileList[i]);
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, fileList[i]);
                    values.put(MediaStore.Images.Media.DATE_TAKEN, new Date().getTime());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.Images.ImageColumns.BUCKET_ID, imageDirectoryPath.hashCode());
                    values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, fileList[i]);
                    values.put("_data", imageDirectoryPath + fileList[i]);
                    ContentResolver contentResolver = view.getContext().getContentResolver();
                    Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    myList.add(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return myList;
    }

}