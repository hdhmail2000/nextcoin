package com.ifenduo.mahattan_x.controller.me;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.RechargeAddress;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.entity.WithdrawAddressInfo;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WithDrawPlaceListFragment extends BaseListFragment<RechargeAddress> {

    RechargeAddress mCheckedWithdrawAddress;
    Wallet mWallet;
    WithdrawAddressInfo mWithdrawAddressInfo;

    public static WithDrawPlaceListFragment newInstance(Wallet wallet) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON,wallet);
        WithDrawPlaceListFragment fragment = new WithDrawPlaceListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        forceRefresh();
    }

    @Override
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, RechargeAddress withdrawAddress) {
        if (withdrawAddress == null) return;
        mCheckedWithdrawAddress = withdrawAddress;
        notifyDataSetChanged();
        if(mWithdrawAddressInfo!=null){
            withdrawAddress.setAppLogo(mWithdrawAddressInfo.getAppLogo());
        }
        ((WithDrawPlaceActivity) getContext()).getSelectedAddress(withdrawAddress);
    }

    @Override
    public void onRequest(int page) {
        LoginInfo loginInfo= SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if(loginInfo==null)return;
        String coinID="";
        if(mWallet!=null){
         coinID=String.valueOf(mWallet.getCoinId());
        }
        Api.getInstance().fetchWithdrawAddress(loginInfo.getToken(), coinID, new Callback<WithdrawAddressInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<WithdrawAddressInfo> response) {
                if(isSuccess){
                    mWithdrawAddressInfo=response.data;
                    if(response.data!=null){
                        dispatchResult(response.data.getWithdrawAddress());
                    }else {
                        dispatchResult(new ArrayList<RechargeAddress>());
                    }
                }else {
                    if (code == 401) {
                        showToast("登录已失效请重新登录");
                        openLogin();
                    } else {
                        dispatchResult(new ArrayList<RechargeAddress>());
                    }

                }
            }
        });
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
        Bundle bundle=getArguments();
        if(bundle!=null){
            mWallet=bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }

    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_with_draw_place;
    }

    @Override
    public void onBindView(ViewHolder holder, RechargeAddress withdrawAddress, int position) {
        ImageView imageView = holder.getView(R.id.image_item_coin);
        if(withdrawAddress!=null){
            if(mWallet!=null ){
                holder.setText(R.id.text_item_with_draw_name, withdrawAddress.getFremark());
            }else {
                holder.setText(R.id.text_item_with_draw_name, "");
            }
            if(mWithdrawAddressInfo!=null){
                Glide.with(getContext()).load(mWithdrawAddressInfo.getAppLogo()).into(imageView);
            }else {
                Glide.with(getContext()).load("").into(imageView);
            }
            holder.setText(R.id.text_item_with_draw_code, withdrawAddress.getFadderess());
        }else {
            holder.setText(R.id.text_item_with_draw_code, "");
            holder.setText(R.id.text_item_with_draw_name, "");
            Glide.with(getContext()).load("").into(imageView);
        }



    }
}
