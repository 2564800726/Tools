package com.blogofyb.tools.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private ExecutorService mExecutor;
    private Handler mHandler;
    private static volatile ThreadManager threadManager;

    private ThreadManager() {
        mExecutor = Executors.newCachedThreadPool();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static ThreadManager getInstance() {
        if (threadManager == null) {
            synchronized (ThreadManager.class) {
                if (threadManager == null) {
                    threadManager = new ThreadManager();
                }
            }
        }
        return threadManager;
    }

    public void post(Runnable commend) {
        mHandler.post(commend);
    }

    public void postDelay(Runnable commend, long delayMillis) {
        mHandler.postDelayed(commend, delayMillis);
    }

    public void execute(Runnable commend) {
        mExecutor.execute(commend);
    }

    public <T> void submit(Runnable commend, T result) {
        mExecutor.submit(commend, result);
    }

    public <T> void submit(Callable<T> commend) {
        mExecutor.submit(commend);
    }

    public void cancelPost(Runnable commend) {
        mHandler.removeCallbacks(commend);
    }
}
