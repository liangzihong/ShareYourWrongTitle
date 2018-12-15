package Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Liang Zihong on 2018/12/15.
 */

public class MyApplication extends Application {
    private String CurrentUserName = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setCurrentUserName(String currentUserName){
        this.CurrentUserName = currentUserName;
    }

    public String getCurrentUserName(){
        return this.CurrentUserName;
    }

}
