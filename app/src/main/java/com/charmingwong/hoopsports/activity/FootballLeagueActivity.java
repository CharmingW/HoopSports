package com.charmingwong.hoopsports.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.adapter.FootballGameListAdapter;
import com.charmingwong.hoopsports.comminterface.OnResponseCallback;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.config.Presenter;

import java.util.Map;

public class FootballLeagueActivity
        extends AppCompatActivity
        implements OnResponseCallback, View.OnClickListener {

    private static final String TAG = "FootballLeagueActivity";
    private RecyclerView mGameList;
    private FootballGameListAdapter mAdapter;
    private View mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_game);
        init();
    }

    /**
     * 初始化视图
     */
    private void init() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        mGameList = (RecyclerView) findViewById(R.id.game_list);
        mGameList.setLayoutManager(new LinearLayoutManager(this));
        mGameList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(30, 30, 30, 0);
            }
        });
        mErrorView = findViewById(R.id.error_refresh);
        mErrorView.setOnClickListener(this);
        Presenter.footballLeaguePresenter.setOnResponseCallback(this);
        Presenter.footballLeaguePresenter.setLeagueName(getIntent().getStringExtra("league"));
        Presenter.footballLeaguePresenter.startPresent();
    }

    @Override
    public void onResponseSuccess(Object response) {
        mErrorView.setVisibility(View.GONE);
        Data.footballGameData = (Map<String, Object>) response;
        ((TextView) findViewById(R.id.title)).setText((String) Data.footballGameData.get("title"));
        if (mGameList.getAdapter() == null) {
            mAdapter = new FootballGameListAdapter(this);
            mGameList.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(Exception e) {
        mErrorView = findViewById(R.id.error_refresh);
        mErrorView.setVisibility(View.VISIBLE);
        Toast.makeText(this, R.string.host_no_data_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_refresh:
                mErrorView.findViewById(R.id.error_image).animate().setDuration(3000).rotationBy(1800);
                Presenter.footballLeaguePresenter.startPresent();
                break;
            case R.id.btn_back:
                super.onBackPressed();
                break;
        }
    }
}
