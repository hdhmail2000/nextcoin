package com.ifenduo.mahattan_x.controller.c2c;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragmentAdapter;
import com.ifenduo.mahattan_x.entity.C2CCoinType;
import com.ifenduo.mahattan_x.entity.LineChartEntity;
import com.ifenduo.mahattan_x.entity.TabEntity;
import com.ifenduo.mahattan_x.widget.MyMarkerView;
import com.ifenduo.mahattan_x.widget.dialog.CoinTypeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class C2CActivity extends BaseActivity implements CoinTypeDialog.OnItemCheckChangeListener, OnTabSelectListener {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.chart_tab_layout)
    SegmentTabLayout chartTabLayout;
    @BindView(R.id.line_chart_view)
    LineChart lineChartView;
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.toolbar_container)
    RelativeLayout mToolbarContainer;

    BaseFragmentAdapter mFragmentAdapter;
    C2CBuyFragment mBuyFragment;
    C2CSellFragment mSellFragment;
    CoinTypeDialog mCoinTypeDialog;

    String mCoinName = "";
    String mTime = "1w";

    @Override
    protected boolean toolbarIsEnable() {
        return false;
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbarContainer);


        setupViewPager();
        setupChartTabLayout();
        setupLineChart();

        fetchCoinType();
    }


    private void setupViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        mBuyFragment = C2CBuyFragment.newInstance();
        mSellFragment = C2CSellFragment.newInstance();
        fragmentList.add(mBuyFragment);
        fragmentList.add(mSellFragment);
        titleList.add("我要买入");
        titleList.add("我要卖出");
        mFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupChartTabLayout() {
        String[] titleArr = new String[]{"本周", "本月", "过去3月"};
        chartTabLayout.setTabData(titleArr);
        chartTabLayout.setOnTabSelectListener(this);
        chartTabLayout.setCurrentTab(0);
    }

    private void setupLineChart() {
        lineChartView.animateXY(3000, 3000);
        // no description text
        lineChartView.getDescription().setEnabled(false);
        // enable touch gestures
        lineChartView.setTouchEnabled(true);
        // enable scaling and dragging
        lineChartView.setDragEnabled(true);
        lineChartView.setScaleEnabled(true);
        lineChartView.setDrawGridBackground(false);
        lineChartView.setHighlightPerDragEnabled(false);
        // set an alternative background color
        lineChartView.setBackgroundColor(Color.TRANSPARENT);

        XAxis xAxis = lineChartView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisLineColor(Color.parseColor("#19ffffff"));
        xAxis.setGridColor(Color.parseColor("#19ffffff"));

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("MM-dd");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.SECONDS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = lineChartView.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setCenterAxisLabels(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisMinimum(0f);

        leftAxis.setTextSize(10f);
        leftAxis.setAxisLineColor(Color.parseColor("#19ffffff"));
        leftAxis.setGridColor(Color.parseColor("#19ffffff"));
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = lineChartView.getAxisRight();
        rightAxis.setEnabled(false);
        lineChartView.setExtraBottomOffset(9);
    }

    private void changeTitle(C2CCoinType c2CCoinType) {
        mCoinName = c2CCoinType == null ? "" : c2CCoinType.getShort_name();
        mTitleTextView.setText(mCoinName);
        fetchLineData();
    }

    @Override
    public void onItemCheckChange(int position, C2CCoinType checkedItem) {
        changeCoinType(checkedItem);
    }

    private void fetchLineData() {
        Api.getInstance().fetchLineChartData(mCoinName, mTime, new Callback<List<LineChartEntity>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<LineChartEntity>> response) {
                LineData lineData = buildLineChartData(response.data);
                lineChartView.setData(lineData);
                lineChartView.invalidate();
                lineChartView.animateX(3000);
                Legend l = lineChartView.getLegend();
                l.setForm(Legend.LegendForm.EMPTY);
            }
        });
    }

    private void fetchCoinType() {

        Api.getInstance().fetchC2CCoinTypeList(new Callback<List<C2CCoinType>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<C2CCoinType>> response) {
                if (isSuccess) {
                    if (response.data != null && response.data.size() > 0) {
                        changeCoinType(response.data.get(0));
                    }

                    if (mCoinTypeDialog == null) {
                        initCoinTypeDialog();
                    }
                    mCoinTypeDialog.setData(response.data);
                }
            }
        });
    }

    private LineData buildLineChartData(List<LineChartEntity> entityList) {
        ArrayList<Entry> values = new ArrayList<Entry>();
        if (entityList != null && entityList.size() > 0) {
            for (LineChartEntity chartEntity : entityList) {
                if (chartEntity != null) {
                    values.add(new Entry(chartEntity.getInputtime(), chartEntity.getTitle()));
                }
            }

        }
        if (values.size() > 0) {
            LineDataSet set = new LineDataSet(values, "");
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(Color.WHITE);
            set.setValueTextColor(getResources().getColor(R.color.colorBlueText));
            set.setLineWidth(1.5f);
            set.setDrawCircles(false);
            set.setDrawValues(true);
            set.setDrawFilled(true);
            set.setFillColor(Color.WHITE);
            set.setDrawCircleHole(false);
            set.setFillAlpha(65);
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set.setDrawHighlightIndicators(false);
            LineData data = new LineData(set);

            return data;
        }
        return null;
    }

    @OnClick({R.id.back_text_view, R.id.title_text_view,R.id.release_image_view})
    public void onClick(View view) {
        if (view.getId() == R.id.title_text_view) {
            if (mCoinTypeDialog == null) {
                mCoinTypeDialog = new CoinTypeDialog(C2CActivity.this);
                mCoinTypeDialog.setOnItemCheckChangeListener(C2CActivity.this);
            }
            mCoinTypeDialog.show();
        } else if (view.getId() == R.id.back_text_view) {
            finish();
        }else if(view.getId()==R.id.release_image_view){
            openActivity(C2CActivity.this, C2CReleaseActivity.class, null);
        }

    }

    private void initCoinTypeDialog() {
        if (mCoinTypeDialog == null) {
            mCoinTypeDialog = new CoinTypeDialog(C2CActivity.this);
            mCoinTypeDialog.setOnItemCheckChangeListener(this);
        }
    }

    private void changeCoinType(C2CCoinType c2CCoinType) {
        changeTitle(c2CCoinType);
        if (mSellFragment != null) mSellFragment.setC2CCoinType(c2CCoinType);
        if (mBuyFragment != null) mBuyFragment.setC2CCoinType(c2CCoinType);
    }

    @Override
    public void onTabSelect(int position) {
        if (position == 0) {
            mTime = "1w";
        } else if (position == 1) {
            mTime = "1m";
        } else if (position == 2) {
            mTime = "3m";
        }
        fetchLineData();
    }

    @Override
    public void onTabReselect(int position) {

    }
}
