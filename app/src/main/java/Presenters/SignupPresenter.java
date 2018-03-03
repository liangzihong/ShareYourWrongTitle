package Presenters;

import Activitys.ISignupView;
import models.IUser;
import models.User_model;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public class SignupPresenter implements ISignupPresenter {
    private ISignupView iSignupView;
    private IUser iUser;


    public SignupPresenter(ISignupView view) {
        iSignupView=view;
        iUser=new User_model(view.getContext());
    }

    private void successSignup(){
        iSignupView.successSignup();
    }

    private void failedSignup(){
        iSignupView.failSignup();
    }


    @Override
    public void startSignup(String name, String password) {
        if(iUser.isSignupSuccess(name,password))
            successSignup();
        else
            failedSignup();
    }
}
