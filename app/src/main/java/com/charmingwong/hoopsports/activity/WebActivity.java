package com.charmingwong.hoopsports.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.util.ApplicationUtil;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    private PopupMenu mPopupMenu;
    private ProgressBar mProgressBar;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();
    }

    private void init() {

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_web_menu).setOnClickListener(this);

        mUrl = getIntent().getData().toString();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.news_webpage);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                View errorView = findViewById(R.id.error_refresh);
//                errorView.setOnClickListener(WebActivity.this);
//                errorView.setVisibility(View.VISIBLE);
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                startProgressBarAnimation();
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                if (newProgress == 100) {
//                    mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
//                }
//            }


        });

        mWebView.loadUrl(mUrl);
    }

    private void startProgressBarAnimation() {
        ValueAnimator va = ValueAnimator.ofInt(0, 900);
        va.setDuration(3000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer i = (Integer) animation.getAnimatedValue();
                mProgressBar.setProgress(i);
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        va.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_web_menu:
                if (mPopupMenu == null) {
                    mPopupMenu = new PopupMenu(this, v, Gravity.TOP);
                    mPopupMenu.inflate(R.menu.news_details);
                    mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.share:
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
                                    shareIntent.setType("text/plain");
                                    startActivity(Intent.createChooser(shareIntent, "分享"));
                                    break;
                                case R.id.open_in_browser:
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                    browserIntent.setData(Uri.parse(mUrl));
                                    startActivity(browserIntent);
                                    break;
                            }
                            return false;
                        }
                    });
                }
                mPopupMenu.show();
            case R.id.error_refresh:
                findViewById(R.id.error_refresh).setVisibility(View.GONE);
                mWebView.loadUrl(mWebView.getUrl());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationUtil.setMiuiStatusBarDarkMode(this, true);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
