package com.ifenduo.mahattan_x.controller.transaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.DepthEntity;
import com.ifenduo.mahattan_x.entity.DepthItemEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.MoneyOfCoinEntity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.CoinValueFilter;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.CustomSeekBar;
import com.ifenduo.mahattan_x.widget.ItemDepthChart;
import com.ifenduo.mahattan_x.widget.dialog.InputPasswordDialog;
import com.trello.rxlifecycle2.android.FragmentEvent;


import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by ll on 2018/2/26.
 */

public class TransactionFragment extends BaseFragment implements InputPasswordDialog.OnInputCallBack, CustomSeekBar.OnProgressChangedListener {
    @BindView(R.id.text_quotes_detail_price)
    TextView mPriceTextView;
    @BindView(R.id.image_quotes_detail_rise_icon)
    ImageView mRiseIcon;
    @BindView(R.id.text_quotes_detail_rise)
    TextView mRiseTextView;
    @BindView(R.id.text_quotes_detail_max_price)
    TextView mMaxPriceTextView;
    @BindView(R.id.text_quotes_detail_min_price)
    TextView mMinPriceTextView;
    @BindView(R.id.text_quotes_detail_volume)
    TextView mVolumeTextView;
    @BindView(R.id.button_quotes_detail_sell)
    TextView mSellTextView;
    @BindView(R.id.button_quotes_detail_buy)
    TextView mBuyTextView;
    @BindView(R.id.submit_button)
    Button mSubmitButton;
    @BindView(R.id.sell_unit_text_view)
    TextView mSellUnitTextView;
    @BindView(R.id.buy_unit_text_view)
    TextView mBuyUnitTextView;
    @BindView(R.id.text_push_proportion)
    TextView textPushProportion;
    @BindView(R.id.seek_bar_push_sell)
    CustomSeekBar seekBarPushSell;
    @BindView(R.id.edit_push_sell_price)
    EditText mPriceEditText;
    @BindView(R.id.edit_push_sell_num)
    EditText mNumEditText;
    @BindView(R.id.text_push_sell_price_)
    TextView mSumTextView;

    //深度图左侧
    @BindView(R.id.lin_transaction_depth_chart_container_left)
    LinearLayout mDepthChartContainerLeft;
    //深度图右侧
    @BindView(R.id.lin_transaction_depth_chart_container_right)
    LinearLayout mDepthChartContainerRight;

    Coin mCoin;
    long mCoinId;
    String mSellName = "";
    String mBuyName = "";
    double mCanUseSell = -1;//可用sell币数量
    double mCanUseBuy = -1;// 可用buy币数量
    double mNowPrice = -1;//实时价格
    String mOrderType = "buy";


