package com.ifenduo.lib_okhttp.listener;


import com.ifenduo.lib_okhttp.Model.Progress;

public interface ProgressListener {
    void onProgress(Progress progress);
}