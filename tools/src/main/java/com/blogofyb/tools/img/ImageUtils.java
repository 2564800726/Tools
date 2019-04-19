package com.blogofyb.tools.img;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageUtils {
    public static int calculateInSampleSize(ImageView img, BitmapFactory.Options options) {
        int reqWidth = img.getWidth();
        int reqHeight = img.getHeight();

        if (reqWidth == 0 || reqHeight == 0) {
            return 0;
        }

        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;
            while ((halfWidth / inSampleSize) >= reqWidth &&
                    (halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
