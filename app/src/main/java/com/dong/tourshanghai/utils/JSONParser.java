package com.dong.tourshanghai.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Intro: JSON解析类
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class JSONParser {
    private Type cls;

    public JSONParser(Type cls) {
        this.cls = cls;
    }

    /**
     * 对服务器端返回的json进行解析,生成一个Map集合
     *
     * @param paramString 服务器返回的带有json信息的字符串
     * @return 生成的Map
     * @throws JSONException
     */
    public HashMap<String, Object> parseJSON(String paramString) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(paramString);
        String result = jsonObject.getString("result");
        //map.put("code", jsonObject.getInt("code"));
//        if (jsonObject.has("servertime")) {
//            map.put("servertime", jsonObject.getLong("servertime"));
//        }
//        if (jsonObject.has("sessionid") && !jsonObject.isNull("sessionid")) {
//            map.put("sessionid", jsonObject.getInt("sessionid"));
//        }

        Gson gson = new Gson();

        map.put("datamap", gson.fromJson(result, cls));

        return map;
    }

}
