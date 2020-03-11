package com.ifenduo.mahattan_x.controller.c2c;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.C2COrder;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.MessageDialog;
import com.ifenduo.mahattan_x.widget.dialog.ShowQrDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ll on 2018/3/8.
 */

public class C2COrderDetailActivity extends BaseActivity {
    //支付状态提示文字
    @BindView(R.id.text_c2c_order_detail_pay_status_hint)
    TextView mPayStatusHintTextView;
    //支付状态
    @BindView(R.id.text_c2c_order_detail_pay_status)
    TextView mPayStatusTextView;
    //付款参考号
    @BindView(R.id.text_c2c_order_detail_pay_no)
    TextView mPayNoTextView;
    //交易金额
    @BindView(R.id.text_c2c_order_detail_money)
    TextView mMoneyTextView;
    //数量
    @BindView(R.id.text_c2c_order_detail_num)
    TextView mNumTextView;
    //价格
    @BindView(R.id.text_c2c_order_detail_price)
    TextView mPriceTextView;
    //类型
    @BindView(R.id.text_c2c_order_detail_type)
    TextView mTypeTextView;
    //订单号
    @BindView(R.id.text_c2c_order_detail_order_no)
    TextView mOrderNoTextView;
    //开户行
    @BindView(R.id.text_c2c_order_detail_bank_name)
    TextView mBankNameTextView;
    //开户名
    @BindView(R.id.text_c2c_order_detail_bank_card_name)
    TextView mBankCardNameTextView;
    //银行账号
    @BindView(R.id.text_c2c_order_detail_bank_card_no)
    TextView mBankCardNoTextView;
    //支付宝账号
    //支付宝名称
    @BindView(R.id.text_c2c_order_detail_alipay_name)
    TextView mAliPayNameTextView;
    //支付宝账号
    @BindView(R.id.text_c2c_order_detail_alipay_account)
    TextView mAliPayAccountTextView;
    //微信名称
    @BindView(R.id.text_c2c_order_detail_wx_name)
    TextView mWeiXinNameTextView;
    //微信账号
    @BindView(R.id.text_c2c_order_detail_wx_account)
    TextView mWeiXinAccountTextView;
    @BindView(R.id.count_down_time_container)
    View mCountDownTimeContainer;
    @BindView(R.id.count_down_time_text_view)
    TextView mCountDownTimeTextView;
    @BindView(R.id.count_down_time_image)
    ImageView mCountDownTimeImageView;
    @BindView(R.id.button_c2c_order_detail_has_pay)
    Button mSubmitButton;
    @BindView(R.id.bank_container)
    View mBankContainer;
    @BindView(R.id.alipay_container)
    View mAlipayContainer;
    @BindView(R.id.wx_container)
    View mWeiXinContainer;
    @BindView(R.id.button_container)
    View mButtonContainer;
    @BindView(R.id.appeal_button)
    Button mAppleButton;

