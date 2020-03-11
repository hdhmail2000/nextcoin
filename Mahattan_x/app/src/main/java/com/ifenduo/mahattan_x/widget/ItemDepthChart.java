package com.ifenduo.mahattan_x.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;

/**
 * Created by ll on 2018/2/27.
 * 深度图 Item
 */

public class ItemDepthChart extends FrameLayout {
    TextView mNoTextView;
    TextView mPriceTextView;
    TextView mNumTextView;
    CustomProgressView mProgressView;

    public ItemDepthChart(Context context) {
        this(context, null);
    }

    public ItemDepthChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemDepthChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
//        setOrientation(HORIZONTAL);
        View root = LayoutInflater.from(getContext()).inflate(R.layout.view_item_depth_chart, this, true);
        mNoTextView = (TextView) root.findViewById(R.id.text_item_depth_chart_no);
        mNumTextView = (TextView) root.findViewById(R.id.text_item_depth_chart_num);
        mPriceTextView = (TextView) root.findViewById(R.id.text_item_depth_chart_price);
        mProgressView = (CustomProgressView) root.findViewById(R.id.progress_view_item_depth_chart);
    }

    public void setNoTextViewTextColor(@ColorRes int color) {
        mNoTextView.setTextColor(getResources().getColor(color));
    }

    public void setPriceTextViewTextColor(@ColorRes int color) {
        mPriceTextView.setTextColor(getResources().getColor(color));
    }

    public void setNumTextViewTextColor(@ColorRes int color) {
        mNumTextView.setTextColor(getResources().getColor(color));
    }

    public void setProgressColor(@ColorRes int color) {
        mProgressView.setProgressColor(color);
    }

    public void setNo(String no) {
        mNoTextView.setText(no);
    }

    public void setNum(String num) {
        mNumTextView.setText(num);
    }

    public void setPrice(String price) {
        mPriceTextView.setText(price);
    }

    public void setProgress(float progress) {
        mProgressView.setProgress(progress);
    }
}
