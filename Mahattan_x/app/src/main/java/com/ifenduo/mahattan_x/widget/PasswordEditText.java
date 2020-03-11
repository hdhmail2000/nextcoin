package com.ifenduo.mahattan_x.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;

import com.ifenduo.mahattan_x.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by Allen on 2017/5/7.
 * 自定义支付密码输入框
 */

public class PasswordEditText extends android.support.v7.widget.AppCompatEditText {

    private Context mContext;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;


    private float cX;


    /**
     * 实心圆的半径
     */
    private int radius = 10;
    /**
     * view的高度
     */
    private int height;
    private int width;

    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    private int bottomLineLength;
    /**
     * 最大输入位数
     */
    private int maxCount = 6;
    /**
     * 圆的颜色   默认BLACK
     */
    private int circleColor = Color.BLACK;
    /**
     * 底部线的颜色   默认GRAY
     */
    private int bottomLineColorNormal = Color.GRAY;
    private int bottomLineColorHasValue = Color.GRAY;

    /**
     * 分割线的颜色
     */
    private int borderColor = Color.GRAY;
    /**
     * 分割线的画笔
     */
    private Paint borderPaint;
    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;
    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = Color.GRAY;
    private int focusedColor = Color.BLUE;
    private RectF rectF = new RectF();
    private RectF focusedRecF = new RectF();
    private int psdType = 0;
    private final static int psdType_weChat = 0;
    private final static int psdType_bottomLine = 1;
    private final static int psdType_grid = 2;

    /**
     * 矩形边框的圆角
     */
    private int rectAngle = 0;
    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
    private Paint circlePaint;
    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;


    /**
     * 光标
     */
    private Paint mCursorPaint;


    /**
     * 当前输入的位置索引
     */
    private int position = 0;

    private OnInputFinishedListener mListener;
    private OnTextChangedListener mTextChangedListener;

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        maxCount = typedArray.getInt(R.styleable.PasswordEditText_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.PasswordEditText_pointColor, circleColor);
        bottomLineColorNormal = typedArray.getColor(R.styleable.PasswordEditText_bottomLineColorNormal, bottomLineColorNormal);
        bottomLineColorHasValue = typedArray.getColor(R.styleable.PasswordEditText_bottomLineColorHasValue, bottomLineColorHasValue);
        radius = typedArray.getDimensionPixelOffset(R.styleable.PasswordEditText_pointRadius, radius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.PasswordEditText_divideLineWidth, divideLineWidth);
        borderColor = divideLineColor = typedArray.getColor(R.styleable.PasswordEditText_divideLineColor, divideLineColor);
        psdType = typedArray.getInt(R.styleable.PasswordEditText_psdType, psdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.PasswordEditText_rectAngle, rectAngle);
        focusedColor = typedArray.getColor(R.styleable.PasswordEditText_focusedColor, focusedColor);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        bottomLinePaint = getPaint(5, Paint.Style.FILL, bottomLineColorNormal);

        borderPaint = getPaint(3, Paint.Style.STROKE, borderColor);

        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);

        mCursorPaint = getPaint(5, Paint.Style.FILL, focusedColor);

    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2;
        startY = h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的画会默认绘制输入的文字
//        super.onDraw(canvas);

        switch (psdType) {
            case psdType_weChat:
                drawWeChatBorder(canvas);
                drawItemFocused(canvas, position);
                break;
            case psdType_bottomLine:
                drawBottomBorder(canvas);
                break;
            case psdType_grid:
                drawGridBorder(canvas);
                break;
        }

        drawPsdCircle(canvas);
    }

    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }
    }

    /**
     * 画网格支付密码的样式
     *
     * @param canvas
     */
    private void drawGridBorder(Canvas canvas) {
        for (int i = 0; i < maxCount; i++) {
            RectF rectF_ = new RectF();
            float left = (height + getGridMargin()) * i;
            float right = left + height;

            rectF_.set(left, 0, right, height);
            canvas.drawRoundRect(rectF_, rectAngle, rectAngle, borderPaint);
        }
    }

    private float getGridMargin() {
        return ((width - height * maxCount) * 1.0f / (maxCount - 1));
    }

    private void drawItemFocused(Canvas canvas, int position) {
        if (position > maxCount - 1) {
            return;
        }
        focusedRecF.set(position * divideLineWStartX, 0, (position) * divideLineWStartX,
                height);
        canvas.drawRoundRect(focusedRecF, rectAngle, rectAngle, getPaint(3, Paint.Style.STROKE, focusedColor));
    }

    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {
        for (int i = 0; i < maxCount; i++) {
            cX = startX + i * 2 * startX;
            if (i < textLength) {
                bottomLinePaint.setColor(bottomLineColorHasValue);
            } else {
                bottomLinePaint.setColor(bottomLineColorNormal);
            }
            canvas.drawLine(cX - bottomLineLength / 2,
                    height,
                    cX + bottomLineLength / 2,
                    height, bottomLinePaint);
        }
    }

    /**
     * 画密码实心圆
     *
     * @param canvas
     */
    private void drawPsdCircle(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            if (psdType == psdType_grid) {
                canvas.drawCircle(height / 2f + (height + getGridMargin()) * i,
                        startY,
                        radius,
                        circlePaint);
            } else {
                canvas.drawCircle(startX + i * 2 * startX,
                        startY,
                        radius,
                        circlePaint);
            }
        }
        int alpha = 0;
        if (isShowCursor) {
            alpha = 255;
        } else {
            alpha = 0;
        }
        mCursorPaint.setAlpha(alpha);
        if (psdType == psdType_grid) {
            canvas.drawLine(height / 2f + (height + getGridMargin()) * textLength, startY - radius * 2,
                    height / 2f + (height + getGridMargin()) * textLength, startY + radius * 2, mCursorPaint);
        } else {
            canvas.drawLine(startX + (textLength) * 2 * startX, startY - radius * 2, startX + (textLength) * 2 * startX, startY + radius * 2, mCursorPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.position = start + lengthAfter;
        textLength = text.toString().length();

        if (mTextChangedListener != null) {
            mTextChangedListener.onTextChanged(getPasswordString());
        }

        if (textLength == maxCount) {
            if (mListener != null) {
                mListener.inputFinished(getPasswordString());
            }
        }
        invalidate();

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getPasswordString() {
        return getText().toString().trim();
    }


    public void setOnInputFinishedListener(OnInputFinishedListener listener) {
        mListener = listener;
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        mTextChangedListener = onTextChangedListener;
    }


    /**
     * 清空密码
     */
    public void cleanPsd() {
        setText("");
    }

    ValueAnimator mCursorAnimator;
    boolean isShowCursor;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        startAnimator();
    }

    void startAnimator() {
        if (mCursorAnimator == null) {
            mCursorAnimator = ValueAnimator.ofInt(0, 2);
            mCursorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    if (value == 0) {
                        isShowCursor = true;
                    } else {
                        isShowCursor = false;
                    }
                    invalidate();
                }
            });
            mCursorAnimator.setDuration(600);
            mCursorAnimator.setRepeatMode(ValueAnimator.REVERSE);
            mCursorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if (!mCursorAnimator.isRunning()) {
            mCursorAnimator.start();
        }
    }


    /**
     * 密码输入完成监听
     */
    public interface OnInputFinishedListener {
        void inputFinished(String inputPsd);
    }

    /**
     * 输入监听
     */
    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }
}
