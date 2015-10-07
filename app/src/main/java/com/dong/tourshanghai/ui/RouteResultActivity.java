package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.utils.AppManager;

/**
 * Intro:
 * <p/>
 * Programmer: dong
 * Date: 15/9/20.
 */
public class RouteResultActivity extends NaviBaseActivity implements OnGetRoutePlanResultListener {
    private BaiduMap baiduMap;
    private MapView mapView;
    RoutePlanSearch mRouteSearch;
    private RouteLine route = null;
    OverlayManager routeOverlay = null;
    private boolean useDefaultIcon = false;
    public static final String CITY_NAME = "上海";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setNavigateMiddleTitle("搜索结果");
        super.setNavigateLeftButtonIsShow(true);
        super.setNavigateRightButtonIsShow(false);

        AppManager.getInstance().addActivity(this);


    }

    @Override
    public void setContentView(LinearLayout contentView) {
        View childView = View.inflate(mContext, R.layout.layout_search_route_result, null);
        mapView = (MapView) childView.findViewById(R.id.bmapview);
        baiduMap = mapView.getMap();
        mRouteSearch = RoutePlanSearch.newInstance();
        mRouteSearch.setOnGetRoutePlanResultListener(this);
        contentView.addView(childView);

        initRoute();
    }

    private void initRoute() {
        Bundle bundle = getIntent().getExtras();
        int searchMode = bundle.getInt("mode");
        String fromStr = bundle.getString("start");
        String toStr = bundle.getString("end");

        if ((!TextUtils.isEmpty(fromStr)) && (!TextUtils.isEmpty(toStr))) {
            PlanNode stNode = PlanNode.withCityNameAndPlaceName(CITY_NAME, fromStr);
            PlanNode enNode = PlanNode.withCityNameAndPlaceName(CITY_NAME, toStr);
            switch (searchMode) {
                case 0:
                    mRouteSearch.transitSearch((new TransitRoutePlanOption()).city(CITY_NAME).from(stNode).to(enNode));
                    break;
                case 1:
                    mRouteSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode));
                    break;
                case 2:
                    mRouteSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));
                    break;
            }
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteResultActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(new LatLng(31.2363, 121.480237))
                    .zoom(12)
                    .build();
            MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            baiduMap.animateMapStatus(msu);
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            result.getSuggestAddrInfo();
            Log.d("SuggestInfo", result.getSuggestAddrInfo().toString());
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteResultActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(new LatLng(31.2363, 121.480237))
                    .zoom(12)
                    .build();
            MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            baiduMap.animateMapStatus(msu);
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            Log.d("SuggestInfo", result.getSuggestAddrInfo().toString());
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(0);
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteResultActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
            MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(31.2363, 121.480237)).zoom(12).build();
            MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            baiduMap.animateMapStatus(msu);
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            Log.d("SuggestInfo", result.getSuggestAddrInfo().toString());
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
            routeOverlay = overlay;
            baiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }


    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_en);
            }
            return null;
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);

        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_en);
            }
            return null;
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_en);
            }
            return null;
        }
    }

    public void onMapClick(LatLng point) {
        baiduMap.hideInfoWindow();
    }

    public boolean onMapPoiClick(MapPoi poi) {
        return false;
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
//        if (!mLocationClient.isStarted()) {
//            mLocationClient.start();
//        }
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
//        mLocationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRouteSearch.destroy();
        mapView.onDestroy();

    }
}
