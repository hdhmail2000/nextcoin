package com.ifenduo.mahattan_x.controller.login;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.tools.Validator;
import com.ifenduo.mahattan_x.widget.WaveView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.cancel_button)
    TextView mCancelButton;
    @BindView(R.id.wave_view)
    WaveView mWaveView;
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
    @BindView(R.id.google_edit_text)
    EditText mGoogleEditText;
    @BindView(R.id.eye_button)
    ImageView mEyeButton;
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
        return R.layout.activity_forget_password;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mCancelButton);
        initView();
    }

    @Override
    protected void onDestroy() {
        stopCountDownTime();
        if (mWaveView != null) {
            mWaveView.stopImmediately();
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWaveView != null) {
            mWaveView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWaveView != null) {
            mWaveView.stop();
        }
    }

    @OnClick({R.id.cancel_button, R.id.clear_button, R.id.get_code_text_view, R.id.eye_button, R.id.submit_button, R.id.login_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                finish();
                break;
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
                submitPassword();
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
        String phone = mPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (!Validator.isMobile(phone)) {
            showToast("请输入正确的手机号");
            return;
        }
        Api.getInstance().sendPhoneMessageNotSigntrue("86", phone, 109, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    startCountDownTime();
                } else {
                    showToast(msg);
                }
            }
        });
    }


    /**
     * 提交密码
     */
    private void submitPassword() {
        String phone = mPhoneEditText.getText().toString();
        String code = mCodeEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String googleCode=mGoogleEditText.getText().toString();
        showProgress();
        Api.getInstance().submitResetLoginPassword(phone, "86", code, googleCode, password, password, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if(isSuccess){
                    showToast("重置登录密码成功");
                    finish();
                }else {
                    showToast(msg);
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
        mWaveView.setInitialRadius(DimensionTool.dp2px(this, 60));
        mWaveView.setMaxRadius(DimensionTool.dp2px(this, 83));
        mWaveView.setStyle(Paint.Style.FILL);
        mWaveView.setColor(Color.parseColor("#BD5FC2"));
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
        WeakReference<ForgetPasswordActivity> weakReference;

        public MyCountDownTimer(ForgetPasswordActivity activity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<>(activity);
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
