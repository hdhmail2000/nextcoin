package com.ifenduo.mahattan_x.controller.mine;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenduo.common.adapter.base.ViewHolder;
import com.ifenduo.common.tool.DateTimeTool;
import com.ifenduo.common.tool.DimensionTool;
import com.ifenduo.lib_banner.Banner;
import com.ifenduo.lib_banner.BannerConfig;
import com.ifenduo.lib_banner.Transformer;
import com.ifenduo.lib_banner.listener.OnBannerListener;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.base.BaseListFragment;
import com.ifenduo.mahattan_x.entity.CalculationTask;
import com.ifenduo.mahattan_x.entity.MineAreaStatus;
import com.ifenduo.mahattan_x.entity.MiningRecord;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.BannerImageLoader;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.CityLayout;
import com.ifenduo.mahattan_x.widget.dialog.MessageDialog;
import com.ifenduo.mahattan_x.widget.dialog.ShareDialog;

import java.util.ArrayList;
import java.util.List;

import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SHARE;
import static com.ifenduo.mahattan_x.widget.dialog.ShareDialog.SHARE_TYPE_FACE_BOOK;
import static com.ifenduo.mahattan_x.widget.dialog.ShareDialog.SHARE_TYPE_GOOGLE;
import static com.ifenduo.mahattan_x.widget.dialog.ShareDialog.SHARE_TYPE_TWITTER;
import static com.ifenduo.mahattan_x.widget.dialog.ShareDialog.SHARE_TYPE_WECHAT;

/**
 * 首页挖矿
 */
public class MineFragment extends BaseListFragment<MiningRecord> implements OnBannerListener, CityLayout.OnRectClickListener, ShareDialog.OnShareTypeClickListener {
    View mHeaderView;
    CityLayout mCityImageView;
    TextView mCalculateTextView;
    ImageView mTaskImageView;
    ImageView mInviteImageView;
    Banner mBannerView;

    int mScreenWidth;
    User mUser;

