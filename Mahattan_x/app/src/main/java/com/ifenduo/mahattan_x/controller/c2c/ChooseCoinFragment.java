package com.ifenduo.mahattan_x.controller.c2c;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.C2CCoinType;

import java.util.List;

public class ChooseCoinFragment extends BaseListFragment<C2CCoinType> {

    private C2CCoinType mCheckedCoin;

    public static ChooseCoinFragment newInstance(C2CCoinType c2CCoinType) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, c2CCoinType);
        ChooseCoinFragment fragment = new ChooseCoinFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public boolean isSupportLoadMore() {
        return false;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCheckedCoin = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        super.initData();
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, C2CCoinType c2CCoinType) {
        if (c2CCoinType == null) return;
        mCheckedCoin = c2CCoinType;
        notifyDataSetChanged();
        ((ChooseCoinActivity) getContext()).setResultCoin(c2CCoinType);
    }

    @Override
    public void onRequest(int page) {
        Api.getInstance().fetchC2CCoinTypeList(new Callback<List<C2CCoinType>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<C2CCoinType>> response) {
                dispatchResult(response.data);
            }
        });
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_c2c_choose_coin;
    }

    @Override
    public void onBindView(ViewHolder holder, C2CCoinType c2CCoinType, int position) {

        ImageView checkBox = holder.getView(R.id.check_box);
        if (c2CCoinType != null) {
            holder.setText(R.id.name_text_view, c2CCoinType.getShort_name());
            if (mCheckedCoin != null && !TextUtils.isEmpty(mCheckedCoin.getId()) && mCheckedCoin.getId().equals(c2CCoinType.getId())) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }
        } else {
            holder.setText(R.id.name_text_view, "");
            checkBox.setVisibility(View.GONE);
        }
    }

}
