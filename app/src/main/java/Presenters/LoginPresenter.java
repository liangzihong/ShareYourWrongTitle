package Presenters;

import android.util.Log;

import Activitys.ILoginView;
import Application.MyApplication;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView iLoginView;

    public LoginPresenter(ILoginView activity){
        iLoginView =activity;
    }


    private void successLogin(String userName) {
        iLoginView.successLogin(userName);
    }

    private void failLogin(){
        iLoginView.failLogin();
    }


    @Override
    public void startLogin(String name, String password) {

        final String tmp_name = name;
        // 添加 bmob 登录功能
        BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null) {
                    successLogin(tmp_name);
                }
                else {
                    Log.e("fuck", "login done: "+e.toString() );
                    failLogin();
                }
            }
        });



    }



}
