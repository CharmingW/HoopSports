package com.charmingwong.hoopsports.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.adapter.NBATeamGameListAdapter;
import com.charmingwong.hoopsports.comminterface.OnResponseCallback;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.config.Presenter;
import com.charmingwong.hoopsports.parser.NBATeamParser;
import com.charmingwong.hoopsports.presenter.NBARegularPresenter;

import java.util.Map;

public class NBATeamGameActivity
        extends AppCompatActivity
        implements View.OnClickListener, OnResponseCallback {


    private static final String TAG = "NBATeamDGameActivity";
    private RecyclerView mGameList;
    private View mErrorView;
    private NBATeamGameListAdapter mAdapter;
    private int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_game);
        init();
    }

    private void init() {
        mErrorView = findViewById(R.id.error_refresh);
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
        Presenter.nbaTeamPresenter.setOnResponseCallback(this);
        Presenter.nbaTeamPresenter.setTeam(
                getIntent().getStringExtra("home"),
                getIntent().getStringExtra("guest")
        );
        Presenter.nbaTeamPresenter.startPresent();
    }

    @Override
    public void onResponseSuccess(Object response) {
        Data.nbaTeamGameData = (Map<String, Object>) response;
        ((TextView) findViewById(R.id.title)).setText((String) Data.nbaTeamGameData.get("title"));
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter = new NBATeamGameListAdapter();
        mGameList.setAdapter(mAdapter);
    }

    @Override
    public void onResponseError(Exception e) {
        if (mErrorView.getAnimation() != null) {
            mErrorView.getAnimation().cancel();
        }
        mErrorView.setVisibility(View.VISIBLE);
        if (mFlag != 0) {
            Toast.makeText(this, R.string.host_no_data_text, Toast.LENGTH_SHORT).show();
        }
        mFlag++;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_refresh:
                mErrorView.findViewById(R.id.error_image).animate().setDuration(2000).rotationBy(1800);
                Presenter.nbaTeamPresenter.startPresent();
                break;
            case R.id.btn_back:
                super.onBackPressed();
                break;
        }
    }
}
