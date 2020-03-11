package com.ifenduo.mahattan_x.controller.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.common.view.CircleImageView;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.Balance;
import com.ifenduo.mahattan_x.entity.HideCoinEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

import static com.ifenduo.mahattan_x.Constant.RMB_PRICE_CACHE;

public class WalletListFragment extends BaseListFragment<Wallet> {
    View mHeaderView;
    TextView mMoneyTextView;
    TextView mUnitTextView;
    ImageView mHeaderImageView;
    int mScreenWidth;

    List<String> mHideCoinNameList;
    LoginInfo mLoginInfo;
    String mEthID = "";

    public static WalletListFragment newInstance() {
        Bundle args = new Bundle();
        WalletListFragment fragment = new WalletListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Wallet wallet) {
        WalletCoinDetailActivity.openWalletCoinDetail(getContext(), wallet, mEthID);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (mLoginInfo == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }

        forceRefresh();
    }

    @Override
    public void onRequest(int page) {
        fetchHideCoinList();
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_wallect;
    }

    @Override
    protected void initView(View parentView) {
        mScreenWidth = DimensionTool.getScreenWidth(getContext());
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public View getHeaderView() {
        if (mHeaderView == null) {
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_wallet, mRecyclerView, false);
            initHeaderView();
        }
        return mHeaderView;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    /**
     * 初始化header相关控件
     */
    private void initHeaderView() {
        mHeaderImageView = mHeaderView.findViewById(R.id.header_image_view);
        mMoneyTextView = mHeaderView.findViewById(R.id.money_text_view);
        mUnitTextView = mHeaderView.findViewById(R.id.unit_text_view);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mHeaderImageView.getLayoutParams();
        int width = mScreenWidth - DimensionTool.dp2px(getContext(), 16) * 2;
        int height = (int) (width * (142f / 344));
        layoutParams.width = width;
        layoutParams.height = height;
        mHeaderImageView.setLayoutParams(layoutParams);
    }

    @Override
    public void onBindView(ViewHolder holder, Wallet wallet, int position) {
        CircleImageView coinImageView = holder.getView(R.id.coin_image_view);

        Glide.with(getContext()).load(wallet.getLogo()).into(coinImageView);
        if (wallet != null) {
            double rmbMoney = getRMBMoney(wallet);
            String rmbMoney_ = "";
            if (rmbMoney >= 0) {
                rmbMoney_ = "≈  ¥" + NumberUtil.formatMoney(rmbMoney);
            }
            holder.setText(R.id.price_text_view, NumberUtil.formatMoney(wallet.getTotal()));
            holder.setText(R.id.rmb_price_text_view, rmbMoney_);
            holder.setText(R.id.name_text_view, wallet.getShortName());
            Glide.with(getContext()).load(wallet.getLogo()).into(coinImageView);
        } else {
            holder.setText(R.id.price_text_view, "");
            holder.setText(R.id.rmb_price_text_view, "");
            holder.setText(R.id.name_text_view, "");
            Glide.with(getContext()).load("").into(coinImageView);
        }
    }

    private void fetchHideCoinList() {
        if (mLoginInfo == null) return;
        Api.getInstance().fetchHideCoinList(mLoginInfo.getUserinfo().getFid(), new Callback<List<HideCoinEntity>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<HideCoinEntity>> response) {
                mHideCoinNameList = new ArrayList<>();
                if (response.data != null && response.data.size() > 0) {
                    for (HideCoinEntity entity : response.data) {
                        if (entity != null && !TextUtils.isEmpty(entity.getCoin_name()) && !mHideCoinNameList.contains(entity.getCoin_name())) {
                            mHideCoinNameList.add(entity.getCoin_name());
                        }
                    }
                }

                fetchBalance();
            }
        });
    }

    private void fetchBalance() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            return;
        }
        Log.d("TAG", "fetchBalance---->");
        Api.getInstance().fetchBalance(loginInfo.getToken(), loginInfo.getSecretKey(), new Callback<Balance>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<Balance> response) {
                if (isSuccess) {
                    Log.d("TAG", "fetchBalance---->isSuccess");
                    if (response.data != null && response.data.getWallet() != null&&response.data.getWallet().size()>0) {
                        for (Wallet wallet : response.data.getWallet()) {
                            if (wallet != null && "ETH".equals(wallet.getShortName())) {
                                mEthID = String.valueOf(wallet.getCoinId());
                                Log.d("TAG", "ETH---->" + mEthID);
                            }
                        }
                        if (mHideCoinNameList == null || mHideCoinNameList.size() == 0) {
                            dispatchResult(response.data.getWallet());
                        } else {
                            List<Wallet> walletList = new ArrayList<>();
                            for (Wallet wallet : response.data.getWallet()) {
                                if (wallet != null && !TextUtils.isEmpty(wallet.getShortName()) && !mHideCoinNameList.contains(wallet.getShortName())) {
                                    walletList.add(wallet);
                                }
                            }
                            dispatchResult(walletList);
                        }

                        calculateTotalBalance(response.data.getWallet());
                    } else {
                        dispatchResult(new ArrayList<Wallet>());
                    }
                } else {
                    dispatchResult(new ArrayList<Wallet>());
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

    /**
     * 计算人民币余额
     */
    private void calculateTotalBalance(List<Wallet> walletList) {
        double balance = 0.00;
        if (walletList != null && walletList.size() > 0 && RMB_PRICE_CACHE != null && RMB_PRICE_CACHE.size() > 0) {
            for (Wallet wallet : walletList) {
                if (wallet != null && !TextUtils.isEmpty(wallet.getShortName())
                        && (RMB_PRICE_CACHE.containsKey(wallet.getShortName()) || "USDT".equals(wallet.getShortName()))) {
                    if ("USDT".equals(wallet.getShortName())) {
                        balance = wallet.getTotal() * HomeActivity.EXCHANGE_RATE + balance;
                    } else {
                        balance = RMB_PRICE_CACHE.get(wallet.getShortName()) * wallet.getTotal() + balance;
                    }
                }
            }
        }
        mMoneyTextView.setText(NumberUtil.formatMoney(balance));
    }
}
