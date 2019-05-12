package com.blogofyb.tools.http;

import com.blogofyb.tools.http.interfaces.HttpCallback;
import com.blogofyb.tools.json.MyJson;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String body;
    private String url;
    private UrlParameters parameters;
    private String method;
    private int readTimeout;
    private int connectTimeout;
    private HttpCallback listener;
    private Headers headers;

    private Request(Builder builder) {
        body = builder.body;
        url = builder.url;
        parameters = builder.parameters;
        method = builder.method;
        readTimeout = builder.readTimeout;
        connectTimeout = builder.connectTimeout;
        listener = builder.listener;
        headers = builder.headers;
        if (method == null || "".equals(method)) {
            if (parameters == null) {
                method = "GET";
            } else {
                method = "POST";
            }
        }
    }

    Headers headers() {
        return headers;
    }

    int readTimeout() {
        return readTimeout;
    }

    int connectTimeout() {
        return connectTimeout;
    }

    HttpCallback listener() {
        return listener;
    }

    String body() {
        return body;
    }

    String url() {
        return url;
    }

    UrlParameters parameters() {
        return parameters;
    }

    String method() {
        return method;
    }

    public static class Builder {
        private String body;
        private String url;
        private UrlParameters parameters;
        private Headers headers;
        private String method;
        private int readTimeout = 3000;
        private int connectTimeout = 3000;
        private HttpCallback listener;
        private Map<String, Object> bodyItems;

        public Request build() {
            if (bodyItems != null) {
                buildBody();
            }
            return new Request(this);
        }

        private void buildBody() {
            StringBuilder body = new StringBuilder();
            body.append("{");
            for (Map.Entry<String, Object> entry : bodyItems.entrySet()) {
                body.append("\"").append(entry.getKey()).append("\"").append(": ");
                Object value = entry.getValue();
                if (value instanceof String) {
                    body.append("\"").append(value).append("\"");
                } else {
                    body.append(String.valueOf(value));
                }
                body.append(", ");
            }
            body.delete(body.length() - 2, body.length() - 1);
            body.append("}");
            this.body = body.toString();
        }

        /**
         * 把一个bean作为请求体
         * @param body  bean
         * @return  this
         */
        public Builder post(Object body) {
            this.body = new MyJson().fromObject(body);
            return this;
        }

        /**
         * 手动构建简单的请求体
         * @param key  key
         * @param value  基本数据类型和字符串
         * @return  this
         */
        public Builder post(String key, Object value) {
            if (bodyItems == null) {
                bodyItems = new HashMap<>();
            }
            bodyItems.put(key, value);
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 通过一个UrlParameters实例添加请求的参数
         * @param parameters  参数
         * @return  this
         */
        public Builder parameters(UrlParameters parameters) {
            this.parameters = parameters;
            return this;
        }

        /**
         * 手动添加请求的参数
         * @param key  key
         * @param value  value
         * @return  this
         */
        public Builder parameters(String key, String value) {
            if (parameters == null) {
                parameters = new UrlParameters();
            }
            parameters.addParameter(key, value);
            return this;
        }

        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder listener(HttpCallback listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 手动添加header
         * @param key  key
         * @param value  value
         * @return  this
         */
        public Builder header(String key, String value) {
            if (headers == null) {
                headers = new Headers();
            }
            headers.add(key, value);
            return this;
        }

        /**
         * 通过一个Headers实例来添加header
         * @param headers  Headers实例
         * @return  this
         */
        public Builder headers(Headers headers) {
            this.headers = headers;
            return this;
        }
    }
}
