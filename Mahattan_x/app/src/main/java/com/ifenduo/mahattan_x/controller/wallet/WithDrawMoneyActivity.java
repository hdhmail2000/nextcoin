package com.ifenduo.mahattan_x.controller.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.controller.me.WithDrawPlaceActivity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.RechargeAddress;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.entity.WithdrawSetting;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.CoinValueFilter;
import com.ifenduo.mahattan_x.tools.EmojiInputFilter;
import com.ifenduo.mahattan_x.tools.FromatTool;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;
import com.ifenduo.mahattan_x.widget.dialog.InputPasswordDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawMoneyActivity extends BaseActivity {
    @BindView(R.id.name_text_view)
    TextView mNameTextView;
    @BindView(R.id.text_with_draw_money_address)
    EditText mAddressTextView;
    @BindView(R.id.image_with_draw_money_icon)
    ImageView mIconImageView;
    @BindView(R.id.edit_with_draw_money)
    EditText mMoneyEditText;
    @BindView(R.id.fee_text_view)
    TextView mFeeTextView;
    @BindView(R.id.button_with_draw_money_sure)
    Button mOkButton;
    @BindView(R.id.text_with_draw_money_enable)
    TextView mEnableMoneyTextView;
    @BindView(R.id.rel_money_text_view)
    TextView mRelMoneyTextView;
    @BindView(R.id.edit_with_draw_money_code)
    EditText mCodeEditText;
    @BindView(R.id.text_with_draw_money_get_code)
    TextView mGetCodeButton;

    Wallet mWallet;
    RechargeAddress mRechargeAddress;
    MyCountDownTimer mMyCountDownTimer;
    double mFeeScale;

    String mPassword = "";
    String mGoogleCode = "";
    String mPhone = "";

    public static void openWithDrawMoney(Context context, Wallet wallet) {
        Intent intent = new Intent(context, WithDrawMoneyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, wallet);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_with_draw_money;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationRight(R.drawable.ic_scan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanActivity.openScanQRForResult(WithDrawMoneyActivity.this, Constant.SCAN_ACTION_PAY, Constant.REQUEST_CODE_SCAN_ADDRESS);
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mWallet = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
            if (mWallet != null) {
                mEnableMoneyTextView.setText("当前余额 " + NumberUtil.formatMoney(mWallet.getTotal()) + mWallet.getShortName());
                setNavigationCenter("发送" + mWallet.getShortName());
            }
        }

        mMoneyEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        mAddressTextView.setFilters(new InputFilter[]{new EmojiInputFilter()});
        addInputListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null || loginInfo.getUserinfo() == null) return;
        mPhone = loginInfo.getUserinfo().getFtelephone();
        if (mFeeScale <= 0) {
            fetchFee();
        }
    }

    private void fetchFee() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null || mWallet == null) return;
        Api.getInstance().fetchWithdrawFee(loginInfo.getToken(), String.valueOf(mWallet.getCoinId()), new Callback<WithdrawSetting>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<WithdrawSetting> response) {
                if (isSuccess) {
                    if (response.data != null) {
                        mFeeScale = response.data.getWithdrawFee();
                        mFeeTextView.setText("手续费  " + String.valueOf(mFeeScale) + "%");
                    }
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

    private void submitWithDrawMoney() {

        if (mWallet == null) return;
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }

        if (TextUtils.isEmpty(mAddressTextView.getText().toString())) {
            showToast("请选择提现地址");
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            mPassword = "";
        }
        if (TextUtils.isEmpty(mGoogleCode)) {
            mGoogleCode = "";
        }
        String money = mMoneyEditText.getText().toString();
        String code = mCodeEditText.getText().toString();
        showProgress();
        Api.getInstance().submitWithdrawCoin(loginInfo.getToken(), loginInfo.getSecretKey(),
                String.valueOf(mWallet.getCoinId()), mRechargeAddress == null ? "-1" : String.valueOf(mRechargeAddress.getFid()),
                mAddressTextView.getText().toString(), money, mPassword, mGoogleCode, code, "0", new Callback<BaseEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                        dismissProgress();
                        if (isSuccess) {
                            EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_WITHDRAW_SUCCESS, null));
                            showToast("提现申请已提交");
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

    private void sendMessage() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        if (TextUtils.isEmpty(mPhone) || !Validator.isMobile(mPhone)) {
            showToast("您未绑定手机号，请先绑定");
            return;
        }
        showProgress();
        Api.getInstance().sendPhoneMessage(loginInfo.getToken(), loginInfo.getSecretKey(), 104, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (mMyCountDownTimer != null) {
                    mMyCountDownTimer.cancel();
                    mMyCountDownTimer = null;
                }
                dismissProgress();
                if (isSuccess) {
                    showToast("验证码已发送至" + FromatTool.phoneNumberFormat(mPhone) + "，请注意查收");
                    mMyCountDownTimer = new MyCountDownTimer(WithDrawMoneyActivity.this, 60000, 1000);
                    mMyCountDownTimer.start();
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

    private void bindData(RechargeAddress rechargeAddress) {
        mRechargeAddress = rechargeAddress;
        if (rechargeAddress != null) {
            mAddressTextView.setText(rechargeAddress.getFadderess());
            RequestOptions options = new RequestOptions();
            options.error(R.drawable.ic_address_book);
            Glide.with(this).load(rechargeAddress.getAppLogo()).apply(options).into(mIconImageView);
        } else {
            mAddressTextView.setText("");
            Glide.with(this).load(R.drawable.ic_address_book).into(mIconImageView);
        }
        if (mWallet != null) {
            mNameTextView.setText(mWallet.getShortName());
        } else {
            mNameTextView.setText("");
        }


    }


    @OnClick({R.id.image_with_draw_money_icon, R.id.button_with_draw_money_sure, R.id.text_with_draw_money_get_code, R.id.all_text_view})
    public void click(View view) {
        if (view.getId() == R.id.image_with_draw_money_icon) {
            if (mWallet == null) return;
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, mWallet);
            openActivity(WithDrawMoneyActivity.this, WithDrawPlaceActivity.class, Constant.REQUEST_CODE_COMMON, bundle);
        } else if (view.getId() == R.id.button_with_draw_money_sure) {
            showInputPasswordDialog();
        } else if (view.getId() == R.id.text_with_draw_money_get_code) {
            sendMessage();
        } else if (view.getId() == R.id.all_text_view) {
            if (mWallet != null) {
                mMoneyEditText.setText(NumberUtil.formatMoney(mWallet.getTotal()));
            }
        }
    }

    private void showInputPasswordDialog() {
        InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(this);
        inputPasswordDialog.setOnInputCallBack(new InputPasswordDialog.OnInputCallBack() {
            @Override
            public void getInputPassword(String password) {
                mPassword = password;
                if (TextUtils.isEmpty(password)) return;
                LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
                if (loginInfo == null) {
                    openLogin();
                    return;
                }
                if (loginInfo.getUserinfo() == null) return;
                if (loginInfo.getUserinfo().isFgooglebind()) {
                    showInputGoogleCodeDialog();
                } else {
                    submitWithDrawMoney();
                }
            }
        });
        inputPasswordDialog.show();
    }

    private void showInputGoogleCodeDialog() {
        InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(this);
        inputPasswordDialog.setTitle("谷歌验证码");
        inputPasswordDialog.setOnInputCallBack(new InputPasswordDialog.OnInputCallBack() {
            @Override
            public void getInputPassword(String password) {
                mGoogleCode = password;
                if (TextUtils.isEmpty(password)) return;
                submitWithDrawMoney();
            }
        });
        inputPasswordDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Constant.REQUEST_CODE_COMMON) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    mRechargeAddress = bundle.getParcelable("hotCoin");
                }
                if (mRechargeAddress != null) {
                    bindData(mRechargeAddress);
                } else {

                }
            } else if (requestCode == Constant.REQUEST_CODE_SCAN_ADDRESS) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    mAddressTextView.setText(bundle.getString(Constant.BUNDLE_KEY_QR_CODE, ""));
                }
            }
        }
    }


    private void addInputListener() {
        mAddressTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOkButton.setEnabled(isOkButtonCanEnabled());
            }
        });

        mMoneyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money_ = NumberUtil.formatMoney(s.toString());
                double money = Double.parseDouble(money_);

                if (mWallet != null) {
                    if (mWallet.getTotal() < money) {
                        mRelMoneyTextView.setText("余额不足");
                    } else {
                        double relMoney = money * (1 - mFeeScale);
                        mRelMoneyTextView.setText("实际到账  " + NumberUtil.formatMoney(relMoney));
                    }

                } else {
                    mRelMoneyTextView.setText("实际到账  --");
                }

                mOkButton.setEnabled(isOkButtonCanEnabled());
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
                mOkButton.setEnabled(isOkButtonCanEnabled());
            }
        });
