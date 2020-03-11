package com.ifenduo.mahattan_x.controller.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragmentAdapter;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.widget.viewpager.NoTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ll on 2018/2/24.
 * 行情详情
 */

public class TransactionDetailActivity extends BaseActivity {
    @BindView(R.id.lin_toolbar_right_container)
    LinearLayout mToolbarIconContainer;
    @BindView(R.id.tab_layout_quotes_detail)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_quotes_detail)
    NoTouchViewPager mViewPager;

    Coin mCoin;
    long mCoinId;
    int mShowPageIndex;

    TransactionDetailFragment mTransactionDetailFragment;
    TransactionFragment mTransactionFragment;
    ConsignFragment mConsignFragment;
    TransactionRecordListFragment mTransactionRecordListFragment;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_transaction_detail;
    }


    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mCoin = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
            mShowPageIndex = bundle.getInt(Constant.BUNDLE_KEY_SHOW_PAGE_INDEX, 0);
            mCoinId = mCoin.getId();
        }
        if (mCoin != null) {
            setNavigationCenter(mCoin.getSellShortName() + "/" + mCoin.getBuyShortName());
        }
        mToolbarIconContainer.setVisibility(View.VISIBLE);
        setupTabLayout();
    }

    private void setupTabLayout() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        mTransactionDetailFragment = TransactionDetailFragment.newInstance(mCoinId);
        mTransactionFragment = TransactionFragment.newInstance(mCoin);
        mConsignFragment = ConsignFragment.newInstance(mCoin);
        mTransactionRecordListFragment = TransactionRecordListFragment.newInstance(mCoin);
        fragmentList.add(mTransactionDetailFragment);
        fragmentList.add(mTransactionFragment);
        fragmentList.add(mConsignFragment);
        fragmentList.add(mTransactionRecordListFragment);
        titleList.add("详情");
        titleList.add("交易");
        titleList.add("当前委托");
        titleList.add("成交记录");
        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(mShowPageIndex, false);
    }

    public void openSellPage() {
        mViewPager.setCurrentItem(1);
        if (mTransactionFragment != null) {
            mTransactionFragment.openSell();
        }
    }

    public void openBuyPage() {
        mViewPager.setCurrentItem(1);
        if (mTransactionFragment != null) {
            mTransactionFragment.openBuy();
        }
    }
}
