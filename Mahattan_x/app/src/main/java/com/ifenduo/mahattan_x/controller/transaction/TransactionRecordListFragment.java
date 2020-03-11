package com.ifenduo.mahattan_x.controller.transaction;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.Order;
import com.ifenduo.mahattan_x.entity.OrderEntity;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

/**
 * Created by ll on 2018/2/27.
 */

public class TransactionRecordListFragment extends BaseListFragment<Order> {

    Coin mCoin;
    String mCoinName;

    public static TransactionRecordListFragment newInstance(Coin coin) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, coin);
        TransactionRecordListFragment fragment = new TransactionRecordListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    public boolean isSupportRefresh() {
        return false;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCoin = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
            if (mCoin != null) {
                mCoinName = mCoin.getBuyShortName() + "/" + mCoin.getSellShortName();
            }
        }
        super.initData();
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Order order) {

    }

    public void showAllList() {
    }

    public void showMyList() {
    }

    @Override
    public void onRequest(int page) {
        fetchOrder();
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_transaction_record_;
    }

    @Override
    public void onBindView(ViewHolder holder, Order order, int position) {
        final LinearLayout tableView = holder.getView(R.id.lin_item_transaction_table);
        final ImageView indicator = holder.getView(R.id.image_item_transaction_record_indicator);
        final LinearLayout tableViewContainer = holder.getView(R.id.lin_item_transaction_table_container);
        TextView typeTextView = holder.getView(R.id.text_item_transaction_record_type);

        if (Constant.ORDER_TYPE_SELL.equals(String.valueOf(order.getType()))) {
            mCoinName = mCoin.getBuyShortName() + "/" + mCoin.getSellShortName();
            holder.setText(R.id.text_item_transaction_record_type, "卖出");
            typeTextView.setTextColor(getContext().getResources().getColor(R.color.colorRiseBgAdd));
        } else {
            mCoinName = mCoin.getSellShortName() + "/" + mCoin.getBuyShortName();
            holder.setText(R.id.text_item_transaction_record_type, "买入");
            typeTextView.setTextColor(getContext().getResources().getColor(R.color.colorRiseBgLess));
        }

        holder.setText(R.id.text_item_transaction_record_title, mCoinName);
        holder.setText(R.id.text_item_transaction_record_time, order.getTime());
        holder.setText(R.id.text_item_transaction_record_price, NumberUtil.formatMoney(order.getPriceString()));
        holder.setText(R.id.text_item_transaction_record_sum_money, "总额度 " + NumberUtil.formatMoney(order.getSumMoney()));
        holder.setText(R.id.text_item_transaction_record_sum_turnover, "成交 " + NumberUtil.formatMoney(order.getSuccessAmountString()));
        holder.setText(R.id.text_item_transaction_record_sum_status, order.getHistoryOrderStatus());

//        Random rand = new Random();
//        int tableItemCount = rand.nextInt(4) + 2;
//        int tableChildCount = tableView.getChildCount();
//        if (tableItemCount == tableChildCount) {
//            for (int i = 0; i < tableChildCount; i++) {
//                TransactionRecordTableItem tableItem = (TransactionRecordTableItem) tableView.getChildAt(i);
//                tableItem.setPrice(String.valueOf(i + 22.033));
//                tableItem.setNum(String.valueOf(365 + i));
//                tableItem.setMoney(String.valueOf(300030.00 + i * 55));
//                tableItem.setOther("1000.03");
//            }
//        } else if (tableChildCount < tableItemCount) {
//            for (int i = 0; i < tableItemCount; i++) {
//                TransactionRecordTableItem tableItem;
//                if (i < tableChildCount) {
//                    tableItem = (TransactionRecordTableItem) tableView.getChildAt(i);
//                } else {
//                    tableItem = new TransactionRecordTableItem(getContext());
//                    tableView.addView(tableItem);
//                }
//                tableItem.setPrice(String.valueOf(i + 22.033));
//                tableItem.setNum(String.valueOf(365 + i));
//                tableItem.setMoney(String.valueOf(300030.00 + i * 55));
//                tableItem.setOther("1000.03");
//            }
//        } else {
//            for (int i = 0; i < tableChildCount; i++) {
//                TransactionRecordTableItem tableItem = (TransactionRecordTableItem) tableView.getChildAt(i);
//                if (i < tableItemCount) {
//                    tableItem.setPrice(String.valueOf(i + 22.033));
//                    tableItem.setNum(String.valueOf(365 + i));
//                    tableItem.setMoney(String.valueOf(300030.00 + i * 55));
//                    tableItem.setOther("1000.03");
//                } else {
//                    tableView.removeView(tableItem);
//                }
//
//            }
//        }

        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableViewContainer.getVisibility() == View.VISIBLE) {
                    startDismissAnim(tableViewContainer, indicator);
                } else {
                    startShowAnim(tableViewContainer, indicator);
                }
            }
        });

    }

    private void startShowAnim(View tableView, View indicator) {
        Animation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        Animation rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        showAction.setDuration(500);
        rotateAnimation.setFillAfter(true);
        tableView.startAnimation(showAction);
        indicator.startAnimation(rotateAnimation);
        tableView.setVisibility(View.VISIBLE);
    }

    private void startDismissAnim(final View tableView, View indicator) {
        Animation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        Animation rotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mHiddenAction.setDuration(500);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tableView.setVisibility(View.GONE);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tableView.startAnimation(mHiddenAction);
        indicator.startAnimation(rotateAnimation);

    }


    private void fetchOrder() {
        if (mCoin == null) return;
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            showToast("您为登录，请登录");
            return;
        }
        Api.getInstance().fetchOrder(loginInfo.getToken(), loginInfo.getSecretKey(),
                mCoin.getSellShortName().toLowerCase() + "_" + mCoin.getBuyShortName().toLowerCase(),
                Constant.ORDER_STATUS_HISTORY, new Callback<OrderEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<OrderEntity> response) {
                        if (isSuccess) {
                            if (response.data != null) {
                                dispatchResult(response.data.getEntrutsHis());
                            }
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
