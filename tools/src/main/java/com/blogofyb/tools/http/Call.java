package com.blogofyb.tools.http;

import com.blogofyb.tools.http.interfaces.ExceptionHandler;
import com.blogofyb.tools.http.interfaces.HttpCallback;
import com.blogofyb.tools.http.interfaces.HttpCallbackE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Call implements Runnable {
    private HttpURLConnection httpURLConnection;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String url;
    private String body;
    private String method;
    private int readTimeout;
    private int connectTimeout;
    private HttpCallback listener;
    private Headers headers;
    private ExceptionHandler handler;

    private Call(Builder builder) {
        url = builder.url;
        body = builder.body;
        method = builder.method;
        readTimeout = builder.readTimeout;
        connectTimeout = builder.connectTimeout;
        listener = builder.listener;
        headers = builder.headers;
        handler = builder.handler;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            System.out.println(body);
            httpURLConnection = this.url.startsWith("https")
                    ? (HttpsURLConnection) url.openConnection()
                    : (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(readTimeout);
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setRequestMethod(method);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.headers().entrySet()) {
                    httpURLConnection.setRequestProperty(entry.getKey() + ": ", entry.getValue());
                }
            }
            if (body != null) {
                httpURLConnection.setDoOutput(true);
                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(httpURLConnection.getOutputStream()));
                bufferedWriter.write(body);
                bufferedWriter.flush();
            }
            Response response = new Response();
            response.setHeaders(httpURLConnection.getHeaderFields());
            bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBody.append(line);
            }
            response.setBody(responseBody.toString());
            listener.onSuccess(response);
        } catch (Exception e) {
            e.printStackTrace();
            if (listener instanceof HttpCallbackE) {
                ((HttpCallbackE) listener).onFailed(e);
            } else if (handler != null) {
                handler.onFailed(e);
            }
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Builder {
        private String url;
        private String body;
        private String method;
        private int readTimeout;
        private int connectTimeout;
        private HttpCallback listener;
        private Headers headers;
        private ExceptionHandler handler;

        Call build() {
            check();
            return new Call(this);
        }

        private void check() {
            if (url == null) {
                throw new RuntimeException("url cannot be null.");
            }
            if (listener == null) {
                throw new RuntimeException("listener cannot be null.");
            }
        }

        Builder exceptionHandler(ExceptionHandler handler) {
            this.handler = handler;
            return this;
        }

        Builder url(String url) {
            this.url = url;
            return this;
        }

        Builder body(String body) {
            this.body = body;
            return this;
        }

        Builder method(String method) {
            this.method = method;
            return this;
        }

        Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        Builder listener(HttpCallbackE listener) {
            this.listener = listener;
            return this;
        }

        Builder headers(Headers headers) {
            this.headers = headers;
            return this;
        }
    }
}
