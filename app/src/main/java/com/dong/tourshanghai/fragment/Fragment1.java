package com.dong.tourshanghai.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.ui.DistanceActivity;
import com.dong.tourshanghai.ui.RouteActivity;
import com.dong.tourshanghai.utils.MapUtils;
import com.dong.tourshanghai.view.ArcMenu;

/**
 * Intro: 地图模块
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment1 extends BaseFragment implements View.OnClickListener {

    private static final String CITY_TO_SEARCH = "上海";
    BaiduMap baiduMap;
    MapView mapView;
    private LocationClient mLocationClient;
    private CustomApplication.MyLocationListener mLocationListener;
    private MyLocationConfiguration.LocationMode mLocationMode;
    private PoiSearch mPoiSearch;

    private RelativeLayout mapSearchBar;
    private ImageView btnSound;
    private EditText etSearch;
    private ImageView btnSearchGo;

    private SuggestionSearch mSuggestionSearch = null;

    private ArcMenu arcMenu;

    private RelativeLayout routeAboveLayout;
    private LinearLayout routeBelowLayout;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //初始化百度地图
        initMap();

        //定位
        setLocation();

        //设置ArcMenu
        setArcMenu();
    }

    @Override
    public void setContentView(LinearLayout contentLayout) {
        super.setContentView(contentLayout);
        View childView = View.inflate(mContext, R.layout.layout_fragment1, null);
        mapView = (MapView) childView.findViewById(R.id.bmapview);
        mapSearchBar = (RelativeLayout) childView.findViewById(R.id.include_map_searchbar);
        etSearch = (EditText) childView.findViewById(R.id.et_search_poi);
        btnSearchGo = (ImageView) childView.findViewById(R.id.iv_searchbar_go);
        contentLayout.addView(childView);
    }

    /**
     * 初始化百度地图
     */
    private void initMap() {
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                Button popWindow = new Button(mContext);
                popWindow.setBackgroundResource(R.mipmap.popup);
                popWindow.setText(mapPoi.getName());
                popWindow.setTextColor(0xAA000000);
                popWindow.setTextSize(12);
                InfoWindow.OnInfoWindowClickListener mListener = null;
                InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popWindow),
                        mapPoi.getPosition(), -5, mListener);
                baiduMap.showInfoWindow(mInfoWindow);
                return true;
            }
        });


        MapUtils.mapStatusUpdate(baiduMap, MapUtils.INITIAL_LATITUDE, MapUtils.INITIAL_LONGITUDE);

        mapSearchBar.setOnClickListener(this);
        btnSearchGo.setOnClickListener(this);

        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null
                        || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(mContext, "抱歉未找到", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    PoiOverlay overlay = new MyPoiOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                    return;
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {
                if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(mContext, "抱歉未找到", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(mContext, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

    }

    /**
     * 设置定位相关
     */
    private void setLocation() {
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationListener = new CustomApplication.MyLocationListener(baiduMap);
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); //指定坐标类型
        option.setIsNeedAddress(true); //是否需要地址
        option.setOpenGps(true); //打开gps
        option.setScanSpan(1000); //每1s进行一次位置请求
        mLocationClient.setLocOption(option);
    }

    /**
     * 设置arcmenu的点击事件
     */
    private void setArcMenu() {
        arcMenu = (ArcMenu) getActivity().findViewById(R.id.arcmenu);
        arcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (pos) {
                    case 0:
                        //切换地图模式
                        if (baiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
                            baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                            Toast.makeText(getActivity(), "卫星模式", Toast.LENGTH_SHORT).show();
                        } else {
                            baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                            Toast.makeText(getActivity(), "普通模式", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        //测距
                        startActivity(new Intent(getActivity(), DistanceActivity.class));
                        break;
                    case 2:
                        //路线搜索
//                        showRouteSearch();
                        startActivity(new Intent(getActivity(), RouteActivity.class));
                        break;
                    case 3:
                        //定位
                        MapUtils.centerToMyLocation(baiduMap,
                                mLocationListener.mLatitude, mLocationListener.mLongitude);
                        break;

                }
            }
        });
    }

    /**
     * 弹出路线搜索动画
     */
    private void showRouteSearch() {
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

    }

    private void searchPoi() {
        if (!TextUtils.isEmpty(etSearch.getText().toString())) {
            mPoiSearch.searchInCity((new PoiCitySearchOption()).city(CITY_TO_SEARCH)
                    .keyword(etSearch.getText().toString()).pageNum(0));
            ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            Toast.makeText(mContext, "输入不能为空", Toast.LENGTH_SHORT).show();
        }

    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_searchbar_go:
                searchPoi();
                break;
        }


    }


}
