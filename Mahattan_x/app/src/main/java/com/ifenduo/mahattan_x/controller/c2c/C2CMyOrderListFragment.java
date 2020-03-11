package com.ifenduo.mahattan_x.controller.c2c;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DateTimeTool;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.C2COrder;
import com.ifenduo.mahattan_x.entity.C2COrderListEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class C2CMyOrderListFragment extends BaseListFragment<C2COrder> {

    String mType = "1";
    User mUser;

    public static C2CMyOrderListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_KEY_COMMON, type);
        C2CMyOrderListFragment fragment = new C2CMyOrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, C2COrder order) {
        if (order == null) return;
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, order);
        C2COrderDetailActivity.openActivity((BaseActivity) getContext(), C2COrderDetailActivity.class, bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getContext().getApplicationContext());
    }

    @Override
    public void onRequest(int page) {
        if (mUser == null) {
            dispatchResult(new ArrayList<C2COrder>());
        } else {
            Api.getInstance().fetchC2COrderList(mType, mUser.getFid(), String.valueOf(page), new Callback<C2COrderListEntity>() {
                @Override
                public void onComplete(boolean isSuccess, int code, String msg, DataResponse<C2COrderListEntity> response) {
                    if ("1".equals(mType)) {
                        dispatchResult(response.data == null ? new ArrayList<C2COrder>() : response.data.getMr());
                    } else if ("2".equals(mType)) {
                        dispatchResult(response.data == null ? new ArrayList<C2COrder>() : response.data.getMc());
                    } else {
                        dispatchResult(new ArrayList<C2COrder>());
                    }
                }
            });
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString(Constant.BUNDLE_KEY_COMMON, "1");
        }
        super.initData();
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        EventBus.getDefault().register(this);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
        mRecyclerView.setPadding(DimensionTool.dp2px(getContext(), 16), 0, 0, 0);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_c2c_order_list;
    }

    @Override
    public void onBindView(ViewHolder holder, C2COrder order, int position) {
        TextView titleTextView = holder.getView(R.id.text_item_c2c_order_title);
        if (order != null) {
            holder.setText(R.id.text_item_c2c_order_no, order.getSn());
            holder.setText(R.id.text_item_c2c_order_status, order.getStrOrderStatus());
            holder.setText(R.id.text_item_c2c_order_price, "¥" + NumberUtil.formatRMB(order.getPrice()));
            holder.setText(R.id.text_item_c2c_order_time, DateTimeTool.getYYYYMMDDHHMMSS(order.getOrder_time()));
            if ("1".equals(mType)) {
                titleTextView.setText("买入"+order.getSymbolName());
                titleTextView.setTextColor(getResources().getColor(R.color.colorRiseBgLess));
                holder.setText(R.id.text_item_c2c_order_num, "买入  " + NumberUtil.formatMoney(order.getOrder_volume()));
            } else {
                titleTextView.setText("卖出"+order.getSymbolName());
                titleTextView.setTextColor(getResources().getColor(R.color.colorRiseBgAdd));
                holder.setText(R.id.text_item_c2c_order_num, "卖出  " + NumberUtil.formatMoney(order.getOrder_volume()));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(BaseEvent event) {
        if (event.getCode() == BaseEvent.EVENT_CODE_CHANGE_C2C_ORDER_STATUS && event.getObj() instanceof C2COrder) {
            forceRefresh();
        }
    }
}
