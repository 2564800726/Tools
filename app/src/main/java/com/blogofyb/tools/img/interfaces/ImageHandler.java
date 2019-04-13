package com.blogofyb.tools.img.interfaces;

import android.graphics.Bitmap;

import com.blogofyb.tools.img.ImageLoader;

public interface ImageHandler {

    void handleImage(Bitmap img, ImageLoader.Options options);

    void failed(Exception e, ImageLoader.Options options);

}
