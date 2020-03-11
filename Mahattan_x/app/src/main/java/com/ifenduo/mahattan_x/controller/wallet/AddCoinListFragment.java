package com.ifenduo.mahattan_x.controller.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.view.CircleImageView;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.Balance;
import com.ifenduo.mahattan_x.entity.HideCoinEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

public class AddCoinListFragment extends BaseListFragment<Wallet> {

    LoginInfo mLoginInfo;
    List<String> mHideCoinNameList;

    public static AddCoinListFragment newInstance() {
        Bundle args = new Bundle();
        AddCoinListFragment fragment = new AddCoinListFragment();
        fragment.setArguments(args);
        return fragment;
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
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Wallet wallet) {

    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onRequest(int page) {
        fetchHideCoinList();
    }

    private void fetchBalance() {
        if(mLoginInfo==null)return;
        Api.getInstance().fetchBalance(mLoginInfo.getToken(), mLoginInfo.getSecretKey(), new Callback<Balance>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<Balance> response) {
                if (isSuccess) {
                    if (response.data != null) {
                        dispatchResult(response.data.getWallet());
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

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_add_coin;
    }

    @Override
    public void onBindView(ViewHolder holder, final Wallet wallet, int position) {
        CircleImageView coinImageView = holder.getView(R.id.coin_image_view);
        ImageView switchButton = holder.getView(R.id.switch_button);
        if (wallet != null) {
            holder.setText(R.id.name_text_view, wallet.getShortName());
            holder.setText(R.id.name_text_view, wallet.getCoinName());
            Glide.with(getContext()).load(wallet.getLogo()).into(coinImageView);
            switchButton.setSelected(!mHideCoinNameList.contains(wallet.getShortName()));
        } else {
            holder.setText(R.id.name_text_view, "");
            holder.setText(R.id.name_text_view, "");
            switchButton.setSelected(false);
        }

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallet != null) {
                    hideCoin(wallet.getShortName());
                }
            }
        });
    }

    private void hideCoin(final String coinName) {
        if(mLoginInfo==null)return;
        Api.getInstance().submitHideCoin(mLoginInfo.getUserinfo().getFid(), coinName, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    if (mHideCoinNameList.contains(coinName)) {
                        mHideCoinNameList.remove(coinName);
                    } else {
                        mHideCoinNameList.add(coinName);
                    }
                    notifyDataSetChanged();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void fetchHideCoinList() {
        if(mLoginInfo==null)return;
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
}
