package com.blogofyb.tools.img.interfaces;

import android.graphics.Bitmap;

import com.blogofyb.tools.img.CompressOptions;

public interface Compressor {

    Bitmap compress(Bitmap raw, CompressOptions options);

}
