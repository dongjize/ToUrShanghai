package com.dong.tourshanghai.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Intro: 详情页模板操作类
 * <p>
 * Programmer: dong
 * Date: 15/9/10.
 */
public class TemplateManager {

    public static final String NEWS_DETAIL_TEMP = "newsdetail.html";
    // $Title 居左
    public static final String NEWS_TITLE = "{$Title}";
    // $Content 图文信息
    public static final String NEWS_CONTENT = "{$Content}";
    // $FontSize 字体大小
    public static final String NEWS_FONTSIZE = "{$FontSize}";

    private static TemplateManager templateManager = null;

    private TemplateManager() {
    }

    ;

    public static TemplateManager getTemplateManager() {
        if (templateManager == null) {
            templateManager = new TemplateManager();
        }
        return templateManager;
    }

    /**
     * 读取资源文件
     *
     * @param context
     * @param name
     * @return 字符串
     */
    private String readAssetsFile(Context context, String name) {
        ByteArrayOutputStream baos = null;
        InputStream is = null;

        try {
            baos = new ByteArrayOutputStream();
            is = context.getApplicationContext().getAssets().open(name);
            byte[] buffer = new byte[256];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
                buffer = new byte[256];
            }
            buffer = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (baos == null) ? null : new String(baos.toByteArray());
    }

    /**
     * 获取模板
     *
     * @param context
     * @param name
     * @return
     */
    public String getTemplate(Context context, String name) {
        String mTemplate = readAssetsFile(context, name);
        return mTemplate;
    }

    /**
     * 获取css
     *
     * @param context
     * @param name
     * @return
     */
    public String getCss(Context context, String name) {
        String mCss = readAssetsFile(context, name);
        return mCss;
    }

    /**
     * 读取资源文件
     *
     * @param context
     * @param name
     * @param type
     * @return byte数组
     */
    public byte[] readAssetsFile(Context context, String name, int type) {
        ByteArrayOutputStream baos = null;
        InputStream is = null;

        try {
            baos = new ByteArrayOutputStream();
            is = context.getApplicationContext().getAssets().open(name);
            byte[] buffer = new byte[256];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
                buffer = new byte[256];
            }
            buffer = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (baos == null) ? null : baos.toByteArray();
    }
}
