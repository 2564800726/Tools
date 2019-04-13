package com.blogofyb.tools.img;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.blogofyb.tools.img.interfaces.Compressor;
import com.blogofyb.tools.img.interfaces.Cache;
import com.blogofyb.tools.img.interfaces.Encrypt;
import com.blogofyb.tools.img.interfaces.ImageHandler;
import com.blogofyb.tools.thread.ThreadManager;

public class ImageLoader
{
    private Options options;
    private ImageHandler callback;

    public ImageLoader with(Context context) {
        if (options == null) {
            options = new Options();
        }
        options.context(context);
        return this;
    }

    public ImageLoader load(String url) {
        if (options == null) {
            throw new RuntimeException("need a Context or Options");
        }
        options.url(url);
        return this;
    }

    public ImageLoader apply(Options options) {
        this.options = options;
        return this;
    }

    public ImageLoader by(ImageHandler callback) {
        this.callback = callback;
        return this;
    }

    public void into(ImageView target) {
        if (options == null) {
            throw new RuntimeException("need a Context or Options");
        }
        options.target(target);
        loadImage();
    }

    private void loadImage() {
        checkOptions();
        if (callback == null) {
            callback = new DefaultImageHandler();
        }
        if (options.place != 0) {
            options.target.setImageResource(options.place());
        }
        options.target().setTag(options.tag());
        ThreadManager.getInstance().execute(new Call(options, callback));
    }

    private void checkOptions() {
        if (options.url == null || options.target == null || options.context == null) {
            throw new RuntimeException("parameter are missing in Options");
        }
    }

    public static class Options {
        private Compressor[] compressors;
        private int place;
        private int failed;
        private Object tag;
        private Cache cache;
        private ImageView target;
        private String url;
        private Context context;
        private boolean scaleImage;
        private Encrypt encrypt;
        private CompressOptions options;

        public Options url(String url) {
            this.url = url;
            return this;
        }

        public Options target(ImageView target) {
            this.target = target;
            return this ;
        }

        public Options compressor(Compressor... compressors) {
            this.compressors = compressors;
            return this;
        }

        public Options tag(Object tag) {
            this.tag = tag;
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

        public Options decodeOptions(boolean scaleImage) {
            this.scaleImage = scaleImage;
            return this;
        }

        public Options compressOptions(CompressOptions options) {
            this.options = options;
            return this;
        }

        String url() {
            return url;
        }

        Compressor[] compressors() {
            return compressors;
        }

        int place() {
            return place;
        }

        int failed() {
            return failed;
        }

        Object tag() {
            return tag;
        }

        Cache cache() {
            return cache;
        }

        ImageView target() {
            return target;
        }

        Context context() {
            return context;
        }

        boolean scaleImage() {
            return scaleImage;
        }

        Encrypt encrypt() {
            return encrypt;
        }

        CompressOptions compressOptions() {
            return options;
        }
    }

}
