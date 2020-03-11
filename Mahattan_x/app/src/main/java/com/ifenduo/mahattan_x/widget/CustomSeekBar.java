package com.ifenduo.mahattan_x.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.mahattan_x.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2018/2/26.
 */

public class CustomSeekBar extends View {
    int mThumbRadiusOnDragging;
    int mDefaultMarkRadius;

    int mDefaultTrackColor;
    int mTrackColor;
    int mMarkMargin;
    float mTrackSize;

    float mLeft;
    float mRight;
    int mMarkCount;
    float mTrackLength;

    float mDelta;
    float mProgress;
    float mMin;
    float mMax;

    float mThumbCenterX;

    Paint mPaint;
    float dx;
    int mAnimDuration = 200;
    private boolean isThumbOnDragging;
    OnProgressChangedListener mProgressListener;
    float mPreSecValue;
    float mSectionValue;
    Bitmap mThumbBitmap;

    public CustomSeekBar(Context context) {
        this(context, null);
    }

    public CustomSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mThumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_seek_bar_blue_point_);

        mMin = 0;
        mMax = 100;

        mDelta = mMax - mMin;

        mTrackColor = Color.parseColor("#308AF5");
        mDefaultTrackColor = Color.parseColor("#363672");
        mTrackSize = 8;
        mMarkMargin = 12;

        mDefaultMarkRadius = 16;
        mThumbRadiusOnDragging = mThumbBitmap.getHeight() / 2;
        mSectionValue = mDelta / (mMarkCount - 1);

        if (isSeekStepSection) {
            isSeekBySection = false;
            isAutoAdjustSectionMark = false;
        }
        if (isAutoAdjustSectionMark && !true) {
            isAutoAdjustSectionMark = false;
        }
        if (isSeekBySection) {
            mPreSecValue = mMin;
            if (mProgress != mMin) {
                mPreSecValue = mSectionValue;
            }
            isAutoAdjustSectionMark = true;
        }

    }

    public void setProgressChangedListener(OnProgressChangedListener progressListener) {
        this.mProgressListener = progressListener;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        if (mProgressListener != null) {
            mProgressListener.onProgressChanged(this, getProgress(), getProgressFloat());
            mProgressListener.getProgressOnFinally(this, getProgress(), getProgressFloat());
        }
        if (isSeekBySection) {
            triggerSeekBySection = false;
        }
        post(new Runnable() {
            @Override
            public void run() {
                mThumbCenterX = (float) (mLeft + mTrackLength * mProgress * 0.01);
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mThumbRadiusOnDragging * 2 + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(resolveSize(DimensionTool.dp2px(getContext(), 180), widthMeasureSpec), height);

        mLeft = getPaddingLeft() + mThumbRadiusOnDragging;
        mRight = getMeasuredWidth() - getPaddingRight() - mThumbRadiusOnDragging;
        mTrackLength = mRight - mLeft;
//        mProgress = calculateProgress();
        if (mThumbCenterX == 0) {
            mThumbCenterX = mLeft;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMarkCount <= 0) {
            mMarkCount = 5;
        }

        float r = mDefaultMarkRadius;
        //画默认mark
        List<Point> pointList = getDefaultMarkPoint();
        for (Point point : pointList) {
            mPaint.setColor(mThumbCenterX >= point.getX() ? mTrackColor : mDefaultTrackColor);
            canvas.drawCircle(point.getX(), point.getY(), r, mPaint);
        }


        //画默认颜色的轨迹
        mPaint.setColor(mDefaultTrackColor);
        mPaint.setStrokeWidth(mTrackSize);
        float y_ = getPaddingTop() + mThumbRadiusOnDragging;
        int pointCount = pointList.size();

        if (pointCount > 1) {
            for (int i = 0; i < pointCount - 1; i++) {
                Point firstPoint = pointList.get(i);
                Point nextPoint = pointList.get(i + 1);
                canvas.drawLine(firstPoint.getX() + mMarkMargin + mDefaultMarkRadius, y_,
                        nextPoint.getX() - mMarkMargin - mDefaultMarkRadius, y_, mPaint);
            }
        }

        mPaint.setColor(mTrackColor);
        mPaint.setStrokeWidth(mTrackSize);
        if (pointCount > 1) {
            for (int i = 0; i < pointCount - 1; i++) {
                Point firstPoint = pointList.get(i);
                Point nextPoint = pointList.get(i + 1);
                if (mThumbCenterX >= nextPoint.getX()) {
                    canvas.drawLine(firstPoint.getX() + mMarkMargin + mDefaultMarkRadius, y_,
                            nextPoint.getX() - mMarkMargin - mDefaultMarkRadius, y_, mPaint);
                } else {
                    if (mThumbCenterX >= nextPoint.getX() + mMarkMargin + mDefaultMarkRadius) {
                        canvas.drawLine(firstPoint.getX() + mMarkMargin + mDefaultMarkRadius, y_,
                                nextPoint.getX() - mMarkMargin - mDefaultMarkRadius, y_, mPaint);
                        break;
                    } else {
                        if (mThumbCenterX >= nextPoint.getX() - mMarkMargin - mDefaultMarkRadius) {
                            canvas.drawLine(firstPoint.getX() + mMarkMargin + mDefaultMarkRadius, y_,
                                    nextPoint.getX() - mMarkMargin - mDefaultMarkRadius, y_, mPaint);
                            break;
                        }
                        if (mThumbCenterX > firstPoint.getX() - mDefaultMarkRadius - mMarkMargin && mThumbCenterX < firstPoint.getX() + mDefaultMarkRadius + mMarkMargin) {
                            break;
                        }
                        canvas.drawLine(firstPoint.getX() + mMarkMargin + mDefaultMarkRadius, y_,
                                mThumbCenterX, y_, mPaint);
                        break;
                    }
                }
            }
        }

        canvas.drawBitmap(mThumbBitmap, mThumbCenterX - mThumbRadiusOnDragging, getPaddingTop(), mPaint);

//        mPaint.setColor(mDefaultTrackColor);
//        mPaint.setStrokeWidth(mTrackSize);
//        canvas.drawLine(mLeft, y_ + 20, mThumbCenterX, y_ + 20, mPaint);
    }


    boolean triggerSeekBySection, isSeekBySection, isTouchToSeek, isSeekStepSection, isAutoAdjustSectionMark, isTouchToSeekAnimEnd;
    private float mPreThumbCenterX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                getParent().requestDisallowInterceptTouchEvent(true);

                isThumbOnDragging = isThumbTouched(event);
                if (isThumbOnDragging) {
                    if (isSeekBySection && !triggerSeekBySection) {
                        triggerSeekBySection = true;
                    }

                    invalidate();
                } else if (isTouchToSeek && isTrackTouched(event)) {
                    isThumbOnDragging = true;
                    if (isSeekBySection && !triggerSeekBySection) {
                        triggerSeekBySection = true;
                    }

                    if (isSeekStepSection) {
                        mThumbCenterX = mPreThumbCenterX = calThumbCxWhenSeekStepSection(event.getX());
                    } else {
                        mThumbCenterX = event.getX();
                        if (mThumbCenterX < mLeft) {
                            mThumbCenterX = mLeft;
                        }
                        if (mThumbCenterX > mRight) {
                            mThumbCenterX = mRight;
                        }
                    }

                    mProgress = calculateProgress();

                    invalidate();
                }

                dx = mThumbCenterX - event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                if (isThumbOnDragging) {
                    boolean flag = true;

                    if (isSeekStepSection) {
                        float x = calThumbCxWhenSeekStepSection(event.getX());
                        if (x != mPreThumbCenterX) {
                            mThumbCenterX = mPreThumbCenterX = x;
                        } else {
                            flag = false;
                        }
                    } else {
                        mThumbCenterX = event.getX() + dx;
                        if (mThumbCenterX < mLeft) {
                            mThumbCenterX = mLeft;
                        }
                        if (mThumbCenterX > mRight) {
                            mThumbCenterX = mRight;
                        }
                    }

                    if (flag) {
                        mProgress = calculateProgress();
                        invalidate();

                        if (mProgressListener != null) {
                            mProgressListener.onProgressChanged(this, getProgress(), getProgressFloat());
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);

                if (isAutoAdjustSectionMark) {
                    if (isTouchToSeek) {
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isTouchToSeekAnimEnd = false;
                                autoAdjustSection();
                            }
                        }, mAnimDuration);
                    } else {
                        autoAdjustSection();
                    }
                } else if (isThumbOnDragging || isTouchToSeek) {
                    animate()
                            .setDuration(mAnimDuration)
                            .setStartDelay(!isThumbOnDragging && isTouchToSeek ? 300 : 0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    isThumbOnDragging = false;
                                    invalidate();
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    isThumbOnDragging = false;
                                    invalidate();
                                }
                            }).start();
                }

                if (mProgressListener != null) {
                    mProgressListener.onProgressChanged(this, getProgress(), getProgressFloat());
                    mProgressListener.getProgressOnActionUp(this, getProgress(), getProgressFloat());
                }

                break;
        }

        return isThumbOnDragging || isTouchToSeek || super.onTouchEvent(event);
    }

    private boolean isThumbTouched(MotionEvent event) {
        if (!isEnabled())
            return false;
        float distance = mTrackLength / mDelta * (mProgress - mMin);
        float x = mLeft + distance;
        float y = getMeasuredHeight() / 2f;
        return (event.getX() - x) * (event.getX() - x) + (event.getY() - y) * (event.getY() - y)
                <= (mLeft + DimensionTool.dp2px(getContext(), 8)) * (mLeft + DimensionTool.dp2px(getContext(), 8));
    }

    private boolean isTrackTouched(MotionEvent event) {
        return isEnabled() && event.getX() >= getPaddingLeft() && event.getX() <= getMeasuredWidth() - getPaddingRight()
                && event.getY() >= getPaddingTop() && event.getY() <= getMeasuredHeight() - getPaddingBottom();
    }

    private float calThumbCxWhenSeekStepSection(float touchedX) {
        if (touchedX <= mLeft) return mLeft;
        if (touchedX >= mRight) return mRight;

        float sectionOffset = mTrackLength * 1f / (mMarkCount - 1);

        int i;
        float x = 0;
        for (i = 0; i <= mMarkCount - 1; i++) {
            x = i * sectionOffset + mLeft;
            if (x <= touchedX && touchedX - x <= sectionOffset) {
                break;
            }
        }

        if (touchedX - x <= sectionOffset / 2f) {
            return x;
        } else {
            return (i + 1) * sectionOffset + mLeft;
        }
    }

    private void autoAdjustSection() {
        float sectionOffset = mTrackLength * 1f / (mMarkCount - 1);
        int i;
        float x = 0;
        for (i = 0; i <= mMarkCount - 1; i++) {
            x = i * sectionOffset + mLeft;
            if (x <= mThumbCenterX && mThumbCenterX - x <= sectionOffset) {
                break;
            }
        }

        BigDecimal bigDecimal = BigDecimal.valueOf(mThumbCenterX);
        float x_ = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        boolean onSection = x_ == x; // 就在section处，不作valueAnim，优化性能

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnim = null;
        if (!onSection) {
            if (mThumbCenterX - x <= sectionOffset / 2f) {
                valueAnim = ValueAnimator.ofFloat(mThumbCenterX, x);
            } else {
                valueAnim = ValueAnimator.ofFloat(mThumbCenterX, (i + 1) * sectionOffset + mLeft);
            }
            valueAnim.setInterpolator(new LinearInterpolator());
            valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mThumbCenterX = (float) animation.getAnimatedValue();
                    mProgress = calculateProgress();
                    invalidate();

                    if (mProgressListener != null) {
                        mProgressListener.onProgressChanged(CustomSeekBar.this, getProgress(), getProgressFloat());
                    }
                }
            });
        }

        if (!onSection) {
            animatorSet.setDuration(mAnimDuration).playTogether(valueAnim);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgress = calculateProgress();
                isThumbOnDragging = false;
                isTouchToSeekAnimEnd = true;
                invalidate();
                if (mProgressListener != null) {
                    mProgressListener.getProgressOnFinally(CustomSeekBar.this, getProgress(), getProgressFloat());
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mProgress = calculateProgress();
                isThumbOnDragging = false;
                isTouchToSeekAnimEnd = true;
                invalidate();
            }
        });
        animatorSet.start();
    }

    private List<Point> getDefaultMarkPoint() {
        List<Point> pointList = new ArrayList<>();
        float offset = mTrackLength / (mMarkCount - 1);
        float x_;
        float y_ = getPaddingTop() + mThumbRadiusOnDragging;
        for (int i = 0; i < mMarkCount; i++) {
            x_ = mLeft + i * offset;
            pointList.add(new Point(x_, y_));
        }
        return pointList;
    }

    public int getProgress() {
        return Math.round(processProgress());
    }

    public float getProgressFloat() {
        return formatFloat(processProgress());
    }

    private float processProgress() {
        final float progress = mProgress;

        if (isSeekBySection && triggerSeekBySection) {
            float half = mSectionValue / 2;

            if (isTouchToSeek) {
                if (progress == mMin || progress == mMax) {
                    return progress;
                }

                float secValue;
                for (int i = 0; i <= (mMarkCount - 1); i++) {
                    secValue = i * mSectionValue;
                    if (secValue < progress && secValue + mSectionValue >= progress) {
                        if (secValue + half > progress) {
                            return secValue;
                        } else {
                            return secValue + mSectionValue;
                        }
                    }
                }
            }

            if (progress >= mPreSecValue) { // increasing
                if (progress >= mPreSecValue + half) {
                    mPreSecValue += mSectionValue;
                    return mPreSecValue;
                } else {
                    return mPreSecValue;
                }
            } else { // reducing
                if (progress >= mPreSecValue - half) {
                    return mPreSecValue;
                } else {
                    mPreSecValue -= mSectionValue;
                    return mPreSecValue;
                }
            }
        }

        return progress;
    }

    private float calculateProgress() {
        return (mThumbCenterX - mLeft) * mDelta / mTrackLength + mMin;
    }

    private float formatFloat(float value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public interface OnProgressChangedListener {

        void onProgressChanged(CustomSeekBar customSeekBar, int progress, float progressFloat);

        void getProgressOnActionUp(CustomSeekBar customSeekBar, int progress, float progressFloat);

        void getProgressOnFinally(CustomSeekBar customSeekBar, int progress, float progressFloat);
    }
}
