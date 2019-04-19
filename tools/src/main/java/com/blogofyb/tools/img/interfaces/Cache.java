package com.blogofyb.tools.img.interfaces;

import android.graphics.Bitmap;

public interface Cache {

    void put(String key, Bitmap value);

    Bitmap get(String key);

}