    List<MineAreaStatus> mMineAreaStatusList;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_maine;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, MiningRecord record) {

    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.task_image_view) {
            TaskActivity.openActivity((BaseActivity) getContext(), TaskActivity.class, null);
        } else if (v.getId() == R.id.invite_image_view) {
            showShareDialog();
        }
    }

    @Override
    public void onRequest(int page) {
        if (mUser == null) {
            dispatchResult(new ArrayList<MiningRecord>());
            return;
        }
        fetchMineStatus();
        fetchTask();
        Api.getInstance().fetchMiningRecord(mUser.getFid(), page, new Callback<List<MiningRecord>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<MiningRecord>> response) {
                dispatchResult(response.data);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBannerView != null) {
            mBannerView.startAutoPlay();
        }
        mUser = SharedPreferencesTool.getUser(getContext().getApplicationContext());
    }

    @Override
    public void onDestroy() {
        mBannerView.stopAutoPlay();
        mBannerView.releaseBanner();
        super.onDestroy();
    }

    @Override
    protected void initView(View parentView) {
        mScreenWidth = DimensionTool.getScreenWidth(getContext());
        super.initView(parentView);
        mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void initData() {
        super.initData();
        fetchBannerData();
    }

    @Override
    public View getHeaderView() {
        if (mHeaderView == null) {
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_mine, mRecyclerView, false);
            initHeaderView();
        }
        return mHeaderView;
    }

    /**
     * 初始化header相关控件
     */
    private void initHeaderView() {
        RelativeLayout cityContainer = mHeaderView.findViewById(R.id.city_container);
        mCityImageView = mHeaderView.findViewById(R.id.city_image_view);
        mCalculateTextView = mHeaderView.findViewById(R.id.calculate_text_view);
        mTaskImageView = mHeaderView.findViewById(R.id.task_image_view);
        mInviteImageView = mHeaderView.findViewById(R.id.invite_image_view);
        mBannerView = mHeaderView.findViewById(R.id.banner_view);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cityContainer.getLayoutParams();
        layoutParams.height = (int) (379.67 / 375 * mScreenWidth);
        cityContainer.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams bannerLayoutParams = (LinearLayout.LayoutParams) mBannerView.getLayoutParams();
        bannerLayoutParams.height = (int) (100f / 375 * mScreenWidth);
        mBannerView.setLayoutParams(bannerLayoutParams);
        setBannerStyle();

        mTaskImageView.setOnClickListener(this);
        mInviteImageView.setOnClickListener(this);
        mCityImageView.setOnClickListener(this);
        mCityImageView.setOnRectClickListener(this);

        AnimationDrawable drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_mine_header);
        mCityImageView.setBackgroundDrawable(drawable);
        drawable.start();
    }

    /**
     * 设置banner样式
     */
    private void setBannerStyle() {
        if (mBannerView == null) return;
        //设置banner样式
        mBannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBannerView.setImageLoader(new BannerImageLoader());
        //设置banner动画效果
        mBannerView.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mBannerView.isAutoPlay(true);
        //设置轮播时间
        mBannerView.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBannerView.setIndicatorGravity(BannerConfig.CENTER);

        mBannerView.setOnBannerListener(this);
    }

    private void fetchMineStatus() {
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        Api.getInstance().fetchMineAreaStatus(mUser.getFid(), new Callback<List<MineAreaStatus>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<MineAreaStatus>> response) {
                mMineAreaStatusList = response.data;
                bindMineAreaStatusData(mMineAreaStatusList);
            }
        });
    }

    private void fetchTask() {
        if (mUser == null) return;
        Api.getInstance().fetchCalculationTask(mUser.getFid(), new Callback<CalculationTask>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<CalculationTask> response) {
                if (isSuccess && response.data != null) {
                    mCalculateTextView.setText(response.data.getFability());
                }
            }
        });
    }

    boolean usdtIsOpen = false;
    boolean mhtIsOpen = false;
    boolean bannerLeftIsOpen = false;
    boolean bannerRightIsOpen = false;
    boolean mIsOpen = false;
    boolean candyIsOpen = false;

    double usdtMoney = 0;
    double mhtMoney = 0;
    double bannerLeftMoney = 0;
    double bannerRightMoney = 0;
    double mMoney = 0;
    double candyMoney = 0;

    private void bindMineAreaStatusData(List<MineAreaStatus> statusList) {
        if (statusList != null && statusList.size() > 0) {
            for (MineAreaStatus status : statusList) {
                if (status != null) {
                    if (MineAreaStatus.MINE_SHORT_NAME_USDT.equals(status.getShortname())) {
                        usdtIsOpen = status.isOpen();
                        usdtMoney = status.getNum();
                    } else if (MineAreaStatus.MINE_SHORT_NAME_CANDY.equals(status.getShortname())) {
                        candyIsOpen = status.isOpen();
                        candyMoney = status.getNum();
                    } else if (MineAreaStatus.MINE_SHORT_NAME_MHT.equals(status.getShortname())) {
                        mhtIsOpen = status.isOpen();
                        mhtMoney = status.getNum();
                    } else if (MineAreaStatus.MINE_SHORT_NAME_BANNER_LEFT.equals(status.getShortname())) {
                        bannerLeftIsOpen = status.isOpen();
                        bannerLeftMoney = status.getNum();
                    } else if (MineAreaStatus.MINE_SHORT_NAME_BANNER_RIGHT.equals(status.getShortname())) {
                        bannerRightIsOpen = status.isOpen();
                        bannerRightMoney = status.getNum();
                    } else if (MineAreaStatus.MINE_SHORT_NAME_M.equals(status.getShortname())) {
                        mIsOpen = status.isOpen();
                        mMoney = status.getNum();
                    }
                }
            }
        } else {

        }

//        if (!usdtIsOpen) {//usdt未开启
//            mCityImageView.setUSDTEnable(false);
//        } else {//usdt已开启
//            mCityImageView.setMhtEnable(false);
//            if (usdtMoney > 0) {//usdt有产出可点击
//                mCityImageView.setUSDTEnable(true);
//            } else {//usdt没有产出不可点击
//                mCityImageView.setUSDTEnable(false);
//            }
//        }
        if (usdtIsOpen) {
            mCityImageView.setUSDTEnable(usdtMoney > 0);
        } else {
            mCityImageView.setUSDTEnable(true);
        }
        mCityImageView.setMhtEnable(mhtIsOpen && mhtMoney > 0);
        mCityImageView.setBannerLeftEnable(bannerLeftIsOpen && bannerLeftMoney > 0);
        mCityImageView.setBannerRightEnable(bannerRightIsOpen && bannerRightMoney > 0);
        mCityImageView.setMEnable(mIsOpen && mMoney > 0);
        mCityImageView.setCandyEnable(candyIsOpen && candyMoney > 0);
        mCityImageView.setIPOEnable(true);
        mCityImageView.setOtherEnable(true);
    }

    private void fetchBannerData() {
        Api.getInstance().fetchBannerList(new Callback<List<com.ifenduo.mahattan_x.entity.Banner>>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<List<com.ifenduo.mahattan_x.entity.Banner>> response) {
                bindBannerData(response.data);
            }
        });

    }

    private void bindBannerData(List<com.ifenduo.mahattan_x.entity.Banner> bannerList) {
        List<String> urlList = new ArrayList<>();
        if (bannerList != null && bannerList.size() > 0) {
            for (com.ifenduo.mahattan_x.entity.Banner banner : bannerList) {
                if (banner != null && !TextUtils.isEmpty(banner.getThumb())) {
                    urlList.add(banner.getThumb());
                }
            }

        }
        mBannerView.setImages(urlList);
        mBannerView.start();
    }

    @Override
    public void onBindView(ViewHolder holder, MiningRecord record, int position) {
        if (record != null) {
            holder.setText(R.id.num_text_view, "+" + String.valueOf(NumberUtil.formatMoney(record.getValue())));
            holder.setText(R.id.unit_text_view, record.getCoin_name());
            holder.setText(R.id.time_text_view, DateTimeTool.getYYYYMMDDHHMMSS(record.getInputtime()));
        } else {
            holder.setText(R.id.num_text_view, "");
            holder.setText(R.id.unit_text_view, "");
            holder.setText(R.id.time_text_view, "");
        }
    }

    private void showShareDialog() {
        ShareDialog shareDialog = new ShareDialog(getContext());
        shareDialog.setOnShareTypeClickListener(this);
        shareDialog.show();
    }


    @Override
    public void onRectClick(int rectType) {
        if (rectType == CityLayout.RECT_CLICK_TYPE_USDT) {
            if (!usdtIsOpen) {
                showMessageDialog("USDT矿池尚未激活", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                });
            } else {
                if (usdtMoney > 0) {
                    submitMining(MineAreaStatus.MINE_SHORT_NAME_USDT);
                } else {
                    showMessageDialog("USDT矿池尚没有产出", new MessageDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClickListener(MessageDialog messageDialog, View button) {
                            messageDialog.dismiss();
                        }
                    });
                }
            }

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_CANDY) {
            if (!candyIsOpen) {
                showMessageDialog("Candy矿池尚未激活", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                });
            } else {
                if (candyMoney > 0) {
                    submitMining(MineAreaStatus.MINE_SHORT_NAME_CANDY);
                } else {
                    showMessageDialog("Candy矿池尚没有产出", new MessageDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClickListener(MessageDialog messageDialog, View button) {
                            messageDialog.dismiss();
                        }
                    });
                }
            }

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_BANNER_LEFT) {
            if (!bannerLeftIsOpen) {
                showMessageDialog("广告矿池尚未激活", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                });
            } else {
                if (bannerLeftMoney > 0) {
                    submitMining(MineAreaStatus.MINE_SHORT_NAME_BANNER_LEFT);
                } else {
                    showMessageDialog("广告矿池尚没有产出", new MessageDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClickListener(MessageDialog messageDialog, View button) {
                            messageDialog.dismiss();
                        }
                    });
                }
            }

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_BANNER_RIGHT) {
            if (!bannerRightIsOpen) {
                showMessageDialog("广告矿池尚未激活", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                });
            } else {
                if (bannerRightMoney > 0) {
                    submitMining(MineAreaStatus.MINE_SHORT_NAME_BANNER_RIGHT);
                } else {
                    showMessageDialog("广告矿池尚没有产出", new MessageDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClickListener(MessageDialog messageDialog, View button) {
                            messageDialog.dismiss();
                        }
                    });
                }
            }

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_M) {
            if (!mIsOpen) {
                showMessageDialog("M币矿池尚未激活", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                });
            } else {
                if (mMoney > 0) {
                    submitMining(MineAreaStatus.MINE_SHORT_NAME_M);
                } else {
                    showMessageDialog("M币矿池尚没有产出", new MessageDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClickListener(MessageDialog messageDialog, View button) {
                            messageDialog.dismiss();
                        }
                    });
                }
            }


        } else if (rectType == CityLayout.RECT_CLICK_TYPE_MHT) {
            if (!mhtIsOpen) {
                showMessageDialog("Mht-x矿池尚未激活", new MessageDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClickListener(MessageDialog messageDialog, View button) {
                        messageDialog.dismiss();
                    }
                });
            } else {
                if (mhtMoney > 0) {
                    submitMining(MineAreaStatus.MINE_SHORT_NAME_MHT);
                } else {
                    showMessageDialog("Mht-x矿池尚没有产出", new MessageDialog.OnButtonClickListener() {
                        @Override
                        public void onButtonClickListener(MessageDialog messageDialog, View button) {
                            messageDialog.dismiss();
                        }
                    });
                }
            }

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_BILLBOARD) {//闪动的广告牌

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_IPO) {
            showMessageDialog("IPO矿池正在建设中，敬请期待", new MessageDialog.OnButtonClickListener() {
                @Override
                public void onButtonClickListener(MessageDialog messageDialog, View button) {
                    messageDialog.dismiss();
                }
            });

        } else if (rectType == CityLayout.RECT_CLICK_TYPE_OTHER) {
            MineAreaActivity.openActivity((BaseActivity) getContext(), MineAreaActivity.class, null);
        }
    }

    private void submitMining(String shortName) {
        if (mUser != null && mMineAreaStatusList != null && mMineAreaStatusList.size() > 0 && !TextUtils.isEmpty(shortName)) {
            MineAreaStatus status = null;
            for (MineAreaStatus status_ : mMineAreaStatusList) {
                if (status_ != null && shortName.equals(status_.getShortname())) {
                    status = status_;
                    break;
                }
            }
            if (status != null) {
                Api.getInstance().submitMining(mUser.getFid(), status.getGetid(), new Callback<BaseEntity>() {
                    @Override
                    public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                        if (isSuccess) {
                            playCoinMusic();
                            forceRefresh();
                        } else {
                            showToast(msg);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onShareTypeClick(int type) {
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        ClipboardManager tvCopy = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        tvCopy.setText(URL_SHARE + mUser.getFid());
        if (type == SHARE_TYPE_WECHAT) {
            showShareMessageDialog("分享链接已复制，是否打开微信，粘贴发送给好友", new MessageDialog.OnButtonClickListener() {
                @Override
                public void onButtonClickListener(MessageDialog messageDialog, View button) {
                    openWechat();
                    messageDialog.dismiss();
                }
            });
        } else if (type == SHARE_TYPE_GOOGLE) {
            showShareMessageDialog("分享链接已复制，是否打开GooglePay，粘贴发送给好友", new MessageDialog.OnButtonClickListener() {
                @Override
                public void onButtonClickListener(MessageDialog messageDialog, View button) {
                    openGooglePlus();
                    messageDialog.dismiss();
                }
            });
        } else if (type == SHARE_TYPE_FACE_BOOK) {
            showShareMessageDialog("分享链接已复制，是否打开FaceBook，粘贴发送给好友", new MessageDialog.OnButtonClickListener() {
                @Override
                public void onButtonClickListener(MessageDialog messageDialog, View button) {
                    openFaceBook();
                    messageDialog.dismiss();
                }
            });
        } else if (type == SHARE_TYPE_TWITTER) {
            showShareMessageDialog("分享链接已复制，是否打开Twitter，粘贴发送给好友", new MessageDialog.OnButtonClickListener() {
                @Override
                public void onButtonClickListener(MessageDialog messageDialog, View button) {
                    openTwitter();
                    messageDialog.dismiss();
                }
            });
        }
    }


    private void showShareMessageDialog(String message, MessageDialog.OnButtonClickListener onButtonClickListener) {
        MessageDialog messageDialog = new MessageDialog(getContext());
        messageDialog.builder();
        messageDialog.setMsg(message);
        messageDialog.setPositiveButton("去分享", onButtonClickListener);
        messageDialog.setNegativeButton("取消", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                messageDialog.dismiss();
            }
        });
        messageDialog.show();
    }

    /**
     * 跳转到微信
     *  
     */
    private void openGooglePlus() {
        Intent intent;
        PackageManager packageManager = getContext().getPackageManager();
        intent = packageManager.getLaunchIntentForPackage("com.google.android.apps.plus");
        if (intent == null) {
            showToast("你尚未安装Google+");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    /**
     *  * 跳转到微信
     *  
     */
    private void openWechat() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            showToast("检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    private void openFaceBook() {
        Intent intent;
        PackageManager packageManager = getContext().getPackageManager();
        intent = packageManager.getLaunchIntentForPackage("com.facebook.katana");
        if (intent == null) {
            showToast("你尚未安装FaceBook");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void openTwitter() {
        Intent intent;
        PackageManager packageManager = getContext().getPackageManager();
        intent = packageManager.getLaunchIntentForPackage("com.twitter.android");
        if (intent == null) {
            showToast("你尚未安装Twitter");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void showMessageDialog(String message, MessageDialog.OnButtonClickListener onButtonClickListener) {
        MessageDialog messageDialog = new MessageDialog(getContext());
        messageDialog.builder();
        messageDialog.setMsg(message);
        messageDialog.setNegativeButton("取消", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                messageDialog.dismiss();
            }
        });
        messageDialog.setPositiveButton("确定", onButtonClickListener);
        messageDialog.show();
    }

    private void playCoinMusic() {
        MediaPlayer player = MediaPlayer.create(getContext(), R.raw.coin_music);
        player.start();
    }
}
