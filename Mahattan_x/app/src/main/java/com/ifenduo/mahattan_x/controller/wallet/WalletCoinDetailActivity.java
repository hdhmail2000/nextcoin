package com.ifenduo.mahattan_x.controller.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.common.view.CircleImageView;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.NumberUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.Constant.RMB_PRICE_CACHE;
import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_WITHDRAW_SUCCESS;

public class WalletCoinDetailActivity extends BaseActivity {

    @BindView(R.id.coin_image_view)
    CircleImageView mCoinImageView;
    @BindView(R.id.name_text_view)
    TextView mNameTextView;
    @BindView(R.id.price_text_view)
    TextView mPriceTextView;
    @BindView(R.id.rmb_price_text_view)
    TextView mRmbPriceTextView;

    Wallet mWallet;
    String mEthID;

    public static void openWalletCoinDetail(Context context, Wallet wallet, String ethID) {
        Intent intent = new Intent(context, WalletCoinDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("wallet", wallet);
        bundle.putString("ethID", ethID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_wallet_coin_detail;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarTool.setTranslucentForImageView(this, mToolBarContainer);
        handleIntent();
        if (mWallet != null) {
            setNavigationCenter(mWallet.getShortName() + "钱包");
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, TransactionRecordFragment.newInstance(mWallet)).commit();

        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.getCode() == EVENT_CODE_WITHDRAW_SUCCESS) {
            finish();
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mWallet = bundle.getParcelable("wallet");
            mEthID = bundle.getString("ethID","");
        }
    }

    private void initData() {
        if (mWallet != null) {
            double rmbMoney = getRMBMoney(mWallet);
            String rmbMoney_ = "";
            if (rmbMoney >= 0) {
                rmbMoney_ = "" + NumberUtil.formatMoney(rmbMoney);
            }
            Glide.with(this).load(mWallet.getLogo()).into(mCoinImageView);

            mNameTextView.setText(mWallet.getShortName());
            mPriceTextView.setText(NumberUtil.formatMoney(mWallet.getTotal()));
            mRmbPriceTextView.setText("≈  ¥" + rmbMoney_);
        } else {
            Glide.with(this).load(R.drawable.image_test_eth).into(mCoinImageView);
            mNameTextView.setText("");
            mPriceTextView.setText("");
            mRmbPriceTextView.setText("");
        }

    }


    @OnClick({R.id.sell_button, R.id.buy_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sell_button:
                if (mWallet == null) return;
                WithDrawMoneyActivity.openWithDrawMoney(this, mWallet);
                break;
            case R.id.buy_button:
                if (mWallet == null) return;
                ChargeMoneyActivity.openChargeMoney(this, mWallet,mEthID);
                break;
        }
    }

    /**
     * 获取单币种的人民币余额
     *
     * @param wallet
     * @return
     */
    private double getRMBMoney(Wallet wallet) {
        double price = -1;
        if (RMB_PRICE_CACHE != null && RMB_PRICE_CACHE.size() > 0 && wallet != null && !TextUtils.isEmpty(wallet.getShortName())
                && (RMB_PRICE_CACHE.containsKey(wallet.getShortName()) || "USDT".equals(wallet.getShortName()))) {
            if ("USDT".equals(wallet.getShortName())) {
                price = wallet.getTotal() * HomeActivity.EXCHANGE_RATE;
            } else {
                price = RMB_PRICE_CACHE.get(wallet.getShortName()) * wallet.getTotal();
            }
        }
        return price;
    }
}
