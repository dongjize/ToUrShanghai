package com.dong.tourshanghai.engine;

/**
 * Intro: 帮助模块的业务操作接口
 * <p>
 * Programmer: dong
 * Date: 15/9/6.
 */
public interface HelpEngine {
    // 1.检查本地是否有帮助列表.如果有,直接展示给用户,检查服务器端是否有新数据;如果没有,到服务器端获取

    /**
     * 获取服务端帮助列表
     */
    public void getServerHelpList();

    /**
     * 依据帮助id获取详情
     */
    public void getServerHelpDetail();

    /**
     * 获取本地的帮助列表
     */
    public void getLocalHelpList();

    /**
     * 获取本地的帮助详情
     */
    public void getLocalHelpDetail();
}
