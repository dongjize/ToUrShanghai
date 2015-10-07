package com.dong.tourshanghai;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationData;

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
        SDKInitializer.initialize(this);
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

    public static class MyLocationListener implements BDLocationListener {

        public BaiduMap baiduMap;
        public double mLatitude, mLongitude;

        public MyLocationListener(BaiduMap baiduMap) {
            this.baiduMap = baiduMap;
        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(data);

            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();
        }
    }

}
