package com.blogofyb.tools.http;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    private Map<String, String> headers;

    public Headers(){
        headers = new HashMap<>();
    }

    public Headers add(String key, String value) {
        headers.put(key, value);
        return this;
    }

    Map<String, String> headers() {
        return headers;
    }
}
