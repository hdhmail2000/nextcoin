package com.ifenduo.mahattan_x.controller.transaction;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.TradingArea;
import com.ifenduo.mahattan_x.entity.TradingCenterDataEntity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.ifenduo.mahattan_x.Constant.RMB_PRICE_CACHE;
import static com.ifenduo.mahattan_x.Constant.TRADING_AREA_CACHE;
import static com.ifenduo.mahattan_x.event.BaseEvent.REFRESH_QUOTES_LIST_PRICE;

public class TransactionCenterListFragment extends BaseFragment implements TransactionCenterAdapter.OnItemClickListener {

    RecyclerView mRecyclerView;
    TransactionCenterAdapter mAdapter;

    long mTradingAreaId = -1;
    String mTradingAreaName = "";
    List<String> mTradingAreaNameList;

    public static TransactionCenterListFragment newInstance(TradingArea tradingArea, List<String> tradingAreaNameList) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.BUNDLE_KEY_COMMON, tradingArea);
        args.putStringArrayList(Constant.BUNDLE_KEY_SYMBOL_LIST, (ArrayList<String>) tradingAreaNameList);
        TransactionCenterListFragment fragment = new TransactionCenterListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_transaction_center_list;
    }

    @Override
    protected void initView(View parentView) {
        mRecyclerView = parentView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().unregister(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            TradingArea tradingArea = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
            mTradingAreaNameList = bundle.getStringArrayList(Constant.BUNDLE_KEY_SYMBOL_LIST);
            if (tradingArea != null) {
                mTradingAreaId = tradingArea.getCode();
                mTradingAreaName = tradingArea.getName();
            }
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TransactionCenterAdapter(getContext(), "自选".equals(mTradingAreaName) ? 1 : 0);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        fetchTradingAreaCoinList();
                    }
                });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取交易对
     */
    private void fetchTradingAreaCoinList() {
        Api.getInstance().fetchTradingAreaCoinList(mTradingAreaId, new Callback<List<Coin>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Coin>> response) {
                if (isSuccess) {
                    saveTradingAreaCache(response.data);
                    fetchTradingAreaCoinInfo(response.data);
                } else {
                    buildData(new ArrayList<Coin>());
                }
            }
        });
    }

    /**
     * 组装数据
     */
    private void buildData(List<Coin> coinList) {
        List<TradingCenterDataEntity> entityList = new ArrayList<>();
        Map<String, List<Coin>> tempData = new LinkedHashMap<>();
        tempData.put("其他分区", new ArrayList<Coin>());
        if (coinList != null && coinList.size() > 0) {
            for (Coin coin : coinList) {
                if (coin != null) {
                    if (!TextUtils.isEmpty(coin.getBlockName())) {
                        if (tempData.containsKey(coin.getBlockName())) {
                            List<Coin> coinList_ = tempData.get(coin.getBlockName());
                            coinList_.add(coin);
                        } else {
                            List<Coin> coinList_ = new ArrayList<>();
                            coinList_.add(coin);
                            tempData.put(coin.getBlockName(), coinList_);
                        }
                    } else {
                        tempData.get("其他分区").add(coin);
                    }
                }
            }
        }

        for (Map.Entry<String, List<Coin>> entry : tempData.entrySet()) {
            if (entry.getValue() != null && entry.getValue().size() > 0) {
                entityList.add(new TradingCenterDataEntity(TransactionCenterAdapter.ITEM_TYPE_TITLE, entry.getKey(), null));
                for (Coin coin : entry.getValue()) {
                    entityList.add(new TradingCenterDataEntity(TransactionCenterAdapter.ITEM_TYPE_COIN, "", coin));
                }
            }
        }
        mAdapter.setData(entityList);
    }

    /**
     * 获取交易对CoinBean（主要是价格）
     *
     * @param coinList
     */
    private void fetchTradingAreaCoinInfo(final List<Coin> coinList) {
        if (coinList != null && coinList.size() > 0) {
            Api.getInstance().fetchTradingAreaCoinInfo(buildCoinIds(coinList), new Callback<List<Coin.CoinBean>>() {
                @Override
                public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Coin.CoinBean>> response) {
                    if (isSuccess) {
                        saveExchangeRate(response.data);
                        relBuildCoinList(coinList, response.data);
                    }
                    buildData(coinList);
                }
            });
        } else {
            buildData(new ArrayList<Coin>());
        }
    }

    /**
     * 拼接coinId
     *
     * @param coinList
     * @return
     */
    private String buildCoinIds(List<Coin> coinList) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (coinList != null && coinList.size() > 0) {
            int size = coinList.size();
            for (int i = 0; i < size; i++) {
                Coin coin = coinList.get(i);
                if (coin != null) {
                    stringBuilder.append(String.valueOf(coin.getId()));
                    if (i != size - 1) {
                        stringBuilder.append(",");
                    }
                }
            }
            return stringBuilder.toString();
        } else {
            return stringBuilder.toString();
        }
    }

    /**
     * 设置交易对的CoinBean(主要是价格)
     *
     * @param coinList
     * @param coinBeanList
     */
    private void relBuildCoinList(List<Coin> coinList, List<Coin.CoinBean> coinBeanList) {

        if (coinList != null && coinList.size() > 0 && coinBeanList != null && coinBeanList.size() > 0) {
            for (Coin coin : coinList) {
                if (coin != null && !TextUtils.isEmpty(coin.getSellShortName()) && !TextUtils.isEmpty(coin.getBuyShortName())) {
                    for (Coin.CoinBean coinBean : coinBeanList) {
                        if (coinBean != null) {
                            if (coin.getSellShortName().equals(coinBean.getSellSymbol()) && coin.getBuyShortName().equals(coinBean.getBuySymbol())) {
                                coin.setCoinBean(coinBean);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 缓存币的RMB价格
     *
     * @param coinBeanList
     */
    private void saveExchangeRate(List<Coin.CoinBean> coinBeanList) {
        if ("USDT".equals(mTradingAreaName) && coinBeanList != null && coinBeanList.size() > 0) {
            if (RMB_PRICE_CACHE != null) {
//                if (RMB_PRICE_CACHE.size() > 0) {
//                    RMB_PRICE_CACHE.clear();
//                }
            } else {
                RMB_PRICE_CACHE = new HashMap<>();
            }
            for (Coin.CoinBean coinBean : coinBeanList) {
                if (coinBean != null && !TextUtils.isEmpty(coinBean.getSellSymbol())) {
                    if (RMB_PRICE_CACHE.containsKey(coinBean.getSellSymbol())) {
                        if (RMB_PRICE_CACHE.get(coinBean.getSellSymbol()).doubleValue() != coinBean.getP_new() * HomeActivity.EXCHANGE_RATE) {
                            RMB_PRICE_CACHE.remove(coinBean.getSellSymbol());
                            RMB_PRICE_CACHE.put(coinBean.getSellSymbol(), coinBean.getP_new() * HomeActivity.EXCHANGE_RATE);
                        }
                    } else {
                        RMB_PRICE_CACHE.put(coinBean.getSellSymbol(), coinBean.getP_new() * HomeActivity.EXCHANGE_RATE);
                    }

                }
            }
            EventBus.getDefault().post(new BaseEvent(REFRESH_QUOTES_LIST_PRICE, null));
        }
    }

    /**
     * 保存交易对
     *
     * @param coinList
     */
    private void saveTradingAreaCache(List<Coin> coinList) {
        if (TRADING_AREA_CACHE == null) {
            TRADING_AREA_CACHE = new LinkedHashMap<>();
        }
        if (TRADING_AREA_CACHE.containsKey(mTradingAreaName)) {
            TRADING_AREA_CACHE.remove(mTradingAreaName);
        }
        TRADING_AREA_CACHE.put(mTradingAreaName, coinList);
    }

//    private void showLoginDialog() {
//        LoginDialog loginDialog = new LoginDialog(getContext());
//        loginDialog.setLoginDialogCallBack(this);
//        loginDialog.show();
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPrice(BaseEvent baseEvent) {
        if (baseEvent.getCode() == REFRESH_QUOTES_LIST_PRICE && !"USDT".equals(mTradingAreaName)) {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onItemClick(Coin coin) {
        if (coin == null) return;
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getContext().getApplicationContext());
        if (loginInfo == null) {
            openLogin();
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, coin);
            TransactionDetailActivity.openActivity((BaseActivity) getContext(), TransactionDetailActivity.class, bundle);
        }
    }
}
