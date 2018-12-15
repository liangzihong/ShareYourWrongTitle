package Presenters;

import android.util.Log;

import Activitys.ISignupView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public class SignupPresenter implements ISignupPresenter {
    private ISignupView iSignupView;


    public SignupPresenter(ISignupView view) {
        iSignupView=view;
    }

    private void successSignup(){
        iSignupView.successSignup();
    }

    private void failedSignup(){
        iSignupView.failSignup();
    }


    @Override
    public void startSignup(String name, String password) {


        // 添加 bmob 注册功能， 抛弃普通 sharepreference
        if ( name.equals("") || password.equals("") )
            failedSignup();
        else {
            BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(name);
            bmobUser.setPassword(password);
            bmobUser.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser user, BmobException e){
                    if (e == null)
                        successSignup();
                    else{
                        Log.e("fuck", "signup done: "+e.toString() );
                        failedSignup();
                    }

                }
            });
        }

    }
}
