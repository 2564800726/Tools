package com.blogofyb.tools.http;

import com.blogofyb.tools.http.interfaces.HttpCallback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public Call(Builder builder) {
        this.url = builder.url;
        this.body = builder.body;
        this.method = builder.method;
        this.readTimeout = builder.readTimeout;
        this.connectTimeout = builder.connectTimeout;
        this.listener = builder.listener;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            System.out.println("----    " + url);
            System.out.println(body);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(readTimeout);
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setRequestMethod(method);
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
            listener.onFailed(e);
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

    public static class Builder {
        private String url;
        private String body;
        private String method;
        private int readTimeout;
        private int connectTimeout;
        private HttpCallback listener;

        public Call build() {
            return new Call(this);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
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
    }
}
