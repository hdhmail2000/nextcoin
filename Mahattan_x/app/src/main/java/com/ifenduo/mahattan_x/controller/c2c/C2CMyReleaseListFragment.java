package com.ifenduo.mahattan_x.controller.c2c;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DateTimeTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.MyRelease;
import com.ifenduo.mahattan_x.entity.MyReleaseItemEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class C2CMyReleaseListFragment extends BaseListFragment<MyReleaseItemEntity> {
    User mUser;

    public static C2CMyReleaseListFragment newInstance() {
        Bundle args = new Bundle();
        C2CMyReleaseListFragment fragment = new C2CMyReleaseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, MyReleaseItemEntity entity) {

    }


    @Override
    public void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getContext().getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
        }
    }

    @Override
    public void onRequest(int page) {
        if (mUser == null) {
            dispatchResult(new ArrayList<MyReleaseItemEntity>());
        } else {
            Api.getInstance().fetchMyRelease(mUser.getFid(), page, new Callback<MyRelease>() {
                @Override
                public void onComplete(boolean isSuccess, int code, String msg, DataResponse<MyRelease> response) {
                    dispatchResult(response.data == null ? new ArrayList<MyReleaseItemEntity>() : response.data.getData());
                }
            });
        }
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_c2c_my_release_list;
    }

    @Override
    public void onBindView(ViewHolder holder, final MyReleaseItemEntity entity, int position) {
        View leftContainer = holder.getView(R.id.left_container);

        TextView textTitle = holder.getView(R.id.text_title);
        TextView statusTextView = holder.getView(R.id.text_status);
        TextView textBuyNumHint = holder.getView(R.id.text_buy_num_hint);
        TextView textBuyNum = holder.getView(R.id.text_buy_num);
        TextView textDealNumHint = holder.getView(R.id.text_deal_num_hint);
        TextView textDealNum = holder.getView(R.id.text_deal_num);
        TextView textData = holder.getView(R.id.text_data);
        TextView textUnregist = holder.getView(R.id.text_unregist);

        if (entity != null) {
            textTitle.setText(entity.getSymbol_name());
            statusTextView.setText(entity.getStrStatus());
            textBuyNumHint.setText("1".equals(entity.getDeal_type()) ? "出售量" : "买入量");
            if ("9".equals(entity.getStatus())) {
                statusTextView.setTextColor(Color.parseColor("#FF67D2"));
                textUnregist.setVisibility(View.VISIBLE);
            } else if ("10".equals(entity.getStatus())) {
                statusTextView.setTextColor(getResources().getColor(R.color.colorTabTextNormal));
                textUnregist.setVisibility(View.GONE);
            } else {
                statusTextView.setTextColor(getResources().getColor(R.color.colorTabChecked));
                textUnregist.setVisibility(View.GONE);
            }
            textBuyNum.setText(NumberUtil.formatMoney(entity.getTrade_total()));
            textDealNumHint.setText("已成交量");
            textDealNum.setText(NumberUtil.formatMoney(entity.getSuccess_total()));
            textData.setText(DateTimeTool.getYYYYMMDDHHMMSS(entity.getInputtime()));
        } else {
            textUnregist.setVisibility(View.GONE);
            textTitle.setText("");
            textBuyNumHint.setText("");
            textBuyNum.setText("");
            textDealNumHint.setText("");
            textDealNum.setText("");
            textData.setText("");
            statusTextView.setText("");
        }

        textUnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity != null) {
                    cancel(entity);
                }
            }
        });

        leftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity == null || (!"1".equals(entity.getDeal_type())) && !"2".equals(entity.getDeal_type()))
                    return;
                Bundle bundle = new Bundle();
                bundle.putString(Constant.BUNDLE_KEY_PAGE_TYPE, "1".equals(entity.getDeal_type()) ? "releaseSell" : "releaseBuy");
                bundle.putString(Constant.BUNDLE_KEY_COMMON, entity.getId());
                C2CReleaseDetailActivity.openActivity(getActivity(), C2CReleaseDetailActivity.class, bundle);
            }
        });

    }

    private void cancel(final MyReleaseItemEntity entity) {
        if (mUser == null || entity == null) return;
        Api.getInstance().submitCancelRelease(mUser.getFid(), entity.getId(), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    entity.setStatus("10");
                    notifyDataSetChanged();
                } else {
                    showToast(msg);
                }
            }
        });
    }
}
