package com.ifenduo.mahattan_x.controller.me;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_SET_TRADE_PWD_SUCCESS;

public class FindTransactionPsdActivity extends BaseActivity {


    @BindView(R.id.password_edit_text)
    EditText mCodeEditText;
    @BindView(R.id.phone_text_view)
    TextView mPhoneTextView;
    @BindView(R.id.code_text_view)
    TextView mSendTextView;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    MyCountDownTimer mCountDownTimer;
    User mUser;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_find_transaction_psd;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        mSubmitButton.setEnabled(false);
        setInputListener();
        Log.d("TAG", "onCreateViewAfter: +++++++++++++");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
        } else {
            mPhoneTextView.setText("验证码已发送至" + mUser.getFormtPhone());
            if(mSendTextView.isEnabled()){
                sendSMS();
            }
        }

    }

    @OnClick({R.id.code_text_view,R.id.submit_button})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.code_text_view) {
            sendSMS();
        } else if (view.getId() == R.id.submit_button) {
            SetTransactionPsdStep1Activity.openSetTransactionPsdStep1(this, true, "", mCodeEditText.getText().toString());
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.getCode() == EVENT_CODE_SET_TRADE_PWD_SUCCESS) {
            finish();
        }
    }

    /**
     * 显示倒计时时间
     */
    public void showCountDownTime(long millisUntilFinished) {
        if (millisUntilFinished > 0) {
            mSendTextView.setText(String.valueOf((millisUntilFinished / 1000)) + "s后可重新发送");
            mSendTextView.setTextColor(getResources().getColor(R.color.colorNotEnabled));
            mSendTextView.setEnabled(false);
        } else {
            mSendTextView.setText("发送验证码");
            mSendTextView.setTextColor(getResources().getColor(R.color.colorTabNormal));
            mSendTextView.setEnabled(true);
        }
    }

    /**
     * 发送短信验证码
     */
    private void sendSMS() {
        if (mUser == null) return;
        Api.getInstance().sendPhoneMessageNotSigntrue("86", mUser.getFtelephone(), 107, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    startCountDownTime();
                } else {
                    showToast(msg);
                    mSendTextView.setEnabled(true);
                }
            }
        });
    }

    /**
     * 开始倒计时
     */
    private void startCountDownTime() {
        stopCountDownTime();
        mCountDownTimer = new MyCountDownTimer(this, 60000, 1000);
        mCountDownTimer.start();
    }

    private void setInputListener() {
        mCodeEditText.addTextChangedListener(new TextWatcher() {
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

    /**
     * 停止倒计时
     */
    private void stopCountDownTime() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    static class MyCountDownTimer extends CountDownTimer {
        WeakReference<FindTransactionPsdActivity> weakReference;

        public MyCountDownTimer(FindTransactionPsdActivity activity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (weakReference.get() != null) {
                weakReference.get().showCountDownTime(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            if (weakReference.get() != null) {
                weakReference.get().showCountDownTime(0);
            }
        }
    }
}
