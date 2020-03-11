package com.ifenduo.mahattan_x.controller.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.MiningArea;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.switchbutton.SwitchButton;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineAreaActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.status_text_view)
    TextView mStatusTextView;
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.sum_num_text_view)
    TextView mSumNumTextView;
    @BindView(R.id.unit_text_view)
    TextView mUnitTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.num_text_view)
    TextView mNumTextView;
    @BindView(R.id.scale_text_view)
    TextView mScaleTextView;
    @BindView(R.id.submit_button)
    Button mSubmitButton;
    @BindView(R.id.switch_button)
    SwitchButton mSwitchButton;
    @BindView(R.id.status_text_view_)
    TextView mStatusTextView_;
    @BindView(R.id.title_text_view_)
    TextView mTitleTextView_;
    @BindView(R.id.sum_num_text_view_)
    TextView mSumNumTextView_;
    @BindView(R.id.unit_text_view_)
    TextView mUnitTextView_;
    @BindView(R.id.progress_bar_)
    ProgressBar mProgressBar_;
    @BindView(R.id.num_text_view_)
    TextView mNumTextView_;
    @BindView(R.id.scale_text_view_)
    TextView mScaleTextView_;
    @BindView(R.id.submit_button_)
    Button mSubmitButton_;
    @BindView(R.id.switch_button_)
    SwitchButton mSwitchButton_;

    User mUser;
    MiningArea mMiningArea;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_mine_area;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        setNavigationCenter("开矿区");
        StatusBarTool.setTranslucentForImageView(this, mToolbar);

        bindData(null);

        mSwitchButton.setOnCheckedChangeListener(this);
        mSwitchButton_.setOnCheckedChangeListener(this);
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
        fetchMiningArea();
    }

    @OnClick({R.id.submit_button_, R.id.submit_button})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.submit_button) {
            if (mMiningArea == null) return;
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_KEY_COIN_TYPE, "MHT-X");
            bundle.putDouble(Constant.BUNDLE_KEY_COMMON, mMiningArea.getMhtx());
            openActivity(this, TransferActivity.class, bundle);
        } else if (view.getId() == R.id.submit_button_) {
            if (mMiningArea == null) return;
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_KEY_COIN_TYPE, "USDT");
            bundle.putDouble(Constant.BUNDLE_KEY_COMMON, mMiningArea.getUsdt());
            openActivity(this, TransferActivity.class, bundle);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.switch_button) {
            submitOneKey("mhtx", isChecked);
        } else if (buttonView.getId() == R.id.switch_button_) {
            submitOneKey("usdt", isChecked);
        }
    }

    private void fetchMiningArea() {
        if (mUser == null) return;
        Api.getInstance().fetchMiningArea(mUser.getFid(), new Callback<MiningArea>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<MiningArea> response) {
                if (isSuccess) {
                    bindData(response.data);
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void bindData(MiningArea area) {
        mMiningArea=area;
        if (area != null) {
            double progress=Double.parseDouble(NumberUtil.formatMoney(area.getMhtx()))/200000d*100;
            if(progress>0&&progress<1){
                progress=1;
            }
            mNumTextView.setText(NumberUtil.formatMoney(area.getMhtx()));
            mProgressBar.setProgress((int) progress);
            mStatusTextView.setEnabled(area.isMhtOpen());
            mStatusTextView.setTextColor(area.isMhtOpen() ? Color.WHITE : Color.parseColor("#7fffffff"));
            mScaleTextView.setText(BigDecimal.valueOf((area.getMhtxmor() * 100)).stripTrailingZeros().toPlainString() + "%");
            mSubmitButton.setEnabled(area.isMhtOpen());
            mSwitchButton.setEnabled(area.isMhtOpen());
            if (area.isMhtOpen()) {
                mStatusTextView.setText(area.getMhtx() >= 200000 ? "完全激活" : "投入中");
                mSwitchButton.setChecked(area.isMhtOneKey());
            } else {
                mSwitchButton.setChecked(false);
                mStatusTextView.setText("未激活");
            }

            double progress_=Double.parseDouble(NumberUtil.formatMoney(area.getUsdt()))/200000d*100;
            if(progress_>0&&progress_<1){
                progress_=1;
            }
            mProgressBar_.setProgress((int) progress_);
            mNumTextView_.setText(NumberUtil.formatMoney(area.getUsdt()));
            mStatusTextView_.setEnabled(area.isUsdtOpen());
            mStatusTextView_.setTextColor(area.isUsdtOpen() ? Color.WHITE : Color.parseColor("#7fffffff"));
            mScaleTextView_.setText(BigDecimal.valueOf((area.getUsdtmor() * 100)).stripTrailingZeros().toPlainString() + "%");
            mSubmitButton_.setEnabled(area.isUsdtOpen());
            mSwitchButton_.setEnabled(area.isUsdtOpen());
            mSumNumTextView_.setTextColor(area.isUsdtOpen()?getResources().getColor(R.color.colorBlueText):getResources().getColor(R.color.colorTabTextNormal));
            if (area.isUsdtOpen()) {
                mStatusTextView_.setText(area.getUsdt() >= 200000 ? "完全激活" : "投入中");
                mSwitchButton_.setChecked(area.isUsdtOneKey());
                mNumTextView_.setTextColor(getResources().getColor(R.color.colorGrayText));
            } else {
                mSwitchButton_.setChecked(false);
                mStatusTextView_.setText("未激活");
                mNumTextView_.setTextColor(getResources().getColor(R.color.colorTabNormal));
            }

        } else {
            mNumTextView.setText("0");
            mStatusTextView.setEnabled(true);
            mStatusTextView.setText("未激活");
            mStatusTextView.setTextColor(Color.WHITE);
            mScaleTextView.setText("0%");
            mSubmitButton.setEnabled(true);
            mSwitchButton.setEnabled(true);
            mSwitchButton.setChecked(false);

            mNumTextView_.setText("不限MHT-X额度");
            mNumTextView_.setTextColor(getResources().getColor(R.color.colorTabNormal));
            mStatusTextView_.setEnabled(false);
            mStatusTextView_.setText("未激活");
            mStatusTextView_.setTextColor(Color.parseColor("#7fffffff"));
            mScaleTextView_.setText("0%");
            mSubmitButton_.setEnabled(false);
            mSwitchButton.setEnabled(false);
            mSwitchButton_.setChecked(false);

        }
    }

    private void submitOneKey(final String type, final boolean isOpen) {
        if (mUser == null) {
            return;
        }

        Api.getInstance().submitOneKey(mUser.getFid(), type, isOpen, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    if ("usdt".equals(type)) {
                        mSwitchButton_.setChecked(isOpen);
                    } else if ("mhtx".equals(type)) {
                        mSwitchButton.setChecked(isOpen);
                    }
                } else {
                    showToast(msg);
                }
            }
        });
    }
}
