package Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import Presenters.ISignupPresenter;
import Presenters.SignupPresenter;

/**
 * Created by Liang Zihong on 2018/2/26.
 */

public class SignupActivity extends BaseActivity implements View.OnClickListener,ISignupView {
    private Button ensure_button;
    private Button cancel_button;
    private EditText user;
    private EditText password;
    private ISignupPresenter iSignupPresenter;


    public static void startAction(Context context){
        Intent intent=new Intent(context,SignupActivity.class);
        context.startActivity(intent);
    }


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

        iSignupPresenter=new SignupPresenter(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup_cancel_button:
                finish();
                break;
            case R.id.signup_ensure_button:
                String inputname=user.getText()+"";
                String inputpassword=password.getText()+"";
                iSignupPresenter.startSignup(inputname,inputpassword);
                break;
        }
    }


    /**
     * 下面都是ISignupView的接口实现方法
     */
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void successSignup() {
        Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failSignup() {
        Toast.makeText(this, "此账号已存在或账号密码为空", Toast.LENGTH_SHORT).show();
    }
}

























