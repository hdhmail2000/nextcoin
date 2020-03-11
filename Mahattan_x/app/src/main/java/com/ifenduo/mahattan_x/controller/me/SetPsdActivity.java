package com.ifenduo.mahattan_x.controller.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPsdActivity extends BaseActivity {

    @BindView(R.id.edit_set_pwd_old_pwd)
    EditText mOldPwdEditText;
    @BindView(R.id.edit_set_pwd_new_pwd)
    EditText mNewPwdEditText;
    @BindView(R.id.edit_set_pwd_new_pwd_)
    EditText mNewPwdEditText_;
    @BindView(R.id.button_submit)
    Button mButton;
    @BindView(R.id.google_edit_text)
    EditText mGoogleEditText;

    String mGoogleCode = "";

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_set_psd;
    }

    @Override
    protected boolean toolbarIsEnable() {
        return true;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setupInputListener();
    }

    private void submitPassword() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        String oldPwd = mOldPwdEditText.getText().toString();
        String newPwd = mNewPwdEditText.getText().toString();
        String newPwd_ = mNewPwdEditText_.getText().toString();
        if (!TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(newPwd_) && !newPwd.equals(newPwd_)) {
            showToast("两次输入的密码不一致");
            return;
        }

        Api.getInstance().submitUpdatePassword(loginInfo.getToken(), loginInfo.getSecretKey(), oldPwd,
                newPwd, newPwd_, "", mGoogleEditText.getText().toString(), "", 0, new Callback<BaseEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                        if (isSuccess) {
                            showToast("修改密码成功");
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

    @OnClick(R.id.button_submit)
    public void click(View view) {
        submitPassword();
    }


    private void setupInputListener() {
        mOldPwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnable();
            }
        });

        mNewPwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnable();
            }
        });

        mNewPwdEditText_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnable();
            }
        });


    }

    private void setButtonEnable() {
        String oldPwd = mOldPwdEditText.getText().toString();
        String newPwd = mNewPwdEditText.getText().toString();
        String newPwd_ = mNewPwdEditText_.getText().toString();

        if (!TextUtils.isEmpty(oldPwd) && !TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(newPwd_)
                && Validator.isPassword(oldPwd) && Validator.isPassword(newPwd) && Validator.isPassword(newPwd_)) {
            mButton.setEnabled(true);
        } else {
            mButton.setEnabled(false);
        }

    }
}
