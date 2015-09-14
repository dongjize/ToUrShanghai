package com.dong.tourshanghai.utils;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

/**
 * Intro: 应用程序的Activity管理类:用于Activity管理和应用程序退出
 * <p>
 * Programmer: dong
 * Date: 15/8/28.
 */
public class AppManager extends Application {
    private Stack<Activity> activityStack;
    private static AppManager instance;

    public synchronized static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前activity(最后压入的activity)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 退出整个程序
     */
    public void appExit() {
        try {
            for (Activity activity : activityStack) {
                if (activity != null)
                    activity.finish();
            }
            activityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }

    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
