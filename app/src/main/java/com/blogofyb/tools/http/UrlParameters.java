package com.blogofyb.tools.http;

import java.util.HashMap;
import java.util.Map;

public class UrlParameters {
    private Map<String, String> parameters;

    public UrlParameters() {
        parameters = new HashMap<>();
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public UrlParameters addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }
}
