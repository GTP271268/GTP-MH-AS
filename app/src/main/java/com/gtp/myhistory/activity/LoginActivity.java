package com.gtp.myhistory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gtp.myhistory.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextMessage;
    private EditText editText_name;
    private EditText editText_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTextMessage = findViewById(R.id.message);

//        editText_name=findViewById(R.id.login_activity_name);
//        editText_pass=findViewById(R.id.login_activity_password);
        editText_name = findViewById(R.id.et_username_login);
        editText_pass = findViewById(R.id.et_password_login);
        Button button1 = findViewById(R.id.login_activity_login);
        Button button2 = findViewById(R.id.login_activity_regist);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_activity_login:

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            // return true;
            case R.id.login_activity_regist:
                Intent intent2 = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent2);
                finish();

                break;
        }

    }
}
