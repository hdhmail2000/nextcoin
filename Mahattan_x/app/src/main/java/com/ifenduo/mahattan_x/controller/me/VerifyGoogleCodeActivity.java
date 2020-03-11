package com.ifenduo.mahattan_x.controller.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.PasswordEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class VerifyGoogleCodeActivity extends BaseActivity implements PasswordEditText.OnInputFinishedListener {

    @BindView(R.id.psd_input_code)
    PasswordEditText psdInputCode;

    String mGoogleKey;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_verify_google_code;
    }


    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("Google验证码");
        psdInputCode.setOnInputFinishedListener(this);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mGoogleKey = intent.getExtras().getString(Constant.BUNDLE_KEY_COMMON, "");
        }
    }

    private void submitBindGoogleCode(String code, String key) {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().submitBindGoogleCode(loginInfo.getToken(), loginInfo.getSecretKey(), code, key, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
//                    if (mFromWhere == Constant.GO_TO_GOOGLE_CODE_FROM_ADD_ADDRESS) {//从提币新增地址页面跳转过来
//                        EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_GOOGLE_CODE_ADD_ADDRESS_SUCCESS, null));
//                        finish();
//                    } else {
//                        openActivity(GoogleVerifyCodeActivity.this, WithDrawSuccessActivity.class, null);
//                    }EVENT_CODE_BIND_GOOGLE_SUCCESS
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_GOOGLE_CODE_ADD_ADDRESS_SUCCESS, null));
                    showToast(msg);
                    finish();
                } else {
                    if (code == 401) {
                        showToast("登录已失效请重新登录");
                        openLogin();
                    } else {
                        showToast(msg);
                    }
                }
            }
        });
    }

    @Override
    public void inputFinished(String inputPsd) {
        submitBindGoogleCode(inputPsd, mGoogleKey);
    }
}
