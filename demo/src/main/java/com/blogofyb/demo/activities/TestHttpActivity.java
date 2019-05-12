package com.blogofyb.demo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogofyb.demo.R;
import com.blogofyb.tools.http.HttpClient;
import com.blogofyb.tools.http.Request;
import com.blogofyb.tools.http.Response;
import com.blogofyb.tools.http.interfaces.HttpCallbackE;
import com.blogofyb.tools.thread.ThreadManager;

import java.util.Random;

public class TestHttpActivity extends AppCompatActivity {
    private String[] urls = {
            "https://news-at.zhihu.com/api/4/news/3892357",
            "https://www.baidu.com",
            "https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90"
    };

    private TextView mTextView;

    private final Random RANDOM = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_http);
        mTextView = findViewById(R.id.tv_response);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = RANDOM.nextInt(urls.length);
                request(urls[index]);
            }
        });
    }

    private void request(String url) {
        HttpClient client = new HttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.connectTimeout(1000)
                .method("GET")
                .url(url)
                .listener(new HttpCallbackE() {
                    @Override
                    public void onFailed(Exception e) {
                        ThreadManager.getInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("ERROR");
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final Response response) {
                        ThreadManager.getInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(response.responseBody());
                            }
                        });
                    }
                })
                .build();
        ThreadManager.getInstance().execute(client.newCall(request));
    }
}
