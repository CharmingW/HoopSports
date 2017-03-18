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
import com.charmingwong.hoopsports.adapter.FootballTeamGameListAdapter;
import com.charmingwong.hoopsports.comminterface.OnResponseCallback;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.config.Presenter;

import java.util.Map;

public class FootballTeamActivity
        extends AppCompatActivity
        implements OnResponseCallback, View.OnClickListener {

    private static final String TAG = "FootballTeamActivity";
    private RecyclerView mGameList;
    private View mErrorView;
    private FootballTeamGameListAdapter mAdapter;

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
        Presenter.footballTeamPresenter.setOnResponseCallback(this);
        Presenter.footballTeamPresenter.setTeamName(getIntent().getStringExtra("team"));
        Log.i(TAG, "init: " + getIntent().getStringExtra("team"));
        Presenter.footballTeamPresenter.startPresent();
    }

    @Override
    public void onResponseSuccess(Object response) {
        mErrorView.setVisibility(View.GONE);
        Data.footballTeamGameData = (Map<String, Object>) response;
        ((TextView) findViewById(R.id.title)).setText((String) Data.footballTeamGameData.get("title"));
        if (mGameList.getAdapter() == null) {
            mAdapter = new FootballTeamGameListAdapter();
            mGameList.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(Exception e) {
        mErrorView.setVisibility(View.VISIBLE);
        Toast.makeText(this, R.string.host_no_data_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_refresh:
                mErrorView.findViewById(R.id.error_image).animate().setDuration(3000).rotationBy(1800);
                Presenter.footballTeamPresenter.startPresent();
                break;
            case R.id.btn_back:
                super.onBackPressed();
                break;
        }
    }
}
