package com.charmingwong.hoopsports.adapter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.charmingwong.hoopsports.R;

/**
 * Created by 56223 on 2017/3/2.
 */

public class NBAOtherAdapter extends FragmentPagerAdapter {

    private static final String[] TITLE = {"常规赛", "球队排名", "球员排名", "社区讨论"};
    private static final String[] URL = {
            "http://xw.qq.com/iphone/m/sports/nba/saicheng.htm",
            "http://sports.qq.com/nba/standings/?ptag=360.onebox.schedule.nba",
            "http://nba.stats.qq.com/stats/?ptag=360.onebox.schedule.nba",
            "http://sports.qq.com/fans/group.htm?mid=69"
    };

    public NBAOtherAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NBAOtherFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }

    public static class NBAOtherFragment extends Fragment {

        public static NBAOtherFragment newInstance(int position) {
            NBAOtherFragment fragment = new NBAOtherFragment();
            Bundle args = new Bundle();
            args.putInt("page", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_nba_other_item, container, false);
            WebView webview = (WebView) rootView.findViewById(R.id.webView);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setSupportZoom(true);
            webview.getSettings().setBuiltInZoomControls(true);
            webview.getSettings().setDisplayZoomControls(false);
            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webview.getSettings().setAppCacheEnabled(true);
            webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webview.setWebViewClient(new WebViewClient());
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                }
            });
            webview.loadUrl("http://sports.qq.com/kbsweb/kbsshare/gamelist.htm?#nav-nba");
            return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

    }


}
