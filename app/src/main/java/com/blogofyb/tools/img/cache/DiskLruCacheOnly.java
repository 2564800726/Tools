package com.blogofyb.tools.img.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blogofyb.tools.img.interfaces.Cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskLruCacheOnly implements Cache<String, Bitmap> {
    private final int MAX_SIZE = 100 * 1024 * 1024;
    private Context context;
    private final String CACHE_DIR;
    private BitmapFactory.Options options;

    public DiskLruCacheOnly(Context context, String cacheDirName) {
        this.context = context;
        CACHE_DIR = context.getExternalCacheDir().getPath() + File.separator + cacheDirName;
    }

    public void setOptions(BitmapFactory.Options options) {
        this.options = options;
    }

    @Override
    public void put(String key, Bitmap value) {
        writeBitmapToDisk(key, value);
    }

    @Override
    public Bitmap get(String key) {
        return readBitmapFromDisk(key);
    }

    private void writeBitmapToDisk(String key, Bitmap value) {
        FileOutputStream out = null;
        try {
            File file = new File(CACHE_DIR + File.separator + key);
            if (!file.exists() && file.createNewFile()) {
                out = new FileOutputStream(file);
                value.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap readBitmapFromDisk(String key) {
        FileInputStream in = null;
        try {
            File file = new File(CACHE_DIR + File.separator + key);
            if (file.exists()) {
                in = new FileInputStream(file);
            } else {
                return null;
            }
            if (options != null) {
                return BitmapFactory.decodeFileDescriptor(in.getFD(), null, options);
            } else {
                return BitmapFactory.decodeFileDescriptor(in.getFD());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
