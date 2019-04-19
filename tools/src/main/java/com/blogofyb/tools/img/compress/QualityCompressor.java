package com.blogofyb.tools.img.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blogofyb.tools.img.CompressOptions;
import com.blogofyb.tools.img.interfaces.Compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QualityCompressor implements Compressor {
    private static volatile QualityCompressor instance;

    private QualityCompressor() {}

    public static QualityCompressor getInstance() {
        if (instance == null) {
            synchronized (QualityCompressor.class) {
                if (instance == null) {
                    instance = new QualityCompressor();
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap compress(Bitmap raw, CompressOptions options) {
        if (options == null) {
            return raw;
        }
        int quality = options.getQuality();
        int maxSize = options.getMaxSize();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        raw.compress(Bitmap.CompressFormat.PNG, quality, out);
        while (raw.getByteCount() > maxSize && quality > 10) {
            quality -= 5;
            out.reset();
            raw.compress(Bitmap.CompressFormat.PNG, quality, out);
        }
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        raw = BitmapFactory.decodeStream(in);
        try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return raw;
    }
}
