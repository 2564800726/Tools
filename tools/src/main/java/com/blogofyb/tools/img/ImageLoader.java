package com.blogofyb.tools.img;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;

import com.blogofyb.tools.img.interfaces.Cache;
import com.blogofyb.tools.img.interfaces.Compressor;
import com.blogofyb.tools.img.interfaces.Encrypt;
import com.blogofyb.tools.img.interfaces.ImageHandler;
import com.blogofyb.tools.thread.ThreadManager;

public class ImageLoader
{
    private Options options;
    private ImageHandler callback;
    private ImageViewToken token;

    public ImageLoader with(Context context) {
        if (options == null) {
            options = new Options();
        }
        options.context(context);
        return this;
    }

    public ImageLoader withTag(Object tag) {
        if (token == null) {
            token = new ImageViewToken();
        }
        token.setTag(tag);
        return this;
    }

    public ImageLoader load(String url) {
        if (token == null) {
            token = new ImageViewToken();
        }
        token.setUrl(url);
        return this;
    }

    public ImageLoader apply(Options options) {
        this.options = options;
        checkOptions();
        return this;
    }

    public ImageLoader by(ImageHandler callback) {
        this.callback = callback;
        return this;
    }

    public void into(ImageView target) {
        if (token == null) {
            token = new ImageViewToken();
        }
        token.setTarget(target);
        loadImage();
    }

    public void into(ImageView target, boolean scaleImage) {
        options.scaleImage = scaleImage;
        into(target);
    }

    private void loadImage() {
        checkToken();
        if (callback == null) {
            callback = new DefaultImageHandler();
        }
        if (token.getTag().equals(token.getTarget().getTag())) {
            return;
        }
        if (options.place != 0) {
            token.getTarget().setImageResource(options.place());
        }
        token.getTarget().setTag(token.getTag());
        ThreadManager.getInstance().execute(new Call(options, callback, token));
        token = null;
    }

    public Options getOptions() {
        return options;
    }

    private void checkOptions() {
        if (options.context == null || (options.compressImage && (options.compressors == null || options.compressOptions == null))) {
            throw new RuntimeException("parameter are missing in Options");
        }
    }

    private void checkToken() {
        if (token.getUrl() == null || token.getTarget() == null) {
            throw new RuntimeException("parameter are missing in ImageViewToken");
        }
    }

    public static class Options {
        private Compressor[] compressors;
        private int place;
        private int failed;
        private Cache cache;
        private Context context;
        private boolean compressImage;
        private Encrypt encrypt;
        private CompressOptions compressOptions;
        private boolean scaleImage;

        public Options compressor(Compressor... compressors) {
            this.compressors = compressors;
            return this;
        }

        public Options compressImage(boolean compressImage) {
            this.compressImage = compressImage;
            return this;
        }

        public Options failed(@DrawableRes int failed) {
            this.failed = failed;
            return this;
        }

        public Options place(@DrawableRes int place) {
            this.place = place;
            return this;
        }

        public Options cache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Options context(Context context) {
            this.context = context;
            return this;
        }

        public Options encrypt(Encrypt encrypt) {
            this.encrypt = encrypt;
            return this;
        }

        public Options scaleImage(boolean scaleImage) {
            this.scaleImage = scaleImage;
            return this;
        }

        public Options compressOptions(CompressOptions options) {
            this.compressOptions = options;
            return this;
        }

        public boolean compressImage() {
            return compressImage;
        }

        public Compressor[] compressors() {
            return compressors;
        }

        public int place() {
            return place;
        }

        public int failed() {
            return failed;
        }

        public Cache cache() {
            return cache;
        }

        public Context context() {
            return context;
        }

        public boolean scaleImage() {
            return scaleImage;
        }

        public Encrypt encrypt() {
            return encrypt;
        }

        public CompressOptions compressOptions() {
            return compressOptions;
        }
    }

}