    C2COrder mOrder;
    boolean isOwner;
    User mUser;
    MyCountDownTimer mMyCountDownTimer;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_order_detail;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        setNavigationCenter("订单详情");
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        mCountDownTimeContainer.setVisibility(View.GONE);
        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mOrder = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        bindData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.getCode() == BaseEvent.EVENT_CODE_C2C_APPLY_SUCCESS) {
            if (mOrder != null && !TextUtils.isEmpty(mOrder.getId()) && mOrder.getId().equals(((String) event.getObj()))) {
                mOrder.setOrder_status("4");
                bindData();
            }
        }
    }

    private void initData() {
        handleIntent();
    }

    private void bindData() {
        if (mOrder != null && mUser != null) {
            if (!TextUtils.isEmpty(mOrder.getSell_uid()) && mOrder.getSell_uid().equals(mUser.getFid())) {
                isOwner = true;
            } else {
                isOwner = false;
            }
            mPayNoTextView.setText(mOrder.getPay_no());
            mMoneyTextView.setText(NumberUtil.formatRMB(mOrder.getOrder_price()));
            mNumTextView.setText(NumberUtil.formatMoney(mOrder.getOrder_volume()));
            mPriceTextView.setText(NumberUtil.formatRMB(mOrder.getPrice()));
            mTypeTextView.setText(isOwner ? "卖出" : "买入");
            mOrderNoTextView.setText("订单号：" + mOrder.getSn());
            bindBankData(mOrder.getBank());
            bindAlipayData(mOrder.getAlipay());
            bindWXData(mOrder.getWeixin());
            bindOrderStatus();
        }
    }

    private void bindBankData(C2COrder.BankBean bankBean) {
        if (bankBean != null && !TextUtils.isEmpty(bankBean.getKhh()) && !TextUtils.isEmpty(bankBean.getKhm()) && !TextUtils.isEmpty(bankBean.getZhhm())) {
            mBankContainer.setVisibility(View.VISIBLE);
            mBankNameTextView.setText("开户行：" + bankBean.getKhh());
            mBankCardNameTextView.setText("开户名：" + bankBean.getKhm());
            mBankCardNoTextView.setText("账号：" + bankBean.getZhhm());
        } else {
            mBankNameTextView.setText("开户行：");
            mBankCardNameTextView.setText("开户名：");
            mBankCardNoTextView.setText("账号：");
            mBankContainer.setVisibility(View.GONE);
        }
    }

    private void bindWXData(C2COrder.WeixinBean weixinBean) {
        if (weixinBean != null && !TextUtils.isEmpty(weixinBean.getQrcode()) && !TextUtils.isEmpty(weixinBean.getUsername())) {
            mWeiXinContainer.setVisibility(View.VISIBLE);
            mWeiXinNameTextView.setText("姓名：" + weixinBean.getName());
            mWeiXinAccountTextView.setText("账号：" + weixinBean.getUsername());
        } else {
            mWeiXinContainer.setVisibility(View.GONE);
        }
    }

    private void bindAlipayData(C2COrder.AlipayBean alipayBean) {
        if (alipayBean != null && !TextUtils.isEmpty(alipayBean.getQrcode()) && !TextUtils.isEmpty(alipayBean.getUsername())) {
            mAlipayContainer.setVisibility(View.VISIBLE);
            mAliPayNameTextView.setText("姓名：" + alipayBean.getName());
            mAliPayAccountTextView.setText("账号：" + alipayBean.getUsername());
        } else {
            mAlipayContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 设置状态
     */
    private void bindOrderStatus() {
        mPayStatusHintTextView.setVisibility(View.GONE);
        if (mOrder != null) {
            mPayStatusTextView.setText(mOrder.getStrOrderStatus());
            if ("1".equals(mOrder.getOrder_status())) {//未付款
                mPayStatusTextView.setTextColor(Color.parseColor("#FF67D2"));
                if (isOwner) {
                    showPayStatusHint(true, "等待买家付款中...");
                    mButtonContainer.setVisibility(View.GONE);
                } else {
                    mButtonContainer.setVisibility(View.VISIBLE);
                    mSubmitButton.setVisibility(View.VISIBLE);
                    mAppleButton.setVisibility(View.GONE);
                    mSubmitButton.setText("我已付款");
                }
                showCancelButton();
                showCountDownTime();
            } else if ("2".equals(mOrder.getOrder_status())) {//已付款
                mPayStatusTextView.setTextColor(getResources().getColor(R.color.colorTabChecked));
                if (isOwner) {
                    mPayStatusTextView.setText("待确认");
                    mButtonContainer.setVisibility(View.VISIBLE);
                    mSubmitButton.setVisibility(View.VISIBLE);
                    mAppleButton.setVisibility(View.VISIBLE);
                    mSubmitButton.setText("我已收到款");
                } else {
                    mButtonContainer.setVisibility(View.VISIBLE);
                    mSubmitButton.setVisibility(View.GONE);
                    mAppleButton.setVisibility(View.VISIBLE);
                }
                mCountDownTimeContainer.setVisibility(View.GONE);
                cancelCountDownTime();
                hideCancelButton();
            } else if ("3".equals(mOrder.getOrder_status())) {//已完成
                mPayStatusTextView.setTextColor(getResources().getColor(R.color.colorTabChecked));
                mButtonContainer.setVisibility(View.GONE);
                cancelCountDownTime();
                hideCancelButton();
            } else if ("4".equals(mOrder.getOrder_status())) {//申诉中
                mPayStatusTextView.setTextColor(Color.parseColor("#FF67D2"));
                if (isOwner) {
                    mButtonContainer.setVisibility(View.GONE);
                } else {
                    mButtonContainer.setVisibility(View.GONE);
                }
                mCountDownTimeContainer.setVisibility(View.GONE);
                hideCancelButton();
                cancelCountDownTime();
            } else if ("9".equals(mOrder.getOrder_status())) {//已取消(关闭)
                mPayStatusTextView.setTextColor(getResources().getColor(R.color.colorTabTextNormal));
                mButtonContainer.setVisibility(View.GONE);
                mCountDownTimeContainer.setVisibility(View.GONE);
                cancelCountDownTime();
                hideCancelButton();
            }
        }
    }

    @OnClick({R.id.image_c2c_order_detail_alipay_qr_icon, R.id.image_c2c_order_detail_wx_qr_icon,
            R.id.button_c2c_order_detail_has_pay, R.id.appeal_button})
    public void click(View view) {
        if (view.getId() == R.id.image_c2c_order_detail_alipay_qr_icon) {
            if (mOrder != null && mOrder.getAlipay() != null) {
                showQrDialog(mOrder.getAlipay().getQrcode(), "用支付宝扫二维码付款");
            }
        } else if (view.getId() == R.id.image_c2c_order_detail_wx_qr_icon) {
            if (mOrder != null && mOrder.getWeixin() != null) {
                showQrDialog(mOrder.getWeixin().getQrcode(), "用微信扫二维码付款");
            }
        } else if (view.getId() == R.id.button_c2c_order_detail_has_pay) {
            if (mOrder != null && mUser != null) {
                if ("1".equals(mOrder.getOrder_status()) && !isOwner) {
                    showHasPayDialog();
                } else if ("2".equals(mOrder.getOrder_status()) && isOwner) {
                    showHasRecevedDialog();
                }
            }
        } else if (view.getId() == R.id.appeal_button) {
            if (mOrder == null) return;
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_KEY_COMMON, mOrder.getId());
            openActivity(this, ApplyActivity.class, bundle);
        }
    }

    /**
     * 展示取消交易按钮
     */
    private void showCancelButton() {
        setNavigationRightColor(R.color.colorGrayText);
        setNavigationRight("取消交易", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCancelOrder();
            }
        });
    }

    private void hideCancelButton() {
        setNavigationRightColor(R.color.colorGrayText);
        setNavigationRight("", null);
    }

    /**
     * 显示倒计时
     */
    private void showCountDownTime() {
        long time = orderIsOutTime();
        if (time > 0) {
            showCountDownTimeAnim();
            mCountDownTimeContainer.setVisibility(View.VISIBLE);
            initCountDownTime(time);
        } else {
            mCountDownTimeContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 倒计时显示动画
     */
    private void showCountDownTimeAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_c2c_order_count_down_time_enter);
        mCountDownTimeContainer.startAnimation(animation);
        Animation animation_ = AnimationUtils.loadAnimation(this, R.anim.anim_c2c_order_count_down_time);
        animation_.setRepeatCount(Animation.INFINITE);
        animation_.setDuration(2000);
        animation_.setRepeatMode(Animation.RESTART);
        mCountDownTimeImageView.startAnimation(animation_);
    }

    private void hideCountDownTime() {
        showHideCountDownTimeAnim();
        mCountDownTimeContainer.setVisibility(View.GONE);
    }

    /**
     * 倒计时结束动画
     */
    private void showHideCountDownTimeAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_c2c_order_count_down_time_exit);
        mCountDownTimeContainer.startAnimation(animation);

    }

    private void setCountDownTime(long millisUntilFinished) {
        if (millisUntilFinished > 0) {
            mCountDownTimeTextView.setText(formatTimer(millisUntilFinished / 1000));
        } else {
            mCountDownTimeTextView.setText(formatTimer(0l));
            if (mCountDownTimeImageView.getVisibility() == View.VISIBLE) ;
            hideCountDownTime();
            mOrder.setOrder_status("9");
            bindData();
        }
    }

    /**
     * 订单是否超时（下单后15分钟后自动取消）
     *
     * @return 超时返回剩余-1，没超时返回剩余毫秒数
     */
    private long orderIsOutTime() {
        if (mOrder != null && "1".equals(mOrder.getOrder_status())) {
            long closeTime = mOrder.getOrder_time() * 1000 + 15 * 60 * 1000;//自动取消的时间（下单后15分钟后自动取消）
            long nowTime = Calendar.getInstance().getTimeInMillis();
            if (closeTime > nowTime) {
                return closeTime - nowTime;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 初始化倒计时
     */
    private void initCountDownTime(long time) {
        cancelCountDownTime();
        mMyCountDownTimer = new MyCountDownTimer(this, time, 1000);
        mMyCountDownTimer.start();
    }

    /**
     * 取消倒计时
     */
    private void cancelCountDownTime() {
        if (mMyCountDownTimer != null) {
            mMyCountDownTimer.cancel();
            mMyCountDownTimer = null;
        }
    }

    /**
     * 展示支付状态提示信息
     *
     * @param isShow
     * @param hint
     */
    private void showPayStatusHint(boolean isShow, String hint) {
        if (isShow) {
            mPayStatusHintTextView.setText(hint);
            mPayStatusHintTextView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_pay_status_hint_enter);
            mPayStatusHintTextView.startAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_pay_status_hint_exit);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPayStatusHintTextView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mPayStatusHintTextView.startAnimation(animation);
        }
    }

    ShowQrDialog mShowQrDialog;

    private void showQrDialog(String path, String title) {
        if (mShowQrDialog == null) {
            mShowQrDialog = new ShowQrDialog(this);
        }
        if (!mShowQrDialog.isShowing()) {
            mShowQrDialog.show();
        }
        mShowQrDialog.setQrImage(path);
        mShowQrDialog.setTitle(title);
    }

    /**
     * 格式化倒计时
     *
     * @param time 秒
     * @return
     */
    public String formatTimer(Long time) {
        int countTime = time.intValue();
        final int MINUTE = 60;
        int minute = (int) (countTime / MINUTE);
        int second = countTime % MINUTE;
        StringBuilder builder = new StringBuilder("");
        builder.append(minute < 10 ? ("0" + minute) : String.valueOf(minute))
                .append(":")
                .append(second < 10 ? ("0" + second) : String.valueOf(second));
        return builder.toString();
    }

    private void submitCancelOrder() {
        if (mOrder == null || mUser == null || !"1".equals(mOrder.getOrder_status())) return;
        Api.getInstance().submitCancelC2COrder(mUser.getFid(), mOrder.getId(), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    mOrder.setOrder_status("9");
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_CHANGE_C2C_ORDER_STATUS, mOrder));
                    bindData();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void showHasPayDialog() {
        MessageDialog messageDialog = new MessageDialog(this);
        messageDialog.builder();
        messageDialog.setMsg("是否确认已给卖家打款");
        messageDialog.setNegativeButton("取消", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                messageDialog.dismiss();
            }
        });
        messageDialog.setPositiveButton("确定", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                submitHasPay();
                messageDialog.dismiss();
            }
        });
        messageDialog.show();
    }

    private void showHasRecevedDialog() {
        MessageDialog messageDialog = new MessageDialog(this);
        messageDialog.builder();
        messageDialog.setMsg("是否确认已收到对方的打款");
        messageDialog.setNegativeButton("取消", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                messageDialog.dismiss();
            }
        });
        messageDialog.setPositiveButton("确定", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                submitHasReceved();
                messageDialog.dismiss();
            }
        });
        messageDialog.show();
    }

    private void submitHasReceved() {
        if (mOrder == null || mUser == null || !"2".equals(mOrder.getOrder_status()) || !isOwner)
            return;
        Api.getInstance().submitSetHasReceved(mUser.getFid(), mOrder.getId(), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    mOrder.setOrder_status("3");
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_CHANGE_C2C_ORDER_STATUS, mOrder));
                    bindData();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void submitHasPay() {
        if (mOrder == null || mUser == null || !"1".equals(mOrder.getOrder_status()) || isOwner)
            return;
        Api.getInstance().submitSetHasPay(mUser.getFid(), mOrder.getId(), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    mOrder.setOrder_status("2");
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_CHANGE_C2C_ORDER_STATUS, mOrder));
                    bindData();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    static class MyCountDownTimer extends CountDownTimer {
        WeakReference<C2COrderDetailActivity> weakReference;

        public MyCountDownTimer(C2COrderDetailActivity activity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().setCountDownTime(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().setCountDownTime(0);
            }
        }
    }

}
