package com.ifenduo.mahattan_x.controller.transaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.Order;
import com.ifenduo.mahattan_x.entity.OrderEntity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ll on 2018/2/27.
 */

public class ConsignFragment extends BaseFragment {
    @BindView(R.id.tab_consign_all)
    TextView mAllTab;
    @BindView(R.id.tab_consign_buy)
    TextView mBuyTab;
    @BindView(R.id.tab_consign_sell)
    TextView mSellTab;

    ConsignListFragment mConsignListFragment;
    Coin mCoin;
    List<Order> mAllOrder;
    List<Order> mBuyOrder;
    List<Order> mSellOrder;

    public static ConsignFragment newInstance(Coin coin) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, coin);
        ConsignFragment fragment = new ConsignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_consign;
    }

    @Override
    protected void initView(View parentView) {
        ButterKnife.bind(this, parentView);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCoin = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        String coinName = "";
        if (mCoin != null) {
            coinName = mCoin.getBuyShortName() + "/" + mCoin.getSellShortName();
        }
        mConsignListFragment = ConsignListFragment.newInstance(coinName,mCoin);
        getChildFragmentManager().beginTransaction().add(R.id.fl_container, mConsignListFragment).commit();
        mAllTab.setSelected(true);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    @Override
    protected void initData() {
        fetchOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshOrder(BaseEvent event){
        if(event.getCode()==BaseEvent.REFRESH_ORDER_LIST){
            fetchOrder();
        }
    }

    private void fetchOrder() {
        if (mCoin == null) return;
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            showToast("您为登录，请登录");
            return;
        }
        Api.getInstance().fetchOrder(loginInfo.getToken(), loginInfo.getSecretKey(),
                mCoin.getSellShortName().toLowerCase() + "_" + mCoin.getBuyShortName().toLowerCase(),
                Constant.ORDER_STATUS_NOW, new Callback<OrderEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<OrderEntity> response) {
                        if (isSuccess) {
                            if (response.data != null) {
                                mAllOrder = response.data.getEntrutsCur();
                                buildBuyOrder();
                                buildSellOrder();
                            }
                            mConsignListFragment.dispatchResult(mAllOrder);
                            if (mAllTab.isSelected() && mConsignListFragment != null) {
                                mConsignListFragment.dispatchResult(mAllOrder);
                            } else if (mBuyTab.isSelected() && mConsignListFragment != null) {
                                mConsignListFragment.dispatchResult(mBuyOrder);
                            } else if (mSellTab.isSelected() && mConsignListFragment != null) {
                                mConsignListFragment.dispatchResult(mSellOrder);
                            }
                        }else {
                            if (code == 401) {
                                showToast("登录已失效请重新登录");
                                openLogin();
                            } else {
                                showToast(msg);
                            }
                        }
                    }
                });
    }

    private void buildBuyOrder() {
        mBuyOrder = new ArrayList<>();
        if (mAllOrder != null && mAllOrder.size() > 0) {
            for (int i = 0; i < mAllOrder.size(); i++) {
                Order order = mAllOrder.get(i);
                if (order != null && Constant.ORDER_TYPE_BUY.equals(String.valueOf(order.getType()))) {
                    mBuyOrder.add(order);
                }
            }
        }
    }

    private void buildSellOrder() {
        mSellOrder = new ArrayList<>();
        if (mAllOrder != null && mAllOrder.size() > 0) {
            for (int i = 0; i < mAllOrder.size(); i++) {
                Order order = mAllOrder.get(i);
                if (order != null && Constant.ORDER_TYPE_SELL.equals(String.valueOf(order.getType()))) {
                    mSellOrder.add(order);
                }
            }
        }
    }

    @OnClick({R.id.tab_consign_all, R.id.tab_consign_buy, R.id.tab_consign_sell})
    public void click(View view) {
        if (view.getId() == R.id.tab_consign_all) {
            if (mAllTab.isSelected()) return;
            showAllList(true);
        } else if (view.getId() == R.id.tab_consign_buy) {
            if (mBuyTab.isSelected()) return;
            showBuyList(true);
        } else if (view.getId() == R.id.tab_consign_sell) {
            if (mSellTab.isSelected()) return;
            showSellList(true);
        }
    }

    private void showAllList(boolean show) {
        if (!show) {
            mAllTab.setSelected(false);
        } else {
            if (mConsignListFragment != null) {
                mConsignListFragment.showAllList();
                mConsignListFragment.dispatchResult(mAllOrder);
            }
            mAllTab.setSelected(true);
            showBuyList(false);
            showSellList(false);

        }
    }

    private void showBuyList(boolean show) {
        if (!show) {
            mBuyTab.setSelected(false);
        } else {
            if (mConsignListFragment != null) {
                mConsignListFragment.showBuyList();
                mConsignListFragment.dispatchResult(mBuyOrder);
            }
            mBuyTab.setSelected(true);
            showAllList(false);
            showSellList(false);

        }
    }

    private void showSellList(boolean show) {
        if (!show) {
            mSellTab.setSelected(false);
        } else {
            if (mConsignListFragment != null) {
                mConsignListFragment.showSellList();
                mConsignListFragment.dispatchResult(mSellOrder);
            }
            mSellTab.setSelected(true);
            showAllList(false);
            showBuyList(false);

        }
    }

    public void removeOrder(Order order){
        if(order==null)return;
        if(mAllOrder!=null &&mAllOrder.contains(order)){
            mAllOrder.remove(order);
        }
        if(mBuyOrder!=null &&mBuyOrder.contains(order)){
            mBuyOrder.remove(order);
        }
        if(mSellOrder!=null &&mSellOrder.contains(order)){
            mSellOrder.remove(order);
        }
    }
}
