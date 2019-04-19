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
import java.io.File;
import java.io.FileNotFoundException;
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
        if (options.url().startsWith("http")) {
            if (!loadImageFromCache()) {
                loadImageFromInternet();
            }
        } else {
            loadImageFromDisk();
        }
    }

    private boolean loadImageFromCache() {
        Bitmap img;
        Cache cache = options.cache();
        if (cache != null) {
            Encrypt encrypt = options.encrypt();
            if (encrypt == null) {
                encrypt = MD5Encrypt.getInstance();
            }
            img = cache.get(encrypt.encrypt(options.url()));
            if (img != null) {
                callback.handleImage(img, options);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void loadImageFromDisk() {
        Bitmap img;
        if (options.scaleImage()) {
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(options.url(), decodeOptions);
            decodeOptions.inSampleSize = ImageUtils.calculateInSampleSize(options.target(), decodeOptions);
            decodeOptions.inJustDecodeBounds = false;
            img = BitmapFactory.decodeFile(options.url(), decodeOptions);
        } else {
            img = BitmapFactory.decodeFile(options.url());
        }
        if (img != null) {
            callback.handleImage(img, options);
        } else {
            callback.failed(new FileNotFoundException(), options);
        }
    }

    private void loadImageFromInternet() {
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
            callback.handleImage(img, options);
        } catch (Exception e) {
            e.printStackTrace();
            callback.failed(e, options);
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
