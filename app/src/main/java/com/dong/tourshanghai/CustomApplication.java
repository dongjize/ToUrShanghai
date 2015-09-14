package com.dong.tourshanghai;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Intro: Application类
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class CustomApplication extends Application {
    public static CustomApplication mInstance;

    // Volley中的请求队列
    public static RequestQueue queue;

    public static CustomApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();

    }

    /**
     * 初始化
     */
    private void init() {
        //SDKInitializer.initialize(this);
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * 获取volley请求队列
     *
     * @return
     */
    public static RequestQueue getHttpQueue() {
        return queue;
    }

}
