package com.ifenduo.mahattan_x.controller.me;

import android.content.Intent;
import android.os.Bundle;
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
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.SafeSettingEntity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.EmojiInputFilter;
import com.ifenduo.mahattan_x.tools.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifiedActivity extends BaseActivity {

    @BindView(R.id.name_edit_text)
    EditText mNameEditText;
    @BindView(R.id.id_card_edit_text)
    EditText mIDCardEditText;
    @BindView(R.id.submit_button)
    Button mSubmitButton;
    @BindView(R.id.name_clear_button)
    ImageView mNameClearButton;
    @BindView(R.id.id_card_clear_button)
    ImageView mIDCardClearButton;
    @BindView(R.id.cancel_button)
    TextView mCancelButton;

    boolean isFromRegister;//注册成功后跳转过来

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_verified;
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        handleIntetn();
        if (isFromRegister) {
            mToolbar.setVisibility(View.GONE);
            mCancelButton.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            mCancelButton.setVisibility(View.GONE);
        }
        StatusBarTool.setTranslucentForImageView(this, isFromRegister ? mCancelButton : mToolBarContainer);

        initView();
        mNameEditText.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(10)});
    }

    private void handleIntetn() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            isFromRegister = bundle.getBoolean(Constant.BUNDLE_KEY_COMMON, false);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onClickBack() {
        if (isFromRegister) {
            openActivity(this, HomeActivity.class, null);
        }
        finish();

    }

    @Override
    public void onBackPressed() {
        onClickBack();
    }

    @OnClick({R.id.cancel_button, R.id.name_clear_button, R.id.id_card_clear_button, R.id.submit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                onClickBack();
                break;
            case R.id.name_clear_button:
                mNameEditText.setText("");
                break;
            case R.id.id_card_clear_button:
                mIDCardEditText.setText("");
                break;
            case R.id.submit_button:
                String name = mNameEditText.getText().toString();
                String idCard = mIDCardEditText.getText().toString();
                VerifyUploadImageActivity.openVerifyUploadImage(this, name, idCard, isFromRegister);
                break;
        }
    }

    /**
     * 初始化相关控件
     */
    private void initView() {
        mNameClearButton.setVisibility(View.INVISIBLE);
        mIDCardClearButton.setVisibility(View.INVISIBLE);
        mSubmitButton.setEnabled(false);
        registerInputListener();
    }

    /**
     * 注册输入监听
     */
    private void registerInputListener() {
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNameClearButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                setSubmitButtonEnable();
            }
        });

        mIDCardEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIDCardClearButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                setSubmitButtonEnable();
            }
        });
    }

    /**
     * 设置提交按钮是否可点击
     */
    private void setSubmitButtonEnable() {
        String name = mNameEditText.getText().toString();
        String idCard = mIDCardEditText.getText().toString();
        mSubmitButton.setEnabled(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(idCard) && name.length() >= 2 && Validator.isIDCard(idCard));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.getCode() == BaseEvent.EVENT_CODE_REAL_AUTH_SUCCESS) {
            finish();
        }
    }
}
