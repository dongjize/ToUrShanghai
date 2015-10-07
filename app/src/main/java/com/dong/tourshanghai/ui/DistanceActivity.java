package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.utils.AppManager;

import java.util.ArrayList;

/**
 * Intro: 测距功能
 * <p/>
 * Programmer: dong
 * Date: 15/9/19.
 */
public class DistanceActivity extends NaviBaseActivity {

    private BaiduMap baiduMap;
    private MapView mapView;
    private LocationClient mLocationClient;
    private double mLatitude, mLongitude;
    private TextView tvClear;
    private ArrayList<LatLng> nodes = new ArrayList<LatLng>();
    private ArrayList<LatLng> dualNodes = new ArrayList<LatLng>();
    private double curDistance;
    private double distance = 0;
    private MyLocationConfiguration.LocationMode mLocationMode;
    private CustomApplication.MyLocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        super.setNavigateMiddleTitle("测距");
        super.setNavigateLeftButtonIsShow(true);
        super.setNavigateRightButtonIsShow(true);
        super.setNavigateRightTextViewText("清除");

    }

    @Override
    public void setContentView(LinearLayout contentView) {
        View childView = View.inflate(mContext, R.layout.layout_distance, null);

        mapView = (MapView) childView.findViewById(R.id.bmapview);
        baiduMap = mapView.getMap();
        tvClear = (TextView) childView.findViewById(R.id.tv_distance);

        mapView.showZoomControls(false);
        LatLng latlng = new LatLng(31.037155, 121.46004);
        MapStatus mMapStatus = new MapStatus.Builder().target(latlng).zoom(16).build();
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.animateMapStatus(msu);
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng ll) {
                nodes.add(ll);
                measureDistance();
                addOverlay();
            }
        });


        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.clear();
                nodes.clear();
                dualNodes.clear();
                distance = 0;
                curDistance = 0;
            }
        });

        contentView.addView(childView);

        initLocation();
    }

    @Override
    public void topRightTextClick(View v) {
        if (baiduMap != null) {
            baiduMap.clear();
            nodes.clear();
            dualNodes.clear();
            distance = 0;
            curDistance = 0;
        }
    }

    private void initLocation() {
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(this);
        mLocationListener = new CustomApplication.MyLocationListener(baiduMap);
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 测距
     */
    private void measureDistance() {
        String popDis;
        if (nodes.size() > 1) {
            curDistance = DistanceUtil.getDistance(nodes.get(nodes.size() - 1), nodes.get(nodes.size() - 2));
            distance += curDistance;
            if (distance >= 1000) {
                popDis = (float) (distance / 1000) + "km";
            } else {
                popDis = (float) distance + "m";
            }
            Button popWindow = new Button(this);
            popWindow.setBackgroundResource(R.mipmap.popup);
            popWindow.setText(popDis);
            popWindow.setTextSize(13);
            InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popWindow),
                    nodes.get(nodes.size() - 1), -5, null);
            baiduMap.showInfoWindow(mInfoWindow);
        }
    }

    private void addOverlay() {
        dualNodes.clear();
        OverlayOptions ooPoint = new DotOptions().center(nodes.get(nodes.size() - 1)).color(0xAAFF0000);
        baiduMap.addOverlay(ooPoint);
        if (nodes.size() > 1) {
            dualNodes.add(nodes.get(nodes.size() - 2));
            dualNodes.add(nodes.get(nodes.size() - 1));
            OverlayOptions ooPolyline = new PolylineOptions().width(5)
                    .color(0xAAFF0000).points(dualNodes);
            baiduMap.addOverlay(ooPolyline);
        }
    }
}
