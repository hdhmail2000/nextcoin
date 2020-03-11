package com.ifenduo.mahattan_x.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.MHXApplication;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.controller.login.LoginActivity;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;


/*
 *************************************************
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *          @author yangxuefeng                  *
 *          @date 2016-10-18                     *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *                                               *
 *************************************************
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected Toolbar mToolbar;
    protected ImageView mNavigationCenterIcon;
    protected ImageView mNavigationLeftIcon;
    protected ImageView mNavigationRightIcon;
    protected ImageView mNavigationRightIcon_;
    protected TextView mMessagePointTextView;
    protected RelativeLayout mRightIconContainer;
    protected TextView mNavigationCenterText;
    protected TextView mNavigationRightButton;
    //    protected TextView mNavigationLeftText;
    protected ProgressDialog mProgressDialog;
    protected LinearLayout mToolBarContainer;

    public static void openActivity(Activity originActivity, Class<? extends BaseActivity> destinationClass, Bundle options) {
        Intent intent = new Intent(originActivity, destinationClass);
        if (options != null) intent.putExtras(options);
        originActivity.startActivity(intent);
    }

    public static void openActivity(Activity originActivity, Class<? extends BaseActivity> destinationClass, int requestCode, Bundle options) {
        Intent intent = new Intent(originActivity, destinationClass);
        if (options != null) intent.putExtras(options);
        originActivity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        if (isFullScreen()) setupFullScreen();
        onCreateViewBefore();
        MHXApplication application = (MHXApplication) getApplication();
        application.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutId());

        //设置状态栏亮色模式，设置状态栏黑色文字、图标，
        //根布局需加上android:fitsSystemWindows="true"
        if (isSetStatusBarLightMode()) StatusBarTool.StatusBarLightMode(this);

        //设置状态栏颜色
        if (isSetStatusBarColor()) {
            StatusBarTool.setColor(this, getStatusBarColor(), getStatusBarAlpha());
        }

        if (isShouldCreateStatusBar()) createStatusBar();

        if (isRequestSystemUI()) requestSystemUI();
        if (toolbarIsEnable()) setupToolbar();
        decorationWithToolBar(mToolbar);
        onReceiveRequestIntent(getIntent());
        onCreateViewAfter(savedInstanceState);
    }


    private void openChooseWorkerType(int roleType) {
    }


//    /**
//     * 设置整个页面向下移
//     *
//     * @param view
//     */
//    public void setMarginTop(View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
//            layoutParams.setMargins(0, StatusBarTool.getStatusBarHeight(this), 0, 0);
//            view.setLayoutParams(layoutParams);
//        }
//    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void cancelNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    protected abstract int getContentViewLayoutId();

    protected void onReceiveRequestIntent(Intent intent) {
    }

    protected void onCreateViewBefore() {
    }

    protected void onCreateViewAfter(Bundle savedInstanceState) {
    }

    protected void decorationWithToolBar(Toolbar toolbar) {
    }

    protected boolean toolbarIsEnable() {
        return true;
    }

    protected boolean showNavigationIcon() {
        return true;
    }

    /**
     * 是否设置状态栏亮色模式，设置状态栏黑色文字、图标，
     *
     * @return
     */
    protected boolean isSetStatusBarLightMode() {
        return false;
    }

    /**
     * 是否需要在状态栏处创建一个View 占位
     *
     * @return
     */
    protected boolean isShouldCreateStatusBar() {
        return false;
    }

    protected boolean isSetStatusBarColor() {
        return true;
    }

    /**
     * 在状态栏处创建一个view 占位
     */
    protected void createStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBar = StatusBarTool.createStatusBarView(this, getStatusBarColor());
            decorView.addView(statusBar);

            ViewGroup parent = findViewById(android.R.id.content);
            for (int i = 0, count = parent.getChildCount(); i < count; i++) {
                View childView = parent.getChildAt(i);
                if (childView instanceof ViewGroup) {
                    childView.setFitsSystemWindows(true);
                    ((ViewGroup) childView).setClipToPadding(true);
                }
            }
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @return
     */
    protected @ColorInt
    int getStatusBarColor() {
        return Color.TRANSPARENT;
    }

    /**
     * 设置状态栏透明度
     *
     * @return
     */
    protected @IntRange(from = 0, to = 255)
    int getStatusBarAlpha() {
        return 0;
    }

    protected @ColorInt
    int getToolbarColor() {
        return Color.TRANSPARENT;
    }

    protected Toolbar initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationLeftIcon = (ImageView) findViewById(R.id.img_toolbar_left_icon);
        mNavigationCenterIcon = (ImageView) findViewById(R.id.img_toolbar_center_icon);
        mNavigationCenterText = (TextView) findViewById(R.id.tv_toolbar_center_text);
        mNavigationRightIcon = (ImageView) findViewById(R.id.img_toolbar_right_icon);
        mNavigationRightIcon_ = (ImageView) findViewById(R.id.img_toolbar_right_icon_);
