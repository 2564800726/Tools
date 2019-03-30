package com.blogofyb.tools.http;

import java.util.Map;

public class HttpClient {
    private UrlParameters parameters;
    private String url;

    public Call newCall(Request request) {
        parameters = request.parameters();
        url = request.url();
        return new Call.Builder()
                .url(addParametersToUrl())
                .body(request.body())
                .connectTimeout(request.connectTimeout())
                .readTimeout(request.readTimeout())
                .method(request.method())
                .listener(request.listener())
                .build();
    }

    private String addParametersToUrl() {
        if (parameters == null) {
            return url;
        }
        StringBuilder url = new StringBuilder();
        url.append(this.url).append("?");
        for (Map.Entry<String, String> entry : parameters.getParameters().entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }
}
