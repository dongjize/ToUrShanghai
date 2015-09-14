package com.dong.tourshanghai.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Intro: 图片缓存
 * <p>
 * Programmer: dong
 * Date: 15/9/5.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    public LruCache<String, Bitmap> cache;
    public int maxCache = 10 * 1024 * 1024; // 最大缓存大小10M

    public BitmapCache() {
        cache = new LruCache<String, Bitmap>(maxCache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String s) {
        return cache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        cache.put(s, bitmap);
    }
}
