package com.blogofyb.tools.img;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.blogofyb.tools.img.compress.Compressor;
import com.blogofyb.tools.img.interfaces.Cache;
public class ImageLoader
{

    public ImageLoader with(Context context) {

    }

    public ImageLoader load(String url) {

    }

    public void into(ImageView target) {

    }

    private void loadImage() {

    }

    public static class Options {
        private Compressor<?>[] compressors;
        private int place;
        private int failed;
        private Object tag;
        private Cache<?, ?> cache;
        private ImageView target;
        private String url;

        public Options url(String url) {
            this.url = url;
            return this;
        }

        public Options target(ImageView target) {
            this.target = target;
            return this ;
        }

        public Options compressor(Compressor<?>... compressors) {
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

        public Options cache(Cache<?, ?> cache) {
            this.cache = cache;
            return this;
        }
    }

}
