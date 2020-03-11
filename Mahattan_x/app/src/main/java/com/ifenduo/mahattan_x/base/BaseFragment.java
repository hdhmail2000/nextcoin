package com.ifenduo.mahattan_x.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.controller.login.LoginActivity;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by xuefengyang on 2016/5/14.
 */
public abstract class BaseFragment extends RxFragment implements View.OnClickListener {

    public View fragmentRootView;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public Activity getCurrentActivity() {
        return (Activity) mContext;
    }

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text, int length) {
        Toast.makeText(getContext(), text, length).show();
    }

    public void showErrorToast(String text) {

    }

    public void showNetErrorToast() {

    }

    public void showProgress() {
        if (getCurrentActivity() instanceof BaseActivity) {
            ((BaseActivity) getCurrentActivity()).showProgress();
        }
    }

    public void showProgress(String message) {
    }

    public void dismissProgress() {
        if (getCurrentActivity() instanceof BaseActivity) {
            ((BaseActivity) getCurrentActivity()).dismissProgress();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRootView = inflater.inflate(getRootViewLayout(), container, false);
        initView(fragmentRootView);
        initData();
        return fragmentRootView;
    }

    protected abstract int getRootViewLayout();

    protected abstract void initView(View parentView);

    protected abstract void initData();

    protected void onViewClick(View v) {
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }

    /**
     * 当token过期 或者loginInfo==null时 跳转登录
     */
    protected void openLogin() {
        SharedPreferencesTool.loginOut(getContext().getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.BUNDLE_KEY_IS_RELOGIN, true);
        LoginActivity.openActivity((BaseActivity) getContext(), LoginActivity.class, bundle);
    }

}