//        mFeeEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String temp = s.toString();
//                int posDot = temp.indexOf(".");
//                if (posDot == 0) {
//                    s.insert(0, "0");
//                }
//                if (posDot >= 0 && temp.length() - posDot - 1 > 2) {
//                    s.delete(posDot + 3, posDot + 4);
//                }
//                mOkButton.setEnabled(isOkButtonCanEnabled());
//            }
//        });
    }

    private boolean isOkButtonCanEnabled() {
        if (TextUtils.isEmpty(mAddressTextView.getText().toString())) {
            return false;
        }

        String moneyValue = "0.0000";
        try {
            moneyValue = NumberUtil.formatMoney(mMoneyEditText.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double moneyValue_ = 0.0000;
        try {
            moneyValue_ = Double.parseDouble(moneyValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        if (moneyValue_ <= 0) return false;

        if (TextUtils.isEmpty(mCodeEditText.getText().toString())) {
            return false;
        }

//        String feeValue = NumberTool.double2String(mFeeEditText.getText().toString());
//        double feeValue_ = 0.00;
//        try {
//            feeValue_ = Double.parseDouble(feeValue);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return false;
//        }
//        if (feeValue_ <= 0) return false;

        return true;
    }

    public void showCountDownTime(long countDownTime) {
        long time = countDownTime / 1000;
        if (time > 0) {
            mGetCodeButton.setText(String.valueOf(time));
            mGetCodeButton.setTextColor(getResources().getColor(R.color.colorGrayText));
            mGetCodeButton.setEnabled(false);
        } else {
            mGetCodeButton.setText("获取验证码");
            mGetCodeButton.setTextColor(getResources().getColor(R.color.colorBlueText));
            mGetCodeButton.setEnabled(true);
        }
    }


    static class MyCountDownTimer extends CountDownTimer {
        WeakReference<WithDrawMoneyActivity> weakReference;

        public MyCountDownTimer(WithDrawMoneyActivity withDrawMoneyActivity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<>(withDrawMoneyActivity);
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
                weakReference.get().showCountDownTime(-1);
            }
        }
    }
}
