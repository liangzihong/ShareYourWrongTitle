package models;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public interface IUser {
    public boolean isLoginSuccess(String name,String password);
    public boolean isSignupSuccess(String name,String password);
}
