package com.ifenduo.mahattan_x.controller.c2c;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.C2CCoinType;
import com.ifenduo.mahattan_x.entity.C2CTransactionListEntity;
import com.ifenduo.mahattan_x.entity.C2cTransactionEntity;
import com.ifenduo.mahattan_x.tools.NumberUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class C2CBuyFragment extends BaseListFragment<C2CTransactionListEntity> {

    C2CCoinType mC2CCoinType;

    public static C2CBuyFragment newInstance() {
        Bundle args = new Bundle();
        C2CBuyFragment fragment = new C2CBuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onStart() {
        super.onStart();
        forceRefresh();
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, C2CTransactionListEntity entity) {

    }

    @Override
    public void onRequest(int page) {
        if (mC2CCoinType == null) {
            dispatchResult(new ArrayList<C2CTransactionListEntity>());
        } else {
            Api.getInstance().fetchC2CBuyList(page, mC2CCoinType.getId(), new Callback<C2cTransactionEntity>() {
                @Override
                public void onComplete(boolean isSuccess, int code, String msg, DataResponse<C2cTransactionEntity> response) {
                    dispatchResult(response.data == null ? new ArrayList<C2CTransactionListEntity>() : response.data.getData());
                }
            });
        }
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_c2c_buy_fragment;
    }

    @Override
    public void onBindView(ViewHolder holder, final C2CTransactionListEntity entity, int position) {

        Button submitButton = holder.getView(R.id.submit_button);
        ImageView alipayImageView = holder.getView(R.id.alipay_image_view);
        ImageView bankImageView = holder.getView(R.id.bank_card_image_view);
        ImageView wxImageView = holder.getView(R.id.wechat_image_view);

        if (entity == null) {
            holder.setText(R.id.name_text_view, "");
            holder.setText(R.id.remainder_text_view, "");
            holder.setText(R.id.num_text_view, "");
            holder.setText(R.id.price_text_view, "");
            holder.setText(R.id.min_num_text_view, "");
            alipayImageView.setVisibility(View.GONE);
            bankImageView.setVisibility(View.GONE);
            wxImageView.setVisibility(View.GONE);
        } else {
            holder.setText(R.id.remainder_text_view, "");
            holder.setText(R.id.name_text_view, entity.getTitle());
            holder.setText(R.id.num_text_view, NumberUtil.formatMoney(entity.getTrade_total()) + "  " + entity.getSymbol_name());
            holder.setText(R.id.price_text_view, NumberUtil.formatRMB(entity.getOrder_price()) + "  CNY");
            holder.setText(R.id.min_num_text_view, NumberUtil.formatMoney(entity.getMin_value()) + "  " + entity.getSymbol_name());
            if (!TextUtils.isEmpty(entity.getPay_type())) {
                bankImageView.setVisibility(entity.getPay_type().contains("银行卡") ? View.VISIBLE : View.GONE);
                wxImageView.setVisibility(entity.getPay_type().contains("微信") ? View.VISIBLE : View.GONE);
                alipayImageView.setVisibility(entity.getPay_type().contains("支付宝") ? View.VISIBLE : View.GONE);
            } else {
                alipayImageView.setVisibility(View.GONE);
                bankImageView.setVisibility(View.GONE);
                wxImageView.setVisibility(View.GONE);
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity == null) return;
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, entity);
                C2CActivity.openActivity(getActivity(), C2CBuyActivity.class, bundle);
            }
        });
    }

    public void setC2CCoinType(C2CCoinType c2CCoinType) {
        this.mC2CCoinType = c2CCoinType;
        forceRefresh();
    }

//    ImageView levelImageView=holder.getView(R.id.level_image_view);
//    if(position%2==0){
//        Glide.with(getContext()).load(R.drawable.ic_diamond_red).into(levelImageView);
//    }else if(position%3==0){
//        Glide.with(getContext()).load(R.drawable.ic_diamond_white).into(levelImageView);
//    }else {
//        Glide.with(getContext()).load(R.drawable.ic_diamond_yellow).into(levelImageView);
//    }
}
