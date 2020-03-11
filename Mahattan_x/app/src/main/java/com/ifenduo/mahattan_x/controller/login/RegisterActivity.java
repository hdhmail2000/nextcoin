package com.ifenduo.mahattan_x.controller.login;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.controller.me.VerifiedActivity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phone_edit_text)
    EditText mPhoneEditText;
    @BindView(R.id.clear_button)
    ImageView mClearButton;
    @BindView(R.id.code_edit_text)
    EditText mCodeEditText;
    @BindView(R.id.get_code_text_view)
    TextView mGetCodeTextView;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.eye_button)
    ImageView mEyeButton;
    @BindView(R.id.invitation_edit_text)
    EditText mInvitationEditText;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    MyCountDownTimer mCountDownTimer;

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
        return R.layout.activity_register;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, null);
        initView();
    }

    @Override
    protected void onDestroy() {
        stopCountDownTime();
        super.onDestroy();
    }

    @OnClick({R.id.clear_button, R.id.get_code_text_view, R.id.eye_button, R.id.submit_button, R.id.login_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_button:
                mPhoneEditText.setText("");
                break;
            case R.id.get_code_text_view:
                fetchCode();
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
                submitRegister();
                break;
            case R.id.login_text_view:
                finish();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void fetchCode() {
        stopCountDownTime();
        String phone = mPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (!Validator.isMobile(phone)) {
            showToast("请输入正确的手机号");
            return;
        }
        showProgress();
        Api.getInstance().sendPhoneMessageNotSigntrue("86", phone, 112, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    startCountDownTime();
                } else {
                    showToast(msg);
                }

            }
        });

    }

    /**
     * 提交注册
     */
    private void submitRegister() {
        final String phone = mPhoneEditText.getText().toString();
        String code = mCodeEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();
        final String invitation = mInvitationEditText.getText().toString();

        if (!Validator.isMobile(phone)) {
            showToast("手机号码格式不正确");
            return;
        }

        if (!Validator.isPassword(password)) {
            showToast("密码格式不正确");
            return;
        }

        showProgress();
        Api.getInstance().submitRegister("86", phone, "0", code, "", password, invitation, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    registerPHP(phone, password, password, invitation);
                } else {
                    dismissProgress();
                    showToast(msg);
                }
            }
        });
    }

    private void registerPHP(final String account, final String password, String password_, String invitation) {
        Api.getInstance().submitRegisterPHP(account, password, password_, invitation, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    submitLogin(account, password);
                } else {
                    dismissProgress();
                    showToast(msg);
                }
            }
        });
    }

    private void submitLogin(String account, String password) {
        int type = -1;
        if (Validator.isMobile(account)) {
            type = 0;
        } else if (Validator.isEmail(account)) {
            type = 1;
        } else {
            showToast("用户名格式不正确，请输入手机号码");
            return;
        }
        Api.getInstance().submitLogin(account, password, "86", type, new Callback<LoginInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<LoginInfo> response) {
                dismissProgress();
                if (isSuccess) {
                    if (response.data != null && response.data.getUserinfo() != null) {
                        SharedPreferencesTool.saveLoginInfo(getApplicationContext(), response.data);
                        Bundle bundle=new Bundle();
                        bundle.putBoolean(Constant.BUNDLE_KEY_COMMON,true);
                        openActivity(RegisterActivity.this, VerifiedActivity.class, bundle);
                        EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_AUTO_LOGIN_SUCCESS,null));
                        finish();
                    }
                } else {
                    showToast("自动登录失败");
                    finish();
                }
            }
        });
    }


    /**
     * 初始化相关控件
     */
    private void initView() {
        mGetCodeTextView.setEnabled(true);
        mSubmitButton.setEnabled(false);
        mClearButton.setVisibility(View.INVISIBLE);
        registerInputListener();
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
                mClearButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                setSubmitButtonEnable();
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

        mCodeEditText.addTextChangedListener(new TextWatcher() {
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
     * 设置注册按钮是否可点击
     */
    private void setSubmitButtonEnable() {
        String phone = mPhoneEditText.getText().toString();
        String code = mCodeEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mSubmitButton.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(code)
                && Validator.isMobile(phone) && code.length() == 6 && Validator.isPassword(password));
    }

    /**
     * 显示倒计时
     *
     * @param millisUntilFinished
     */
    private void showCountDownTime(long millisUntilFinished) {
        if (millisUntilFinished > 0) {
            mGetCodeTextView.setEnabled(false);
            mGetCodeTextView.setText(String.valueOf(millisUntilFinished / 1000));
        } else {
            mGetCodeTextView.setEnabled(true);
            mGetCodeTextView.setText("获取验证码");
        }
    }

    /**
     * 开始倒计时
     */
    private void startCountDownTime() {
        if (mCountDownTimer != null) {
            stopCountDownTime();
        }
        mCountDownTimer = new MyCountDownTimer(this, 60000, 1000);
        mCountDownTimer.start();
    }

    /**
     * 停止倒计时
     */
    private void stopCountDownTime() {
        if (mCountDownTimer == null) return;
        mCountDownTimer.cancel();
        mCountDownTimer = null;
    }

    static class MyCountDownTimer extends CountDownTimer {
        WeakReference<RegisterActivity> weakReference;

        public MyCountDownTimer(RegisterActivity registerActivity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<>(registerActivity);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().showCountDownTime(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().showCountDownTime(0);
            }
        }
    }

}
