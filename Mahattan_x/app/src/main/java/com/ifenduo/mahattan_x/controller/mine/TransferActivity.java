package com.ifenduo.mahattan_x.controller.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.C2CCoinType;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TransferActivity extends BaseActivity {
    @BindView(R.id.all_hint_text_view)
    TextView mAllHintTextView;
    @BindView(R.id.all_num_text_view)
    TextView mAllNumTextView;
    @BindView(R.id.name_text_view)
    TextView mNameTextView;
    @BindView(R.id.num_edit_text)
    EditText mNumEditText;
    @BindView(R.id.num_text_view_)
    TextView mNumTextView_;
    @BindView(R.id.submit_button)
    Button mSubmitButton;

    User mUser;
    String mCoinName;
    String mCoinID = "";
    double mBalance;
    double mCanPutMoney;
    double mSumNum;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        setNavigationCenter("立即投入");
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        handleIntent();
        mAllHintTextView.setText("可用余额（" + mCoinName + "）");
        setInputListener();
        setSubmitButtonEnable();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mCoinName = bundle.getString(Constant.BUNDLE_KEY_COIN_TYPE, "");
            mSumNum = bundle.getDouble(Constant.BUNDLE_KEY_COMMON, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        fetchCoinList(mCoinName);
    }

    @OnClick({R.id.all_text_view, R.id.submit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_text_view:
                mNumEditText.setText(NumberUtil.formatMoney(mCanPutMoney));
                break;
            case R.id.submit_button:
                submitPut();
                break;
        }
    }

    private void submitPut() {
        if (mUser == null || TextUtils.isEmpty(mCoinID) || TextUtils.isEmpty(mCoinName)) return;
        String type = "";
        if (mCoinName.equals("MHT-X")) {
            type = "1";
        } else if (mCoinName.equals("USDT")) {
            type = "2";
        }
        String num = NumberUtil.formatMoney(mNumEditText.getText().toString());
        Api.getInstance().submitPutMiningArea(mUser.getFid(), type, num, mCoinID, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    showToast("投入成功");
                    finish();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void fetchCoinList(final String coinName) {
        if (TextUtils.isEmpty(coinName)) {
            return;
        }
        Api.getInstance().fetchC2CCoinTypeList(new Callback<List<C2CCoinType>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<C2CCoinType>> response) {
                if (response.data != null && response.data.size() > 0) {
                    for (C2CCoinType coinType : response.data) {
                        if (coinType != null && !TextUtils.isEmpty(coinType.getShort_name()) && coinType.getShort_name().equals(coinName)) {
                            mCoinID = coinType.getId();
                            fetchBalance(coinType.getId());
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取当前币种的余额
     */
    private void fetchBalance(String id) {
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().fetchC2CBalance(mUser.getFid(), id, new Callback<String>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<String> response) {
                dismissProgress();
                if (!TextUtils.isEmpty(response.data)) {
                    try {
                        bindBalance(Double.parseDouble(response.data));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        bindBalance(0);
                    }
                } else {
                    bindBalance(0);
                }
            }
        });
    }

    private void bindBalance(double balance) {
        mBalance = balance;
        if (mBalance >= (200000 - mSumNum)) {
            mCanPutMoney = 200000 - mSumNum;
        } else {
            mCanPutMoney = mBalance;
        }
        mAllNumTextView.setText(NumberUtil.formatMoney(mBalance));
        mNumTextView_.setText("可转入    " + NumberUtil.formatMoney(mCanPutMoney));

        setSubmitButtonEnable();
    }

    private void setInputListener() {
        mNumEditText.addTextChangedListener(new TextWatcher() {
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

    public void setSubmitButtonEnable() {
        String num = NumberUtil.formatMoney(mNumEditText.getText().toString());
        double num_ = Double.parseDouble(num);

        if (TextUtils.isEmpty(mCoinName)) {
            mSubmitButton.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(mCoinID)) {
            mSubmitButton.setEnabled(false);
            return;
        }

        if (mCanPutMoney <= 0) {
            mSubmitButton.setEnabled(false);
            return;
        }

        if (num_ > mCanPutMoney) {
            mSubmitButton.setEnabled(false);
            return;
        }
        if(num_<=0){
            mSubmitButton.setEnabled(false);
        }
        mSubmitButton.setEnabled(true);
    }
}
