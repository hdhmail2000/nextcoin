package com.ifenduo.mahattan_x.controller.transaction;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import com.ifenduo.mahattan_x.controller.home.HomeActivity;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.NumberTool;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.ifenduo.mahattan_x.Constant.RMB_PRICE_CACHE;
import static com.ifenduo.mahattan_x.Constant.TRADING_AREA_CACHE;
import static com.ifenduo.mahattan_x.Constant.TRADING_AREA_CACHE;
import static java.security.AccessController.getContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfChooseCoinListFragment extends BaseListFragment<Coin.CoinBean> {

    public static SelfChooseCoinListFragment newInstance() {
        Bundle args = new Bundle();
        SelfChooseCoinListFragment fragment = new SelfChooseCoinListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Coin.CoinBean coin) {
        if (coin == null) return;
        Coin coin1 = new Coin();
        coin1.setCoinBean(coin);
        coin1.setId(Long.parseLong(coin.getTradeId()));
        coin1.setSellShortName(coin.getSellSymbol());
        coin1.setBuyShortName(coin.getBuySymbol());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, coin1);
        TransactionDetailActivity.openActivity((BaseActivity) getContext(), TransactionDetailActivity.class, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        //轮询
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        fetchSelfChooseCoin();
                    }
                });
    }

    @Override
    protected void initView(View parentView) {
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onRequest(int page) {

    }

    private void fetchSelfChooseCoin() {
        User user = SharedPreferencesTool.getUser(getContext());
        if (user == null) return;
        Api.getInstance().fetchSelfChooseCoin(user.getFid(), new Callback<List<Coin.CoinBean>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Coin.CoinBean>> response) {
                if (response.data!=null && response.data.size()>0) {
                    List<Coin.CoinBean> list = response.data;
                    if (list != null && list.size() > 0 && TRADING_AREA_CACHE != null && TRADING_AREA_CACHE.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            for (Map.Entry<String, List<Coin>> map : TRADING_AREA_CACHE.entrySet()) {
                                if (map.getKey().equals(list.get(i).getBuySymbol())) {
                                    for (int k = 0; k < map.getValue().size(); k++) {
                                        if (list.get(i).getTradeId() != null && list.get(i).getTradeId().equals(String.valueOf(map.getValue().get(k).getId()))) {
                                            list.get(i).setSellAppLogo(map.getValue().get(k).getSellAppLogo());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    dispatchResult(response.data);
                } else {
                    dispatchResult(new ArrayList<Coin.CoinBean>());
                }
            }
        });
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_trading_center_list;
    }

    @Override
    public void onBindView(ViewHolder holder, Coin.CoinBean coin, int position) {
        ImageView imageView = holder.getView(R.id.image_item_quotes_list);
        TextView nameTextView = holder.getView(R.id.text_item_quotes_list_name);
        TextView numTextView = holder.getView(R.id.text_item_quotes_list_message);
        TextView riseTextView = holder.getView(R.id.text_item_quotes_list_rise);
        TextView dollarTextView = holder.getView(R.id.text_item_quotes_list_price_dollar);
        TextView rmbTextView = holder.getView(R.id.text_item_quotes_list_price_rmb);
        if (coin != null) {
            Glide.with(getContext()).load(coin.getSellAppLogo()).into(imageView);
            numTextView.setText("量  " + NumberTool.double2String(coin.getTotal()));
            dollarTextView.setText("" + NumberUtil.formatMoney(coin.getP_new()));
            nameTextView.setText(coin.getSellSymbol());
            //涨幅
//            double rise = NumberTool.calculateRise(coin.getPrice(), coin.getSell());
            double rise = NumberTool.calculateRise(coin.getP_open(), coin.getP_new());
            if (rise < 0) {
                riseTextView.setText(NumberTool.double2String(rise) + "%");
                riseTextView.setBackground(getContext().getResources().getDrawable(R.drawable.shape_rise_bg_less));
            } else {
                riseTextView.setText("+" + NumberTool.double2String(rise) + "%");
                riseTextView.setBackground(getContext().getResources().getDrawable(R.drawable.shape_rise_bg_add));
            }
//转化人民币价格
//            if (coin.getCoinBean() != null && !TextUtils.isEmpty(coin.getCoinBean().getSellSymbol())) {
            if (HomeActivity.EXCHANGE_RATE == -1) {
                rmbTextView.setText("");
            } else {
                String price = getRMBPrice(coin.getBuySymbol(), coin.getP_new());
                rmbTextView.setText("¥" + price);
            }
//            } else {
//                rmbTextView.setText("");
//            }
        } else {

        }
    }

    /**
     * 获取币的RMB价格
     *
     * @param buyName
     * @param price
     * @return
     */
    private String getRMBPrice(String buyName, double price) {
        double rmbPrice = 0.00;
        if (!TextUtils.isEmpty(buyName) && RMB_PRICE_CACHE.containsKey(buyName) && !"USDT".equals(buyName)) {
            rmbPrice = RMB_PRICE_CACHE.get(buyName) * price;
        } else {
            rmbPrice = price * HomeActivity.EXCHANGE_RATE;
        }
        return NumberUtil.formatMoney(rmbPrice);
    }
}
