package com.ifenduo.mahattan_x.controller.me;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_PERSONAL_CENTER_SHARE;

public class ShareActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView mWebView;

    String mUrl;

    @Override
    protected boolean toolbarIsEnable() {
        return false;
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, null);
        init();
    }

    private void init() {
        User user = SharedPreferencesTool.getUser(getApplicationContext());
        if (user == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        mUrl = URL_PERSONAL_CENTER_SHARE + user.getFid();

        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //优先使用缓存
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        showProgress(null);
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        mWebView.loadUrl(mUrl);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return false;
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    dismissProgress();
                } else {
                    showProgress();
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    @OnClick({R.id.cover_view})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(mUrl)) return;
        ClipboardManager tvCopy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        tvCopy.setText(mUrl);
        showToast("分享链接已复制");
    }
}
