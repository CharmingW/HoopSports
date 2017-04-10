package com.charmingwong.hoopsports.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.Toast;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.adapter.SportsPagerAdapter;
import com.charmingwong.hoopsports.comminterface.OnResponseCallback;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.config.Presenter;
import com.charmingwong.hoopsports.presenter.NBARegularPresenter;
import com.charmingwong.hoopsports.utils.ApplicationUtils;
import com.charmingwong.hoopsports.utils.NetworkUtils;

import java.util.Map;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnResponseCallback {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private SwipeRefreshLayout mRefresh;
    private NetworkStatusReceiver mReceiver;
    private long mFirstBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化基本的view
     */
    private void init() {
        mReceiver = new NetworkStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mReceiver, filter);
        mReceiver.onReceive(this, new Intent("android.net.conn.CONNECTIVITY_CHANGE"));

        //设置刷新动作
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.data_refresh);
        setRefreshAction();

        //设置tablayout和viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sports_type_tl);
        mViewPager = (ViewPager) findViewById(R.id.sports_pager);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(0);
        loadNBARegularData();
    }

    /**
     * 初始化界面时加载nba常规赛数据填充界面
     */
    private void loadNBARegularData() {
        //获取数据
        if (Presenter.nbaRegularPresenter == null) {
            Presenter.nbaRegularPresenter = NBARegularPresenter.getInstance(this);
        }
        Presenter.nbaRegularPresenter.setOnResponseCallback(this);
        Presenter.nbaRegularPresenter.startPresent();
    }

    /**
     *请求数据完成后，填充数据到相应的界面
     *
     * @param data 请求返回的数据集合
     */
    private void initView(Map<String, Object> data) {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
            mRefresh.setEnabled(false);
        }
        Data.nbaGameData = data;
        if (data != null) {
            if (mViewPager.getAdapter() != null) {
                findViewById(R.id.error_refresh).setVisibility(View.GONE);
                (findViewById(R.id.game_list)).post(new Runnable() {
                    @Override
                    public void run() {
                        ((RecyclerView) findViewById(R.id.game_list)).getAdapter().notifyDataSetChanged();
                    }
                });
            } else {
                mViewPager.setAdapter(new SportsPagerAdapter(getSupportFragmentManager()));
            }
        } else {
            if (mViewPager.getAdapter() != null) {
                findViewById(R.id.error_refresh).setVisibility(View.VISIBLE);
                findViewById(R.id.error_refresh).setOnClickListener(this);
            } else {
                mViewPager.setAdapter(new SportsPagerAdapter(getSupportFragmentManager()));
            }
        }
    }

    /**
     * 设置刷新动作
     */
    private void setRefreshAction() {
        mRefresh.setEnabled(false);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Presenter.nbaRegularPresenter.startPresent();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh_image:
                mRefresh.setEnabled(true);
                ApplicationUtils.simulateScroll(mRefresh, 0, 0, 0, 800, 200);
                break;
            case R.id.no_network:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
            case R.id.error_refresh:
                Presenter.nbaRegularPresenter.startPresent();
        }
    }

    /**
     * 检查网络是否可用，不可用则跳出通知条提示用户设置网络
     */
    private void checkNetworkStatus() {
        Boolean isAvailable = NetworkUtils.checkNetworkStatus(this);
        ViewStub viewStub = (ViewStub) findViewById(R.id.no_network_stub);
        if (!isAvailable) {
            View noNetwork;
            if (viewStub != null) {
                viewStub.inflate();
                noNetwork = findViewById(R.id.no_network);
            } else {
                noNetwork = findViewById(R.id.no_network);
                noNetwork.setVisibility(View.VISIBLE);
            }
            noNetwork.setOnClickListener(this);
        } else {
            if (viewStub == null) {
                findViewById(R.id.no_network).setVisibility(View.GONE);
            }
        }
    }

    /**
     *接收网络状态变化广播
     */
    public class NetworkStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetworkStatus();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onBackPressed() {
        int position = ((ViewPager) findViewById(R.id.nba_pager)).getCurrentItem();
        if (position == 2) {
            WebView webView = ((WebView) findViewById(R.id.webView_nba));
            if (webView.canGoBack()) {
                webView.goBack();
                return;
            }
        }
        long current = System.currentTimeMillis();
        if (current - mFirstBackTime < 2000) {
            super.onBackPressed();
        }
        mFirstBackTime = current;
    }

    @Override
    public void onResponseSuccess(Object response) {
        if (response instanceof Map) {
            initView((Map<String, Object>) response);
        }
        Toast.makeText(this, R.string.refresh_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseError(Exception e) {
        initView(null);
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
        }
    }


}
