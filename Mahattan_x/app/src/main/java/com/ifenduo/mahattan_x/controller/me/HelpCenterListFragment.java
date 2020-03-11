package com.ifenduo.mahattan_x.controller.me;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.Help;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpCenterListFragment extends BaseListFragment<Help> {


    public static HelpCenterListFragment newInstance() {
        Bundle args = new Bundle();
        HelpCenterListFragment fragment = new HelpCenterListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Help help) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, help);
        Intent intent = new Intent(getContext(), HelpCenterDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
    public void onRequest(int page) {
        Api.getInstance().fetchHelpCenterList(new Callback<List<Help>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Help>> response) {
                if (isSuccess) {
                    List<Help> helpList=new ArrayList<>();
                    if (response.data != null && response.data.size() > 0) {
                        for (Help help : response.data) {
                            if (help != null && !"3".equals(help.getId())) {
                                helpList.add(help);
                            }
                        }
                    }
                    dispatchResult(helpList);
                } else {
                    showToast(msg);
                }
            }
        });
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_help_center;
    }

    @Override
    public void onBindView(ViewHolder holder, Help help, int position) {
        TextView textView = holder.getView(R.id.text_title);
        if (help != null) {
            textView.setText(help.getName());
        } else {
            textView.setText("");
        }
    }
}
