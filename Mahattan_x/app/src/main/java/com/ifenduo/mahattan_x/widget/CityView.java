package com.ifenduo.mahattan_x.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.mahattan_x.R;

public class CityView extends View {
    private int screenWidth;            //屏幕宽度
    private Bitmap backgroundBitmap;    //背景图
    private Paint backgroundPaint;      //画背景的画笔

    public CityView(Context context) {
        this(context, null);
    }

    public CityView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        //获取屏幕宽度
        screenWidth = DimensionTool.getScreenWidth(getContext());

        backgroundBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.image_test_mine_header)).getBitmap();
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        drawBackGround(canvas);
        //
        drawLight(canvas);
    }

    private void drawLight(Canvas canvas){
        int startX=300;
        int startY=400;
        int w = 80;
        int h = 160;

        int w_ = 50;
        int h_ = 120;
        int startX_=300+((w-w_)/2);
        int startY_=startY+h-h_;

        Rect clientRect = new Rect(startX,startY,startX+w,startY+h);
        Paint paint=new Paint();  //定义一个Paint
        Shader mShader = new LinearGradient(startX,startY,startX+w,startY+h,new int[] {Color.TRANSPARENT,Color.parseColor("#4d1eddfe")},null,Shader.TileMode.REPEAT);
        //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。
        // 然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
        paint.setShader(mShader);
        canvas.drawRect(clientRect,paint);

        Rect clientRect_ = new Rect(startX_,startY_,startX_+w_,startY_+h_);
        Paint paint_=new Paint();  //定义一个Paint
        Shader mShader_ = new LinearGradient(startX_,startY_,startX_+w_,startY_+h_,new int[] {Color.TRANSPARENT,Color.parseColor("#4d1eddfe")},null,Shader.TileMode.REPEAT);
        paint_.setShader(mShader_);
        canvas.drawRect(clientRect_,paint_);
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBackGround(Canvas canvas) {
        Rect rect=new Rect(0,0,getWidth(),getHeight());
        Rect rectBitmap=new Rect(0,0,backgroundBitmap.getWidth(),backgroundBitmap.getHeight());
        canvas.drawBitmap(backgroundBitmap, rectBitmap,rect,backgroundPaint);
    }

}
