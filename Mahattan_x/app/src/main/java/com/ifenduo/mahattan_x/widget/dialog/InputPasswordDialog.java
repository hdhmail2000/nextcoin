package com.ifenduo.mahattan_x.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.controller.me.FindTransactionPsdActivity;
import com.ifenduo.mahattan_x.controller.me.SetTransactionPsdStep1Activity;
import com.ifenduo.mahattan_x.entity.SafeSettingEntity;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.PasswordEditText;

/**
 * Created by ll on 2018/3/6.
 */

public class InputPasswordDialog extends Dialog implements View.OnClickListener {

    TextView mCancelButton;
    Button mOkButton;
    EditText mEditText;
    TextView mTitleTextView;
    TextView mForgetButton;

    OnInputCallBack mOnInputCallBack;
    String mTitle;
    SafeSettingEntity mSafeSettingEntity;

    public InputPasswordDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    public void setOnInputCallBack(OnInputCallBack onInputCallBack) {
        this.mOnInputCallBack = onInputCallBack;
    }

    public void setTitle(String title) {
        mTitle = title;
        if (mTitleTextView != null) {
            mTitleTextView.setText(mTitle);
        }
        if (mEditText != null) {
            mEditText.setHint("请输入" + mTitle);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_password);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.BOTTOM);
        onWindowAttributesChanged(attributes);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mCancelButton = findViewById(R.id.text_input_password_dialog_cancel);
        mOkButton = findViewById(R.id.button_input_password_dialog);
        mEditText = findViewById(R.id.edit_input_password_dialog);
        mTitleTextView = findViewById(R.id.text_pwd_dialog_title);
        mForgetButton = findViewById(R.id.forget_text_view);
        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
        mOkButton.setEnabled(false);
        mForgetButton.setOnClickListener(this);

        if (!TextUtils.isEmpty(mTitle)) {
            mTitleTextView.setText(mTitle);
            mEditText.setHint("请输入" + mTitle);
        }

        mSafeSettingEntity = SharedPreferencesTool.getSafeSetting(getContext().getApplicationContext());
        if (mSafeSettingEntity != null && mSafeSettingEntity.isBindTrade()) {
            mForgetButton.setText("忘记交易密码？");
        } else {
            mForgetButton.setText("设置交易密码?");
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOkButton.setEnabled(s.length() >= 6);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_input_password_dialog_cancel) {
            dismiss();
        } else if (v.getId() == R.id.button_input_password_dialog) {
            if (mOnInputCallBack != null) {
                mOnInputCallBack.getInputPassword(mEditText.getText().toString());
                dismiss();
            }
        } else if (v.getId() == R.id.forget_text_view) {
            mSafeSettingEntity = SharedPreferencesTool.getSafeSetting(getContext().getApplicationContext());
            if (mSafeSettingEntity != null && mSafeSettingEntity.isBindTrade()) {
                Intent intent = new Intent(getContext(), FindTransactionPsdActivity.class);
                getContext().startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), SetTransactionPsdStep1Activity.class);
                getContext().startActivity(intent);
            }

        }
    }

    public interface OnInputCallBack {
        void getInputPassword(String password);
    }

    @Override
    public void dismiss() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
        super.dismiss();
    }
}
