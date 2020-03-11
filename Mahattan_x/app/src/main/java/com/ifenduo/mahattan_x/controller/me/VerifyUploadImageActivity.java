package com.ifenduo.mahattan_x.controller.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_REAL_AUTH_SUCCESS;

public class VerifyUploadImageActivity extends BaseActivity {
    public static final int REQUEST_CODE_POSITIVE = 1;
    public static final int REQUEST_CODE_NEGATIV = 2;
    public static final int REQUEST_CODE_PHOTO = 3;

    @BindView(R.id.positive_image_view)
    ImageView mPositiveImageView;
    @BindView(R.id.negative_image_view)
    ImageView mNegativeImageView;
    @BindView(R.id.photo_image_view)
    ImageView mPhotoImageView;
    @BindView(R.id.submit_button)
    TextView mSubmitButton;

    String mName;
    String mIdCard;
    String mPositivePath;
    String mNegativePath;
    String mPhotoPath;
    int mScreenWidth;
    boolean isFromRegister;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_verify_upload_image;
    }


    public static void openVerifyUploadImage(Context context, String name, String idCard, boolean isFromRegister) {
        Intent intent = new Intent(context, VerifyUploadImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("idCard", idCard);
        bundle.putBoolean("isFromRegister", isFromRegister);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        handleIntent();
        setNagivation();
        initView();
    }

    private void initView() {
        mSubmitButton.setEnabled(false);
        mScreenWidth = DimensionTool.getScreenWidth(this);
        int imageWidth = mScreenWidth - DimensionTool.dp2px(this, 16) * 2;
        int imageHeight = (int) ((180f / 340f) * imageWidth);
        RelativeLayout.LayoutParams positiveLayoutParams = (RelativeLayout.LayoutParams) mPositiveImageView.getLayoutParams();
        RelativeLayout.LayoutParams negativeLayoutParams = (RelativeLayout.LayoutParams) mNegativeImageView.getLayoutParams();
        RelativeLayout.LayoutParams photoLayoutParams = (RelativeLayout.LayoutParams) mPhotoImageView.getLayoutParams();
        positiveLayoutParams.height = imageHeight;
        negativeLayoutParams.height = imageHeight;
        photoLayoutParams.height = imageHeight;
        mPositiveImageView.setLayoutParams(positiveLayoutParams);
        mNegativeImageView.setLayoutParams(negativeLayoutParams);
        mPhotoImageView.setLayoutParams(photoLayoutParams);
    }

    /**
     * 接收页面传值
     */
    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mName = bundle.getString("name", "");
            mIdCard = bundle.getString("idCard", "");
            isFromRegister = bundle.getBoolean("isFromRegister", false);
        }
    }

    private void setNagivation() {
        setNavigationCenter("实名认证");
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
    }

    @OnClick({R.id.submit_button, R.id.positive_image_view, R.id.negative_image_view, R.id.photo_image_view})
    public void click(View view) {
        if (view.getId() == R.id.submit_button) {
            submitRelAuth();
        } else if (view.getId() == R.id.positive_image_view) {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .isCamera(true)
                    .imageFormat(PictureMimeType.JPEG)
                    .enableCrop(true)
                    .compress(true)
                    .showCropFrame(true)
                    .rotateEnabled(true)
                    .scaleEnabled(true)
                    .withAspectRatio(17, 9)
                    .forResult(REQUEST_CODE_POSITIVE);
        } else if (view.getId() == R.id.negative_image_view) {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .isCamera(true)
                    .imageFormat(PictureMimeType.JPEG)
                    .enableCrop(true)
                    .compress(true)
                    .showCropFrame(true)
                    .rotateEnabled(true)
                    .scaleEnabled(true)
                    .withAspectRatio(17, 9)
                    .forResult(REQUEST_CODE_NEGATIV);
        } else if (view.getId() == R.id.photo_image_view) {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .isCamera(true)
                    .imageFormat(PictureMimeType.JPEG)
                    .enableCrop(true)
                    .compress(true)
                    .showCropFrame(true)
                    .rotateEnabled(true)
                    .scaleEnabled(true)
                    .withAspectRatio(17, 9)
                    .forResult(REQUEST_CODE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_POSITIVE || requestCode == REQUEST_CODE_NEGATIV || requestCode == REQUEST_CODE_PHOTO) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                handleImage(requestCode, selectList);
            }
        }
    }

    private void handleImage(int requestCode, List<LocalMedia> localMediaList) {
        if (localMediaList != null && localMediaList.size() > 0) {
            LocalMedia localMedia = localMediaList.get(0);
            if (localMedia != null) {
                String path = "";
                if (localMedia.isCompressed()) {
                    path = localMedia.getCompressPath();
                } else if (localMedia.isCut()) {
                    path = localMedia.getCutPath();
                } else {
                    path = localMedia.getPath();
                }
                uploadImage(path, requestCode);
            }
        }
    }

    private void submitRelAuth() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            showToast("登录已失效请重新登录");
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().submitRealAuth(loginInfo.getToken(), loginInfo.getSecretKey(), mName, mIdCard, "0", mPositivePath, mNegativePath, mPhotoPath, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    EventBus.getDefault().post(new BaseEvent(EVENT_CODE_REAL_AUTH_SUCCESS, null));
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constant.BUNDLE_KEY_COMMON, isFromRegister);
                    openActivity(VerifyUploadImageActivity.this, VerifiedSuccessActivity.class, bundle);
                    finish();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    /**
     * 上传图片
     *
     * @param path
     * @param tag
     */
    private void uploadImage(String path, final int tag) {
        Api.getInstance().ossUploadImage(path, new Callback<String>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<String> response) {
                //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                PictureFileUtils.deleteCacheDirFile(VerifyUploadImageActivity.this);
                if (tag == REQUEST_CODE_POSITIVE) {
                    mPositivePath = response.data;
                    if (TextUtils.isEmpty(mPositivePath)) {
                        mPositiveImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        Glide.with(VerifyUploadImageActivity.this).load(R.drawable.image_front_card).into(mPositiveImageView);
                    } else {
                        mPositiveImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(VerifyUploadImageActivity.this).load(mPositivePath).into(mPositiveImageView);
                    }
                } else if (tag == REQUEST_CODE_NEGATIV) {
                    mNegativePath = response.data;
                    if (TextUtils.isEmpty(mNegativePath)) {
                        Glide.with(VerifyUploadImageActivity.this).load(R.drawable.image_back_card).into(mNegativeImageView);
                        mNegativeImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    } else {
                        Glide.with(VerifyUploadImageActivity.this).load(mNegativePath).into(mNegativeImageView);
                        mNegativeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                } else if (tag == REQUEST_CODE_PHOTO) {
                    mPhotoPath = response.data;
                    if (TextUtils.isEmpty(mPhotoPath)) {
                        mPhotoImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        Glide.with(VerifyUploadImageActivity.this).load(R.drawable.image_with_hand_card).into(mPhotoImageView);
                    } else {
                        mPhotoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(VerifyUploadImageActivity.this).load(mPhotoPath).into(mPhotoImageView);
                    }

                }
                setSubmitButtonEnable();
            }
        });
    }

    private void setSubmitButtonEnable() {
        mSubmitButton.setEnabled(!TextUtils.isEmpty(mPositivePath) && !TextUtils.isEmpty(mNegativePath) && !TextUtils.isEmpty(mPhotoPath));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
