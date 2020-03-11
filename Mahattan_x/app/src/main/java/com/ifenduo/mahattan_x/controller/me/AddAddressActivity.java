package com.ifenduo.mahattan_x.controller.me;

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

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.controller.wallet.ScanActivity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.EmojiInputFilter;
import com.ifenduo.mahattan_x.tools.FromatTool;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;
import com.ifenduo.mahattan_x.widget.dialog.InputPasswordDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;


public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.edit_add_address_address)
    EditText mAddressEditText;
    @BindView(R.id.edit_add_address_other)
    EditText mOtherEditText;
    @BindView(R.id.btn_add_address)
    Button mOkButton;
    @BindView(R.id.edit_add_address_code)
    EditText mCodeEditText;
    @BindView(R.id.text_add_address_get_code)
    TextView mGetCodeButton;
    @BindView(R.id.coin_image_view)
    ImageView mCoinImageView;
    @BindView(R.id.coin_name_text_view)
    TextView mCoinNameTextView;

    Wallet mWallet;
    String mGoogleCode = "";
    String mPwd = "";
    String mPhone = "";

    MyCountDownTimer mMyCountDownTimer;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected boolean toolbarIsEnable() {
        return true;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationRight(R.drawable.ic_scan);
        setNavigationCenter("创建新地址");
        setNavigationRight(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanActivity.openScanQRForResult(AddAddressActivity.this, Constant.SCAN_ACTION_ADD_ADDRESS, Constant.REQUEST_CODE_COMMON);
            }
        });
        EventBus.getDefault().register(this);
        setupInputListener();
        mOkButton.setEnabled(false);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mWallet = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        mAddressEditText.setFilters(new InputFilter[]{new EmojiInputFilter()});
        mOtherEditText.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(10)});
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null || loginInfo.getUserinfo() == null) return;
        mPhone = loginInfo.getUserinfo().getFtelephone();
    }

    private void submitAddress() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        if (mWallet == null) return;
        String address = mAddressEditText.getText().toString();
        String remark = mOtherEditText.getText().toString();
        String code = mCodeEditText.getText().toString();
        Api.getInstance().submitWithdrawAddress(loginInfo.getToken(), loginInfo.getSecretKey(),
                String.valueOf(mWallet.getCoinId()), code, mGoogleCode, mPwd, address, remark, new Callback<BaseEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                        if (isSuccess) {
                            showToast("添加成功");
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
                    mMyCountDownTimer = new MyCountDownTimer(AddAddressActivity.this, 60000, 1000);
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


    @OnClick({R.id.btn_add_address, R.id.text_add_address_get_code})
    public void click(View view) {
        if (R.id.btn_add_address == view.getId()) {
            showInputPasswordDialog();
        } else if (R.id.text_add_address_get_code == view.getId()) {
            sendMessage();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_COMMON) {
                if (data == null) return;
                Bundle bundle = data.getExtras();
                if (bundle == null) return;
                String qrCode = bundle.getString(Constant.BUNDLE_KEY_QR_CODE, "");
                mAddressEditText.setText(qrCode);
            }
        }
    }

    private void setupInputListener() {
        mAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOkButton.setEnabled(isOKButtonCanEnabled());
            }
        });

        mOtherEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOkButton.setEnabled(isOKButtonCanEnabled());
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
                mOkButton.setEnabled(isOKButtonCanEnabled());
            }
        });
    }


    private void showInputPasswordDialog() {
        InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(this);
        inputPasswordDialog.setOnInputCallBack(new InputPasswordDialog.OnInputCallBack() {
            @Override
            public void getInputPassword(String password) {
                mPwd = password;
                if (!TextUtils.isEmpty(mPwd)) {
                    LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
                    if (loginInfo == null) {
                        openLogin();
                        return;
                    }
                    if (loginInfo.getUserinfo() == null) return;
                    submitAddress();
                }
            }
        });
        inputPasswordDialog.show();

    }


    /**
     * 判断添加按钮是否可点击
     *
     * @return
     */
    private boolean isOKButtonCanEnabled() {
        String address = mAddressEditText.getText().toString();
        String remark = mOtherEditText.getText().toString();
        String code = mCodeEditText.getText().toString();
        return (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(remark) && !TextUtils.isEmpty(code));
    }


    /**
     * google验证成功 销毁页面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCallback(BaseEvent event) {
        if (event.getCode() == BaseEvent.EVENT_CODE_GOOGLE_CODE_ADD_ADDRESS_SUCCESS) {
            finish();
        }
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

    @Override
    protected void onDestroy() {
        if (mMyCountDownTimer != null) {
            mMyCountDownTimer.cancel();
            mMyCountDownTimer = null;
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    static class MyCountDownTimer extends CountDownTimer {
        WeakReference<AddAddressActivity> weakReference;

        public MyCountDownTimer(AddAddressActivity addAddressActivity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<>(addAddressActivity);
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
