package com.dong.tourshanghai.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.dong.tourshanghai.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Intro: Http请求管理类
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class HttpManager {

    private Context context;
    private HttpRequestListener listener;
    //    private Thread currentRequest = null;
    private HttpRequestVo requestVo;

    public static final int GET_METHOD = 1;
    public static final int POST_METHOD = 2;
    private int requestStatus;
    public static final String ENCODING = "UTF-8";


    public HttpManager(Context context, HttpRequestListener listener,
                       HttpRequestVo requestVo, int requestStatus) {
        this.context = context;
        this.listener = listener;
        this.requestVo = requestVo;
        this.requestStatus = requestStatus;
    }

    public HttpManager(Context context, HttpRequestListener listener, HttpRequestVo requestVo) {
        this.context = context;
        this.listener = listener;
        this.requestVo = requestVo;
    }

    /**
     * 对请求的字符串进行转码
     *
     * @param requestStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String requestEncodeStr(String requestStr) throws UnsupportedEncodingException {
        return URLEncoder.encode(requestStr, ENCODING);
    }

    /**
     * 发送GET请求,获得JSONObject
     *
     * @return 带有结果数据的JSONObject
     */
    public void sendGetRequest() {
        requestStatus = 1;
        String urlStr = "";
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(requestVo.requestUrl);
            if (requestVo.requestDataMap != null) {
                if (!buffer.toString().contains("?")) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                HashMap<String, String> map = requestVo.requestDataMap;
                int i = 1;
                int size = map.size();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (i == size) {
                        if (TextUtils.isEmpty(entry.getValue())) {
                            buffer.append(entry.getKey() + "=");
                        } else {
                            buffer.append(entry.getKey() + "=" + requestEncodeStr(entry.getValue()));
                        }
                    } else {
                        if (TextUtils.isEmpty(entry.getValue())) {
                            buffer.append(entry.getKey() + "=" + "&");
                        } else {
                            buffer.append(entry.getKey() + "=" + requestEncodeStr(entry.getValue()) + "&");
                        }
                    }
                    i++;
                }
            }

            urlStr = buffer.toString();
            VolleyRequest.RequestGet(context, urlStr, "volleyGet",
                    new VolleyInterface(context, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                        @Override
                        public void onMySuccess(JSONObject result) {
                            if (result != null) {
                                try {
                                    if (requestVo.parser != null) {
                                        HashMap<String, Object> map = requestVo.parser.parseJSON(result.toString());
                                        listener.action(HttpRequestListener.EVENT_GET_DATA_SUCCESS, map);
                                    } else {
                                        listener.action(HttpRequestListener.EVENT_GET_DATA_SUCCESS, result);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                        @Override
                        public void onMyError(VolleyError error) {
                            listener.action(HttpRequestListener.EVENT_NETWORK_ERROR, null);
                            Log.i("TAG", error.toString());
                        }
                    });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送POST请求,获得JSONObject
     *
     * @return 带有结果数据的JSONObject
     */
    public void sendPostRequest() {
        requestStatus = 2;
        String urlStr = requestVo.requestJson.toString();
        Map<String, String> dataMap = requestVo.requestDataMap;
        VolleyRequest.RequestPost(context, urlStr, "volleyPost", dataMap,
                new VolleyInterface(context, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(JSONObject result) {
                        if (result != null) {
                            try {
                                if (requestVo.parser != null) {
                                    HashMap<String, Object> map = requestVo.parser.parseJSON(result.toString());
                                    listener.action(HttpRequestListener.EVENT_GET_DATA_SUCCESS, map);
                                } else {
                                    listener.action(HttpRequestListener.EVENT_GET_DATA_SUCCESS, result);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        listener.action(HttpRequestListener.EVENT_NETWORK_ERROR, null);
                        Log.i("TAG", error.toString());
                    }
                });
    }

    public void getRequest() {
        requestStatus = 1;
        sendRequest();
    }

    public void postRequest() {
        requestStatus = 2;
        sendRequest();
    }

    /**
     * 根据网络情况和request类型,选择相应的请求方式
     */
    public void sendRequest() {
        int netType = CommonUtils.isNetworkAvailable(context);
        if (netType != 0) {
            if (requestStatus == 1) {
                sendGetRequest();
            } else if (requestStatus == 2) {
                sendPostRequest();
            }
        } else {
            listener.action(HttpRequestListener.EVENT_NO_NETWORK, null);
        }
    }
}
