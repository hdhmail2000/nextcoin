package com.ifenduo.mahattan_x.controller.transaction;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.base.BaseFragmentAdapter;
import com.ifenduo.mahattan_x.controller.c2c.C2CActivity;
import com.ifenduo.mahattan_x.entity.TabEntity;
import com.ifenduo.mahattan_x.entity.TradingArea;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TransactionCenterFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_status_bar)
    View mStatusBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    Unbinder mUnBinder;
    List<Fragment> mFragmentList;
    List<String> mQuotesTitleList;
    BaseFragmentAdapter mFragmentAdapter;

    public static TransactionCenterFragment newInstance() {
        Bundle args = new Bundle();
        TransactionCenterFragment fragment = new TransactionCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_transaction_center;
    }

    @Override
    protected void initView(View parentView) {
        mUnBinder = ButterKnife.bind(this, parentView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mStatusBar.getLayoutParams();
            layoutParams.height = StatusBarTool.getStatusBarHeight(getContext());
            mStatusBar.setLayoutParams(layoutParams);
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        fetchTransactionArea();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @OnClick(R.id.c2c_text_view)
    public void onViewClicked() {
        C2CActivity.openActivity((BaseActivity) getContext(), C2CActivity.class, null);
    }

    /**
     * 获取交易区
     */
    private void fetchTransactionArea() {
        Api.getInstance().fetchTradingArea(new Callback<List<TradingArea>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<TradingArea>> response) {
                if (isSuccess) {
                    setupViewPager(response.data);
                }
            }
        });
    }


    /**
     * 设置viewPager
     *
     * @param areaList
     */
    private void setupViewPager(List<TradingArea> areaList) {
        buildFragmentList(areaList);
        if (mFragmentList != null && mFragmentList.size() > 0) {
            mFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragmentList, mQuotesTitleList);
            mViewPager.setAdapter(mFragmentAdapter);
            mViewPager.setOffscreenPageLimit(mFragmentList.size());
        }
        String[] titleArr = new String[mQuotesTitleList.size()];
        for (int i = 0; i < mQuotesTitleList.size(); i++) {
            titleArr[i] = mQuotesTitleList.get(i);
        }

        mTabLayout.setViewPager(mViewPager, titleArr);
    }

    /**
     * 创建TransactionCenterListFragment
     *
     * @param tradingAreaList
     */
    private void buildFragmentList(List<TradingArea> tradingAreaList) {
        if (tradingAreaList != null && tradingAreaList.size() > 0) {
            mFragmentList = new ArrayList<>();
            mQuotesTitleList = new ArrayList<>();
            List<String> tradingAreaNameList = new ArrayList<>();
            for (TradingArea tradingArea : tradingAreaList) {
                if (tradingArea != null && !TextUtils.isEmpty(tradingArea.getName())) {
                    tradingAreaNameList.add(tradingArea.getName());
                }
            }


            for (TradingArea tradingArea : tradingAreaList) {
                if (tradingArea != null && !TextUtils.isEmpty(tradingArea.getName())) {
                    mFragmentList.add(TransactionCenterListFragment.newInstance(tradingArea, tradingAreaNameList));
                    mQuotesTitleList.add(tradingArea.getName());
                }
            }

            mQuotesTitleList.add("自选");
            mFragmentList.add(SelfChooseCoinListFragment.newInstance());
        }

    }
}
