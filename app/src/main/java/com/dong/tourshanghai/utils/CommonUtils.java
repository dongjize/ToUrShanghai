package com.dong.tourshanghai.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Intro: 一般方法类,包括:获取现在时间,像素转换,md5加密...
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class CommonUtils {

    /**
     * 获取现在时间
     *
     * @return 短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = simpleDateFormat.format(currentTime);
        return dataString;
    }

    /**
     * 获取现在粗略时间
     *
     * @return 短时间字符串格式MM-dd HH:mm
     */
    public static String getBriefCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String dataString = simpleDateFormat.format(currentTime);
        return dataString;
    }

    /**
     * 判断当前是否有可用网络以及网络类型 0:无网络 1:wifi 2:cmwap 3:cmnet
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return 0;
        } else {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo networkInfo = info[i];
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = networkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * dp --> px
     *
     * @param context
     * @param value
     * @return
     */
    public static int dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * px --> dp
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2dp(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * 160 / scale + 0.5f);
    }

    /**
     * sp --> px
     *
     * @param context
     * @param value
     * @return
     */
    public static int sp2px(Context context, float value) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        float spvalue = value * resources.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }

    /**
     * px --> sp
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2sp(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value / scale + 0.5f);
    }

    /**
     * md5加密
     *
     * @param password
     * @return
     */
    public static String md5Util(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] bytes = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(hex);

            }
            return buffer.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
