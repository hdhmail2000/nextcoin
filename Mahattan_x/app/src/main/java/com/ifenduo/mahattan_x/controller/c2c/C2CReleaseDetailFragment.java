package com.ifenduo.mahattan_x.controller.c2c;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.ifenduo.mahattan_x.entity.C2CReleaseDetailEntity;
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
 * Created by ll on 2018/3/8.
 */

public class C2CReleaseDetailFragment extends BaseListFragment<C2COrder> {

    TextView mNumTextView;
    TextView mTitleTextView;
    TextView mSuccessTextView;
    TextView mSuccessHintTextView;

    View mHeaderView;
    String mID;
    String mType;
    User mUser;

    String mRelType = "";
    String mCoinName = "";

    public static C2CReleaseDetailFragment newInstance(String id, String type) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_KEY_COMMON, id);
        args.putString(Constant.BUNDLE_KEY_PAGE_TYPE, type);
        C2CReleaseDetailFragment fragment = new C2CReleaseDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
        getRecyclerView().setPadding(DimensionTool.dp2px(getContext(), 16), 0, DimensionTool.dp2px(getContext(), 16), 0);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getContext().getApplicationContext());
    }

    @Override
    public View getHeaderView() {
        if (mHeaderView == null) {
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_c2c_release_detail, mRecyclerView, false);
            mNumTextView = mHeaderView.findViewById(R.id.sum_num_text_view);
            mTitleTextView = mHeaderView.findViewById(R.id.title_text_view);
            mSuccessTextView = mHeaderView.findViewById(R.id.success_num_text_view);
            mSuccessHintTextView = mHeaderView.findViewById(R.id.success_num_hint_text_view);
        }
        return mHeaderView;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mID = bundle.getString(Constant.BUNDLE_KEY_COMMON, "");
            mType = bundle.getString(Constant.BUNDLE_KEY_PAGE_TYPE, "");
        }
        mRelType = ("releaseSell".equals(mType) ? "卖出" : "买入");
        super.initData();
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, C2COrder order) {
        if (order == null) return;
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, order);
        C2COrderDetailActivity.openActivity((BaseActivity) getContext(), C2COrderDetailActivity.class, bundle);
    }

    @Override
    public void onRequest(int page) {
        if (mUser == null) {
            dispatchResult(new ArrayList<C2COrder>());
        } else {
            Api.getInstance().fetchC2CReleaseDetail(mType, mUser.getFid(), mID, page, new Callback<C2CReleaseDetailEntity>() {
                @Override
                public void onComplete(boolean isSuccess, int code, String msg, DataResponse<C2CReleaseDetailEntity> response) {
                    if (response.data != null && response.data.getTrade() != null) {
                        mCoinName = response.data.getTrade().getSymbol_name();
                        mNumTextView.setText(mRelType + "总量(" + response.data.getTrade().getSymbol_name() + ")：" + NumberUtil.formatMoney(response.data.getTrade().getTrade_total()));
                        mSuccessTextView.setText(NumberUtil.formatMoney(response.data.getTrade().getSuccess_total()));
                        mSuccessHintTextView.setText("已成交量(" + response.data.getTrade().getSymbol_name() + ")");
                        mTitleTextView.setText(mRelType + response.data.getTrade().getSymbol_name());
                    } else {
                        mNumTextView.setText(mRelType + "总量：");
                        mSuccessHintTextView.setText("已成交量");
                        mSuccessTextView.setText("");
                        mTitleTextView.setText(mRelType);
                    }

                    if ("releaseBuy".equals(mType)) {
                        dispatchResult(response.data == null ? new ArrayList<C2COrder>() : response.data.getMr());
                    } else if ("releaseSell".equals(mType)) {
                        dispatchResult(response.data == null ? new ArrayList<C2COrder>() : response.data.getMc());
                    } else {
                        dispatchResult(new ArrayList<C2COrder>());
                    }


                }
            });
        }
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
            if ("releaseBuy".equals(mType)) {
                titleTextView.setText("买入" + mCoinName);
                titleTextView.setTextColor(getResources().getColor(R.color.colorRiseBgLess));
                holder.setText(R.id.text_item_c2c_order_num, "买入  " + order.getOrder_volume());
            } else {
                titleTextView.setText("卖出" + mCoinName);
                titleTextView.setTextColor(getResources().getColor(R.color.colorRiseBgAdd));
                holder.setText(R.id.text_item_c2c_order_num, "卖出  " + order.getOrder_volume());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(BaseEvent event) {
        if (event.getCode() == BaseEvent.EVENT_CODE_CHANGE_C2C_ORDER_STATUS && event.getObj() instanceof C2COrder) {
            C2COrder order = (C2COrder) event.getObj();
            if (order == null) return;
            List<C2COrder> orderList = getDataSource();
            if (orderList != null && orderList.size() > 0) {
                for (C2COrder c2COrder_ : orderList) {
                    if (c2COrder_ != null && !TextUtils.isEmpty(c2COrder_.getId()) && c2COrder_.getId().equals(order.getId())) {
                        c2COrder_.setOrder_status(order.getOrder_status());
                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
