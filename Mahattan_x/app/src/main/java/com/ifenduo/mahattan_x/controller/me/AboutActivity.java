package com.ifenduo.mahattan_x.controller.me;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.widget.dialog.MessageDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.version_text_view)
    TextView mVersionTextView;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        mVersionTextView.setText(String.format("V%s", getVerName(this)));
    }

    @OnClick({R.id.phone_container, R.id.version_text_view, R.id.version_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_container:
                showPhoneDialog();
                break;
            case R.id.version_container:
                break;
        }
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 拨打电话弹窗
     */
    private void showPhoneDialog() {
        MessageDialog messageDialog = new MessageDialog(this);
        messageDialog.builder()
                .setMsg("是否拨打我们平台的电话")
                .setNegativeButton("取消", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                        callPhone("10086");
                    }
                }).show();
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
