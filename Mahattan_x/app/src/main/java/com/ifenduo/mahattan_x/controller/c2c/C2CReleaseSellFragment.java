package com.ifenduo.mahattan_x.controller.c2c;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.C2CCoinType;
import com.ifenduo.mahattan_x.entity.C2CTransationPriceEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.CoinValueFilter;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.InputPasswordDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.ifenduo.mahattan_x.Constant.RMB_PRICE_CACHE;
import static com.ifenduo.mahattan_x.tools.NumberUtil.MONEY_DECIMAL_LENGTH;

/**
 * A simple {@link Fragment} subclass.
 */
public class C2CReleaseSellFragment extends BaseFragment implements InputPasswordDialog.OnInputCallBack, SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.title_text_view)
    TextView titleTextView;
    @BindView(R.id.price_edit_text)
    TextView priceEditText;
    @BindView(R.id.num_edit_text)
    EditText numEditText;
    @BindView(R.id.can_use_num_text_view)
    TextView canUseNumTextView;
    @BindView(R.id.min_num_edit_text)
    EditText minNumEditText;
    @BindView(R.id.min_name_text_view)
    TextView minNameTextView;
    @BindView(R.id.max_num_edit_text)
    EditText maxNumEditText;
    @BindView(R.id.max_name_text_view)
    TextView maxNameTextView;
    @BindView(R.id.pay_type_text_view)
    TextView payTypeTextView;
    @BindView(R.id.pay_type_container)
    LinearLayout payTypeContainer;
    @BindView(R.id.sum_money_text_view)
    TextView sumMoneyTextView;
    @BindView(R.id.name_text_view)
    TextView nameTextView;
    @BindView(R.id.message_text_view)
    TextView messageTextView;
    @BindView(R.id.price_scale_text_view)
    TextView priceScaleTextView;
    @BindView(R.id.seek_bar)
    AppCompatSeekBar seekBar;

    Unbinder unbinder;
    C2CCoinType mC2CCoinType;
    List<String> mPayTypeList;

    double mBalance;
    double mCoinPrice;


    public static C2CReleaseSellFragment newInstance() {
        Bundle args = new Bundle();
        C2CReleaseSellFragment fragment = new C2CReleaseSellFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_c2c_release_sell;
    }

    @Override
    protected void initView(View parentView) {
        unbinder = ButterKnife.bind(this, parentView);
        priceEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        numEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        maxNumEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        minNumEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(this);
        setupInputListener();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.all_button, R.id.button_c2c_buy, R.id.pay_type_container, R.id.coin_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_button:
                numEditText.setText(NumberUtil.formatMoney(mBalance));
                break;
            case R.id.button_c2c_buy:
                User user = SharedPreferencesTool.getUser(getContext().getApplicationContext());
                if (user == null) {
                    showToast("你尚未登录，请先登录");
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
                bundle_.putStringArrayList(Constant.BUNDLE_KEY_COMMON, (ArrayList<String>) mPayTypeList);
                bundle_.putString(Constant.BUNDLE_KEY_PAGE_TITLE, "收付方式");
                intent_.putExtras(bundle_);
                startActivityForResult(intent_, 2);
                break;
            case R.id.coin_container:
                Intent intent = new Intent(getContext(), ChooseCoinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, mC2CCoinType);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void setC2CCoinType(C2CCoinType c2CCoinType) {
        mC2CCoinType = c2CCoinType;
        nameTextView.setText(mC2CCoinType == null ? "" : mC2CCoinType.getShort_name());
        maxNameTextView.setText(mC2CCoinType == null ? "" : mC2CCoinType.getShort_name());
        minNameTextView.setText(mC2CCoinType == null ? "" : mC2CCoinType.getShort_name());
        titleTextView.setText(mC2CCoinType == null ? "" : mC2CCoinType.getShort_name());
        getRMBPrice(mC2CCoinType == null ? "" : mC2CCoinType.getShort_name());
        changeInputPrice(seekBar.getProgress());
        fetchBalance();
    }


    private void changeInputPrice(int progress) {
        float progress_ = progress / 100f * 10;
        priceScaleTextView.setText(String.valueOf((int) (100 + progress_) + "%"));
        priceEditText.setText(NumberUtil.formatRMB((1.0 + progress_ / 100) * mCoinPrice));
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
                priceEditText.setText(NumberUtil.formatRMB(mCoinPrice));
                seekBar.setProgress(0);
            }
        });

