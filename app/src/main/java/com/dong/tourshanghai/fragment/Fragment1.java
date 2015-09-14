package com.dong.tourshanghai.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.view.ArcMenu;

/**
 * Intro: 地图模块
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment1 extends BaseFragment {

//    BaiduMap baiduMap;
//    MapView mapView;
//    private LocationClient mLocationClient;
//    private MyLocationListener mLocationListener;
//    private MyLocationConfiguration.LocationMode mLocationMode;

    private RelativeLayout mapSearchBar;
    private ImageView btnSound;
    ArcMenu arcMenu;

    private double mLatitude, mLongitude;

    private RelativeLayout routeAboveLayout;
    private LinearLayout routeBelowLayout;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_fragment1, null);

        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        //初始化百度地图
//        initMap();
//
//        //定位
//        setLocation();
//
//        //设置ArcMenu
//        setArcMenu();

    }

//    /**
//     * 初始化百度地图
//     */
//    private void initMap() {
//        mapView = (MapView) getActivity().findViewById(R.id.bmapview);
//        mapView.showZoomControls(false);
//        baiduMap = mapView.getMap();
//
//        MapUtils.mapStatusUpdate(baiduMap, MapUtils.INITIAL_LATITUDE, MapUtils.INITIAL_LONGITUDE);
//
//        mapSearchBar = (RelativeLayout) getActivity().findViewById(R.id.include_map_searchbar);
//        mapSearchBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "搜索...待实现...", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btnSound = (ImageView) getActivity().findViewById(R.id.iv_searchbar_sound);
//        btnSound.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "语音识别...待实现...", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    /**
//     * 设置定位相关
//     */
//    private void setLocation() {
//        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
//        mLocationClient = new LocationClient(getActivity().getApplicationContext());
//        mLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(mLocationListener);
//
//        LocationClientOption option = new LocationClientOption();
//        option.setCoorType("bd09ll"); //指定坐标类型
//        option.setIsNeedAddress(true); //是否需要地址
//        option.setOpenGps(true); //打开gps
//        option.setScanSpan(1000); //每1s进行一次位置请求
//        mLocationClient.setLocOption(option);
//    }
//
//    /**
//     * 设置arcmenu的点击事件
//     */
//    private void setArcMenu() {
//        arcMenu = (ArcMenu) getActivity().findViewById(R.id.arcmenu);
//        arcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
//            @Override
//            public void onClick(View view, int pos) {
//                switch (pos) {
//                    case 0:
//                        //切换地图模式
//                        if (baiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
//                            baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//                            Toast.makeText(getActivity(), "卫星模式", Toast.LENGTH_SHORT).show();
//                        } else {
//                            baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//                            Toast.makeText(getActivity(), "普通模式", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 1:
//                        //路线搜索
//                        showRouteSearch();
//                        Toast.makeText(getActivity(), "测距...待实现...", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        //测距
//                        Toast.makeText(getActivity(), "测距...待实现...", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        //定位
//                        MapUtils.centerToMyLocation(baiduMap, mLatitude, mLongitude);
//                        break;
//                    case 4:
//                        //全景
//                        break;
//
//                }
//            }
//        });
//    }
//
//    /**
//     * 弹出路线搜索动画
//     */
//    private void showRouteSearch() {
//        View searchRouteView = LayoutInflater.from(mContext).inflate(R.layout.layout_search_route, null);
//        routeAboveLayout = (RelativeLayout) searchRouteView.findViewById(R.id.search_route_above);
//        routeBelowLayout = (LinearLayout) searchRouteView.findViewById(R.id.search_route_below);
//        TranslateAnimation anim1 = new TranslateAnimation(
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.5f);
//        anim1.setDuration(2000);
//        TranslateAnimation anim2 = new TranslateAnimation(
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, -0.5f);
//        anim2.setDuration(2000);
//        routeAboveLayout.startAnimation(anim1);
//        routeBelowLayout.startAnimation(anim2);
//
//    }
//
//
//    /**
//     * 语音识别搜索
//     */
//    private void soundSearch() {
//        Toast.makeText((MainActivity) getActivity(), "语音识别", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        baiduMap.setMyLocationEnabled(true);
//        if (!mLocationClient.isStarted()) {
//            mLocationClient.start();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        baiduMap.setMyLocationEnabled(false);
//        mLocationClient.stop();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//
//    }
//
//    /**
//     * 定位监听器
//     */
//    private class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            MyLocationData data = new MyLocationData.Builder()
//                    .accuracy(bdLocation.getRadius())
//                    .latitude(bdLocation.getLatitude())
//                    .longitude(bdLocation.getLongitude())
//                    .build();
//            baiduMap.setMyLocationData(data);
//
//            mLatitude = bdLocation.getLatitude();
//            mLongitude = bdLocation.getLongitude();
//        }
//    }


}
