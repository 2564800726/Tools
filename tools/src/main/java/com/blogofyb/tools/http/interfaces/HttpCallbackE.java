package com.blogofyb.tools.http.interfaces;

public interface HttpCallbackE extends HttpCallback {

    /**
     * 可以自定义异常的处理方式
     * @param e 异常
     */
    void onFailed(Exception e);

}
