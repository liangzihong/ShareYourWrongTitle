package com.example.liangzihong.viewpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Liang Zihong on 2018/2/26.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button ensure_button;
    private Button cancel_button;
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        init();
    }


    private void init(){
        ensure_button=(Button)findViewById(R.id.login_ensure_button);
        cancel_button=(Button)findViewById(R.id.login_cancel_button);
        ensure_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);

        user=(EditText) findViewById(R.id.login_inputUser_editText);
        password=(EditText) findViewById(R.id.login_inputPassword_editText);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_ensure_button:
                String inputname=user.getText()+"";
                String inputpassword=password.getText()+"";

                if(useSharedPreferences(inputname,inputpassword)){
                    Toast.makeText(this, "进入我不是微信", Toast.LENGTH_SHORT).show();
                    
                    Intent intent1=new Intent(this,MainActivity.class);
                    startActivity(intent1);
                }

                else
                    Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
                break;

            case R.id.login_cancel_button:
                this.finish();
                break;
        }
    }

    /**
     * 通过传入  输入的姓名和密码作为参数，判断是否是已经注册过的账号。
     * @param inputname
     * @param inputpassword
     * @return
     */
    private boolean useSharedPreferences(String inputname,String inputpassword){
        SharedPreferences pref=getSharedPreferences("FirstStore",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        if(pref.getString(inputname,"").equals(inputpassword)){
            return true;
        }
        else return false;
    }
}
