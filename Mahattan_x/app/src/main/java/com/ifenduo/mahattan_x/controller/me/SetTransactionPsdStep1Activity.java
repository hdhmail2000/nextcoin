package com.ifenduo.mahattan_x.controller.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;
import com.ifenduo.mahattan_x.widget.PasswordEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_SET_TRADE_PWD_SUCCESS;

public class SetTransactionPsdStep1Activity extends BaseActivity {

    @BindView(R.id.phone_text_view)
    TextView mPhoneTextView;

    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    private String mOldPwd = "";
    private String mCode = "";
    private boolean isBindTrade;

    public static void openSetTransactionPsdStep1(Context context, boolean isBindTrade, String oldPwd, String code) {
        Intent intent = new Intent(context, SetTransactionPsdStep1Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("oldPwd", oldPwd);
        bundle.putString("code", code);
        bundle.putBoolean("isBindTrade", isBindTrade);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_set_transaction_psd_step1;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        handleIntent();
        mSubmitButton.setEnabled(false);
        setInputListener();
        User user = SharedPreferencesTool.getUser(getApplicationContext());
        if (user != null) {
            mPhoneTextView.setText("为账户" + user.getFormtPhone() + "设置6-16位数字、字母组合的支付密码");
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 接收页面传值
     */
    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mOldPwd = bundle.getString("oldPwd", "");
            mCode = bundle.getString("code", "");
            isBindTrade = bundle.getBoolean("isBindTrade", false);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.getCode() == EVENT_CODE_SET_TRADE_PWD_SUCCESS) {
            finish();
        }
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
        String pwd = mPasswordEditText.getText().toString();
        if (!Validator.isPassword(pwd)) {
            showToast("密码格式不正确");
            return;
        }
        SetTransactionPsdStep2Activity.openSetTransactionPsdStep2(this, isBindTrade, mOldPwd, pwd, mCode);
    }
}
