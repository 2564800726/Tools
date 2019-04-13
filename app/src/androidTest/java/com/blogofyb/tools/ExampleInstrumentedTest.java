package com.blogofyb.tools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.blogofyb.tools.http.HttpClient;
import com.blogofyb.tools.http.Request;
import com.blogofyb.tools.http.Response;
import com.blogofyb.tools.http.UrlParameters;
import com.blogofyb.tools.http.interfaces.HttpCallbackE;
import com.blogofyb.tools.json.MyJson;
import com.blogofyb.tools.json.TypeToken;
import com.blogofyb.tools.thread.ThreadManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.blogofyb.app", appContext.getPackageName());
    }
}
