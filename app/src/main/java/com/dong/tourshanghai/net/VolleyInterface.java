package com.dong.tourshanghai.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/8.
 */
public abstract class VolleyInterface {
    public Context mContext;
    public static Response.Listener<JSONObject> mListener;
    public static Response.ErrorListener mErrorListener;

    public VolleyInterface(Context context, Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        this.mContext = context;
        this.mErrorListener = errorListener;
        this.mListener = listener;
    }

    public abstract void onMySuccess(JSONObject result);

    public abstract void onMyError(VolleyError error);

    public Response.Listener<JSONObject> loadingListener() {
        mListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                onMySuccess(jsonObject);
            }
        };
        return mListener;
    }

    public Response.ErrorListener errorListener() {
        mErrorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyError(volleyError);
            }
        };
        return mErrorListener;
    }


}