    public static TransactionFragment newInstance(Coin coin) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, coin);
        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_transaction;
    }

    @Override
    protected void initView(View parentView) {
        ButterKnife.bind(this, parentView);
        seekBarPushSell.setProgressChangedListener(this);

        //初始为买入状态
        mOrderType = "buy";
        mBuyTextView.setSelected(true);
        mSellTextView.setSelected(false);

        mPriceEditText.setFilters(new InputFilter[]{new CoinValueFilter()});
        mNumEditText.setFilters(new InputFilter[]{new CoinValueFilter()});

        setupInputListener();
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCoin = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
            if (mCoin != null) {
                mCoinId = mCoin.getId();
                mSellName = mCoin.getSellShortName();
                mBuyName = mCoin.getBuyShortName();

                mBuyUnitTextView.setText(mBuyName);
                mSellUnitTextView.setText(mSellName);
            }
            fetchMoneyOfCoin();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //轮询
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        fetchTradingAreaCoinInfo();
                        fetchDepth();
                    }
                });
    }


    /**
     * 获取交易对的用户可用资产
     */
    private void fetchMoneyOfCoin() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            showToast("您还未登录，请先登录");
            openLogin();
            return;
        }
        Api.getInstance().fetchMoneyOfCoin(String.valueOf(mCoinId), loginInfo.getToken(), loginInfo.getSecretKey(), new Callback<MoneyOfCoinEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<MoneyOfCoinEntity> response) {
                if (isSuccess) {
                    bindMoneyOfCoin(response.data);
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


    /**
     * 获取交易对价格
     */
    private void fetchTradingAreaCoinInfo() {
        Api.getInstance().fetchTradingAreaCoinInfo(String.valueOf(mCoinId), new Callback<List<Coin.CoinBean>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Coin.CoinBean>> response) {
                if (isSuccess) {
                    if (response.data != null && response.data.size() > 0 && response.data.get(0) != null) {
                        bindMarkDetail(response.data.get(0));
                    }
                }
            }
        });
    }

    /**
     * 获取深度图数据
     */
    private void fetchDepth() {
        Api.getInstance().fetchDepth(String.valueOf(mCoinId), new Callback<DepthEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<DepthEntity> response) {
                if (isSuccess) {
                    DepthEntity depthEntity = response.data;
                    if (depthEntity != null) {
                        setupDepthLeft(response.data.getBids());
                        setupDepthRight(response.data.getAsks());
                    }
                }
            }
        });
    }

    /**
     * 绑定交易对的用户可用资产
     *
     * @param moneyOfCoinEntity
     */
    private void bindMoneyOfCoin(MoneyOfCoinEntity moneyOfCoinEntity) {
        if (moneyOfCoinEntity != null && moneyOfCoinEntity.getBuyCoin() != null) {
            mCanUseBuy = moneyOfCoinEntity.getBuyCoin().getTotal();
        }

        if (moneyOfCoinEntity != null && moneyOfCoinEntity.getSellCoin() != null) {
            mCanUseSell = moneyOfCoinEntity.getSellCoin().getTotal();
        }
        Log.d("TAG", "bindMoneyOfCoin: mCanUseBuy=" + mCanUseBuy + "     mCanUseSell=" + mCanUseSell);
    }

    public void bindMarkDetail(Coin.CoinBean coinBean) {
        if (coinBean != null) {
            if (coinBean == null) {
                mMaxPriceTextView.setText("");
                mMinPriceTextView.setText("");
                mVolumeTextView.setText("");
                mPriceTextView.setText("");
                mRiseTextView.setText("");
            } else {
                double rise = coinBean.getChg();
                mMaxPriceTextView.setText(NumberUtil.formatMoney(coinBean.getSell()));
                mMinPriceTextView.setText(NumberUtil.formatMoney(coinBean.getBuy()));
                mVolumeTextView.setText(NumberUtil.double2String2(coinBean.getTotal()));
                mPriceTextView.setText(NumberUtil.formatMoney(coinBean.getP_new()));
                if (rise > 0) {
                    mRiseTextView.setText(NumberUtil.double2String2(rise) + "%");
                    showAddStyle();
                } else {
                    mRiseTextView.setText(NumberUtil.double2String2(rise) + "%");
                    showLessStyle();
                }
            }
        }
    }

    private void showLessStyle() {
        if (isAdded()) {
            mRiseIcon.setImageResource(R.drawable.ic_rise_less);
        }

    }

    private void showAddStyle() {
        if (isAdded()) {
            mRiseIcon.setImageResource(R.drawable.ic_rise_add);
        }

    }


    private List<DepthItemEntity> buildDepthItemList(List<List<Double>> lists) {
        List<DepthItemEntity> depthItemEntityList = new ArrayList<>();
        if (lists != null && lists.size() > 0) {
            double sumNum = 0;
            int count = lists.size() > 7 ? 7 : lists.size();

            for (int i = 0; i < count; i++) {
                List<Double> doubleList = lists.get(i);
                if (doubleList != null && doubleList.size() >= 2) {
                    sumNum = sumNum + doubleList.get(1);
                }
            }

            for (int i = 0; i < count; i++) {
                List<Double> doubleList = lists.get(i);
                if (doubleList != null && doubleList.size() >= 2) {
                    DepthItemEntity depthItemEntity;
                    double sum = 0;
                    float progress = 0;
                    if (i == 0) {
                        sum = doubleList.get(1);
                        if (sumNum > 0) {
                            progress = (float) (sum / sumNum);
                        }
                        depthItemEntity = new DepthItemEntity(doubleList.get(0), doubleList.get(1), sum, progress);
                    } else {
                        sum = doubleList.get(1) + depthItemEntityList.get(depthItemEntityList.size() - 1).getSum();
                        if (sumNum > 0) {
                            progress = (float) (sum / sumNum);
                        }
                        depthItemEntity = new DepthItemEntity(doubleList.get(0), doubleList.get(1),
                                sum, progress);
                    }
                    depthItemEntityList.add(depthItemEntity);
                }
            }
        }
        return depthItemEntityList;
    }

    private void setupDepthLeft(List<List<Double>> data) {
        List<DepthItemEntity> depthItemEntityList = buildDepthItemList(data);
        int count = 0;
        if (data == null) {
            mDepthChartContainerLeft.removeAllViews();
        } else {
            count = depthItemEntityList.size() > 7 ? 7 : depthItemEntityList.size();
        }
        for (int i = 0; i < count; i++) {
            ItemDepthChart itemDepthChart;
            if (mDepthChartContainerLeft.getChildCount() < (i + 1)) {
                itemDepthChart = new ItemDepthChart(getContext());
                itemDepthChart.setNoTextViewTextColor(R.color.colorRiseBgLess);
                itemDepthChart.setPriceTextViewTextColor(R.color.colorRiseBgLess);
                itemDepthChart.setProgressColor(R.color.colorRiseBgLess);
                mDepthChartContainerLeft.addView(itemDepthChart);
            } else {
                itemDepthChart = (ItemDepthChart) mDepthChartContainerLeft.getChildAt(i);
            }
            itemDepthChart.setNo("买" + String.valueOf(i + 1));
            if (i < count) {
                DepthItemEntity depthItemEntity = depthItemEntityList.get(i);
                itemDepthChart.setNum(NumberUtil.formatMoney(depthItemEntity.getNum()));
                itemDepthChart.setPrice(NumberUtil.formatMoney(depthItemEntity.getPrice()));
                itemDepthChart.setProgress(depthItemEntity.getProgress());
            } else {
                itemDepthChart.setNum("");
                itemDepthChart.setPrice("");
                itemDepthChart.setProgress(0);
            }
        }
    }

    private void setupDepthRight(List<List<Double>> data) {
        List<DepthItemEntity> depthItemEntityList = buildDepthItemList(data);
        int count = 0;
        if (data == null) {
            mDepthChartContainerRight.removeAllViews();
        } else {
            count = depthItemEntityList.size() > 7 ? 7 : depthItemEntityList.size();
        }
        for (int i = 0; i < count; i++) {
            ItemDepthChart itemDepthChart;
            if (mDepthChartContainerRight.getChildCount() < (i + 1)) {
                itemDepthChart = new ItemDepthChart(getContext());
                itemDepthChart.setNoTextViewTextColor(R.color.colorRiseBgAdd);
                itemDepthChart.setPriceTextViewTextColor(R.color.colorRiseBgAdd);
                itemDepthChart.setProgressColor(R.color.colorRiseBgAdd);
                mDepthChartContainerRight.addView(itemDepthChart);
            } else {
                itemDepthChart = (ItemDepthChart) mDepthChartContainerRight.getChildAt(i);
            }
            itemDepthChart.setNo("卖" + String.valueOf(count - i));
            if (i < count) {
                DepthItemEntity depthItemEntity = depthItemEntityList.get(count - i - 1);
                itemDepthChart.setNum(NumberUtil.formatMoney(depthItemEntity.getNum()));
                itemDepthChart.setPrice(NumberUtil.formatMoney(depthItemEntity.getPrice()));
                itemDepthChart.setProgress(depthItemEntity.getProgress());
            } else {
                itemDepthChart.setNum("");
                itemDepthChart.setPrice("");
                itemDepthChart.setProgress(0);
            }
        }
    }


    @OnClick({R.id.button_quotes_detail_buy, R.id.button_quotes_detail_sell, R.id.submit_button})
    public void click(View view) {
        if (view.getId() == R.id.button_quotes_detail_buy) {
            openBuy();
        } else if (view.getId() == R.id.button_quotes_detail_sell) {
            openSell();
        } else if (view.getId() == R.id.submit_button) {
            String num = mNumEditText.getText().toString();
            String price = mPriceEditText.getText().toString();
            double num_ = 0;
            double price_ = 0;

            try {
                num_ = Double.parseDouble(num);
                price_ = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (price_ <= 0) {
                showToast("请输入委托价格");
                return;
            }
            if (num_ <= 0) {
                showToast("请输数量");
                return;
            }

            InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(getContext());
            inputPasswordDialog.setOnInputCallBack(this);
            inputPasswordDialog.show();
        }
    }

    @Override
    public void getInputPassword(String password) {
        if (!TextUtils.isEmpty(password)) {
            submitOrder(password);
        }
    }

    private void submitOrder(String password) {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            showToast("您未登录，请先登录");
            openLogin();
            return;
        }

        String num = mNumEditText.getText().toString();
        String price = mPriceEditText.getText().toString();

        showProgress();
        Api.getInstance().submitOrder(loginInfo.getToken(), loginInfo.getSecretKey(), mSellName.toLowerCase() + "_" + mBuyName.toLowerCase(), num, price, password, mOrderType, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    showToast("订单委托成功");
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.REFRESH_ORDER_LIST, null));
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


    @Override
    public void onProgressChanged(CustomSeekBar customSeekBar, int progress, float progressFloat) {
        textPushProportion.setText(progress + "%");
    }

    @Override
    public void getProgressOnActionUp(CustomSeekBar customSeekBar, int progress, float progressFloat) {

        if ("buy".equals(mOrderType)) {
            String price = mPriceEditText.getText().toString();

            if (TextUtils.isEmpty(price)) {
                showToast("请输入价格");
                return;
            }
            double price_ = Double.parseDouble(NumberUtil.formatMoney(price));
            if (price_ > 0) {
                double maxNum = BigDecimal.valueOf((mCanUseBuy / price_)).setScale(NumberUtil.MONEY_DECIMAL_LENGTH, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                mNumEditText.setText(NumberUtil.formatMoney(progress * 0.01 * maxNum));
            }
        } else if ("sell".equals(mOrderType)) {
            mNumEditText.setText(NumberUtil.formatMoney(progress * 0.01 * mCanUseSell));
        }
        mNumEditText.setSelection(mNumEditText.getText().length());
    }

    @Override
    public void getProgressOnFinally(CustomSeekBar customSeekBar, int progress, float progressFloat) {

    }

    /**
     * 设置输入监听
     */
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

                if ("buy".equals(mOrderType)) {
                    onBuyInputChange();
                }

                if (TextUtils.isEmpty(s.toString())) {
                    mSumTextView.setText("--");
                } else {
                    mSumTextView.setText(NumberUtil.formatMoney(calculateSumMoney()));
                }
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

                if ("buy".equals(mOrderType)) {
                    onBuyInputChange();

                } else if ("sell".equals(mOrderType)) {
                    String num = NumberUtil.formatMoney(s.toString());
                    double num_ = Double.parseDouble(num);

                    double scal = Math.round((mCanUseSell <= 0 ? 0 : (num_ / mCanUseSell * 100)));
                    double progress = seekBarPushSell.getProgress();
                    if (scal != progress) {
                        seekBarPushSell.setProgress((float) scal);
                    }
                }

                if (TextUtils.isEmpty(s.toString())) {
                    mSumTextView.setText("--");
                } else {
                    mSumTextView.setText(NumberUtil.formatMoney(calculateSumMoney()));
                }
            }
        });
    }

    private void onBuyInputChange() {
        String price = mPriceEditText.getText().toString();
        double price_ = Double.parseDouble(NumberUtil.formatMoney(price));

        String num = NumberUtil.formatMoney(mNumEditText.getText().toString());
        double num_ = Double.parseDouble(num);

        if (price_ > 0) {
            if (mCanUseBuy <= 0) {
                seekBarPushSell.setProgress((num_ > 0 ? 100f : 0f));
            } else {
                double maxNum = BigDecimal.valueOf((mCanUseBuy / price_)).setScale(NumberUtil.MONEY_DECIMAL_LENGTH, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                double scal = Math.round(num_ / maxNum * 100);
                double progress = seekBarPushSell.getProgress();
                if (scal != progress) {
                    seekBarPushSell.setProgress((float) scal);
                }
            }
        } else {
            seekBarPushSell.setProgress(0);
        }
    }

    /**
     * 计算交易额
     *
     * @return
     */
    private double calculateSumMoney() {
        String price_ = mPriceEditText.getText().toString();
        String num_ = mNumEditText.getText().toString();
        double price = 0;
        double num = 0;
        if (!TextUtils.isEmpty(price_)) {
            price = Double.parseDouble(price_);
        }
        if (!TextUtils.isEmpty(num_)) {
            num = Double.parseDouble(num_);
        }
        return price * num;
    }

    public void openBuy() {
        mBuyTextView.setSelected(true);
        mSellTextView.setSelected(false);
        mSubmitButton.setText("买入");
        mOrderType = "buy";
        mPriceEditText.setText("");
        mNumEditText.setText("");
        seekBarPushSell.setProgress(0f);
    }

    public void openSell() {
        mBuyTextView.setSelected(false);
        mSellTextView.setSelected(true);
        mSubmitButton.setText("卖出");
        mOrderType = "sell";
        mPriceEditText.setText("");
        mNumEditText.setText("");
        seekBarPushSell.setProgress(0f);
    }
}
