package Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.liangzihong.viewpager.R;

import cn.bmob.v3.Bmob;

/**
 * Created by Liang Zihong on 2018/12/9.
 */

public class AnimateActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "639a960d0b197dc4f960edca51cd6a9e");
        final View view = View.inflate(this, R.layout.animatelayout, null);
        setContentView(view);

        //渐变展示启动屏

        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });
    }

    /**
     * 跳转到...
     */
    private void redirectTo(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
