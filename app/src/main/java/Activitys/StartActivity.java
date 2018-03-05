package Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liangzihong.viewpager.R;

/**
 * Created by Liang Zihong on 2018/2/26.
 */

public class StartActivity extends BaseActivity implements View.OnClickListener {

    private Button login_button;
    private Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        init();

    }


    private void init() {
        login_button = (Button) findViewById(R.id.welcome_login_button);
        signup_button = (Button) findViewById(R.id.welcome_signup_button);

        login_button.setOnClickListener(this);
        signup_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.welcome_login_button:
                LoginActivity.startAction(this);
                break;
            case R.id.welcome_signup_button:
                SignupActivity.startAction(this);
                break;
        }
    }


}