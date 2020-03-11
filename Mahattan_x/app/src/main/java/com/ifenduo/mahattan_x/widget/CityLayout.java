package com.ifenduo.mahattan_x.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.mahattan_x.R;

import java.util.Random;

public class CityLayout extends FrameLayout {

    private float scale = 1f;
    private OnRectClickListener onRectClickListener;

    //气泡插值器
    private Interpolator lineInterpolator = new LinearInterpolator();//线性
    private Interpolator accelerateInterpolator = new AccelerateInterpolator();//加速
    private Interpolator decelerateInterpolator = new DecelerateInterpolator();//减速
    private Interpolator[] interpolatorArr;

    private Random random = new Random();

    private Rect mBillboardRect;

    //左边广告矿区相关
    private Rect bannerOutLightRect;             //外层光束Rect
    private Rect bannerInnerLightRect;           //内层光束Rect
    private Rect bannerBubbleRect;               //气泡的Rect
    private Drawable[] bannerBubbleDrawableArr;  //气泡图片
    private ImageView bannerImageView;           //旋转的图片
    @ColorInt
    private int bannerStartColor;                //光束底部颜色
    @ColorInt
    private int bannerCenterColor;               //光束中间颜色
    @ColorInt
    private int bannerEndColor;                  //光束顶部颜色
    private Drawable bannerDrawable;             //旋转的图片
    private boolean isOpenBanner = false;         //是否开启广告矿区

    //IPO区相关
    private Rect ipoOutLightRect;             //外层光束Rect
    private Rect ipoInnerLightRect;           //内层光束Rect
    private Rect ipoBubbleRect;               //气泡的Rect
    private Drawable[] ipoBubbleDrawableArr;  //气泡图片
    private ImageView ipoImageView;           //旋转的图片
    @ColorInt
    private int ipoStartColor;                //光束底部颜色
    @ColorInt
    private int ipoCenterColor;               //光束中间颜色
    @ColorInt
    private int ipoEndColor;                  //光束顶部颜色
    private Drawable ipoDrawable;             //旋转的图片
    private boolean isOpenIpo = false;         //是否开启广告矿区

    //右边广告矿区相关
    @ColorInt
    private int bannerStartColor_;               //光束底部颜色
    @ColorInt
    private int bannerCenterColor_;              //光束中间颜色
    @ColorInt
    private int bannerEndColor_;                 //光束顶部颜色
    private Drawable bannerDrawable_;            //旋转的图片
    private Drawable[] bannerBubbleDrawableArr_; //气泡图片
    private Rect bannerOutLightRect_;            //外层光束Rect
    private Rect bannerInnerLightRect_;          //内层光束Rect
    private ImageView bannerImageView_;          //旋转的图片
    private boolean isOpenBanner_ = false;         //是否开启广告矿区

    //M币相关
    private Rect mOutLightRect;                  //外层光束Rect
    private Rect mInnerLightRect;                //内层光束Rect
    private ImageView mCoinTopImageView;         //上面的金币View
    private ImageView mCoinBottomImageView;      //下面的金币View
    private boolean isOpenMCoin = false;          //是否开启M币
    private Drawable mCoinTopDrawable;           //上面的金币
    private Drawable mCoinBottomDrawable;        //下面的金币
    private Drawable[] mCoinBubbleDrawableArr;   //气泡图片
    @ColorInt
    private int mCoinStartColor;                 //光束底部颜色
    @ColorInt
    private int mCoinCenterColor;                //光束中间颜色
    @ColorInt
    private int mCoinEndColor;                   //光束顶部颜色

    //USDT相关
    private boolean isOpenUSDT = false;
    private ImageView usdtImageView;             //usdt图片
    private Drawable[] usdtDrawableArr;
    @ColorInt
    private int usdtStartColor;
    @ColorInt
    private int usdtCenterColor;
    @ColorInt
    private int usdtEndColor;
    private Drawable usdtDrawable;
    private Rect usdtInnerRect;                  //
    private Rect usdtOutRect;                    //

    //candy矿池
    @ColorInt
    private int candyStartColor;                //光束底部颜色
    @ColorInt
    private int candyCenterColor;               //光束中间颜色
    @ColorInt
    private int candyEndColor;                  //光束顶部颜色
    private Rect candyOutLightRect;             //外层光束Rect
    private Rect candyInnerLightRect;           //内层光束Rect
    private ImageView candyImageView;           //旋转的ImageView
    private boolean isOpenCandy = false;         //是否开启candy
    private Drawable candyDrawable;             //旋转的图片
    private Drawable[] candyBubbleDrawableArr;  //气泡图片


