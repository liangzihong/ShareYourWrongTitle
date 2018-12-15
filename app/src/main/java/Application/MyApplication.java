package Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Liang Zihong on 2018/12/15.
 */

public class MyApplication extends Application {
    private String CurrentUserName = null;
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public void setCurrentUserName(String currentUserName){
        this.CurrentUserName = currentUserName;
    }

    public static Context getContext() {
        return context;
    }

    public String getCurrentUserName(){
        return this.CurrentUserName;
    }


}