//        return mCoinPrice;

    }

    /**
     * 验证输入的数据
     */
    private boolean verifyInputData() {
        String price_ = priceEditText.getText().toString();
        String num_ = numEditText.getText().toString();
//        String max_ = maxNumEditText.getText().toString();
        String min_ = minNumEditText.getText().toString();
        String payType = payTypeTextView.getText().toString();

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
            showToast("卖出数量必须大于0");
            return false;
        }
        if (num > mBalance) {
            showToast("卖出数量必须小于或等于可卖数量");
            return false;
        }
        if (min <= 0) {
            showToast("最小购买数量必须大于0");
            return false;
        }
//        if (max <= 0) {
//            showToast("最大购买数量必须大于0");
//            return false;
//        }
        if (min > num) {
            showToast("最小购买数量必须小于或等于卖出数量");
            return false;
        }
//        if (max > num) {
//            showToast("最大购买数量必须小于或等于卖出数量");
//            return false;
//        }
//        if (max < min) {
//            showToast("最大购买数量必须大于或等于最小购买数量");
//            return false;
//        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                if (data != null && data.getExtras() != null) {
                    bindPayType(data.getExtras().getStringArrayList(Constant.BUNDLE_KEY_COMMON));
                }
            } else if (requestCode == 1) {
                mC2CCoinType = data.getExtras().getParcelable(Constant.BUNDLE_KEY_COMMON);
                mCoinPrice = 0;
                priceEditText.setText("0.00");
                seekBar.setProgress(50);
                setC2CCoinType(mC2CCoinType);
            }
        }
    }

    private void bindPayType(List<String> payTypeList) {
        if (payTypeList == null) {
            mPayTypeList = new ArrayList<>();
        } else {
            mPayTypeList = payTypeList;
        }

        StringBuilder builder = new StringBuilder("");
        if (mPayTypeList.size() > 0) {
            for (int i = 0; i < payTypeList.size(); i++) {
                String s = payTypeList.get(i);
                if (!TextUtils.isEmpty(s)) {
                    if (builder.length() > 0) {
                        builder.append(",");
                        builder.append(s);
                    } else {
                        builder.append(s);
                    }
                }
            }
        }

        payTypeTextView.setText(builder.toString());
    }


    private void setupInputListener() {
        priceEditText.addTextChangedListener(new TextWatcher() {
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

        numEditText.addTextChangedListener(new TextWatcher() {
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

    /**
     * 计算总金额
     */
    private void calculateSumMoney() {
        String price_ = priceEditText.getText().toString();
        String num_ = numEditText.getText().toString();
        double price = Double.parseDouble(!TextUtils.isEmpty(price_) ? price_ : "0.00");
        double num = Double.parseDouble(!TextUtils.isEmpty(num_) ? num_ : "0.00");
        sumMoneyTextView.setText(NumberUtil.formatRMB(price * num));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void getInputPassword(String password) {
        submitReleaseSell(password);
    }

    /**
     * 发布卖出
     *
     * @param pwd
     */
    private void submitReleaseSell(String pwd) {
        User user = SharedPreferencesTool.getUser(getContext().getApplicationContext());
        if (user == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        if (!verifyInputData()) return;
        String price_ = priceEditText.getText().toString();
        String num_ = numEditText.getText().toString();
//        String max_ = maxNumEditText.getText().toString();
        String min_ = minNumEditText.getText().toString();
        String payType = payTypeTextView.getText().toString();

        Api.getInstance().submitC2CRelease(user.getFid(), "1", "中国", mC2CCoinType.getId(), mC2CCoinType.getShort_name(), price_, num_, min_, num_, payType, pwd, new Callback<String>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<String> response) {
                if (isSuccess) {
                    showToast("发布成功");
                    ((BaseActivity) getContext()).finish();
                } else {
                    showToast(msg);
                }
            }
        });

    }

    /**
     * 获取当前币种的余额
     */
    private void fetchBalance() {
        User user = SharedPreferencesTool.getUser(getContext().getApplicationContext());
        if (user == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        if (mC2CCoinType == null) {
            bindBalance(0);
            return;
        }
        Api.getInstance().fetchC2CBalance(user.getFid(), mC2CCoinType.getId(), new Callback<String>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<String> response) {
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
        mBalance = BigDecimal.valueOf(balance).setScale(MONEY_DECIMAL_LENGTH, BigDecimal.ROUND_HALF_UP).doubleValue();
        canUseNumTextView.setText(NumberUtil.formatMoney(mBalance));
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