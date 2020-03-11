package com.ifenduo.mahattan_x.controller.c2c;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.luck.picture.lib.config.PictureMimeType.ofImage;

public class AddWechatActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    public static final int RC_CAMERA_AND_STORAGE_PERM = 1;
    private static final String[] CAMERA_AND_STORAGE = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.account_edit_text)
    EditText accountEditText;
    @BindView(R.id.qr_image_view)
    ImageView qrImageView;
    @BindView(R.id.submit_button)
    TextView mSubmitButton;

    String mQRPath;
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_add_c2c_wechat;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("微信");
        setSubmitButtonEnable();
        setupInputListener();
    }

    @OnClick({R.id.qr_image_view, R.id.submit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qr_image_view:
                openAlbum();
                break;
            case R.id.submit_button:
                submitAccount();
                break;
        }
    }
    /**
     * 打开相册
     */
    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE_PERM)
    private void openAlbum() {
        if (!hasCameraAndStoragePermission()) {
            requestCameraAndStoragePermission();
            return;
        }
        PictureSelector.create(this).openGallery(ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.choose_picture)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                if (data == null) return;
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    uploadQR(selectList.get(0));
                }
            }
        }
    }

    private void submitAccount() {
        String name = nameEditText.getText().toString();
        String account = accountEditText.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showToast("请输入微信账号");
            return;
        }
        if (TextUtils.isEmpty(mQRPath)) {
            showToast("请上传微信收款二维码");
            return;
        }

        User user = SharedPreferencesTool.getUser(this);
        if (user == null) {
            showToast("你尚未登录，请先登录");
            return;
        }
        showProgress();
        Api.getInstance().submitAddWechat(user.getFid(), name, account, mQRPath, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    showToast("添加成功");
                    finish();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 上传二维码
     *
     * @param localMedia
     */
    private void uploadQR(LocalMedia localMedia) {
        String path = "";
        if (localMedia != null) {
            if (localMedia.isCompressed()) {
                path = localMedia.getCompressPath();
            } else if (localMedia.isCut()) {
                path = localMedia.getCutPath();
            } else {
                path = localMedia.getPath();
            }
        }
        if (TextUtils.isEmpty(path)) return;
        showProgress();
        Api.getInstance().ossUploadImage(path, new Callback<String>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<String> response) {
                dismissProgress();
                if (isSuccess) {
                    bindQRImageView(response.data);
                }
                PictureFileUtils.deleteCacheDirFile(AddWechatActivity.this);
                setSubmitButtonEnable();
            }
        });
    }

    /**
     * 设置提交按钮是否可点击
     */
    private void setSubmitButtonEnable() {
        if (TextUtils.isEmpty(mQRPath)) {
            mSubmitButton.setEnabled(false);
            return;
        }

        if (TextUtils.isEmpty(accountEditText.getText().toString()) || accountEditText.getText().length() < 4) {
            mSubmitButton.setEnabled(false);
            return;
        }
        mSubmitButton.setEnabled(true);
    }

    /**
     * 上传图片后绑定后图片
     *
     * @param url 上传图片后返回的地址
     */
    private void bindQRImageView(String url) {
        mQRPath = url;
        Glide.with(this).load(url).into(qrImageView);
    }

    /**
     * 设置输入监听
     */
    private void setupInputListener() {
        accountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setSubmitButtonEnable();
            }
        });
    }

    /**
     * 是否有相机和读写权限
     *
     * @return
     */
    private boolean hasCameraAndStoragePermission() {
        return EasyPermissions.hasPermissions(this, CAMERA_AND_STORAGE);
    }

    /**
     * 申请相机和读写权限
     */
    private void requestCameraAndStoragePermission() {
        EasyPermissions.requestPermissions(
                this, "需要相机和读写权限，否则部分功能不能正常使用",
                RC_CAMERA_AND_STORAGE_PERM,
                CAMERA_AND_STORAGE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
