package Activitys;

import android.content.Context;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public interface ILoginView {
    public Context getContext();
    public void successLogin(String userName);
    public void failLogin();
}
