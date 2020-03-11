package com.ifenduo.mahattan_x.widget.kchartview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ifenduo.mahattan_x.R;
import com.wordplat.ikvstockchart.InteractiveKLineView;
import com.wordplat.ikvstockchart.KLineHandler;
import com.wordplat.ikvstockchart.align.YLabelAlign;
import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.drawing.KLineVolumeDrawing;
import com.wordplat.ikvstockchart.drawing.KLineVolumeHighlightDrawing;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.StockKLineVolumeIndex;
import com.wordplat.ikvstockchart.marker.XAxisTextMarkerView;
import com.wordplat.ikvstockchart.marker.YAxisTextMarkerView;
import com.wordplat.ikvstockchart.render.KLineRender;


public class KLineLayout extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "InteractiveKLineLayout";

    private Context context;

    private InteractiveKLineView kLineView;
    private KLineHandler kLineHandler;
    private KLineRender kLineRender;

    private ImageView mLoadingRightImageView;
    private ImageView mLoadingLeftImageView;

    private int stockMarkerViewHeight;
    private int stockIndexViewHeight;
    private AttributeSet mAttributeSet;
    private int mDefStyleAttr;

    public KLineLayout(Context context) {
        this(context, null);
    }

    public KLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAttributeSet = attrs;
        mDefStyleAttr = defStyleAttr;
        this.context = context;
        stockMarkerViewHeight = context.getResources().getDimensionPixelOffset(com.wordplat.ikvstockchart.R.dimen.stock_marker_view_height);
        stockIndexViewHeight = context.getResources().getDimensionPixelOffset(com.wordplat.ikvstockchart.R.dimen.stock_index_view_height);
        initUI(context, attrs, defStyleAttr);
    }

    private void initUI(Context context, AttributeSet attrs, int defStyleAttr) {
        kLineView = new InteractiveKLineView(context);
        kLineRender = (KLineRender) kLineView.getRender();
        kLineRender.setSizeColor(ViewUtils.getSizeColor(context, attrs, defStyleAttr));
        kLineRender.getSizeColor().setAxisColor(Color.parseColor("#0B0841"));//轴线颜色
        kLineRender.getSizeColor().setGridColor(Color.parseColor("#0B0841"));//网格颜色
        kLineRender.getSizeColor().setXLabelColor(Color.WHITE);
        kLineRender.getSizeColor().setYLabelColor(Color.WHITE);
        kLineRender.getSizeColor().setHighlightColor(Color.WHITE);
        kLineRender.getSizeColor().setMarkerBorderColor(Color.WHITE);
        kLineRender.getSizeColor().setYLabelAlign(YLabelAlign.RIGHT_IN);
        kLineRender.getSizeColor().setIncreasingColor(Color.parseColor("#00BD9A"));
        kLineRender.getSizeColor().setNeutralColor(Color.parseColor("#00BD9A"));
        kLineRender.getSizeColor().setDecreasingColor(Color.parseColor("#FF6960"));
        kLineRender.getSizeColor().setMarkerTextColor(Color.WHITE);
        kLineRender.getSizeColor().setLoadingTextColor(getResources().getColor(R.color.colorAccent));
        kLineRender.getSizeColor().setLoadingTextSize(getResources().getDimension(R.dimen.textSize10));
        kLineView.setEnableLeftRefresh(false);
        kLineView.setEnableRightRefresh(false);
        kLineView.setKLineHandler(new KLineHandler() {
            @Override
            public void onLeftRefresh() {
//                if (kLineHandler != null) {
//                    kLineHandler.onLeftRefresh();
//                }
            }

            @Override
            public void onRightRefresh() {
//                if (kLineHandler != null) {
//                    kLineHandler.onRightRefresh();
//                }
            }

            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {
                if (kLineHandler != null) {
                    kLineHandler.onSingleTap(e, x, y);
                }
                onTabClick(x, y);
            }

            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {
                if (kLineHandler != null) {
                    kLineHandler.onDoubleTap(e, x, y);
                }
            }

            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {
                if (kLineHandler != null) {
                    kLineHandler.onHighlight(entry, entryIndex, x, y);
                }
            }

            @Override
            public void onCancelHighlight() {
                if (kLineHandler != null) {
                    kLineHandler.onCancelHighlight();
                }
            }
        });

        // 成交量
        StockKLineVolumeIndex kLineVolumeIndex = new StockKLineVolumeIndex(stockIndexViewHeight);
        kLineVolumeIndex.addDrawing(new KLineVolumeDrawing());
        kLineVolumeIndex.addDrawing(new KLineVolumeHighlightDrawing());
        kLineRender.addStockIndex(kLineVolumeIndex);

        kLineRender.addMarkerView(new YAxisTextMarkerView(stockMarkerViewHeight));
        kLineRender.addMarkerView(new XAxisTextMarkerView(stockMarkerViewHeight));

        addView(kLineView);
    }

    public void reInit() {
        if (kLineView != null) {
            removeView(kLineView);
        }
        initUI(getContext(), mAttributeSet, mDefStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingLeftImageView = (ImageView) findViewById(R.id.image_loading_quotes_detail_left);
        mLoadingRightImageView = (ImageView) findViewById(R.id.image_loading_quotes_detail_right);
        mLoadingRightImageView.setVisibility(GONE);
        mLoadingLeftImageView.setVisibility(GONE);
    }

    public InteractiveKLineView getKLineView() {
        return kLineView;
    }

    public void setKLineHandler(KLineHandler kLineHandler) {
        this.kLineHandler = kLineHandler;
    }

    private void onTabClick(float x, float y) {
    }

    @Override
    public void onClick(View v) {
        if (kLineHandler != null) {
            kLineHandler.onCancelHighlight();
        }
        kLineView.notifyDataSetChanged();
    }

    public void showLeftRefresh() {
        mLoadingLeftImageView.setVisibility(View.VISIBLE);
        ((Animatable) mLoadingLeftImageView.getDrawable()).start();
    }

    public void showRightRefresh() {
        mLoadingRightImageView.setVisibility(View.VISIBLE);
        ((Animatable) mLoadingRightImageView.getDrawable()).start();
    }

    public void stopLeftRefresh() {
        mLoadingLeftImageView.setVisibility(View.GONE);
        ((Animatable) mLoadingLeftImageView.getDrawable()).stop();
    }

    public void stopRightRefresh() {
        mLoadingRightImageView.setVisibility(View.GONE);
        ((Animatable) mLoadingRightImageView.getDrawable()).stop();
    }

    public boolean isRightRefreshing() {
        return (mLoadingRightImageView.getVisibility() == VISIBLE);
    }

}
