package com.ifenduo.mahattan_x.controller.wallet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ll on 2018/3/7.
 */

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, EasyPermissions.PermissionCallbacks {

    private static final int RC_CAMERA_PERM = 123;

    @BindView(R.id.zx_scan)
    QRCodeView mQRCodeView;

    int mAction;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected int getToolbarColor() {
        return Color.TRANSPARENT;
    }

    @Override
    protected int getStatusBarColor() {
        return Color.TRANSPARENT;
    }

    public static void openScanQR(Context context, int action) {
        Intent intent = new Intent(context, ScanActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("action", action);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void openScanQRForResult(BaseActivity activity, int action, int requestCode) {
        Intent intent = new Intent(activity, ScanActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("action", action);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("扫一扫");
        handleIntent();
        mQRCodeView.setDelegate(this);
    }

    /**
     * 接收页面传值
     */
    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mAction = bundle.getInt("action");
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        mQRCodeView.startSpot();
        if (TextUtils.isEmpty(result)) return;
        vibrate();
        if (mAction == Constant.SCAN_ACTION_ADD_ADDRESS) {//扫二维码添加地址
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_KEY_QR_CODE, result);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }else if(mAction==Constant.SCAN_ACTION_PAY){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_KEY_QR_CODE, result);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showToast("打开相机出错");
    }


    @Override
    protected void onStart() {
        super.onStart();
        openScan();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    static class DecodeQRCodeAsyncTask extends AsyncTask<Void, Void, String> {

        WeakReference<ScanActivity> weakReference;
        String picturePath;

        public DecodeQRCodeAsyncTask(ScanActivity scanActivity, String picturePath) {
            this.weakReference = new WeakReference<ScanActivity>(scanActivity);
            this.picturePath = picturePath.toString();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return QRCodeDecoder.syncDecodeQRCode(picturePath);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (TextUtils.isEmpty(s) && weakReference.get() != null) {
                weakReference.get().showToast("未发现二维码");
            } else {
                weakReference.get().onScanQRCodeSuccess(s);
            }
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    private void openScan() {
        if (hasCameraPermission()) {
            mQRCodeView.startCamera();
            mQRCodeView.showScanRect();
            mQRCodeView.startSpot();
        } else {
            EasyPermissions.requestPermissions(this, "需要相机权限",
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        openScan();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //当从软件设置界面，返回当前程序时候
            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                openScan();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 把执行结果的操作给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
