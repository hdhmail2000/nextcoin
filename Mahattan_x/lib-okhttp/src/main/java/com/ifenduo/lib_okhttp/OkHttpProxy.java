package com.ifenduo.lib_okhttp;


import com.ifenduo.lib_okhttp.body.BodyWrapper;
import com.ifenduo.lib_okhttp.builder.GetRequestBuilder;
import com.ifenduo.lib_okhttp.builder.PostRequestBuilder;
import com.ifenduo.lib_okhttp.builder.UploadRequestBuilder;
import com.ifenduo.lib_okhttp.listener.DownloadListener;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 */
public class OkHttpProxy {

    private static OkHttpClient mHttpClient;

    private static OkHttpClient init() {
        synchronized (OkHttpProxy.class) {
            if (mHttpClient == null) {
                mHttpClient = new OkHttpClient();
            }
        }
        return mHttpClient;
    }

    public static OkHttpClient getInstance() {
        return mHttpClient == null ? init() : mHttpClient;
    }

    public static void setInstance(OkHttpClient okHttpClient) {
        OkHttpProxy.mHttpClient = okHttpClient;
    }

    public static GetRequestBuilder get() {
        return new GetRequestBuilder();
    }

    public static PostRequestBuilder post() {
        return new PostRequestBuilder();
    }

    public static Call download(String url, DownloadListener downloadListener) {
        Request request = new Request.Builder().url(url).build();
        Call call = BodyWrapper.addProgressResponseListener(getInstance(), downloadListener).newCall(request);
        call.enqueue(downloadListener);
        return call;
    }

    /**
     * default time out is 30 min
     */
    public static UploadRequestBuilder upload() {
        return new UploadRequestBuilder();
    }

    public static void cancel(Object tag) {
        Dispatcher dispatcher = getInstance().dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


}
