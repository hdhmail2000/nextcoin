package com.ifenduo.mahattan_x.controller.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DateTimeTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.CalculationRecord;
import com.ifenduo.mahattan_x.entity.CalculationTask;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends BaseListFragment<CalculationRecord> {

    View mHeaderView;
    TextView mCalculateTextView;
    TextView mLevel1CalculateTextView;
    TextView mLevel2CalculateTextView;
    TextView mLevel1NumTextView;
    TextView mLevel2NumTextView;

    User mUser;

    public static TaskFragment newInstance() {
        Bundle args = new Bundle();
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, CalculationRecord calculationRecord) {

    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public View getHeaderView() {
        if (mHeaderView == null) {
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_task, mRecyclerView, false);
            initHeaderView();
        }
        return mHeaderView;
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getContext().getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        forceRefresh();
    }

    private void initHeaderView() {
        if (mHeaderView == null) return;
        mCalculateTextView = mHeaderView.findViewById(R.id.calculate_text_view);
        mLevel1NumTextView = mHeaderView.findViewById(R.id.level_1_text_view);
        mLevel1CalculateTextView = mHeaderView.findViewById(R.id.level_1_calculate_text_view);
        mLevel2NumTextView = mHeaderView.findViewById(R.id.level_2_text_view);
        mLevel2CalculateTextView = mHeaderView.findViewById(R.id.level_2_calculate_text_view);
    }

    private void fetchTask() {
        if (mUser == null) return;
        Api.getInstance().fetchCalculationTask(mUser.getFid(), new Callback<CalculationTask>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<CalculationTask> response) {
                if (isSuccess && response.data != null) {
                    mCalculateTextView.setText(response.data.getFability());
                    if (response.data.getInvite() != null && response.data.getInvite().size() == 2) {
                        CalculationTask.InviteBean inviteBean = response.data.getInvite().get(0);//一级
                        CalculationTask.InviteBean inviteBean_ = response.data.getInvite().get(1);//二级
                        if (inviteBean != null) {
                            mLevel1CalculateTextView.setText(String.valueOf(inviteBean.getTotal()));
                            mLevel1NumTextView.setText(String.valueOf(inviteBean.getNum()));
                        }

                        if (inviteBean != null) {
                            mLevel2CalculateTextView.setText(String.valueOf(inviteBean_.getTotal()));
                            mLevel2NumTextView.setText(String.valueOf(inviteBean_.getNum()));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onRequest(int page) {
        if (mUser == null) {
            dispatchResult(new ArrayList<CalculationRecord>());
        }
        fetchTask();

        Api.getInstance().fetchCalculationRecord(mUser.getFid(), page, new Callback<List<CalculationRecord>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<CalculationRecord>> response) {
                dispatchResult(response.data);
            }
        });

    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_task;
    }

    @Override
    public void onBindView(ViewHolder holder, CalculationRecord record, int position) {
        if (record != null) {
            holder.setText(R.id.title_text_view, record.getNote());
            holder.setText(R.id.num_text_view, "+"+record.getValue());
            holder.setText(R.id.time_text_view, DateTimeTool.formatTimeWithPattern(record.getInputtime(), DateTimeTool.PATTERN_YYYY_MM_DD_HH_MM_SS));
        } else {
            holder.setText(R.id.title_text_view, "");
            holder.setText(R.id.num_text_view, "");
            holder.setText(R.id.time_text_view, "");
        }

    }
}
