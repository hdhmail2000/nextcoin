package com.ifenduo.mahattan_x.controller.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;
import com.ifenduo.mahattan_x.widget.PasswordEditText;
import com.ifenduo.mahattan_x.widget.dialog.CommonInputDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_SET_TRADE_PWD_SUCCESS;

public class SetTransactionPsdStep2Activity extends BaseActivity {
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    String mOldPwd = "";
    String mNewPwd = "";
    String mCode = "";
    String mIDCard = "";
    String mPassword = "";
    String mGoogleCode = "";
    boolean isBindTrade;

    public static void openSetTransactionPsdStep2(Context context, boolean isBindTrade, String oldPwd, String newPwd, String code) {
        Intent intent = new Intent(context, SetTransactionPsdStep2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("oldPwd", oldPwd);
        bundle.putString("newPwd", newPwd);
        bundle.putString("code", code);
        bundle.putBoolean("isBindTrade", isBindTrade);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_set_transaction_psd_step2;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        handleIntent();
        mSubmitButton.setEnabled(false);
        setInputListener();
    }

    /**
     * 接收页面传值
     */
    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mOldPwd = bundle.getString("oldPwd", "");
            mNewPwd = bundle.getString("newPwd", "");
            mCode = bundle.getString("code", "");
            isBindTrade = bundle.getBoolean("isBindTrade", false);
        }
    }


    private void submitFindPassword(String password) {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        Api.getInstance().submitFindTransPwd("86", loginInfo.getUserinfo().getFtelephone(), mCode, mGoogleCode, password, password, loginInfo.getToken(), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    showToast("找回交易密码成功");
                    EventBus.getDefault().post(new BaseEvent(EVENT_CODE_SET_TRADE_PWD_SUCCESS, null));
                    finish();
                } else {
                    if (!TextUtils.isEmpty(msg) && msg.contains("谷歌验证码错误")) {
                        showGoogleDialog();
                    } else if (!TextUtils.isEmpty(msg) && (msg.contains("请输入身份证号") || msg.contains("身份证号码错误"))) {
                        if (msg.contains("身份证号码错误")) {
                            showToast(msg);
                        }
                        showIDCardDialog();
                    }else if (code == 401) {
                        showToast(msg);
                        openLogin();
                    } else {
                        showToast(msg);
                    }
                }
            }
        });
    }

    private void submitPassword(String password) {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }

        if (!TextUtils.isEmpty(mNewPwd) && !TextUtils.isEmpty(password) && !mNewPwd.equals(password)) {
            showToast("两次输入的密码不一致");
            return;
        }

        Api.getInstance().submitUpdatePassword(loginInfo.getToken(), loginInfo.getSecretKey(), mOldPwd,
                mNewPwd, password, mCode, mGoogleCode, mIDCard, 1, new Callback<BaseEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                        if (isSuccess) {
                            if (isBindTrade) {
                                showToast("修改交易密码成功");
                                EventBus.getDefault().post(new BaseEvent(EVENT_CODE_SET_TRADE_PWD_SUCCESS, null));
                            } else {
                                EventBus.getDefault().post(new BaseEvent(EVENT_CODE_SET_TRADE_PWD_SUCCESS, null));
                                showToast("设置交易密码成功");
                            }
                            finish();
                        } else {
                            if (code == 401) {
                                showToast("登录已失效请重新登录");
                                openLogin();
                            } else if (!TextUtils.isEmpty(msg) && (msg.contains("请输入身份证号") || msg.contains("身份证号码错误"))) {
                                if (msg.contains("身份证号码错误")) {
                                    showToast(msg);
                                }
                                showIDCardDialog_();
                            }else if (!TextUtils.isEmpty(msg) && msg.contains("谷歌验证码错误")) {
                                showGoogleDialog_();
                            } else {
                                showToast(msg);
                            }
                        }
                    }
                });
    }

    private void showIDCardDialog() {
        CommonInputDialog dialog = new CommonInputDialog(this);
        dialog.builder();
        dialog.setTitle("身份证号码")
                .setHint("请输入身份证号(与实名认证一致)")
                .setNegativeButton("取消", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        commonInputDialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        if (!Validator.isIDCard(inputContent)) {
                            showToast("身份证号格式不正确");
                        } else {
                            mIDCard = inputContent;
                            submitFindPassword(mPassword);
                            commonInputDialog.dismiss();
                        }
                    }
                })
                .show();
    }

    private void showIDCardDialog_() {
        CommonInputDialog dialog = new CommonInputDialog(this);
        dialog.builder();
        dialog.setTitle("身份证号码")
                .setHint("请输入身份证号(与实名认证一致)")
                .setNegativeButton("取消", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        commonInputDialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        if (!Validator.isIDCard(inputContent)) {
                            showToast("身份证号格式不正确");
                        } else {
                            mIDCard = inputContent;
                            submitPassword(mPassword);
                            commonInputDialog.dismiss();
                        }
                    }
                })
                .show();
    }

    private void showGoogleDialog() {
        CommonInputDialog dialog = new CommonInputDialog(this);
        dialog.builder();
        dialog.setTitle("谷歌验证码")
                .setHint("请输入谷歌验证码")
                .setNegativeButton("取消", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        commonInputDialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        if (TextUtils.isEmpty(inputContent)) {
                            showToast("请输入谷歌验证码");
                        } else {
                            mGoogleCode = inputContent;
                            submitFindPassword(mPassword);
                            commonInputDialog.dismiss();
                        }
                    }
                })
                .show();
    }

    private void showGoogleDialog_() {
        CommonInputDialog dialog = new CommonInputDialog(this);
        dialog.builder();
        dialog.setTitle("谷歌验证码")
                .setHint("请输入谷歌验证码")
                .setNegativeButton("取消", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        commonInputDialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new CommonInputDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent) {
                        if (TextUtils.isEmpty(inputContent)) {
                            showToast("请输入谷歌验证码");
                        } else {
                            mGoogleCode = inputContent;
                            submitPassword(mPassword);
                            commonInputDialog.dismiss();
                        }
                    }
                })
                .show();
    }

    private void setInputListener() {
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSubmitButton.setEnabled(s.length() >= 6 && s.length() <= 16);
            }
        });
    }


    @OnClick(R.id.submit_button)
    public void onViewClicked() {
        mPassword = mPasswordEditText.getText().toString();
        if (!Validator.isPassword(mPassword)) {
            showToast("密码格式不正确");
            return;
        }
        if (isBindTrade && !TextUtils.isEmpty(mCode)) {
            submitFindPassword(mPassword);
        } else {
            submitPassword(mPassword);
        }
    }
}
