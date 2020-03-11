package com.ifenduo.lib_okhttp.builder;

import android.text.TextUtils;
import android.util.Log;

import com.ifenduo.lib_okhttp.OkHttpProxy;
import com.ifenduo.lib_okhttp.callback.OkCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequestBuilder<T> extends RequestBuilder<T> {

    public GetRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public GetRequestBuilder setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public GetRequestBuilder addParams(String key, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    public GetRequestBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public Call enqueue(Callback callback) {

        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be null !");
        }

        Request.Builder builder = new Request.Builder().url(url);

        if (tag != null) {
            builder.tag(tag);
        }

        if (params != null && params.size() > 0) {
            url = appendParams(url, params);
        }

        Request request = builder.build();
        if (callback instanceof OkCallback) {
            ((OkCallback) callback).onStart();
        }

        Call call = OkHttpProxy.getInstance().newCall(request);
        call.enqueue(callback);
        Log.i("TAG","requestUrl:"+url);
        return call;
    }

    @Override
    public Response execute() throws IOException {

        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be null !");
        }

        Request.Builder builder = new Request.Builder().url(url);

        if (tag != null) {
            builder.tag(tag);
        }

        if (params != null && params.size() > 0) {
            url = appendParams(url, params);
        }

        Request request = builder.build();

        Call call = OkHttpProxy.getInstance().newCall(request);
        return call.execute();
    }

}
