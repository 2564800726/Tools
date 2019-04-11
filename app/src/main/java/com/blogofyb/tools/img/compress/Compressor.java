package com.blogofyb.tools.img.compress;

import android.graphics.Bitmap;

public interface Compressor<T> {

    Bitmap compress(T raw);

}