    //Mht矿池
    @ColorInt
    private int mhtStartColor;                  //光束底部颜色
    @ColorInt
    private int mhtCenterColor;                 //光束中间颜色
    @ColorInt
    private int mhtEndColor;                    //光束顶部颜色
    private Rect mhtOutLightRect;               //外层光束Rect
    private Rect mhtInnerLightRect;             //内层光束Rect
    private ImageView mhtImageView;             //旋转的ImageView
    private boolean isOpenMht = false;           //是否开启mht
    private Drawable mhtDrawable;               //旋转的图片
    private Drawable[] mhtBubbleDrawableArr;    //气泡图片

    //
    @ColorInt
    private int otherStartColor;                  //光束底部颜色
    @ColorInt
    private int otherCenterColor;                 //光束中间颜色
    @ColorInt
    private int otherEndColor;                    //光束顶部颜色
    private Rect otherOutLightRect;               //外层光束Rect
    private Rect otherInnerLightRect;             //内层光束Rect
    private ImageView otherImageView;             //旋转的ImageView
    private boolean isOpenOther = false;           //是否开启other
    private Drawable otherDrawable;               //旋转的图片

    public CityLayout(Context context) {
        this(context, null);
    }

    public CityLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }


    private void initialize() {
        calculateScale();

        // 初始化插补器
        interpolatorArr = new Interpolator[3];
        interpolatorArr[0] = lineInterpolator;
        interpolatorArr[1] = accelerateInterpolator;
        interpolatorArr[2] = decelerateInterpolator;

        initUSDT();      //初始化USDT矿池相关
        initBanner();    //初始化左边banner矿池相关
        initBanner_();   //初始化右边banner矿池相关
        initMCoin();     //初始化M币相关
        initCandy();
        initMHT();
        initOther();
        initIpo();

        mBillboardRect = new Rect((int) (191 * scale), (int) (280 * scale), (int) (246 * scale), (int) (354 * scale));

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setupBannerBubble();
                            setupBannerBubble_();
                            setupUSDTBubble();
                            setupMBubble();
                            setupCandyBubble();
                            setupIpoBubble();
                            setupMHTBubble();
                            if (isOpenBanner) {
                                setupBannerImageView();
                            }
                            if (isOpenBanner_) {
                                setupBannerImageView_();
                            }

                            if (isOpenUSDT) {
                                setupUSDTImage();
                            }

                            if (isOpenMCoin) {
                                setupMCoinAnim();
                            }

                            if (isOpenCandy) {
                                setupCandyImageView();
                            }

                            if (isOpenMht) {
                                setupMHTImageView();
                            }
                            if (isOpenOther) {
                                setupOtherImage();
                            }
                            if (isOpenIpo) {
                                setupIpoImageView();
                            }
                        }
                    }, 500);
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }.start();

    }

    public void setOnRectClickListener(OnRectClickListener onRectClickListener) {
        this.onRectClickListener = onRectClickListener;
    }

    private void calculateScale() {
        int screenWidth = DimensionTool.getScreenWidth(getContext());
        scale = screenWidth * 1.0f / 375f;
    }

    /***********************************************USDT矿池***********************************************/
    /**
     * 初始化USDT相关
     */
    private void initUSDT() {
        usdtStartColor = Color.TRANSPARENT;
        usdtCenterColor = Color.parseColor("#1afff30f");
        usdtEndColor = Color.parseColor("#66fff30f");
        usdtDrawable = getResources().getDrawable(R.drawable.ic_dollar);
        usdtInnerRect = new Rect((int) (122 * scale), (int) (132 * scale), (int) (154 * scale), (int) (202 * scale));
        usdtOutRect = new Rect((int) (112 * scale), (int) (132 * scale), (int) (163 * scale), (int) (201 * scale));
        usdtDrawableArr = new Drawable[3];
        usdtDrawableArr[0] = getResources().getDrawable(R.drawable.ic_point_yellow_1);
        usdtDrawableArr[1] = getResources().getDrawable(R.drawable.ic_point_yellow_2);
        usdtDrawableArr[2] = getResources().getDrawable(R.drawable.ic_point_yellow_3);
    }

    /**
     * 画USDT光束
     *
     * @param canvas
     */
    private void drawUSDTLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 112, 132, 163, 132, 112, 196, 163, 201, usdtStartColor, usdtCenterColor, usdtEndColor);
        //内光束
        drawLight(canvas, 122, 132, 154, 132, 122, 196, 154, 202, usdtStartColor, usdtCenterColor, usdtEndColor);
    }

    /**
     * 设置USDT图片动画
     */
    private void setupUSDTImage() {
        if (usdtImageView != null) return;
        usdtImageView = new ImageView(getContext());
        int width = usdtDrawable.getIntrinsicWidth();
        int height = usdtDrawable.getIntrinsicHeight();
        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.setMargins((int) (122 * scale + (154 * scale - 122 * scale - width) / 2), (int) ((202 * scale - 196 * scale) / 2 + 196 * scale - height + 4 * scale), 0, 0);
        usdtImageView.setLayoutParams(layoutParams);
        usdtImageView.setImageDrawable(usdtDrawable);
        addView(usdtImageView);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(usdtImageView, View.ROTATION_Y, 0f, 45);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setDuration(3000);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.setInterpolator(new LinearInterpolator());

        ObjectAnimator translation = ObjectAnimator.ofFloat(usdtImageView, View.TRANSLATION_Y, 0, -10, 0);
        translation.setRepeatCount(ObjectAnimator.INFINITE);
        translation.setDuration(1000);
        translation.setRepeatMode(ObjectAnimator.REVERSE);
        translation.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotation, translation);
        animatorSet.start();
    }

    /**
     * 设置USDT矿区气泡
     */
    private void setupUSDTBubble() {
        addBubble(usdtInnerRect, usdtDrawableArr);
    }


    /***********************************************左边的banner矿池***********************************************/
    /**
     * 初始化左边banner矿池相关
     */
    private void initBanner() {
        //初始化光束颜色
        bannerEndColor = Color.parseColor("#991eddfe");
        bannerCenterColor = Color.parseColor("#1a1eddfe");
        bannerStartColor = Color.TRANSPARENT;
        //初始化气泡
        bannerBubbleDrawableArr = new Drawable[3];
        bannerBubbleDrawableArr[0] = getResources().getDrawable(R.drawable.ic_point_blue_1);
        bannerBubbleDrawableArr[1] = getResources().getDrawable(R.drawable.ic_point_blue_2);
        bannerBubbleDrawableArr[2] = getResources().getDrawable(R.drawable.ic_point_blue_3);
        //初始化旋转的图片
        bannerDrawable = getResources().getDrawable(R.drawable.ic_coin_banner);
        bannerOutLightRect = new Rect((int) (171 * scale), (int) (100 * scale), (int) (214 * scale), (int) (160 * scale));
        bannerInnerLightRect = new Rect((int) (180 * scale), (int) (116 * scale), (int) (205 * scale), (int) (160 * scale));
    }

    /**
     * 画左边的banner矿池光束
     *
     * @param canvas
     */
    private void drawBannerLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 171, 100, 214, 100, 171, 160, 214, 160, bannerStartColor, bannerCenterColor, bannerEndColor);
        //内光束
        drawLight(canvas, 180, 116, 205, 116, 180, 160, 205, 160, bannerStartColor, bannerCenterColor, bannerEndColor);
    }

    /**
     * 添加左边广告矿区旋转的ImageView
     */
    private void setupBannerImageView() {
        if (bannerInnerLightRect == null || bannerImageView != null) return;
        bannerImageView = addRotationImage(bannerInnerLightRect.left, bannerInnerLightRect.right, bannerInnerLightRect.bottom, bannerDrawable);
        setupRotationImageAnim(bannerImageView);
    }

    /**
     * 设置左边Banner矿池气泡
     */
    private void setupBannerBubble() {
        addBubble(bannerInnerLightRect, bannerBubbleDrawableArr);
    }

    /***********************************************右边的banner矿池***********************************************/
    /**
     * 初始化右边的banner矿池相关
     */
    private void initBanner_() {
        //初始化光束颜色
        bannerEndColor_ = Color.parseColor("#661eddfe");
        bannerCenterColor_ = Color.parseColor("#1a1eddfe");
        bannerStartColor_ = Color.TRANSPARENT;
        //初始化气泡
        bannerBubbleDrawableArr_ = new Drawable[3];
        bannerBubbleDrawableArr_[0] = getResources().getDrawable(R.drawable.ic_point_blue_1);
        bannerBubbleDrawableArr_[1] = getResources().getDrawable(R.drawable.ic_point_blue_2);
        bannerBubbleDrawableArr_[2] = getResources().getDrawable(R.drawable.ic_point_blue_3);
        //初始化旋转的图片
        bannerDrawable_ = getResources().getDrawable(R.drawable.ic_coin_banner_);
        bannerOutLightRect_ = new Rect((int) (290 * scale), (int) (139 * scale), (int) (323 * scale), (int) (190 * scale));
        bannerInnerLightRect_ = new Rect((int) (296 * scale), (int) (139 * scale), (int) (317 * scale), (int) (190 * scale));
    }

    /**
     * 画右边的banner矿池光束
     *
     * @param canvas
     */
    private void drawBannerLight_(Canvas canvas) {
        //外光束
        drawLight(canvas, 290, 139, 323, 139, 290, 190, 323, 190, bannerStartColor_, bannerCenterColor_, bannerEndColor_);
        //内光束
        drawLight(canvas, 296, 139, 317, 139, 296, 190, 317, 190, bannerStartColor_, bannerCenterColor_, bannerEndColor_);
    }

    /**
     * 添加右边广告矿区旋转的ImageView
     */
    private void setupBannerImageView_() {
        if (bannerInnerLightRect_ == null || bannerImageView_ != null) return;
        bannerImageView_ = addRotationImage(bannerInnerLightRect_.left, bannerInnerLightRect_.right, bannerInnerLightRect_.bottom, bannerDrawable_);
        setupRotationImageAnim(bannerImageView_);
    }

    /**
     * 设置右边Banner矿池气泡
     */
    private void setupBannerBubble_() {
        addBubble(bannerInnerLightRect_, bannerBubbleDrawableArr_);
    }

    /***********************************************M币相关***********************************************/
    /**
     * 初始化M币相关
     */
    private void initMCoin() {
        //初始化光束颜色
        mCoinEndColor = Color.parseColor("#661eddfe");
        mCoinCenterColor = Color.parseColor("#1a1eddfe");
        mCoinStartColor = Color.TRANSPARENT;

        //初始化金币图片
        mCoinTopDrawable = getResources().getDrawable(R.drawable.ic_gold_2);
        //初始化气泡
        mCoinBubbleDrawableArr = new Drawable[3];
        mCoinBubbleDrawableArr[0] = getResources().getDrawable(R.drawable.ic_point_blue_1);
        mCoinBubbleDrawableArr[1] = getResources().getDrawable(R.drawable.ic_point_blue_2);
        mCoinBubbleDrawableArr[2] = getResources().getDrawable(R.drawable.ic_point_blue_3);
        mCoinBottomDrawable = getResources().getDrawable(R.drawable.ic_gold_1);
        mInnerLightRect = new Rect((int) (265 * scale), (int) (208 * scale), (int) (276 * scale), (int) (249 * scale));
        mOutLightRect = new Rect((int) (257 * scale), (int) (208 * scale), (int) (283 * scale), (int) (249 * scale));
    }


    /**
     * 画的M币矿池光束
     *
     * @param canvas
     */
    private void drawMLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 257, 208, 283, 208, 264, 249, 276, 249, mCoinStartColor, mCoinCenterColor, mCoinEndColor);
        //内光束
        drawLight(canvas, 265, 208, 276, 208, 265, 249, 276, 249, mCoinStartColor, mCoinCenterColor, mCoinEndColor);
    }

    /**
     * 设置M币气泡
     */
    private void setupMBubble() {
        addBubble(mInnerLightRect, mCoinBubbleDrawableArr);
    }

    /**
     * 设置M币动画
     */
    private void setupMCoinAnim() {
        if (mInnerLightRect == null || mCoinBottomImageView != null || mCoinTopImageView != null)
            return;
        mCoinTopImageView = new ImageView(getContext());
        mCoinBottomImageView = new ImageView(getContext());
        int width = mCoinTopDrawable.getIntrinsicWidth();
        int height = mCoinTopDrawable.getIntrinsicHeight();
        int width_ = mCoinBottomDrawable.getIntrinsicWidth();
        int height_ = mCoinBottomDrawable.getIntrinsicHeight();

        int marginLeft = mInnerLightRect.left + (mInnerLightRect.right - mInnerLightRect.left - width) / 2;
        int marginTop = mInnerLightRect.top + (mInnerLightRect.bottom - mInnerLightRect.top - height - height_) / 2;

        int marginLeft_ = mInnerLightRect.left + (mInnerLightRect.right - mInnerLightRect.left - width_) / 2;
        int marginTop_ = marginTop + height;

        LayoutParams topLayoutParams = new LayoutParams(width, height);
        topLayoutParams.setMargins(marginLeft, marginTop + 8, 0, 0);
        mCoinTopImageView.setLayoutParams(topLayoutParams);
        mCoinTopImageView.setImageDrawable(mCoinTopDrawable);
        addView(mCoinTopImageView);

        LayoutParams bottomLayoutParams = new LayoutParams(width_, height_);
        bottomLayoutParams.setMargins(marginLeft_, marginTop_ - 8, 0, 0);
        mCoinBottomImageView.setLayoutParams(bottomLayoutParams);
        mCoinBottomImageView.setImageDrawable(mCoinBottomDrawable);
        addView(mCoinBottomImageView);

        ObjectAnimator translation = ObjectAnimator.ofFloat(mCoinTopImageView, View.TRANSLATION_Y, 0, 8);
        translation.setDuration(800);
        translation.setInterpolator(new AccelerateDecelerateInterpolator());
        translation.setRepeatCount(ObjectAnimator.INFINITE);
        translation.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(mCoinTopImageView, View.ROTATION, 0f, 30);
        rotation.setDuration(1200);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translation, rotation);
        animatorSet.start();

        ObjectAnimator translation_ = ObjectAnimator.ofFloat(mCoinBottomImageView, View.TRANSLATION_Y, 0, 8);
        translation_.setDuration(800);
        translation_.setInterpolator(new AccelerateDecelerateInterpolator());
        translation_.setRepeatCount(ObjectAnimator.INFINITE);
        translation_.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator rotation_ = ObjectAnimator.ofFloat(mCoinBottomImageView, View.ROTATION, 0f, -20);
        rotation_.setDuration(1200);
        rotation_.setRepeatCount(ObjectAnimator.INFINITE);
        rotation_.setRepeatMode(ObjectAnimator.REVERSE);
        rotation_.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet_ = new AnimatorSet();
        animatorSet_.playTogether(translation_, rotation_);
        animatorSet_.start();

    }

    /***********************************************Candy矿池相关***********************************************/
    /**
     * 初始化Candy矿池
     */
    private void initCandy() {
        candyStartColor = Color.TRANSPARENT;
        candyCenterColor = Color.parseColor("#1ac878b3");
        candyEndColor = Color.parseColor("#66c878b3");

        //初始化气泡
        candyBubbleDrawableArr = new Drawable[3];
        candyBubbleDrawableArr[0] = getResources().getDrawable(R.drawable.ic_point_red_1);
        candyBubbleDrawableArr[1] = getResources().getDrawable(R.drawable.ic_point_red_2);
        candyBubbleDrawableArr[2] = getResources().getDrawable(R.drawable.ic_point_red_3);

        //初始化旋转的图片
        candyDrawable = getResources().getDrawable(R.drawable.ic_coin_candy);
        candyInnerLightRect = new Rect((int) (332 * scale), (int) (37 * scale), (int) (353 * scale), (int) (89 * scale));
        candyOutLightRect = new Rect((int) (328 * scale), (int) (47 * scale), (int) (358 * scale), (int) (90 * scale));
    }

    /**
     * 画的Candy矿池光束
     *
     * @param canvas
     */
    private void drawCandyLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 328, 47, 358, 47, 328, 85, 358, 90, candyStartColor, candyCenterColor, candyEndColor);
        //内光束
        drawLight(canvas, 332, 37, 353, 37, 332, 85, 353, 89, candyStartColor, candyCenterColor, candyEndColor);
    }

    /**
     * 添加右边广告矿区旋转的ImageView
     */
    private void setupCandyImageView() {
        if (candyInnerLightRect == null || candyImageView != null) return;
        candyImageView = addRotationImage(candyInnerLightRect.left, candyInnerLightRect.right, candyInnerLightRect.bottom, candyDrawable);
        setupRotationImageAnim(candyImageView);
    }

    /**
     * 设置右边Banner矿池气泡
     */
    private void setupCandyBubble() {
        addBubble(candyInnerLightRect, candyBubbleDrawableArr);
    }

    /***********************************************MHT矿池相关***********************************************/
    /**
     * 初始化MHT矿区
     */
    private void initMHT() {
        mhtStartColor = Color.TRANSPARENT;
        mhtCenterColor = Color.parseColor("#1aff89e0");
        mhtEndColor = Color.parseColor("#66ff89e0");

        //初始化气泡
        mhtBubbleDrawableArr = new Drawable[3];
        mhtBubbleDrawableArr[0] = getResources().getDrawable(R.drawable.ic_point_mht_1);
        mhtBubbleDrawableArr[1] = getResources().getDrawable(R.drawable.ic_point_mht_2);
        mhtBubbleDrawableArr[2] = getResources().getDrawable(R.drawable.ic_point_mht_3);

        //初始化旋转的图片
        mhtDrawable = getResources().getDrawable(R.drawable.ic_coin_mht);
        mhtInnerLightRect = new Rect((int) (141 * scale), (int) (274 * scale), (int) (164 * scale), (int) (321 * scale));
        mhtOutLightRect = new Rect((int) (138 * scale), (int) (274 * scale), (int) (166 * scale), (int) (321 * scale));
    }

    /**
     * 画的MHT矿池光束
     *
     * @param canvas
     */
    private void drawMHTLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 138, 274, 166, 274, 138, 321, 166, 321, mhtStartColor, mhtCenterColor, mhtEndColor);
        //内光束
        drawLight(canvas, 141, 274, 164, 274, 141, 321, 164, 321, mhtStartColor, mhtCenterColor, mhtEndColor);
    }

    /**
     * 添加右边广告矿区旋转的ImageView
     */
    private void setupMHTImageView() {
        if (mhtInnerLightRect == null || mhtImageView != null) return;
        mhtImageView = addRotationImage(mhtInnerLightRect.left, mhtInnerLightRect.right, mhtInnerLightRect.bottom, mhtDrawable);
        setupRotationImageAnim(mhtImageView);
    }

    /**
     * 设置右边Banner矿池气泡
     */
    private void setupMHTBubble() {
        addBubble(mhtInnerLightRect, mhtBubbleDrawableArr);
    }


    /***********************************************otherT矿池***********************************************/
    /**
     * 初始化other相关
     */
    private void initOther() {
        otherEndColor = Color.parseColor("#991eddfe");
        otherCenterColor = Color.parseColor("#1a1eddfe");
        otherStartColor = Color.TRANSPARENT;
        otherDrawable = getResources().getDrawable(R.drawable.image_mine_man);
        otherInnerLightRect = new Rect((int) (53 * scale), (int) (190 * scale), (int) (74 * scale), (int) (237 * scale));
        otherOutLightRect = new Rect((int) (50 * scale), (int) (190 * scale), (int) (77 * scale), (int) (237 * scale));
    }

    /**
     * 画USDT光束
     *
     * @param canvas
     */
    private void drawOtherLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 50, 190, 77, 190, 50, 237, 77, 237, otherStartColor, otherCenterColor, otherEndColor);
        //内光束
        drawLight(canvas, 53, 190, 74, 190, 53, 237, 74, 237, otherStartColor, otherCenterColor, otherEndColor);
    }

    /**
     * 设置Other图片动画
     */
    private void setupOtherImage() {
        if (otherInnerLightRect == null || otherImageView != null) return;
        otherImageView = addRotationImage(otherInnerLightRect.left, otherInnerLightRect.right, otherInnerLightRect.bottom, otherDrawable);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(otherImageView, View.ROTATION_Y, 0f, 30);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setDuration(500);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.setInterpolator(new LinearInterpolator());


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotation);
        animatorSet.start();
    }

    /***********************************************IPO矿池***********************************************/
    /**
     * 初始化左边banner矿池相关
     */
    private void initIpo() {
        //初始化光束颜色
        ipoEndColor = Color.parseColor("#991eddfe");
        ipoCenterColor = Color.parseColor("#1a1eddfe");
        ipoStartColor = Color.TRANSPARENT;
        //初始化气泡
        ipoBubbleDrawableArr = new Drawable[3];
        ipoBubbleDrawableArr[0] = getResources().getDrawable(R.drawable.ic_point_blue_1);
        ipoBubbleDrawableArr[1] = getResources().getDrawable(R.drawable.ic_point_blue_2);
        ipoBubbleDrawableArr[2] = getResources().getDrawable(R.drawable.ic_point_blue_3);
        //初始化旋转的图片
        ipoDrawable = getResources().getDrawable(R.drawable.ic_ipo);
        ipoOutLightRect = new Rect((int) (260 * scale), (int) (16 * scale), (int) (306 * scale), (int) (76 * scale));
        ipoInnerLightRect = new Rect((int) (266 * scale), (int) (16 * scale), (int) (300 * scale), (int) (76 * scale));
    }

    /**
     * 画左边的ipo矿池光束
     *
     * @param canvas
     */
    private void drawIpoLight(Canvas canvas) {
        //外光束
        drawLight(canvas, 260, 16, 306, 16, 260, 76, 306, 76, ipoStartColor, ipoCenterColor, ipoEndColor);
        //内光束
        drawLight(canvas, 266, 16, 300, 16, 266, 76, 300, 76, ipoStartColor, ipoCenterColor, ipoEndColor);
    }

    /**
     * 添加左边广告矿区旋转的ImageView
     */
    private void setupIpoImageView() {
        if (ipoInnerLightRect == null || ipoImageView != null) return;
//        ipoImageView = addRotationImage(ipoInnerLightRect.left, ipoInnerLightRect.right, ipoInnerLightRect.bottom, ipoDrawable);
//        setupRotationImageAnim(ipoImageView);

        ipoImageView = new ImageView(getContext());
        int width = usdtDrawable.getIntrinsicWidth();
        int height = usdtDrawable.getIntrinsicHeight();
        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.setMargins((int) (ipoInnerLightRect.left + (ipoInnerLightRect.width() - width) / 2), (int) ((ipoInnerLightRect.height()) / 2 + ipoInnerLightRect.top - height / 2), 0, 0);
        ipoImageView.setLayoutParams(layoutParams);
        ipoImageView.setImageDrawable(ipoDrawable);
        addView(ipoImageView);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(ipoImageView, View.ROTATION_Y, 0f, 45);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setDuration(3000);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.setInterpolator(new LinearInterpolator());

        ObjectAnimator translation = ObjectAnimator.ofFloat(ipoImageView, View.TRANSLATION_Y, 5, 0, -5);
        translation.setRepeatCount(ObjectAnimator.INFINITE);
        translation.setDuration(1000);
        translation.setRepeatMode(ObjectAnimator.REVERSE);
        translation.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotation, translation);
        animatorSet.start();
    }

    /**
     * 设置Ipo矿池气泡
     */
    private void setupIpoBubble() {
        addBubble(ipoInnerLightRect, ipoBubbleDrawableArr);
    }


    /**
     * 给旋转的图片设置动画
     *
     * @param imageView
     */
    private void setupRotationImageAnim(ImageView imageView) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 270);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setDuration(10000);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator translation = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, 0, -10, 0);
        translation.setRepeatCount(ObjectAnimator.INFINITE);
        translation.setDuration(8000);
        translation.setRepeatMode(ObjectAnimator.REVERSE);
        translation.setInterpolator(new LinearInterpolator());

