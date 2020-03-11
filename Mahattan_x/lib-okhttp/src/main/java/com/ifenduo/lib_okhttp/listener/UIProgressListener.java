package com.ifenduo.lib_okhttp.listener;


import com.ifenduo.lib_okhttp.Model.Progress;

public interface UIProgressListener {

    void onUIProgress(Progress progress);

    void onUIStart();

    void onUIFinish();
}
