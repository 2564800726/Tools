package com.blogofyb.tools.img;

import android.widget.ImageView;

public class ImageViewToken {
    private ImageView target;
    private Object tag;
    private String url;

    ImageView getTarget() {
        return target;
    }

    void setTarget(ImageView target) {
        this.target = target;
    }

    Object getTag() {
        return tag;
    }

    void setTag(Object tag) {
        this.tag = tag;
    }

    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }
}
