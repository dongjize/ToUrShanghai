package com.dong.tourshanghai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Intro: 首选项管理类
 * <p>
 * Programmer: dong
 * Date: 15/9/5.
 */
public class SharedPreferencesUtils {
    private static final String SHARED_KEY_NOTIFY = "shared_key_notify";
    private static final String SHARED_KEY_VOICE = "shared_key_voice";
    private static final String SHARED_KEY_VIBRATE = "shared_key_vibrate";
    private static final String SHARED_KEY_USERICON = "shared_key_usericon";
    private SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 允许推送通知
     *
     * @return
     */
    public boolean isAllowPushNotify() {
        return sp.getBoolean(SHARED_KEY_NOTIFY, true);
    }

    /**
     * 存储设置推送通知
     *
     * @param isChecked
     */
    public void setPushNotifyEnable(boolean isChecked) {
        editor.putBoolean(SHARED_KEY_NOTIFY, isChecked);
        editor.commit();
    }

    /**
     * 允许声音
     *
     * @return
     */
    public boolean isAllowVoice() {
        return sp.getBoolean(SHARED_KEY_VOICE, true);
    }

    /**
     * 存储设置声音
     *
     * @param isChecked
     */
    public void setPushVoiceEnable(boolean isChecked) {
        editor.putBoolean(SHARED_KEY_VOICE, isChecked);
        editor.commit();
    }

    /**
     * 允许震动
     *
     * @return
     */
    public boolean isAllowVibrate() {
        return sp.getBoolean(SHARED_KEY_VIBRATE, true);
    }

    /**
     * 存储设置震动
     *
     * @param isChecked
     */
    public void setPushVibrateEnable(boolean isChecked) {
        editor.putBoolean(SHARED_KEY_VIBRATE, isChecked);
        editor.commit();
    }

    public String getUserIconURI() {
        return sp.getString(SHARED_KEY_USERICON, null);
    }

    public void setUserIconURI(Uri uri) {
        editor.putString(SHARED_KEY_USERICON, uri.toString());
        editor.commit();
    }
}
