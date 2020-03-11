package com.ifenduo.mahattan_x.controller.transaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.KLineChartTick;
import com.ifenduo.mahattan_x.entity.TabEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.tools.SocketDataTool;
import com.ifenduo.mahattan_x.widget.kchartview.KLineLayout;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wordplat.ikvstockchart.KLineHandler;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.render.KLineRender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ll on 2018/2/26.
 * <p>
 * 详情
 */

public class TransactionDetailFragment extends BaseFragment implements OnTabSelectListener {
    @BindView(R.id.text_quotes_detail_price)
    TextView mPriceTextView;
    @BindView(R.id.image_quotes_detail_rise_icon)
    ImageView mRiseIcon;
    @BindView(R.id.text_quotes_detail_rise)
    TextView mRiseTextView;
    @BindView(R.id.text_quotes_detail_max_price)
    TextView mMaxPriceTextView;
    @BindView(R.id.text_quotes_detail_min_price)
    TextView mMinPriceTextView;
    @BindView(R.id.text_quotes_detail_volume)
    TextView mVolumeTextView;
    @BindView(R.id.k_line_layout_quotes_detail)
    KLineLayout mKLineLayout;
    @BindView(R.id.tab_quotes_detail_child)
    CommonTabLayout mChildTabLayout;
    @BindView(R.id.collect_button)
    TextView mCollectButton;
    ArrayList<CustomTabEntity> mCustomTabEntityList;

    String mTimeInterval;
    EntrySet mKLineEntrySet;

    long mCoinId;
    User mUser;

    public static TransactionDetailFragment newInstance(long coinId) {
        Bundle args = new Bundle();
        args.putLong(Constant.BUNDLE_KEY_COMMON, coinId);
        TransactionDetailFragment fragment = new TransactionDetailFragment();
        fragment.setArguments(args);
        return fragment;
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

        fetchIsCollect();
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_transaction_detail;
    }

