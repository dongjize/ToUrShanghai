package com.dong.tourshanghai.net;

/**
 * Intro: Http请求接口
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public interface HttpRequestListener {

    public static final int BASE_EVENT = 0x100;

    //没有网络提示
    public static final int EVENT_NO_NETWORK = BASE_EVENT + 1;

    //网络异常
    public static final int EVENT_NETWORK_ERROR = BASE_EVENT + 2;

    //获取网络数据失败
    public static final int EVENT_GET_DATA_ERROR = BASE_EVENT + 3;

    //获取网络数据成功
    public static final int EVENT_GET_DATA_SUCCESS = BASE_EVENT + 4;

    /**
     * 实现方法
     *
     * @param actionCode 事件代码
     * @param object
     */
    public abstract void action(int actionCode, Object object);

}
