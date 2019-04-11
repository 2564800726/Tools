package com.blogofyb.tools.img.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.blogofyb.tools.img.interfaces.Cache;

public class LruCacheOnly implements Cache<String, Bitmap> {
    private LruCache<String, Bitmap> lruCache;
    private final int MAX_SIZE = (int) Runtime.getRuntime().maxMemory() / 8;

    public LruCacheOnly() {
        lruCache = new LruCache<String, Bitmap>(MAX_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public void put(String key, Bitmap value) {
        lruCache.put(key, value);
    }

    @Override
    public Bitmap get(String key) {
        return lruCache.get(key);
    }
}
