package com.blogofyb.tools.img.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blogofyb.tools.img.CompressOptions;
import com.blogofyb.tools.img.interfaces.Compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class InSampleSizeCompressor implements Compressor {
    private static volatile InSampleSizeCompressor instance;

    private InSampleSizeCompressor() {}

    public static InSampleSizeCompressor getInstance() {
        if (instance == null) {
            synchronized (InSampleSizeCompressor.class) {
                if (instance == null) {
                    instance = new InSampleSizeCompressor();
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap compress(Bitmap raw, CompressOptions options) {
        final int inSampleSize = calculateInSampleSize(raw, options);
        if (inSampleSize != 1) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            raw.compress(Bitmap.CompressFormat.PNG, 100, out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = inSampleSize;
            return BitmapFactory.decodeStream(in, null, bitmapOptions);
        }
        return raw;
    }

    private int calculateInSampleSize(Bitmap img, CompressOptions options) {
        final int maxSize = options.getMaxSize(), size = img.getRowBytes() * img.getHeight();
        int inSampleSize = 1;
        while (size / inSampleSize > maxSize) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }
}
