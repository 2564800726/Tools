package com.blogofyb.tools.img;

import android.graphics.Bitmap;

import com.blogofyb.tools.img.encrypt.MD5Encrypt;
import com.blogofyb.tools.img.interfaces.Cache;
import com.blogofyb.tools.img.interfaces.Compressor;
import com.blogofyb.tools.img.interfaces.Encrypt;
import com.blogofyb.tools.img.interfaces.ImageHandler;
import com.blogofyb.tools.thread.ThreadManager;

public class DefaultImageHandler implements ImageHandler {

    @Override
    public void handleImage(Bitmap img, ImageViewToken token, ImageLoader.Options options) {
        compressImage(options, img);
        displayImg(token, img);
        cacheImage(options, img, token);
    }

    @Override
    public void failed(Exception e, final ImageViewToken token, final ImageLoader.Options options) {
        ThreadManager.getInstance().post(new Runnable() {
            @Override
            public void run() {
                token.getTarget().setImageResource(options.failed());
            }
        });
    }

    private void compressImage(ImageLoader.Options options, Bitmap img) {
        Compressor[] compressors = options.compressors();
        if (options.compressImage() && compressors != null) {
            for (Compressor compressor : compressors) {
                img = compressor.compress(img, options.compressOptions());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void cacheImage(ImageLoader.Options options, Bitmap img, ImageViewToken token) {
        Cache cache = options.cache();
        if (cache != null) {
            Encrypt encrypt = options.encrypt();
            if (encrypt == null) {
                encrypt = MD5Encrypt.getInstance();
            }
            cache.put(encrypt.encrypt(token.getUrl()), img);
        }
    }

    private void displayImg(final ImageViewToken token, final Bitmap img) {
        ThreadManager.getInstance().post(new Runnable() {
            @Override
            public void run() {
                if (token.getTarget().getTag().equals(token.getTag())) {
                    token.getTarget().setImageBitmap(img);
                }
            }
        });
    }
}
