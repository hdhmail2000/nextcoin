package com.ifenduo.mahattan_x.controller.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.MainActivity;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.Validator;
import com.ifenduo.mahattan_x.widget.PasswordEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_SET_TRADE_PWD_SUCCESS;

public class ReSetTransactionPsdActivity extends BaseActivity {


    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_re_set_transaction_psd;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        mSubmitButton.setEnabled(false);
        setInputListener();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.forget_text_view, R.id.submit_button})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.submit_button) {
            String pwd = mPasswordEditText.getText().toString();
            if (!Validator.isPassword(pwd)) {
                showToast("密码格式不正确");
                return;
            }
            SetTransactionPsdStep1Activity.openSetTransactionPsdStep1(this, true, pwd, "");
        } else if (view.getId() == R.id.forget_text_view) {
            Intent intent = new Intent();
            intent.setClass(this, FindTransactionPsdActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            openActivity(this, FindTransactionPsdActivity.class, null);
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
}
