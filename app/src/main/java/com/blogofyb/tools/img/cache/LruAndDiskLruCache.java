package com.blogofyb.tools.img.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blogofyb.tools.img.interfaces.Cache;

public class LruAndDiskLruCache implements Cache {
    private LruCacheOnly lruCache;
    private DiskLruCacheOnly diskLruCache;

    public LruAndDiskLruCache(Context context, String diskCacheDirName) {
        lruCache = new LruCacheOnly();
        diskLruCache = new DiskLruCacheOnly(context, diskCacheDirName);
    }

    @Override
    public void put(String key, Bitmap value) {
        lruCache.put(key, value);
        diskLruCache.put(key, value);
    }

    @Override
    public Bitmap get(String key) {
        Bitmap img = lruCache.get(key);
        if (img == null) {
            img = diskLruCache.get(key);
        }
        return img;
    }
}