//        mMessagePointTextView = (TextView) findViewById(R.id.text_top_count);
        mRightIconContainer = (RelativeLayout) findViewById(R.id.rel_right_icon_container);
        mNavigationRightButton = (TextView) findViewById(R.id.btn_toolbar_right);
        mToolBarContainer = (LinearLayout) findViewById(R.id.ll_toolbar_container);
//        mNavigationLeftText = (TextView) findViewById(R.id.text_toolbar_left);
        mToolbar.setBackgroundColor(getToolbarColor());
        return mToolbar;
    }


    protected int getNavigationIcon() {
        return 0;
    }

    private void setupToolbar() {
        Toolbar toolBar = initToolbar();
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (showNavigationIcon()) {
            if (getNavigationIcon() == 0) {
                toolBar.setNavigationIcon(R.drawable.rc_back_icon);
            } else {
                toolBar.setNavigationIcon(getNavigationIcon());
            }
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBack();
                }
            });
        }
    }

    protected void onClickBack() {
        finish();
    }

    public boolean isRequestSystemUI() {
        return false;
    }

    @TargetApi(21)
    private void requestSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    protected int obtainThemeColorPrimary() {
        TypedValue value = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public void onViewClick(View v) {
    }


    /*****************
     * windowState
     *****************************************************************/
    public void hideKeyboardForCurrentFocus() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

    public void showKeyboardAtView(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void forceShowKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void setupFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void exitFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    public boolean isFullScreen() {
        return false;
    }
    /***************** windowState*****************************************************************/


    /*****************
     * setup NavigationItem
     ********************************************************/
//    protected void setNavigationLeft(CharSequence s) {
//        mNavigationLeftText.setVisibility(View.VISIBLE);
//        mNavigationLeftText.setText(s);
//    }

//    protected void setmNavigationLeftColor(int recColor){
//        mNavigationLeftText.setTextColor(getResources().getColor(recColor));
//    }
    protected void setToolBarBackground(String color) {
        mToolbar.setBackgroundColor(Color.parseColor(color));
    }

    protected void setToolBarBackground(@ColorRes int color) {
        mToolbar.setBackgroundResource(color);
    }

    protected void setNavigationCenter(CharSequence s) {
        mNavigationCenterText.setVisibility(View.VISIBLE);
        mNavigationCenterText.setText(s);
    }

    protected void setNavigationCenter(CharSequence s, @ColorInt int color) {
        mNavigationCenterText.setVisibility(View.VISIBLE);
        mNavigationCenterText.setTextColor(color);
        mNavigationCenterText.setText(s);
    }

    protected void setNavigationLeft(CharSequence s, View.OnClickListener clickListener, String color, int resId) {
        mNavigationCenterText.setVisibility(View.VISIBLE);
        mNavigationCenterText.setText(s);
        mNavigationCenterText.setTextColor(Color.parseColor(color));
        mNavigationCenterText.setOnClickListener(clickListener);

        mNavigationLeftIcon.setOnClickListener(clickListener);
        mNavigationLeftIcon.setVisibility(View.VISIBLE);
        mNavigationLeftIcon.setImageResource(resId);
    }

    protected void setNavigationLeft(CharSequence s, View.OnClickListener clickListener, String color) {
        mNavigationCenterText.setVisibility(View.VISIBLE);
        mNavigationCenterText.setText(s);
        mNavigationCenterText.setTextColor(Color.parseColor(color));
        mNavigationCenterText.setOnClickListener(clickListener);

    }

    protected void setNavigationLeft(int resId) {
        mNavigationLeftIcon.setVisibility(View.VISIBLE);
        mNavigationLeftIcon.setImageResource(resId);
    }

    protected void setNavigationCenterColor(String recColor) {
        mNavigationCenterText.setTextColor(Color.parseColor(recColor));
    }

    protected void setNavigationCenter(int resId) {
        mNavigationCenterIcon.setVisibility(View.VISIBLE);
        mNavigationCenterIcon.setImageResource(resId);
    }


    protected void setNavigationRight(int resId) {
        mNavigationRightIcon.setImageResource(resId);
        mNavigationRightIcon.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
    }

    protected void setNavigationRight(int resId, View.OnClickListener clickListener) {
        mNavigationRightIcon.setImageResource(resId);
        mNavigationRightIcon.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
        mNavigationRightIcon.setOnClickListener(clickListener);
    }

    protected void setNavigationRight(View.OnClickListener clickListener) {
        mNavigationRightIcon.setOnClickListener(clickListener);
        mNavigationRightIcon.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
    }

    protected void setNavigationRight_(int resId) {
        mNavigationRightIcon_.setImageResource(resId);
        mNavigationRightIcon_.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
    }

    protected void setNavigationRight_(int resId, View.OnClickListener clickListener) {
        mNavigationRightIcon_.setImageResource(resId);
        mNavigationRightIcon_.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
        mNavigationRightIcon_.setOnClickListener(clickListener);
    }

    protected void setNavigationRight_(View.OnClickListener clickListener) {
        mNavigationRightIcon_.setOnClickListener(clickListener);
        mNavigationRightIcon_.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
    }

    protected void setNavigationRight(CharSequence s, View.OnClickListener clickListener, String color) {
        mNavigationRightButton.setTextColor(Color.parseColor(color));
        mNavigationRightButton.setVisibility(View.VISIBLE);
        mNavigationRightButton.setText(s);
        mNavigationRightButton.setOnClickListener(clickListener);
    }

    protected void setNavigationRight(CharSequence s, View.OnClickListener clickListener, String color, float size) {
        mNavigationRightButton.setTextColor(Color.parseColor(color));
        mNavigationRightButton.setVisibility(View.VISIBLE);
        mNavigationRightButton.setText(s);
        mNavigationRightButton.setOnClickListener(clickListener);
        mNavigationRightButton.setTextSize(size);
    }


    protected void setMessageCount(int count) {
        if (count < 0) {
            count = 0;
        }
        if (count > 99) {
            mMessagePointTextView.setText("99+");
        } else {
            mMessagePointTextView.setText(String.valueOf(count));
        }
        mMessagePointTextView.setVisibility(View.VISIBLE);
        mRightIconContainer.setVisibility(View.VISIBLE);
    }

    protected void setNavigationRight(CharSequence s, View.OnClickListener clickListener) {
        if(!TextUtils.isEmpty(s)){
            mNavigationRightButton.setVisibility(View.VISIBLE);
            mNavigationRightButton.setText(s);
            mNavigationRightButton.setOnClickListener(clickListener);
        }else {
            mNavigationRightButton.setVisibility(View.GONE);
        }

    }

    protected void setNavigationRightColor(@ColorRes int recColor) {
        mNavigationRightButton.setTextColor(getResources().getColor(recColor));
    }

    protected void setNavigationRight(CharSequence s) {
        mNavigationRightButton.setVisibility(View.VISIBLE);
        mNavigationRightButton.setText(s);
    }

    /***************** setup NavigationItem********************************************************/


    /*****************
     * alert or notification
     *******************************************************/
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        if (mProgressDialog.isShowing() || isFinishing()) {
            return;
        }
        mProgressDialog.show();
    }


    public void showProgress(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        if (mProgressDialog.isShowing() || isFinishing()) {
            return;
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void dismissProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(CharSequence text, int length) {
        Toast.makeText(this, text, length).show();
    }
    /***************** alert or notification*******************************************************/


    /****************
     * networkAvailable
     ************************************************************/
    public boolean networkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public boolean networkAvailableWithWIFI() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public boolean networkAvailableWithMobile() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /****************
     * networkAvailable
     ************************************************************/


    public Bundle buildBundleOptions(BundleParams bundleParams) {
        return null;
    }


    public static class BundleParams {
        public String key;
        public String vale;

        public BundleParams(String key, String vale) {
            this.key = key;
            this.vale = vale;
        }

    }

    /**
     * 当token过期 或者loginInfo==null时 跳转登录
     */
    protected void openLogin() {
        SharedPreferencesTool.loginOut(getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.BUNDLE_KEY_IS_RELOGIN, true);
        openActivity(this, LoginActivity.class, bundle);
    }

}
