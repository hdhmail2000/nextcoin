package com.ifenduo.mahattan_x.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_AUTO_LOGIN_SUCCESS;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone_edit_text)
    EditText mPhoneEditText;
    @BindView(R.id.clear_button)
    ImageButton mClearButton;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.eye_button)
    ImageButton mEyeButton;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    boolean isReLogin;//当token过期 或者loginInfo==null时从其他页面跳转过来


    @Override
    protected boolean toolbarIsEnable() {
        return false;
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return false;
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarTool.setTranslucentForImageView(this, null);
        initView();
        registerInputListener();
        handleIntent();

        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo != null) {
            openActivity(this, HomeActivity.class, null);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.getCode() == EVENT_CODE_AUTO_LOGIN_SUCCESS) {//注册后自动登录成功
            finish();
        }
    }

    @OnClick({R.id.clear_button, R.id.eye_button, R.id.submit_button, R.id.forget_text_view, R.id.register_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_button:
                mPhoneEditText.setText("");
                break;
            case R.id.eye_button:
                mEyeButton.setSelected(!mEyeButton.isSelected());
                if (mEyeButton.isSelected()) {
                    //显示密码
                    mPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    mPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                //移动光标
                mPasswordEditText.setSelection(mPasswordEditText.getText().toString().length());
                break;
            case R.id.submit_button:
                submitLogin();
                break;
            case R.id.forget_text_view:
                openActivity(this, ForgetPasswordActivity.class, null);
                break;
            case R.id.register_text_view:
                openActivity(this, RegisterActivity.class, null);
                break;
        }
    }

    /**
     * 初始化页面控件
     */
    private void initView() {
        mClearButton.setVisibility(View.INVISIBLE);
        mSubmitButton.setEnabled(false);
        mEyeButton.setSelected(false);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            isReLogin = bundle.getBoolean(Constant.BUNDLE_KEY_IS_RELOGIN);
        }
    }

    private void submitLogin() {
        String account = mPhoneEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }

        int type = -1;
        if (Validator.isMobile(account)) {
            type = 0;
        } else if (Validator.isEmail(account)) {
            type = 1;
        } else {
            showToast("用户名格式不正确，请输入手机号码");
            return;
        }
        showProgress();
        Api.getInstance().submitLogin(account, password, "86", type, new Callback<LoginInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<LoginInfo> response) {
                dismissProgress();
                if (isSuccess) {
                    if (response.data != null && response.data.getUserinfo() != null) {
                        SharedPreferencesTool.saveLoginInfo(getApplicationContext(), response.data);
                        if (!isReLogin) {
                            openActivity(LoginActivity.this, HomeActivity.class, null);
                        }
                        finish();
                    }
                } else {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 注册输入监听
     */
    private void registerInputListener() {
        mPhoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setSubmitButtonEnable();
                mClearButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setSubmitButtonEnable();
            }
        });
    }

    /**
     * 设置登录按钮是否可点击
     */
    private void setSubmitButtonEnable() {
        String phone = mPhoneEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mSubmitButton.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && Validator.isMobile(phone) && Validator.isPassword(password));
    }
}
