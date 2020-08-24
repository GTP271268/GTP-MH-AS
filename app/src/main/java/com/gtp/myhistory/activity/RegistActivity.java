package com.gtp.myhistory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gtp.myhistory.R;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextMessage;
    private EditText editText_name;
    private EditText editText_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        mTextMessage = findViewById(R.id.message);

        editText_name = findViewById(R.id.regist_activity_name);
        editText_pass = findViewById(R.id.regist_activity_password);
        Button button1 = findViewById(R.id.regist_activity_back);
        Button button2 = findViewById(R.id.regist_activity_commit);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
//        editText_name.setHint("请输入登陆账户");
//        editText_pass.setHint("请输入登陆密码");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist_activity_back:

                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            // return true;
            case R.id.regist_activity_commit:

                Intent intent2 = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;
        }

    }
}
