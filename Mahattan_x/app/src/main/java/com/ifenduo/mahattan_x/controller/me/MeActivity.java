package com.ifenduo.mahattan_x.controller.me;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.common.view.CircleImageView;
import com.ifenduo.mahattan_x.MHXApplication;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.controller.c2c.AddAlipayActivity;
import com.ifenduo.mahattan_x.entity.PersonalInfo;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.CommonInputDialog;
import com.ifenduo.mahattan_x.widget.dialog.MessageDialog;
import com.ifenduo.wheelpicker.picker.SinglePicker;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.luck.picture.lib.config.PictureMimeType.ofImage;

public class MeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    public static final int RC_CAMERA_AND_STORAGE_PERM = 1;
    private static final String[] CAMERA_AND_STORAGE = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @BindView(R.id.avatar_image_view)
    CircleImageView avatarImageView;
    @BindView(R.id.name_text_view)
    TextView nameTextView;
    @BindView(R.id.sex_text_view)
    TextView sexTextView;
    @BindView(R.id.phone_text_view)
    TextView phoneTextView;
    @BindView(R.id.submit_button)
    Button submitButton;

    User mUser;
    PersonalInfo mPersonalInfo;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_me;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
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

        fetchPersonalInfo();
    }

    private void fetchPersonalInfo() {
        if (mUser == null) return;
        Api.getInstance().fetchPersonalInfo(mUser.getFid(), new Callback<PersonalInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<PersonalInfo> response) {
                bindData(response.data);
            }
        });
    }

    private void submitPersonalInfo() {
        if (mPersonalInfo == null || mUser == null) return;
        Api.getInstance().updatePersonalInfo(mUser.getFid(), mPersonalInfo.getFavatar(), mPersonalInfo.getFnickname(), mPersonalInfo.getFsex(), mUser.getFtelephone(), new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    showToast("修改成功");
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
    private void uploadAvatar(LocalMedia localMedia) {
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
                Log.d("TAG", "msg=" + msg + "   url=" + response.data);
                if (isSuccess) {
                    if (mPersonalInfo == null) {
                        mPersonalInfo = new PersonalInfo();
                    }
                    mPersonalInfo.setFavatar(response.data);
                    Glide.with(MeActivity.this).load(response.data).into(avatarImageView);
                }
                PictureFileUtils.deleteCacheDirFile(MeActivity.this);
            }
        });
    }


    private void bindData(PersonalInfo info) {
        if (info != null) {
            if (!TextUtils.isEmpty(info.getFavatar())) {
                Glide.with(this).load(info.getFavatar()).into(avatarImageView);
            } else {
                Glide.with(this).load(R.drawable.image_head_def).into(avatarImageView);
            }
            nameTextView.setText(info.getFnickname());
            sexTextView.setText(info.getFsex());
        }
        if (mUser != null) {
            phoneTextView.setText(mUser.getFormtPhone());
        }
    }

    @OnClick({R.id.avatar_container, R.id.name_container, R.id.sex_container, R.id.phone_container, R.id.submit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar_container:
                openAlbum();
                break;
            case R.id.name_container:
                showInputDialog();
                break;
            case R.id.sex_container:
                showSexPicker();
                break;
            case R.id.phone_container:
                break;
            case R.id.submit_button:
                submitPersonalInfo();
                break;
        }
    }

    private void showInputDialog() {
        CommonInputDialog dialog = new CommonInputDialog(this);
        dialog.builder();
        dialog.setTitle("昵称");
        dialog.setHint("请输入昵称");
        dialog.setMaxInputLength(11);
        dialog.setNegativeButton("取消", new CommonInputDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(CommonInputDialog messageDialog, View button, String text) {
                messageDialog.dismiss();
            }
        });
        dialog.setPositiveButton("确定", new CommonInputDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(CommonInputDialog messageDialog, View button, String text) {
                nameTextView.setText(text);
                if (mPersonalInfo == null) {
                    mPersonalInfo = new PersonalInfo();
                }
                mPersonalInfo.setFnickname(text);
                messageDialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showSexPicker() {
        List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        sexList.add("保密");
        SinglePicker<String> picker = new SinglePicker<String>(this, sexList);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                sexTextView.setText(item);
                if (mPersonalInfo == null) {
                    mPersonalInfo = new PersonalInfo();
                }
                mPersonalInfo.setFsex(item);
            }
        });
        picker.show();
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
                    uploadAvatar(selectList.get(0));
                }
            }
        }
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
