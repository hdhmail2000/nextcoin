package com.ifenduo.mahattan_x.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ll on 2018/2/27.
 */

public class CustomProgressView extends View {

    float mProgress;
    Paint mPaint;
    int mProgressColor;

    public CustomProgressView(Context context) {
        this(context, null);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mProgressColor = Color.parseColor("#FF6960");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(getHeight());
        mPaint.setColor(mProgressColor);

        float stopX=getWidth();
        float startX=((1-mProgress) * getWidth());
        float y=getHeight() / 2;
        canvas.drawLine(startX, y, stopX, y, mPaint);
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setProgressColor(@ColorRes int progressColor) {
        this.mProgressColor = getResources().getColor(progressColor);
        invalidate();
    }
}
