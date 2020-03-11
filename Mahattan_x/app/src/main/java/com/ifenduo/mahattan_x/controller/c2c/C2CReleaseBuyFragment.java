package com.ifenduo.mahattan_x.controller.c2c;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.entity.C2CCoinType;
import com.ifenduo.mahattan_x.entity.C2CTransationPriceEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.CoinValueFilter;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.InputPasswordDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class C2CReleaseBuyFragment extends BaseFragment implements InputPasswordDialog.OnInputCallBack, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.message_text_view)
    TextView mMessageTextView;
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.price_text_view)
    TextView mPriceEditText;
    @BindView(R.id.num_edit_text)
    EditText mNumEditText;
    @BindView(R.id.unit_text_view)
    TextView mUnitTextView;
    @BindView(R.id.min_num_edit_text)
    EditText minNumEditText;
    @BindView(R.id.max_num_edit_text)
    EditText maxNumEditText;
    @BindView(R.id.unit_text_view_)
    TextView mUnitTextView_;
    @BindView(R.id.max_unit_text_view)
    TextView mMaxUnitTextView;
    @BindView(R.id.pay_type_text_view)
    TextView mPayTypeTextView;
    @BindView(R.id.money_text_view)
    TextView mMoneyTextView;
    @BindView(R.id.button_c2c_buy)
    Button mSubmitButton;
    @BindView(R.id.price_scale_text_view)
    TextView priceScaleTextView;
    @BindView(R.id.seek_bar)
    AppCompatSeekBar seekBar;

    Unbinder mUnBinder;

    C2CCoinType mC2CCoinType;
    List<String> mPayType;
    double mCoinPrice;

    public static C2CReleaseBuyFragment newInstance() {
        Bundle args = new Bundle();
        C2CReleaseBuyFragment fragment = new C2CReleaseBuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_c2c_release_buy;
    }

    @Override
    protected void initView(View parentView) {
        mUnBinder = ButterKnife.bind(this, parentView);
        maxNumEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        minNumEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        mNumEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        mPriceEditText.setFilters(new InputFilter[]{new CoinValueFilter().setDigits(2)});
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(this);
        setupInputListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getInputPassword(String password) {
        submitReleaseBuy(password);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    private void setupInputListener() {
        mPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateSumMoney();
            }
        });

        mNumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateSumMoney();
            }
        });
    }

    @OnClick({R.id.coin_container, R.id.button_c2c_buy, R.id.pay_type_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.coin_container:
                Intent intent = new Intent(getContext(), ChooseCoinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, mC2CCoinType);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            case R.id.button_c2c_buy:
                User user = SharedPreferencesTool.getUser(getContext().getApplicationContext());
                if (user == null) {
                    openLogin();
                    return;
                }
                if (verifyInputData()) {
                    InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(getContext());
                    inputPasswordDialog.setOnInputCallBack(this);
                    inputPasswordDialog.show();
                }
                break;
            case R.id.pay_type_container:
                Intent intent_ = new Intent(getContext(), PayTypeActivity.class);
                Bundle bundle_ = new Bundle();
                bundle_.putStringArrayList(Constant.BUNDLE_KEY_COMMON, (ArrayList<String>) mPayType);
                bundle_.putString(Constant.BUNDLE_KEY_PAGE_TITLE, "支付方式");
                intent_.putExtras(bundle_);
                startActivityForResult(intent_, 2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                mC2CCoinType = data.getExtras().getParcelable(Constant.BUNDLE_KEY_COMMON);
                bindCoinType();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                mPayType = data.getExtras().getStringArrayList(Constant.BUNDLE_KEY_COMMON);
                bindPayType();
            }
        }
    }

    /**
     * 计算总金额
     */
    private void calculateSumMoney() {
        String price_ = mPriceEditText.getText().toString();
        String num_ = mNumEditText.getText().toString();
        double price = Double.parseDouble(!TextUtils.isEmpty(price_) ? price_ : "0.00");
        double num = Double.parseDouble(!TextUtils.isEmpty(num_) ? num_ : "0.00");
        mMoneyTextView.setText("所需金额   " + NumberUtil.formatRMB(price * num));
    }

    /**
     * 设置币种名称
     */
    private void bindCoinType() {
        Log.d("TAG", "bindCoinType: ");
        if (mC2CCoinType == null) {
            mUnitTextView.setText("");
            mUnitTextView_.setText("");
            mTitleTextView.setText("");
            mMaxUnitTextView.setText("");
        } else {
            mUnitTextView.setText(mC2CCoinType.getShort_name());
            mUnitTextView_.setText(mC2CCoinType.getShort_name());
            mMaxUnitTextView.setText(mC2CCoinType.getShort_name());
            mTitleTextView.setText(mC2CCoinType.getShort_name());
        }
//        changeInputPrice(50);
        mCoinPrice = 0;
        mPriceEditText.setText("0.00");
        getRMBPrice(mC2CCoinType == null ? "" : mC2CCoinType.getShort_name());

    }

    private void changeInputPrice(int progress) {
        float progress_ = progress / 100f * 20;
        Log.d("TAG", "changeInputPrice: " + progress_ + "     " + progress);
        priceScaleTextView.setText(String.valueOf((int) (90 + progress_) + "%"));
        mPriceEditText.setText(NumberUtil.formatRMB((0.9 + progress_ / 100f) * mCoinPrice));
    }

    private void getRMBPrice(String coinName) {
//        if (RMB_PRICE_CACHE != null && RMB_PRICE_CACHE.size() > 0 && !TextUtils.isEmpty(coinName)
//                && (RMB_PRICE_CACHE.containsKey(coinName) || "USDT".equals(coinName))) {
//            if ("USDT".equals(coinName)) {
//                mCoinPrice = HomeActivity.EXCHANGE_RATE;
//            } else {
//                mCoinPrice = RMB_PRICE_CACHE.get(coinName);
//            }
//        } else {
//            mCoinPrice = 0;
//        }

        Api.getInstance().fetchC2CTransactionPrice(coinName, new Callback<C2CTransationPriceEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<C2CTransationPriceEntity> response) {
                if (response.data != null) {
                    mCoinPrice = response.data.getPrice();
                } else {
                    mCoinPrice = 0;
                }
                mPriceEditText.setText(NumberUtil.formatRMB(mCoinPrice));
                seekBar.setProgress(50);
            }
        });
    }

    /**
     * 设置支付方式
     */
    private void bindPayType() {
        StringBuilder stringBuilder = new StringBuilder("");
        if (mPayType != null && mPayType.size() > 0) {
            for (String payType : mPayType) {
                if (!TextUtils.isEmpty(payType)) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(payType);
                }
            }
        }
        mPayTypeTextView.setText(stringBuilder.toString());
    }

    /**
     * 验证输入的数据
     */
    private boolean verifyInputData() {
        String price_ = mPriceEditText.getText().toString();
        String num_ = mNumEditText.getText().toString();
//        String max_ = maxNumEditText.getText().toString();
        String min_ = minNumEditText.getText().toString();
        String payType = mPayTypeTextView.getText().toString();
        if (mC2CCoinType == null || TextUtils.isEmpty(mC2CCoinType.getId())) {
            showToast("请选择币种");
            return false;
        }

        if (TextUtils.isEmpty(price_)) {
            showToast("请输入价格");
            return false;
        }
        if (TextUtils.isEmpty(num_)) {
            showToast("请输入购买数量");
            return false;
        }
        if (TextUtils.isEmpty(min_)) {
            showToast("请输入最小出售数量");
            return false;
        }
//        if (TextUtils.isEmpty(max_)) {
//            showToast("请输入最大出售数量");
//            return false;
//        }
        if (TextUtils.isEmpty(payType)) {
            showToast("请选择支付方式");
            return false;
        }
        double price = Double.parseDouble(price_);
        double num = Double.parseDouble(num_);
//        double max = Double.parseDouble(max_);
        double min = Double.parseDouble(min_);

        if (price <= 0) {
            showToast("价格必须大于0");
            return false;
        }
        if (num <= 0) {
            showToast("购买数量必须大于0");
            return false;
        }
        if (min <= 0) {
            showToast("最小出售数量必须大于0");
            return false;
        }
//        if (max <= 0) {
//            showToast("最大出售数量必须大于0");
//            return false;
//        }
        if (min > num) {
            showToast("最小出售数量必须小于或等于购买量");
            return false;
        }
//        if (max > num) {
//            showToast("最大出售数量必须小于或等于购买量");
//            return false;
//        }
//        if (max < min) {
//            showToast("最大出售数量必须大于或等于最小出售数量");
//            return false;
//        }
        return true;
    }

    /**
     * 发布买入
     *
     * @param pwd
     */
    private void submitReleaseBuy(String pwd) {
        User user = SharedPreferencesTool.getUser(getContext().getApplicationContext());
        if (user == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        if (!verifyInputData()) return;
        String price_ = mPriceEditText.getText().toString();
        String num_ = mNumEditText.getText().toString();
        String max_ = maxNumEditText.getText().toString();
        String min_ = minNumEditText.getText().toString();
        String payType = mPayTypeTextView.getText().toString();

        Api.getInstance().submitC2CRelease(user.getFid(), "2", "中国", mC2CCoinType.getId(), mC2CCoinType.getShort_name(), price_, num_, min_, num_, payType, pwd, new Callback<String>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<String> response) {
                if (isSuccess) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "release_buy");
                    bundle.putString(Constant.BUNDLE_KEY_COMMON, response.data);
                    showToast("发布成功");
                    ((BaseActivity) getContext()).finish();
                } else {
                    showToast(msg);
                }
            }
        });

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        changeInputPrice(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
