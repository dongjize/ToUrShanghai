package com.dong.tourshanghai.net;

import android.content.Context;

import com.dong.tourshanghai.utils.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Intro: 客户端发送请求中携带的参数
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class HttpRequestVo {

    //请求地址
    public String requestUrl;
    //上下文
    public Context context;
    //客户端的本地请求参数
    public HashMap<String, String> requestDataMap;
    //JSON请求参数
    public JSONObject requestJson;
    //JSON解析器
    public JSONParser parser;
}
