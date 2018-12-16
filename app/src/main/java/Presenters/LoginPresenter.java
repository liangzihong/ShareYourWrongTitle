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


    private void successLogin(String userName, String userId) {
        iLoginView.successLogin(userName, userId);
    }

    private void failLogin(){
        iLoginView.failLogin();
    }


    @Override
    public void startLogin(String name, String password) {

        // 添加 bmob 登录功能
        final BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null) {
                    Log.e("fuck", "登录成功，用户ID为: "+bu.getObjectId() );
                    successLogin(bu.getUsername(), bu.getObjectId());
                }
                else {
                    Log.e("fuck", "登录失败，LoginPresenter: "+e.toString() );
                    failLogin();
                }
            }
        });



    }



}