    @Override
    protected void initView(View parentView) {
        ButterKnife.bind(this, parentView);
        mCustomTabEntityList = new ArrayList<>();
        mCustomTabEntityList.add(new TabEntity(0, 0, "1分"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "5分"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "15分"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "30分"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "1小时"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "1天"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "1周"));
        mCustomTabEntityList.add(new TabEntity(0, 0, "1月"));
        mChildTabLayout.setTabData(mCustomTabEntityList);
        mChildTabLayout.setOnTabSelectListener(this);
        mChildTabLayout.setCurrentTab(2);
        initKLine();
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCoinId = bundle.getLong(Constant.BUNDLE_KEY_COMMON, 0);
        }
        mTimeInterval = Constant.K_LINE_TIME_INTERVAL_15_MIN;
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
                        fetchMarkDetail();
                        fetchKLIne();
                    }
                });
    }

    private void submitCollectTrade() {
        if (mUser == null) return;
        Api.getInstance().submitCollectTrading(mUser.getFid(), String.valueOf(mCoinId), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    mCollectButton.setSelected(!mCollectButton.isSelected());
                    if (mCollectButton.isSelected()) {
                        mCollectButton.setText("已选");
                    } else {
                        mCollectButton.setText("添加自选");
                    }
                } else {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 判断是否收藏
     */
    private void fetchIsCollect() {
        if (String.valueOf(mCoinId) == null || mUser == null) return;
        Api.getInstance().fetchIsCollectTrading(mUser.getFid(), String.valueOf(mCoinId), new Callback<Coin.CoinBean>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<Coin.CoinBean> response) {
                if (isSuccess) {
                    Coin.CoinBean coinBean = response.data;
                    bindIsCollect(coinBean);
                } else {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 获取实时行情
     */
    private void fetchMarkDetail() {
        Api.getInstance().fetchTradingAreaCoinInfo(String.valueOf(mCoinId), new Callback<List<Coin.CoinBean>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<Coin.CoinBean>> response) {
                if (isSuccess) {
                    if (response.data != null && response.data.size() > 0) {
                        bindMarkDetail(response.data.get(0));
                    } else {
                        bindMarkDetail(null);
                    }
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void bindMarkDetail(Coin.CoinBean coinBean) {
        if (coinBean != null) {
            if (coinBean == null) {
                mMaxPriceTextView.setText("$");
                mMinPriceTextView.setText("$");
                mVolumeTextView.setText("");
                mPriceTextView.setText("$");
                mRiseTextView.setText("");
            } else {
                double rise = coinBean.getChg();
                mMaxPriceTextView.setText(NumberUtil.formatMoney(coinBean.getSell()));
                mMinPriceTextView.setText(NumberUtil.formatMoney(coinBean.getBuy()));
                mVolumeTextView.setText(NumberUtil.double2String2(coinBean.getTotal()));
                mPriceTextView.setText(NumberUtil.formatMoney(coinBean.getP_new()));
                if (rise > 0) {
                    mRiseTextView.setText(NumberUtil.double2String2(rise) + "%");
                    showAddStyle();
                } else {
                    mRiseTextView.setText(NumberUtil.double2String2(rise) + "%");
                    showLessStyle();
                }
            }
        }
    }

    private void fetchKLIne() {
        Api.getInstance().fetchKLine(String.valueOf(mCoinId), mTimeInterval, new Callback<List<List<BigDecimal>>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<List<BigDecimal>>> response) {
                if (isSuccess) {
                    getKLineData(response.data);
                } else {
                    showToast(msg);
                }
            }
        });
    }


    private void initKLine() {
        mKLineLayout.setKLineHandler(new KLineHandler() {
            @Override
            public void onLeftRefresh() {
            }

            @Override
            public void onRightRefresh() {
            }

            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {

            }

            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {
                final KLineRender kLineRender = (KLineRender) mKLineLayout.getKLineView().getRender();
                if (kLineRender.getKLineRect().contains(x, y)) {
                    kLineRender.zoomIn(x, y);
                }
            }

            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {
//                mMinPriceTextView.setText("$" + entry.getLow());
//                mMaxPriceTextView.setText("$" + entry.getHigh());
//                mVolumeTextView.setText(entry.getVolume() + "B");
            }

            @Override
            public void onCancelHighlight() {

            }
        });
    }


    @OnClick({R.id.button_quotes_detail_buy, R.id.button_quotes_detail_sell, R.id.collect_button})
    public void click(View view) {
        if (R.id.button_quotes_detail_sell == view.getId()) {
            ((TransactionDetailActivity) getContext()).openSellPage();
        } else if (R.id.button_quotes_detail_buy == view.getId()) {
            ((TransactionDetailActivity) getContext()).openBuyPage();
        } else if (R.id.collect_button == view.getId()) {
            submitCollectTrade();
        }
    }

    private void showLessStyle() {
        if (isAdded()) {
            mRiseIcon.setImageResource(R.drawable.ic_rise_less);
        }

    }

    private void showAddStyle() {
        if (isAdded()) {
            mRiseIcon.setImageResource(R.drawable.ic_rise_add);
        }

    }


    private EntrySet loadFirst() {
        EntrySet set = new EntrySet();
        if (mKLineEntrySet == null) return set;
        for (int i = 0; i < mKLineEntrySet.getEntryList().size(); i++) {
            set.addEntry(mKLineEntrySet.getEntryList().get(i));
        }
        return set;
    }

    @Override
    public void onTabSelect(int position) {
        if (position == 0) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_1_MIN;
        } else if (position == 1) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_5_MIN;
        } else if (position == 2) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_15_MIN;
        } else if (position == 3) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_30_MIN;
        } else if (position == 4) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_60_MIN;
        } else if (position == 5) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_1_DAY;
        } else if (position == 6) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_1_WEEK;
        } else if (position == 7) {
            mTimeInterval = Constant.K_LINE_TIME_INTERVAL_1_MON;
        }
        if (mKLineLayout != null) {
            mKLineLayout.reInit();
            fetchKLIne();
        }
    }

    @Override
    public void onTabReselect(int position) {

    }


    private void getKLineData(List<List<BigDecimal>> kLineData) {
//        if (mKLineLayout.isRightRefreshing()) {
//            mKLineLayout.stopRightRefresh();
//            clearKLineData();
//        }
        List<KLineChartTick> kLineChartTickList = new ArrayList<>();
        if (kLineData != null && kLineData.size() > 0) {
            for (int i = 0; i < kLineData.size(); i++) {
                List<BigDecimal> bigDecimalList = kLineData.get(i);
                if (bigDecimalList != null && bigDecimalList.size() == 6) {
                    KLineChartTick kLineChartTick = new KLineChartTick(bigDecimalList.get(1).doubleValue(),
                            bigDecimalList.get(4).doubleValue(), bigDecimalList.get(3).doubleValue(), bigDecimalList.get(2).doubleValue(),
                            bigDecimalList.get(5).doubleValue(), bigDecimalList.get(0).longValue());
                    kLineChartTickList.add(kLineChartTick);
                }
            }
        }
        mKLineEntrySet = SocketDataTool.parseKLineData(kLineChartTickList, mTimeInterval);
        mKLineEntrySet.computeStockIndex();
        mKLineLayout.getKLineView().setEntrySet(loadFirst());
        mKLineLayout.getKLineView().notifyDataSetChanged();
    }

    private void clearKLineData() {
        if (mKLineEntrySet == null) return;
        mKLineEntrySet.clear();
        mKLineEntrySet.computeStockIndex();
        mKLineLayout.getKLineView().setEntrySet(loadFirst());
        mKLineLayout.getKLineView().notifyDataSetChanged();
    }

    private void bindIsCollect(Coin.CoinBean coinBean) {
        if (coinBean != null) {
            mCollectButton.setSelected(coinBean.isCollect());
            if(mCollectButton.isSelected()){
                mCollectButton.setText("已选");
            }else {
                mCollectButton.setText("添加自选");
            }
        }
    }
}
