package com.ifenduo.mahattan_x.controller.transaction;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.Order;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2018/2/27.
 */

public class ConsignListFragment extends BaseListFragment<Order> {
    String mCoinName = "";
    Coin mCoin;

    public static ConsignListFragment newInstance(String coinName, Coin coin) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_BUY_COIN, coin);
        args.putString(Constant.BUNDLE_KEY_COMMON, coinName);
        ConsignListFragment fragment = new ConsignListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isSupportRefresh() {
        return false;
    }

    @Override
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCoinName = bundle.getString(Constant.BUNDLE_KEY_COMMON, "");
            mCoin = bundle.getParcelable(Constant.BUNDLE_KEY_BUY_COIN);
        }
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Order order) {

    }

    public void showAllList() {
    }

    public void showBuyList() {
    }

    public void showSellList() {
    }

    @Override
    public void onRequest(int page) {
        dispatchResult(new ArrayList<Order>());
    }


    @Override
    public int getItemLayoutResId() {
        return R.layout.item_consign_list_fragment;
    }

    @Override
    public void onBindView(ViewHolder holder, final Order order, int position) {
        TextView actionButton = holder.getView(R.id.text_item_consign_list_sum_status);
        if (order != null) {
            TextView typeTextView = holder.getView(R.id.text_item_consign_list_type);
            if (Constant.ORDER_TYPE_SELL.equals(String.valueOf(order.getType()))) {
                mCoinName = mCoin.getBuyShortName() + "/" + mCoin.getSellShortName();
                holder.setText(R.id.text_item_consign_list_type, "卖出");
                typeTextView.setTextColor(getContext().getResources().getColor(R.color.colorRiseBgAdd));
            } else {
                mCoinName = mCoin.getSellShortName() + "/" + mCoin.getBuyShortName();
                holder.setText(R.id.text_item_consign_list_type, "买入");
                typeTextView.setTextColor(getContext().getResources().getColor(R.color.colorRiseBgLess));
            }

            holder.setText(R.id.text_item_consign_list_title, mCoinName);
            holder.setText(R.id.text_item_consign_list_time, order.getTime());
            holder.setText(R.id.text_item_consign_list_price, order.getPriceString());
            holder.setText(R.id.text_item_consign_list_num, "x" + order.getCountString());
            holder.setText(R.id.text_item_consign_list_sum_money, "总额度 " + order.getSumMoney());
            holder.setText(R.id.text_item_consign_list_sum_turnover, "成交 " + order.getSuccessAmountString());
            actionButton.setText("停止委托");
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (order == null) return;
                    cancelOrder(order);
                }
            });

        } else {
            holder.setText(R.id.text_item_consign_list_title, "");
            holder.setText(R.id.text_item_consign_list_time, "");
            holder.setText(R.id.text_item_consign_list_type, "");
            holder.setText(R.id.text_item_consign_list_price, "");
            holder.setText(R.id.text_item_consign_list_num, "");
            holder.setText(R.id.text_item_consign_list_sum_money, "");
            holder.setText(R.id.text_item_consign_list_sum_turnover, "");
            holder.setText(R.id.text_item_consign_list_sum_status, "");
        }

    }

    private void cancelOrder(final Order order) {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            showToast("您还未登录，请登录");
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().cancelOrder(loginInfo.getToken(), loginInfo.getSecretKey(), String.valueOf(order.getId()), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    List<Order> orderList = getDataSource();
                    if (orderList != null && orderList.contains(order)) {
                        orderList.remove(order);
                    }
                    ((ConsignFragment) getParentFragment()).removeOrder(order);
                    notifyDataSetChanged();
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
}
