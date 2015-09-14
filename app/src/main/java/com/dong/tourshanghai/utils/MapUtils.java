//package com.dong.tourshanghai.utils;
//
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.utils.DistanceUtil;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//
///**
// * Intro: 地图功能方法类.包括定位到自己,定位任意坐标,测距...
// * <p/>
// * Programmer: dong
// * Date: 15/9/3.
// */
//public class MapUtils {
//
//    public static final double INITIAL_LATITUDE = 31.037155;
//    public static final double INITIAL_LONGITUDE = 121.46004;
//    /**
//     * 定位到用户所在的位置
//     *
//     * @param baiduMap
//     * @param latitude
//     * @param longitude
//     */
//    public static void centerToMyLocation(BaiduMap baiduMap, double latitude, double longitude) {
//        LatLng latLng = new LatLng(latitude, longitude);
//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
//        baiduMap.animateMapStatus(msu);
//    }
//
//    /**
//     * 将地图定位到指定坐标
//     *
//     * @param baiduMap  当前context中的BaiduMap对象
//     * @param latitude  纬度
//     * @param longitude 经度
//     */
//    public static void mapStatusUpdate(BaiduMap baiduMap, double latitude, double longitude) {
//        LatLng latLng = new LatLng(latitude, longitude);
//        MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(16).build();
//        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mapStatus);
//        baiduMap.animateMapStatus(msu);
//    }
//
//    /**
//     * 测距
//     *
//     * @param nodes 屏幕记录下的一系列节点
//     * @return 第一个节点到最后一个节点的折线距离
//     */
//    public static String getDistance(ArrayList<LatLng> nodes) {
//        String popDistance;
//        double curDistance;
//        double distance = 0;
//        nodes = new ArrayList<LatLng>();
//        curDistance = DistanceUtil.getDistance(nodes.get(nodes.size() - 1), nodes.get(nodes.size() - 2));
//        distance += curDistance;
//        if (distance >= 1000) {
//            //显示公里,保留两位小数
//            popDistance = new DecimalFormat(".##%").format(distance / 1000) + "km";
//        } else {
//            popDistance = new DecimalFormat(".##%").format(distance) + "m";
//        }
//        return popDistance;
//    }

//    public static void poiSearch(final Context context, final BaiduMap baiduMap) {
//        PoiSearch mPoiSearch = PoiSearch.newInstance();
//        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
//            @Override
//            public void onGetPoiResult(PoiResult poiResult) {
//                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//                    Toast.makeText(context, "未找到结果", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                    baiduMap.clear();
//                    PoiOverlay overlay = new
//
//                }
//            }
//
//            @Override
//            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//
//            }
//        };
//    }
//}
