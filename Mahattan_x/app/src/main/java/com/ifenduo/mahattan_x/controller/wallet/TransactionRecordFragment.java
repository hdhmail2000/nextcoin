package com.ifenduo.mahattan_x.controller.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DateTimeTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.ChargeRecord;
import com.ifenduo.mahattan_x.entity.CoinRecord;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.tools.DateTool;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

public class TransactionRecordFragment extends BaseListFragment<CoinRecord> {

    LoginInfo mLoginInfo;
    Wallet mWallet;

    public static TransactionRecordFragment newInstance(Wallet wallet) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, wallet);
        TransactionRecordFragment fragment = new TransactionRecordFragment();
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
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, CoinRecord record) {

    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mWallet = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onRequest(int page) {
        if (mLoginInfo == null || mWallet == null) {
            dispatchResult(new ArrayList<CoinRecord>());
        } else {
            Api.getInstance().fetchTransactionRecord(mLoginInfo.getToken(), String.valueOf(mWallet.getCoinId()), new Callback<List<CoinRecord>>() {
                @Override
                public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<CoinRecord>> response) {
                    dispatchResult(response.data);
                }
            });

        }
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_transaction_record;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public void onBindView(ViewHolder holder, CoinRecord record, int position) {
        ImageView imageView = holder.getView(R.id.logo);
        if (record != null) {
            holder.setText(R.id.unit_text_view, record.getRelationCoinName());
            holder.setText(R.id.text_title, record.getOperationDesc());
            holder.setText(R.id.text_type, record.getStatusDesc());
            holder.setText(R.id.text_time, DateTool.formatTimeWithPattern(record.getCreateDate(), DateTimeTool.PATTERN_YYYY_MM_DD_HH_MM_SS));
            if (record.getOperation() == 1 || record.getOperation() == 4|| record.getOperation() == 5|| record.getOperation() == 8) {
                imageView.setImageResource(R.drawable.ic_transfer_in);
                holder.setText(R.id.text_money, "+" + NumberUtil.formatMoney(record.getAmount().doubleValue()));
                ((TextView) holder.getView(R.id.text_money)).setTextColor(Color.parseColor("#4EE2FE"));
            } else if (record.getOperation() == 2 || record.getOperation() == 3|| record.getOperation() == 7) {
                imageView.setImageResource(R.drawable.ic_transfer_out);
                holder.setText(R.id.text_money, "-" + NumberUtil.formatMoney(record.getAmount().doubleValue()));
                ((TextView) holder.getView(R.id.text_money)).setTextColor(Color.parseColor("#FF67D2"));
            } else {
                imageView.setImageResource(R.drawable.ic_transfer_in);
                holder.setText(R.id.text_money, NumberUtil.formatMoney(record.getAmount().doubleValue()));
                ((TextView) holder.getView(R.id.text_money)).setTextColor(Color.parseColor("#4EE2FE"));
            }

        }
    }
}
