package Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import Presenters.ILoginPresenter;
import Presenters.LoginPresenter;

/**
 * Created by Liang Zihong on 2018/2/26.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView {
    private Button ensure_button;
    private Button cancel_button;
    private EditText user;
    private EditText password;
    private ILoginPresenter iLoginPresenter;

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

        iLoginPresenter=new LoginPresenter(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_ensure_button:
                String inputname=user.getText()+"";
                String inputpassword=password.getText()+"";
                iLoginPresenter.startLogin(inputname,inputpassword);


            case R.id.login_cancel_button:
                this.finish();
                break;
        }
    }


    /**
     * 下面是接口的方法
     */
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void successLogin() {
        Toast.makeText(this, "进入我不是微信", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(this,MainActivity.class);
        startActivity(intent1);

    }

    @Override
    public void failLogin() {
        Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
    }

}
