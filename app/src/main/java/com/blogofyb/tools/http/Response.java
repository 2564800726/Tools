package com.blogofyb.tools.http;

import java.util.List;
import java.util.Map;

public class Response {
    private String responseBody;
    private Map<String, List<String>> responseHeaders;

    public Map<String, List<String>> headers() {
        return responseHeaders;
    }

    public String responseBody() {
        return responseBody;
    }

    public void setBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
