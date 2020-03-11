package com.ifenduo.lib_okhttp.callback;

import android.os.Handler;
import android.os.Looper;

import com.ifenduo.lib_okhttp.parser.OkBaseParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class OkCallback<T> implements Callback {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private OkBaseParser<T> mParser;

    public OkCallback(OkBaseParser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        }
        this.mParser = mParser;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final int code =mParser.getCode();
        try {

            final T t=mParser.parseResponse(response);
            if(response.isSuccessful() && t!=null){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(code,t);
                    }
                });
            }else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(new Exception(Integer.toString( code)));
                    }
                });
            }

        }catch (final Exception e){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(e);
                }
            });
        }
    }

    public abstract void onSuccess(int code, T t);

    public abstract void onFailure(Throwable e);

    public void onStart() {
    }

}