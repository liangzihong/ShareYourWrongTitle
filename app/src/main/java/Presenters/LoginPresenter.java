package Presenters;

import android.util.Log;

import Activitys.ILoginView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import models.IUser;
import models.User_model;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView iLoginView;
    private IUser iUser;

    public LoginPresenter(ILoginView activity){
        iLoginView =activity;
        iUser=new User_model(iLoginView.getContext());
    }


    private void successLogin() {
        iLoginView.successLogin();
    }

    private void failLogin(){
        iLoginView.failLogin();
    }


    @Override
    public void startLogin(String name, String password) {


        // 添加 bmob 登录功能
        BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null)
                    successLogin();
                else{
                    Log.e("fuck", "login done: "+e.toString() );
                    failLogin();
                }
            }
        });



//        if(iUser.isLoginSuccess(name,password))
//            successLogin();
//        else
//            failLogin();
    }



}
