package com.blogofyb.tools.img;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blogofyb.tools.img.encrypt.MD5Encrypt;
import com.blogofyb.tools.img.interfaces.Cache;
import com.blogofyb.tools.img.interfaces.Encrypt;
import com.blogofyb.tools.img.interfaces.ImageHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Call implements Runnable {
    private ImageLoader.Options options;
    private ImageViewToken token;
    private ImageHandler callback;
    private HttpURLConnection connection;
    private InputStream in;

    Call(ImageLoader.Options options, ImageHandler callback, ImageViewToken token) {
        this.options = options;
        this.callback = callback;
        this.token = token;
    }

    @Override
    public void run() {
        if (token.getUrl().startsWith("http")) {
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
            img = cache.get(encrypt.encrypt(token.getUrl()));
            if (img != null) {
                callback.handleImage(img, token, options);
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
            BitmapFactory.decodeFile(token.getUrl(), decodeOptions);
            decodeOptions.inSampleSize = ImageUtils.calculateInSampleSize(token.getTarget(), decodeOptions);
            decodeOptions.inJustDecodeBounds = false;
            img = BitmapFactory.decodeFile(token.getUrl(), decodeOptions);
        } else {
            img = BitmapFactory.decodeFile(token.getUrl());
        }
        if (img != null) {
            callback.handleImage(img, token, options);
        } else {
            callback.failed(new FileNotFoundException(), token, options);
        }
    }

    private void loadImageFromInternet() {
        try {
            URL url = new URL(token.getUrl());
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
                decodeOptions.inSampleSize = ImageUtils.calculateInSampleSize(token.getTarget(), decodeOptions);
                decodeOptions.inJustDecodeBounds = false;
                in.reset();
                img = BitmapFactory.decodeStream(in, null, decodeOptions);
            }
            callback.handleImage(img, token, options);
        } catch (Exception e) {
            e.printStackTrace();
            callback.failed(e, token, options);
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
