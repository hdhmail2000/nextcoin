package com.ifenduo.mahattan_x.controller.me;

import android.content.Intent;
import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;

import butterknife.OnClick;

public class VerifiedSuccessActivity extends BaseActivity {
    boolean isFromRegister;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_verified_success;
    }

    @Override
    protected boolean toolbarIsEnable() {
        return false;
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, null);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            isFromRegister = bundle.getBoolean(Constant.BUNDLE_KEY_COMMON, false);
        }
    }

    @OnClick(R.id.submit_button)
    public void onViewClicked() {
        if (isFromRegister) {
            openActivity(this, HomeActivity.class, null);
        }
        finish();
    }
}
