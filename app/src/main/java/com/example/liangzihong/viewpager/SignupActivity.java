package com.example.liangzihong.viewpager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Liang Zihong on 2018/2/26.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private Button ensure_button;
    private Button cancel_button;
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        init();

    }

    private void init(){
        ensure_button=(Button)findViewById(R.id.signup_ensure_button);
        cancel_button=(Button)findViewById(R.id.signup_cancel_button);
        user=(EditText) findViewById(R.id.signup_inputUser_editText);
        password=(EditText) findViewById(R.id.signup_inputPassword_editText);

        ensure_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup_cancel_button:
                finish();
                break;
            case R.id.signup_ensure_button:
                userSharedPreferences();
                break;
        }
    }


    private void userSharedPreferences(){
        SharedPreferences pref=getSharedPreferences("FirstStore",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(user.getText()+"",password.getText()+"");
        editor.commit();
        finish();
    }
}

























