package com.ifenduo.mahattan_x.api;

/**
 * Created by yangxuefeng on 16/8/16.
 */
public interface Callback<T> {

    void onComplete(boolean isSuccess, int code, String msg, DataResponse<T> response);

}
