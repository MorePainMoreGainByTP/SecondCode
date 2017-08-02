package com.example.swjtu.secondcode.util;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/4/17.
 * 程序中的所有类管理器
 */

public class ActivitiesCollector {

    public static List<Activity> activities = new ArrayList<>();


    /**
     * @param activity 新创建的activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }


    /**
     * @param activity 被销毁的activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有的activity，即退出程序
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing())
                activity.finish();
        }
        activities.clear();
        //kill掉当前进程，确保进行结束
        android.os.Process.killProcess(Process.myPid());    //只能杀死当前进程
    }
}
