package com.ifenduo.mahattan_x.controller.c2c;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBankCardActivity extends BaseActivity {
    @BindView(R.id.bank_name_edit_text)
    EditText bankNameEditText;
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.account_edit_text)
    EditText accountEditText;
    @BindView(R.id.id_card_edit_text)
    EditText idCardEditText;
    @BindView(R.id.phone_edit_text)
    EditText phoneEditText;
    @BindView(R.id.submit_button)
    TextView submitButton;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_add_bank_card;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("银行卡");
        setupInputListener();
        setSubmitButtonEnable();
    }


    @OnClick(R.id.submit_button)
    public void onViewClicked() {
        submitAccount();
    }

    private void submitAccount() {
        User user = SharedPreferencesTool.getUser(getApplicationContext());
        if (user == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        String bankName = bankNameEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String account = accountEditText.getText().toString();
        String idCard = idCardEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        showProgress();
        Api.getInstance().submitAddBankAccount(user.getFid(), name, idCard, account, bankName, phone, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    showToast("添加成功");
                    finish();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 设置输入监听
     */
    private void setupInputListener() {
        bankNameEditText.addTextChangedListener(new TextWatcher() {
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

        nameEditText.addTextChangedListener(new TextWatcher() {
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

        phoneEditText.addTextChangedListener(new TextWatcher() {
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

        idCardEditText.addTextChangedListener(new TextWatcher() {
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

        accountEditText.addTextChangedListener(new TextWatcher() {
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
     * 设置提交按钮是否可点击
     */
    private void setSubmitButtonEnable() {
        String bankName = bankNameEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String account = accountEditText.getText().toString();
        String idCard = idCardEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (TextUtils.isEmpty(bankName) || bankName.length() < 4) {
            submitButton.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(name) || name.length() < 2) {
            submitButton.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(account) || account.length() < 8) {
            submitButton.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(idCard) || !Validator.isIDCard(idCard)) {
            submitButton.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(phone) || !Validator.isMobile(phone)) {
            submitButton.setEnabled(false);
            return;
        }
        submitButton.setEnabled(true);
    }
}
