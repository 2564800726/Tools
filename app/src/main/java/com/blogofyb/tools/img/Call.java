package com.blogofyb.tools.img;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.blogofyb.tools.img.encrypt.MD5Encrypt;
import com.blogofyb.tools.img.interfaces.Cache;
import com.blogofyb.tools.img.interfaces.Encrypt;
import com.blogofyb.tools.img.interfaces.ImageHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Call implements Runnable {
    private ImageLoader.Options options;
    private ImageHandler callback;
    private HttpURLConnection connection;
    private InputStream in;

    Call(ImageLoader.Options options, ImageHandler callback) {
        this.options = options;
        this.callback = callback;
    }

    @Override
    public void run() {
        Bitmap img = null;
        Cache cache = options.cache();
        if (cache != null) {
            Encrypt encrypt = options.encrypt();
            if (encrypt == null) {
                encrypt = MD5Encrypt.getInstance();
            }
            img = cache.get(encrypt.encrypt(options.url()));
        }
        if (img == null) {
            Object obj = loadImageFromInternet();
            if (obj instanceof Exception) {
                callback.failed((Exception) obj, options);
            } else {
                if (obj == null) {
                    callback.failed(null, options);
                } else {
                    callback.handleImage((Bitmap) obj, options);
                }
            }
        } else {
            callback.handleImage(img, options);
        }
    }

    private Object loadImageFromInternet() {
        try {
            Log.e("TAG", "LOAD");
            URL url = new URL(options.url());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            in = connection.getInputStream();
            Bitmap img = BitmapFactory.decodeStream(in);
            if (options.scaleImage()) {
                BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
                decodeOptions.inJustDecodeBounds = true;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, out);
                ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
                BitmapFactory.decodeStream(in, null, decodeOptions);
                decodeOptions.inSampleSize = ImageUtils.calculateInSampleSize(options.target(), decodeOptions);
                decodeOptions.inJustDecodeBounds = false;
                in.reset();
                img = BitmapFactory.decodeStream(in, null, decodeOptions);
            }
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
