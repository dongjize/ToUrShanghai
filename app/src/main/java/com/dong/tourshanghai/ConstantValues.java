package com.dong.tourshanghai;

/**
 * Intro: 常量类
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public interface ConstantValues {
    // baidumap API key
    public final static String BAIDU_API_KEY = "7UD4mp3kTvfjG5OlziyRXrOZ";
    // 基础url
    public final static String BASE_URL = "http://10.0.2.2:8080/ToUrShanghai/";
    //检查新版本
    public final static String CHECK_UPDATE_URL = BASE_URL + "UpdateInfo.html";
    // 新版本下载地址

    // 注册地址
    public final static String REGISTER_URL = BASE_URL + "register/";
    // 登录地址
    public final static String LOGIN_URL = BASE_URL + "login/";
    // 图片基础地址
    public final static String PIC_BASE_URL = BASE_URL + "resources/";
    // 新闻图片基础地址
    public final static String PIC_URL_NEWS = PIC_BASE_URL + "news/";
    // 景点图片基础地址
    public final static String PIC_URL_SPOTS = PIC_BASE_URL + "spots/";
    // 新闻列表
    public final static String GET_NEWS_LIST = BASE_URL + "news";
    // 新闻内容
    public final static String GET_NEWS_DETAIL = BASE_URL + "news";


    //新闻类型
    public static final int NEWS_TYPE_0 = 0;
    public static final int NEWS_TYPE_1 = 1;
    public static final int NEWS_TYPE_2 = 2;
    public static final int NEWS_TYPE_3 = 3;
    public static final int NEWS_TYPE_4 = 4;
    public static final int NEWS_TYPE_5 = 5;
    public static final int NEWS_TYPE_6 = 6;

}
