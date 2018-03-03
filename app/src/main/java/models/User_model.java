package models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Liang Zihong on 2018/3/3.
 */

public class User_model implements IUser {
    private Context context;

    public User_model(Context context){
        this.context=context;
    }

    @Override
    public boolean isSignupSuccess(String name, String password) {
        SharedPreferences pref=context.getSharedPreferences("FirstStore",Context.MODE_APPEND);
        SharedPreferences.Editor editor=pref.edit();

        if(name==null||password==null)
            return false;
        else if(pref.getString(name,null)!=null)
            return false;
        else{
            editor.putString(name,password);
            editor.commit();
            return true;
        }

    }

    @Override
    public boolean isLoginSuccess(String name, String password) {
        SharedPreferences pref=context.getSharedPreferences("FirstStore",Context.MODE_APPEND);
        SharedPreferences.Editor editor=pref.edit();
        if(pref.getString(name,null)==null)
            return false;
        else return true;
    }


}
