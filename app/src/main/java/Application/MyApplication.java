package Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Liang Zihong on 2018/12/15.
 */

public class MyApplication extends Application {
    public static String CurrentUserName = null;
    public static String CurrentUserId = null;
    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public void setCurrentUserName(String currentUserName){
        CurrentUserName = currentUserName;
    }

    public static Context getContext() {
        return context;
    }

    public String getCurrentUserName(){
        return CurrentUserName;
    }

    public String getCurrentUserId() {
        return CurrentUserId;
    }

    public void setCurrentUserId(String currentUserId){
        CurrentUserId = currentUserId;
    }
}
