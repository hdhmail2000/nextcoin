package com.ifenduo.mahattan_x.controller.transaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.entity.Coin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ll on 2018/2/27.
 */

public class TransactionRecordFragment extends BaseFragment {
    @BindView(R.id.tab_transaction_record_all)
    TextView mAllTab;
    @BindView(R.id.tab_transaction_record_my)
    TextView mMyTab;

    TransactionRecordListFragment mRecordListFragment;
    Coin mCoin;

    public static TransactionRecordFragment newInstance(Coin coin) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, coin);
        TransactionRecordFragment fragment = new TransactionRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_transaction_record;
    }

    @Override
    protected void initView(View parentView) {
        ButterKnife.bind(this, parentView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCoin = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        mRecordListFragment = TransactionRecordListFragment.newInstance(mCoin);
        getChildFragmentManager().beginTransaction().add(R.id.fl_container, mRecordListFragment).commit();
//        showMyList(true);
    }

    @Override
    protected void initData() {

    }

//    @OnClick({R.id.tab_transaction_record_my, R.id.tab_transaction_record_all})
//    public void click(View view) {
//        if (view.getId() == R.id.tab_transaction_record_my) {
//            if (mMyTab.isSelected()) return;
//            showMyList(true);
//        } else if (view.getId() == R.id.tab_transaction_record_all) {
//            if (mAllTab.isSelected()) return;
//            showAllList(true);
//        }
//    }

//    private void showMyList(boolean show) {
//        if (!show) {
//            mMyTab.setSelected(false);
//        } else {
//            if (mRecordListFragment != null) {
//                mRecordListFragment.showMyList();
//            }
//            mMyTab.setSelected(true);
//            showAllList(false);
//        }
//    }
//
//    private void showAllList(boolean show) {
//        if (!show) {
//            mAllTab.setSelected(false);
//        } else {
//            if (mRecordListFragment != null) {
//                mRecordListFragment.showAllList();
//            }
//            mAllTab.setSelected(true);
//            showMyList(false);
//        }
//    }
}
