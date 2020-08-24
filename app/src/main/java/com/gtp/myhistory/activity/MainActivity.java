package com.gtp.myhistory.activity;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gtp.myhistory.R;
import com.gtp.myhistory.fragment.FirstFragment;
import com.gtp.myhistory.fragment.SecondFragment;
import com.gtp.myhistory.fragment.ThirdFragment;

public class MainActivity extends AppCompatActivity {
    private static final int PERMS_REQUEST_CODE = 200;
    private static final int SCAN_REQUEST_CODE = 1;
    private TextView mTextMessage;
    private FirstFragment f1;
    private SecondFragment f2;
    private ThirdFragment f3;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (f1 == null) {
                        // f1 = FragmentTest.newInstance("发现", R.layout.first_fragment_layout);
                        f1 = new FirstFragment();
                        transaction.add(R.id.FramePage, f1, "A");
                    } else {
                        transaction.show(f1);
                    }
                    mTextMessage.setText(R.string.title_home);
                    Log.d("gtp", "1");
                    break;
                // return true;
                case R.id.navigation_dashboard:
                    if (f2 == null) {
                        //  f2 = FragmentTest.newInstance("我的", R.layout.second_fragment_layout);//"第二个Fragment"
                        f2 = new SecondFragment();
                        transaction.add(R.id.FramePage, f2, "B");
                    } else {
                        transaction.show(f2);
                    }
                    mTextMessage.setText(R.string.title_dashboard);
                    Log.d("gtp", "2");

                    break;

                //return true;
                case R.id.navigation_notifications:
                    if (f3 == null) {
                        //  f3 = FragmentTest.newInstance("关于", R.layout.third_fragment_layout);//"第三个Fragment"
                        f3 = new ThirdFragment();
                        transaction.add(R.id.FramePage, f3, "C");
                    } else {
                        transaction.show(f3);
                    }
                    mTextMessage.setText(R.string.title_notifications);
                    Log.d("gtp", "3");
                    break;
                //return true;

            }
            transaction.commit();
            return true;
        }

        private void hideAllFragment(FragmentTransaction transaction) {
            if (f1 != null) {
                transaction.hide(f1);
            }
            if (f2 != null) {
                transaction.hide(f2);
            }
            if (f3 != null) {
                transaction.hide(f3);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        f1 = new FirstFragment();
        //FragmentManager fm=getFragmentManager();
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.FramePage, f1, "A");
        tx.commit();

        //6.0版本或以上需请求权限
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
        mTextMessage.setOnCreateContextMenuListener(this);//给组件注册Context菜单
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
                v.showContextMenu();//单击直接显示Context菜单
            }

        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        //添加菜单项 menu.add(参数一：分组,选项的id,菜单项的显示顺序(默认是0,代表按照添加的顺序),"选项显示的字段");
        //menu.add(0,1,0,"收藏");
        //menu.add(0,2,0,"举报");
        //menu.add(0,3,0,"关注");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String str = null;
        switch (item.getItemId()) {
            case R.id.school:
            case R.id.college:
                str = "一级标签:" + item.getItemId() + "|" + item.toString();
                break;
            case R.id.njdx:
            case R.id.whdx:
            case R.id.english:
            case R.id.art:
                str = "二级标签:" + item.getItemId() + "|" + item.toString();
                break;
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        return super.onContextItemSelected(item);
    }
    //标题栏选项菜单
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载 布局实现
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String str = null;
        switch (item.getItemId()) {
            case R.id.school:
            case R.id.college:
                str = "一级标签:" + item.getItemId() + "|" + item.toString();
                break;
            case R.id.njdx:
            case R.id.whdx:
            case R.id.english:
            case R.id.art:
                str = "二级标签:" + item.getItemId() + "|" + item.toString();
                break;
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            String input = "";
            //String input = data.getStringExtra(ScanActivity.INTENT_EXTRA_RESULT);

            Toast.makeText(this, "扫描结果1:" + input, Toast.LENGTH_SHORT).show();
        }
    }


}