//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(bannerImageView, View.SCALE_X, 1f,0.9f, 1f);
//        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleX.setDuration(6000);
//        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
//
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(bannerImageView, View.SCALE_Y, 1f,0.9f, 1f);
//        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleY.setDuration(6000);
//        scaleY.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotation, translation);
        animatorSet.start();
    }

    /**
     * 添加旋转的ImageView
     *
     * @param left     内部光束左边位置
     * @param right    内部光束右边位置
     * @param bottom   内部光束底部位置
     * @param drawable
     * @return
     */
    private ImageView addRotationImage(int left, int right, int bottom, Drawable drawable) {
        ImageView imageView = new ImageView(getContext());
        if (drawable != null) {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            int marginLeft = (right - left - width) / 2 + left;
            int marginTop = bottom - height;
            LayoutParams layoutParams = new LayoutParams(width, height);
            layoutParams.setMargins(marginLeft, marginTop, 0, 0);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(drawable);
            addView(imageView);
        }
        return imageView;
    }

    /**
     * 添加气泡
     *
     * @param rect
     * @param drawables
     */
    private void addBubble(Rect rect, Drawable[] drawables) {
        if (rect == null || drawables == null || drawables.length == 0) return;
        ImageView imageView = new ImageView(getContext());
        //随机选一个
        Drawable drawable = drawables[random.nextInt(drawables.length)];

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        imageView.setImageDrawable(drawable);
        LayoutParams layoutParams = new LayoutParams(width, height);

        int minX = rect.left;
        int maxX = rect.right - drawable.getIntrinsicWidth();

        Random random = new Random();
        int left = random.nextInt(maxX) % (maxX - minX + 1) + minX;
        imageView.layout(left, rect.bottom + height, left + width, rect.bottom);
        imageView.setLayoutParams(layoutParams);
        addView(imageView, getChildCount());
        Animator set = getAnimator(imageView, rect);
        set.addListener(new AnimEndListener(imageView));
        set.start();
    }

    private Animator getAnimator(View target, Rect rect) {
        AnimatorSet set = getEnterAnimator(target);
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target, rect);
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playTogether(set, bezierValueAnimator);
        finalSet.setInterpolator(interpolatorArr[random.nextInt(interpolatorArr.length)]);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimator(final View target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.98f, 0.96f, 0.94f, 0.92f, 0f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(3000);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha);
        enter.setTarget(target);
        return enter;
    }

    /**
     * 气泡贝塞尔动画
     *
     * @param target
     * @param rect
     * @return
     */
    private ValueAnimator getBezierValueAnimator(View target, Rect rect) {
        int minX = rect.left + target.getLayoutParams().width;
        int maxX = rect.right - target.getLayoutParams().width;
        Random random = new Random();
        int endLeft = random.nextInt(maxX) % (maxX - minX + 1) + minX;

        //初始化一个贝塞尔计算器- - 传入
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2, rect), getPointF(1, rect));

        //这里最好画个图 理解一下 传入了起点 和 终点
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(target.getLeft(), rect.bottom - target.getLayoutParams().height),
                new PointF(endLeft, rect.top));
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawUSDTLight(canvas);

        drawBannerLight(canvas);

        drawBannerLight_(canvas);

        drawCandyLight(canvas);

        drawMHTLight(canvas);

        drawMLight(canvas);

        drawOtherLight(canvas);

        drawIpoLight(canvas);
    }


    private void drawLight(Canvas canvas, int topLeftX, int topLeftY, int topRightX, int topRightY,
                           int bottomLeftX, int bottomLeftY, int bottomRightX, int bottomRightY,
                           @ColorInt int sartColor, @ColorInt int centerColor, @ColorInt int endColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Shader mShader = new LinearGradient(0, topLeftY < topRightY ? topLeftY * scale : topRightY * scale,
                0, bottomLeftY < bottomRightY ? bottomLeftY * scale : bottomRightY * scale,
                new int[]{sartColor, centerColor, endColor},
                new float[]{0f, 0.2f, 1f}, Shader.TileMode.CLAMP);
        paint.setShader(mShader);

        Path path = new Path();
        path.moveTo(topLeftX * scale, topLeftY * scale);
        path.lineTo(topRightX * scale, topRightY * scale);
        path.lineTo(bottomRightX * scale, bottomRightY * scale);
        path.lineTo(bottomLeftX * scale, bottomLeftY * scale);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 获取中间的两个 点
     *
     * @param scale
     */
    private PointF getPointF(int scale, Rect rect) {
        int minX = rect.left;
        int minY = rect.top;
        int maxX = rect.right;
        int maxY = rect.bottom;

        if (scale == 1) {
            maxY = minY + (maxY - minY) / 2 + 1;
        } else {
            minY = maxY - (maxY - minY) / 2;
        }

        PointF pointF = new PointF();
        pointF.x = random.nextInt(maxX) % (maxX - minX + 1) + minX;//减去100 是为了控制 x轴活动范围,看效果 随意~~
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = random.nextInt(maxY) % (maxY - minY + 1) + minY;
        return pointF;
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
        }
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // 这里顺便做一个alpha动画
//            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (onRectClickListener != null) {
                if (candyOutLightRect != null && x >= candyOutLightRect.left && x <= candyOutLightRect.right
                        && y >= candyOutLightRect.top && y <= candyOutLightRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_CANDY);
                    return true;
                } else if (bannerOutLightRect != null && x >= bannerOutLightRect.left && x <= bannerOutLightRect.right
                        && y >= bannerOutLightRect.top && y <= bannerOutLightRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_BANNER_LEFT);
                    return true;
                } else if (bannerOutLightRect_ != null && x >= bannerOutLightRect_.left && x <= bannerOutLightRect_.right
                        && y >= bannerOutLightRect_.top && y <= bannerOutLightRect_.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_BANNER_RIGHT);
                    return true;
                } else if ( usdtOutRect != null && x >= usdtOutRect.left && x <= usdtOutRect.right
                        && y >= usdtOutRect.top && y <= usdtOutRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_USDT);
                    return true;
                } else if (mOutLightRect != null && x >= mOutLightRect.left && x <= mOutLightRect.right
                        && y >= mOutLightRect.top && y <= mOutLightRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_M);
                    return true;
                } else if (mhtOutLightRect != null && x >= mhtOutLightRect.left && x <= mhtOutLightRect.right
                        && y >= mhtOutLightRect.top && y <= mhtOutLightRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_MHT);
                    return true;
                } else if (mBillboardRect != null && x >= mBillboardRect.left && x <= mBillboardRect.right
                        && y >= mBillboardRect.top && y <= mBillboardRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_BILLBOARD);
                    return true;
                } else if ( ipoOutLightRect != null && x >= ipoOutLightRect.left && x <= ipoOutLightRect.right
                        && y >= ipoOutLightRect.top && y <= ipoOutLightRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_IPO);
                    return true;
                } else if ( otherOutLightRect != null && x >= otherOutLightRect.left && x <= otherOutLightRect.right
                        && y >= otherOutLightRect.top && y <= otherOutLightRect.bottom) {
                    onRectClickListener.onRectClick(RECT_CLICK_TYPE_OTHER);
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void clearAllView() {
        removeAllViews();
        mhtImageView = null;
        candyImageView = null;
        mCoinTopImageView = null;
        mCoinBottomImageView = null;
        bannerImageView_ = null;
        bannerImageView = null;
        usdtImageView = null;
        otherImageView = null;
        ipoImageView = null;
    }

    public void setBannerLeftEnable(boolean enable) {
        isOpenBanner = enable;
        clearAllView();
        invalidate();
    }

    public void setBannerRightEnable(boolean enable) {
        isOpenBanner_ = enable;
        clearAllView();
        invalidate();
    }

    public void setUSDTEnable(boolean enable) {
        isOpenUSDT = enable;
        clearAllView();
        invalidate();
    }

    public void setMEnable(boolean enable) {
        isOpenMCoin = enable;
        clearAllView();
        invalidate();
    }

    public void setMhtEnable(boolean enable) {
        isOpenMht = enable;
        clearAllView();
        invalidate();
    }

    public void setCandyEnable(boolean enable) {
        isOpenCandy = enable;
        clearAllView();
        invalidate();
    }

    public void setIPOEnable(boolean enable){
        isOpenIpo = enable;
        clearAllView();
        invalidate();
    }

    public void setOtherEnable(boolean enable){
        isOpenOther = enable;
        clearAllView();
        invalidate();
    }

    public interface OnRectClickListener {
        void onRectClick(int rectType);
    }

    public static final int RECT_CLICK_TYPE_CANDY = 1;
    public static final int RECT_CLICK_TYPE_BANNER_LEFT = 2;
    public static final int RECT_CLICK_TYPE_BANNER_RIGHT = 3;
    public static final int RECT_CLICK_TYPE_USDT = 4;
    public static final int RECT_CLICK_TYPE_M = 5;
    public static final int RECT_CLICK_TYPE_MHT = 6;
    public static final int RECT_CLICK_TYPE_BILLBOARD = 7;
    public static final int RECT_CLICK_TYPE_IPO = 8;
    public static final int RECT_CLICK_TYPE_OTHER = 9;
}
