package com.blogofyb.tools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.blogofyb.tools.http.HttpClient;
import com.blogofyb.tools.http.Request;
import com.blogofyb.tools.http.Response;
import com.blogofyb.tools.http.UrlParameters;
import com.blogofyb.tools.http.interfaces.HttpCallback;
import com.blogofyb.tools.json.MyJson;
import com.blogofyb.tools.json.TypeToken;
import com.blogofyb.tools.thread.ThreadManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        MyJson json = new MyJson();
        Test1<com.blogofyb.tools.Test> testTest1 = json.fromJson("{\"message\": \"OK\", \"status\": 200, " +
                "\"data\": {" +
                "\"name\": \"袁兵\"," +
                "\"age\": 19," +
                "\"scores\": [100,100,100]}}", new TypeToken<Test1<com.blogofyb.tools.Test>>(){});

        testTest1 = json.fromJson(json.fromObject(testTest1), new TypeToken<Test1<com.blogofyb.tools.Test>>(){});
        System.out.println(testTest1.getData().getScores());
        HttpClient client = new HttpClient();
        UrlParameters parameters = new UrlParameters();
        parameters.addParameter("testKey1", "testValue1");
        parameters.addParameter("testKey2", "testValue2");
        Request request = new Request.Builder().url("http://www.baidu.com")
                .method("POST")
                .post("name", "xiao ming")
                .post("age", 2)
                .parameters("testKey1", "testValue1")
                .listener(new HttpCallback() {
                    @Override
                    public void onSuccess(final Response response) {
                        ThreadManager.getInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(response.responseBody());
                            }
                        });
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                }).build();
        ThreadManager.getInstance().execute(client.newCall(request));
    }
}
