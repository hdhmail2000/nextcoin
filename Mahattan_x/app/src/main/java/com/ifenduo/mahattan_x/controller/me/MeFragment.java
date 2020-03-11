package com.ifenduo.mahattan_x.controller.me;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.common.view.CircleImageView;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.controller.c2c.AccountActivity;
import com.ifenduo.mahattan_x.controller.wallet.WalletActivity;
import com.ifenduo.mahattan_x.entity.PersonalInfo;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends BaseFragment {
    @BindView(R.id.view_status_bar)
    View mStatusBar;
    @BindView(R.id.image_head)
    CircleImageView mAvatarImageView;
    @BindView(R.id.text_person_name)
    TextView mNameTextView;
    @BindView(R.id.text_person_signature)
    TextView mSignatureTextView;
    @BindView(R.id.rel_safe_center)
    RelativeLayout relSafeCenter;
    @BindView(R.id.rel_order)
    RelativeLayout relOrder;
    @BindView(R.id.rel_wallet)
    RelativeLayout relWallet;
    @BindView(R.id.rel_address)
    RelativeLayout relAddress;
    @BindView(R.id.rel_help_center)
    RelativeLayout relHelpCenter;
    @BindView(R.id.rel_about)
    RelativeLayout relAbout;
    @BindView(R.id.rel_setting)
    RelativeLayout relSetting;
    Unbinder mUnBinder;

    User mUser;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(View parentView) {
        mUnBinder = ButterKnife.bind(this, parentView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mStatusBar.getLayoutParams();
            layoutParams.height = StatusBarTool.getStatusBarHeight(getContext());
            mStatusBar.setLayoutParams(layoutParams);
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser= SharedPreferencesTool.getUser(getContext().getApplicationContext());
        fetchPersonalInfo();
        if(mUser!=null){
            mSignatureTextView.setText("邀请码："+mUser.getFid());
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @OnClick({R.id.rel_person_info, R.id.rel_safe_center, R.id.rel_order, R.id.rel_wallet,R.id.rel_share,
            R.id.rel_address, R.id.rel_help_center, R.id.rel_about, R.id.rel_setting, R.id.c2c_account_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_person_info:
                MeActivity.openActivity((Activity) getContext(), MeActivity.class, null);
                break;
            case R.id.rel_safe_center:
                SafeCenterActivity.openActivity((Activity) getContext(), SafeCenterActivity.class, null);
                break;
            case R.id.rel_order:
                break;
            case R.id.rel_wallet:
                WalletActivity.openActivity((BaseActivity) getContext(), WalletActivity.class, null);
                break;
            case R.id.rel_address:
                break;
            case R.id.rel_help_center:
                HelpCenterActivity.openActivity((Activity) getContext(), HelpCenterActivity.class, null);
                break;
            case R.id.rel_about:
                AboutActivity.openActivity((BaseActivity) getContext(), AboutActivity.class, null);
                break;
            case R.id.rel_setting:
                SettingActivity.openActivity((BaseActivity) getContext(), SettingActivity.class, null);
                break;
            case R.id.c2c_account_container:
                AccountActivity.openActivity((BaseActivity) getContext(), AccountActivity.class, null);
                break;
            case R.id.rel_share:
                ShareActivity.openActivity((BaseActivity)getContext(),ShareActivity.class,null);
                break;
        }
    }

    private void fetchPersonalInfo() {
        if (mUser == null) return;
        Api.getInstance().fetchPersonalInfo(mUser.getFid(), new Callback<PersonalInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<PersonalInfo> response) {
                if(response.data!=null){
                    RequestOptions options=new RequestOptions();
                    options.error(R.drawable.image_head_def);
                    Glide.with(getContext()).load(response.data.getFavatar()).apply(options).into(mAvatarImageView);
                    mNameTextView.setText(response.data.getFnickname());
                }
            }
        });
    }
}
