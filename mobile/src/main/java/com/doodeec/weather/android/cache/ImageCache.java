package com.doodeec.weather.android.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author Dusan Bartos
 */
public class ImageCache {

    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    private static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    private static int cacheSize = maxMemory / 8;

    private static LruCache<String, Bitmap> mBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
    };

    public static void addBitmapToCache(String key, Bitmap bitmap) {
        if (bitmap != null) {
            mBitmapCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromCache(String key) {
        return mBitmapCache.get(key);
    }
}
