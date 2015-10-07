package com.dong.tourshanghai.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dong.tourshanghai.CustomApplication;

import org.json.JSONObject;

import java.util.Map;

/**
 * Intro: Volley请求类
 * <p>
 * Programmer: dong
 * Date: 15/9/8.
 */
public class VolleyRequest {
    public static StringRequest stringRequest;
    public static JsonObjectRequest jsonObjectRequest;
    public static Context context;

    /**
     * GET请求
     *
     * @param mContext 上下文
     * @param url      资源所在网址
     * @param tag      标签
     * @param vif      回调接口
     */
    public static void requestObjectGet(Context mContext, String url, String tag, VolleyInterface vif) {
        CustomApplication.getHttpQueue().cancelAll(tag);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, vif.loadingListener(), vif.errorListener());
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(tag);
        CustomApplication.getHttpQueue().add(jsonObjectRequest);
        CustomApplication.getHttpQueue().start();

    }

    /**
     * POST请求
     *
     * @param mContext 上下文
     * @param url      资源所在网址
     * @param tag      标签
     * @param params   参数的Map
     * @param vif      回调接口
     */
    public static void requestObjectPost(Context mContext, String url, String tag, final Map<String, String> params,
                                   VolleyInterface vif) {
        CustomApplication.getHttpQueue().cancelAll(tag);
        JSONObject object = new JSONObject(params);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(tag);
        CustomApplication.getHttpQueue().add(jsonObjectRequest);
        CustomApplication.getHttpQueue().start();

    }

}