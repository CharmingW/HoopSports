package com.charmingwong.hoopsports.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.activity.MainActivity;
import com.charmingwong.hoopsports.activity.NBATeamGameActivity;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.config.Presenter;
import com.charmingwong.hoopsports.presenter.NBATeamPresenter;

/**h
 * Created by 56223 on 2017/2/28.
 */

public class NBAPagerAdapter extends FragmentPagerAdapter {

    private static final String[] NBA_TYPE = {"赛程", "球队", "数据"};

    public NBAPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        NBAFragment fragment = NBAFragment.newInstance(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return NBA_TYPE.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return NBA_TYPE[position];
    }

    public static class NBAFragment extends Fragment {

        public static NBAFragment newInstance(int page) {
            NBAFragment fragment = new NBAFragment();
            Bundle args = new Bundle();
            args.putInt("page", page);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View rootView;
            int page = getArguments().getInt("page");
            if (page == 0) {
                rootView = inflater.inflate(R.layout.fragment_nba_game, container, false);
                rootView.findViewById(R.id.refresh_image).setOnClickListener(((View.OnClickListener) rootView.getContext()));
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.game_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                recyclerView.addItemDecoration(new ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.set(30, 30, 30, 0);
                    }
                });
                if (Data.nbaGameData != null) {
                    recyclerView.setAdapter(new NBAGameListAdapter(rootView.getContext()));
                } else {
                    rootView.findViewById(R.id.error_refresh).setVisibility(View.VISIBLE);
                }
            } else if (page == 1) {
                rootView = inflater.inflate(R.layout.fragment_nba_team, container, false);
                GridView gridView = (GridView) rootView.findViewById(R.id.nba_team_list);
                final NBATeamAdapter adapter = new NBATeamAdapter(rootView.getContext());
                gridView.setAdapter(adapter);
                rootView.findViewById(R.id.text_ensure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Presenter.nbaTeamPresenter == null) {
                            Presenter.nbaTeamPresenter = NBATeamPresenter.getInstance(v.getContext());
                        }
                        Intent intent = new Intent(container.getContext(), NBATeamGameActivity.class);
                        if (adapter.getmCount() == 1) {
                            intent.putExtra("home" ,adapter.getSelectedTeams().get(0));
                        } else {
                            intent.putExtra("home" , adapter.getSelectedTeams().get(0));
                            intent.putExtra("guest" ,adapter.getSelectedTeams().get(1));
                        }
                        container.getContext().startActivity(intent);
                    }
                });
            } else {
                rootView = inflater.inflate(R.layout.fragment_nba_other, container, false);
                WebView webView = (WebView) rootView.findViewById(R.id.webView_nba);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setDisplayZoomControls(false);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.getSettings().setAppCacheEnabled(true);
                webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                webView.setWebViewClient(new WebViewClient() {
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

                webView.loadUrl("http://sports.qq.com/kbsweb/kbsshare/gamelist.htm?#nav-nba");            }
            return rootView;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

    }

}
