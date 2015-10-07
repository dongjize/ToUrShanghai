package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.utils.AppManager;

/**
 * Intro: 景点列表的地图显示
 * <p/>
 * Programmer: dong
 * Date: 15/9/21.
 */
public class SpotsMapActivity extends NaviBaseActivity {

    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private LatLng[] mLatLng;
    private Marker[] mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().addActivity(this);
        super.setNavigateMiddleTitle("景点");
        super.setNavigateLeftButtonIsShow(true);
        super.setNavigateRightButtonIsShow(false);

    }

    @Override
    public void setContentView(LinearLayout contentView) {
        View childView = View.inflate(mContext, R.layout.layout_spots_map, null);
        contentView.addView(childView);

        mMapView = (MapView) findViewById(R.id.bmapview);
        mBaiduMap = mMapView.getMap();
        mLatLng = new LatLng[50];
        mMarker = new Marker[50];
        MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(31.2363, 121.480237)).zoom(12).build();
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(msu);

        initListOverlay();
        mMapView.showZoomControls(false);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.hideInfoWindow();
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mBaiduMap.hideInfoWindow();
                return true;
            }
        });


    }

    /**
     * 添加覆盖物
     */
    private void initListOverlay() {
        Bundle bundle = getIntent().getExtras();
        String[] listName = bundle.getStringArray("listName");
        double[] listLat = bundle.getDoubleArray("listLat");
        double[] listLon = bundle.getDoubleArray("listLon");
        for (int i = 0; i < listName.length; i++) {
            mLatLng[i] = new LatLng(listLat[i], listLon[i]);
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marker);
            OverlayOptions options = new MarkerOptions().position(mLatLng[i]).icon(bd).zIndex(5);
            mMarker[i] = (Marker) mBaiduMap.addOverlay(options);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
