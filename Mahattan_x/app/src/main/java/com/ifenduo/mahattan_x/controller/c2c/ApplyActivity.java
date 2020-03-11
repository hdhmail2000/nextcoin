package com.ifenduo.mahattan_x.controller.c2c;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.luck.picture.lib.config.PictureMimeType.ofImage;

public class ApplyActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    public static final int RC_CAMERA_AND_STORAGE_PERM = 1;
    private static final String[] CAMERA_AND_STORAGE = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.cause_edit_text)
    EditText causeEditText;
    @BindView(R.id.appeal_image_view)
    ImageView appealImageView;
    @BindView(R.id.submit_button)
    Button submitButton;

    String mImagePath;
    String mOrderID = "";
    User mUser;
    MyAsyncTask mAsyncTask;
    String mImageBase64;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_apply;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        setNavigationCenter("申诉");
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        handleIntent();
        setupInputListener();
        setSubmitButtonEnable();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mOrderID = bundle.getString(Constant.BUNDLE_KEY_COMMON, "");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录,请先登录");
            openLogin();
        }
    }

    @OnClick({R.id.submit_button, R.id.appeal_image_view})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.submit_button) {
            submitApply();
        } else if (view.getId() == R.id.appeal_image_view) {
            openAlbum();
        }
    }

    /**
     * 开启图片转换线程
     */
    private void startImage2Base64AsyncTask(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) return;
        cancelImage2Base64AsyncTask();
        mAsyncTask = new MyAsyncTask(this);
        mAsyncTask.execute(imagePath);
    }

    /**
     * 停止图片转换线程
     */
    private void cancelImage2Base64AsyncTask() {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
            mAsyncTask = null;
        }
    }


    /**
     * 图片转换完成
     *
     * @param imageBase64
     */
    public void onImage2Base64Over(String imageBase64) {
        mImageBase64 = imageBase64;
        Glide.with(this).load(TextUtils.isEmpty(mImagePath) ? R.drawable.ic_c2c_apply_add_image : mImagePath).into(appealImageView);
        PictureFileUtils.deleteCacheDirFile(ApplyActivity.this);
        Log.d("TAG","imageBase64="+imageBase64);
    }

    /**
     * 提交信息
     */
    private void submitApply() {
        if (mUser == null) {
            showToast("你尚未登录,请先登录");
            openLogin();
            return;
        }
        if (TextUtils.isEmpty(mImageBase64)) {
            dismissProgress();
            showToast("图片已损毁，请重新上传");
            return;
        }
        showProgress();
        String content = causeEditText.getText().toString();
        Api.getInstance().submitC2CApply(mUser.getFid(), mOrderID, content, mImageBase64, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_CODE_C2C_APPLY_SUCCESS, mOrderID));
                    finish();
                } else {
                    showToast(msg);
                }
            }
        });
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
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(80)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .cropCompressQuality(50)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 设置输入监听
     */
    private void setupInputListener() {
        causeEditText.addTextChangedListener(new TextWatcher() {
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
     * 设置提交按钮是否可点击
     */
    private void setSubmitButtonEnable() {
        if (TextUtils.isEmpty(causeEditText.getText().toString())) {
            submitButton.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(mImagePath)) {
            submitButton.setEnabled(false);
            return;
        }
        submitButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                mImagePath = "";
                if (data == null) return;
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia != null) {
                        if (localMedia.isCompressed()) {
                            mImagePath = localMedia.getCompressPath();
                        } else {
                            mImagePath = localMedia.getPath();
                        }
                    }
                }
                startImage2Base64AsyncTask(mImagePath);
                setSubmitButtonEnable();
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

    /**
     * 将图片转城Base64线程
     */
    static class MyAsyncTask extends AsyncTask<String, Void, String> {
        WeakReference<ApplyActivity> weakReference;

        public MyAsyncTask(ApplyActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            String file = params[0];
            FileInputStream fis = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            int len;
            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();
                while ((len = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (weakReference.get() != null) {
                weakReference.get().onImage2Base64Over("data:image/png;base64,"+s);
            }
        }
    }
}