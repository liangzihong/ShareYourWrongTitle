package Presenters;

import Activitys.ILoginView;
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
        if(iUser.isLoginSuccess(name,password))
            successLogin();
        else
            failLogin();
    }



}
