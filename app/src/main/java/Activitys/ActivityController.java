package Activitys;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang Zihong on 2018/3/5.
 */

public class ActivityController {

    private static List<Activity> ListOfActivity=new ArrayList<>();

    public static void addActivity(Activity activity){
        ListOfActivity.add(activity);
    }

    public static void removeActivity(Activity activity){
        ListOfActivity.remove(activity);
    }

    public static void removeAll(){
        for(Activity activity: ListOfActivity){
            ListOfActivity.remove(activity);
            activity.finish();
        }
    }

}
