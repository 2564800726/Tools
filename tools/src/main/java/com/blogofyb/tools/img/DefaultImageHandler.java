package com.blogofyb.tools.img;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blogofyb.tools.img.encrypt.MD5Encrypt;
import com.blogofyb.tools.img.interfaces.Cache;
import com.blogofyb.tools.img.interfaces.Compressor;
import com.blogofyb.tools.img.interfaces.Encrypt;
import com.blogofyb.tools.img.interfaces.ImageHandler;
import com.blogofyb.tools.thread.ThreadManager;

public class DefaultImageHandler implements ImageHandler {
    private ImageLoader.Options options;
    private Bitmap img;

    @Override
    public void handleImage(Bitmap img, ImageLoader.Options options) {
        this.options = options;
        this.img = img;
        compressImage();
        displayImg();
        cacheImage();
    }

    @Override
    public void failed(Exception e, final ImageLoader.Options options) {
        ThreadManager.getInstance().post(new Runnable() {
            @Override
            public void run() {
                options.target().setImageResource(options.failed());
            }
        });
    }

    private void compressImage() {
        Compressor[] compressors = options.compressors();
        if (compressors != null) {
            for (Compressor compressor : compressors) {
                img = compressor.compress(img, options.compressOptions());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void cacheImage() {
        Cache cache = options.cache();
        if (cache != null) {
            Encrypt encrypt = options.encrypt();
            if (encrypt == null) {
                encrypt = MD5Encrypt.getInstance();
            }
            cache.put(encrypt.encrypt(options.url()), img);
        }
    }

    private void displayImg() {
        ThreadManager.getInstance().post(new Runnable() {
            @Override
            public void run() {
                ImageView target = options.target();
                Object tag = options.tag();
                if (target.getTag().equals(tag)) {
                    target.setImageBitmap(img);
                }
            }
        });
    }
}
