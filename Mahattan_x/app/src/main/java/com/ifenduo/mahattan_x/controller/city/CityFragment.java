package com.ifenduo.mahattan_x.controller.city;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CityFragment extends BaseFragment {

    @BindView(R.id.view_status_bar)
    View mStatusBar;
    Unbinder mUnBinder;

    public static CityFragment newInstance() {
        Bundle args = new Bundle();
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayout() {
        return R.layout.fragment_city;
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
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
