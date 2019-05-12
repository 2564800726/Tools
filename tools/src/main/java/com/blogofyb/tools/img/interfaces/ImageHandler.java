package com.blogofyb.tools.img.interfaces;

import android.graphics.Bitmap;

import com.blogofyb.tools.img.ImageLoader;
import com.blogofyb.tools.img.ImageViewToken;

public interface ImageHandler {

    void handleImage(Bitmap img, ImageViewToken token, ImageLoader.Options options);

    void failed(Exception e, ImageViewToken token, ImageLoader.Options options);

}
