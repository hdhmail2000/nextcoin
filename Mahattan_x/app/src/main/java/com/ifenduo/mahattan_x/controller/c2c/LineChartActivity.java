package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
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

public class LineChartActivity extends BaseActivity implements OnTabSelectListener, CoinTypeDialog.OnItemCheckChangeListener {

    @BindView(R.id.line_chart_view)
    LineChart mChart;
    @BindView(R.id.tab_layout)
    CommonTabLayout mTabLayout;
    CoinTypeDialog mCoinTypeDialog;
    String mTime = "1w";
    String mCoinName = "";

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_line_chart;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        initView();
        handleIntent();

        Drawable drawable = getResources().getDrawable(R.drawable.ic_triangle_small_white);
        drawable.setBounds(0, -4, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() - 4);
        mNavigationCenterText.setCompoundDrawables(null, null, drawable, null);
        mNavigationCenterText.setMinWidth(DimensionTool.dp2px(this, 20));

        mNavigationCenterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCoinTypeDialog == null) {
                    mCoinTypeDialog = new CoinTypeDialog(LineChartActivity.this);
                    mCoinTypeDialog.setOnItemCheckChangeListener(LineChartActivity.this);
                }
                mCoinTypeDialog.show();
            }
        });

        fetchCoinType();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mCoinName = bundle.getString(Constant.BUNDLE_KEY_COMMON, "");
        }
        changeTitle(mCoinName);
    }

    private void initView() {
        TabEntity tabEntity1 = new TabEntity(0, 0, "本周");
        TabEntity tabEntity2 = new TabEntity(0, 0, "本月");
        TabEntity tabEntity3 = new TabEntity(0, 0, "过去3月");

        ArrayList<CustomTabEntity> tabEntityList = new ArrayList<>();
        tabEntityList.add(tabEntity1);
        tabEntityList.add(tabEntity2);
        tabEntityList.add(tabEntity3);
        mTabLayout.setTabData(tabEntityList);
        mTabLayout.setOnTabSelectListener(this);
        mTabLayout.setCurrentTab(0);

        mChart.animateXY(3000, 3000);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisLineColor(Color.parseColor("#19ffffff"));
        xAxis.setGridColor(Color.parseColor("#19ffffff"));
        xAxis.setGranularity(1f); // one hour

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.SECONDS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawAxisLine(true);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setCenterAxisLabels(false);
        rightAxis.setAxisMinimum(0f);

        rightAxis.setYOffset(-10f);
        rightAxis.setTextSize(10f);
        rightAxis.setAxisLineColor(Color.parseColor("#19ffffff"));
        rightAxis.setGridColor(Color.parseColor("#19ffffff"));
        rightAxis.setTextColor(Color.WHITE);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        mChart.setExtraBottomOffset(9);
    }

    private void initCoinTypeDialog() {
        if (mCoinTypeDialog == null) {
            mCoinTypeDialog = new CoinTypeDialog(LineChartActivity.this);
            mCoinTypeDialog.setOnItemCheckChangeListener(this);
        }
    }

    private void fetchLineData() {
        Api.getInstance().fetchLineChartData(mCoinName, mTime, new Callback<List<LineChartEntity>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<LineChartEntity>> response) {
                LineData lineData = buildLineChartData(response.data);
                mChart.setData(lineData);
                mChart.invalidate();

                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
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
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setColor(Color.WHITE);
            set.setValueTextColor(Color.WHITE);
            set.setLineWidth(1.5f);
            set.setDrawCircles(false);
            set.setDrawValues(true);
            set.setDrawFilled(false);
            set.setHighLightColor(Color.rgb(244, 117, 117));
            set.setDrawCircleHole(false);
            LineData data = new LineData(set);

            return data;
        }
        return null;
    }

    @Override
    public void onTabSelect(int position) {
        if (position == 0) {
            mTime = "1w";
        } else if (position == 1) {
            mTime = "1m";
        } else if (position == 1) {
            mTime = "3m";
        }
        fetchLineData();
    }

    @Override
    public void onTabReselect(int position) {

    }

    private void changeCoinType(C2CCoinType c2CCoinType) {
        changeTitle(c2CCoinType == null ? "" : c2CCoinType.getShort_name());
    }

    private void changeTitle(String title) {
        mCoinName = TextUtils.isEmpty(title) ? "" : title;
        setNavigationCenter(mCoinName);
        fetchLineData();
    }

    @Override
    public void onItemCheckChange(int position, C2CCoinType checkedItem) {
        changeCoinType(checkedItem);
    }
}
