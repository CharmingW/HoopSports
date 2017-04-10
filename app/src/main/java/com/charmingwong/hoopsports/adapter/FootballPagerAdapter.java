package com.charmingwong.hoopsports.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.activity.FootballLeagueActivity;
import com.charmingwong.hoopsports.comminterface.OnResponseCallback;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.config.Presenter;
import com.charmingwong.hoopsports.presenter.FootballLeaguePresenter;
import com.charmingwong.hoopsports.presenter.FootballTeamPresenter;

import java.util.Map;

/**
 * Created by 56223 on 2017/3/2.
 */

public class FootballPagerAdapter extends FragmentPagerAdapter {

    private static final String[] NBA_TYPE = {"联赛", "球队", "排行"};

    public FootballPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FootballFragment fragment = FootballFragment.newInstance(position);
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

    public static class FootballFragment extends Fragment {

        public static FootballFragment newInstance(int page) {
            FootballFragment fragment = new FootballFragment();
            Bundle args = new Bundle();
            args.putInt("page", page);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            int page = getArguments().getInt("page");
            if (page == 0) {
                final View rootView = inflater.inflate(R.layout.football_league, container, false);
                setLeagueAction(rootView);
                return rootView;
            } else if (page == 1) {
                final View rootView = inflater.inflate(R.layout.fragment_football_team, container, false);
                if (Presenter.footballTeamPresenter == null) {
                    Presenter.footballTeamPresenter = FootballTeamPresenter.getInstance(rootView.getContext());
                }
                GridView gridView = (GridView) rootView.findViewById(R.id.football_team_list);
                final FootballTeamListAdapter adapter = new FootballTeamListAdapter(rootView.getContext());
                adapter.setChannel(R.id.yc);
                gridView.setAdapter(adapter);
                final ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.football_league_list);
                viewGroup.getChildAt(0).setBackgroundColor(rootView.getContext().getResources().getColor(R.color.white));
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewGroup.findViewById(adapter.getChannel())
                                    .setBackgroundColor(v.getContext().getResources().getColor(R.color.lighter_gray));
                            v.setBackgroundColor(v.getContext().getResources().getColor(R.color.white));
                            adapter.setChannel(v.getId());
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                return rootView;
            } else {
                final View rootView = inflater.inflate(R.layout.fragment_football_ranking, container, false);
                if (Presenter.footballLeaguePresenter == null) {
                    Presenter.footballLeaguePresenter = FootballLeaguePresenter.getInstance(rootView.getContext());
                }
                final ListView rankingList = (ListView) rootView.findViewById(R.id.ranking_list);
                final FootballRankingAdapter adapter1 = new FootballRankingAdapter(rootView.getContext());
                final FootballShooterRankingAdapter adapter2 = new FootballShooterRankingAdapter(rootView.getContext());
                final OnResponseCallback callback1 = new OnResponseCallback() {
                    @Override
                    public void onResponseSuccess(Object response) {
                        Data.footballGameData = (Map<String, Object>) response;
                        rankingList.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponseError(Exception e) {

                    }
                };

                final OnResponseCallback callback2 = new OnResponseCallback() {
                    @Override
                    public void onResponseSuccess(Object response) {
                        Data.footballGameData = (Map<String, Object>) response;
                        rankingList.setAdapter(adapter2);
                        adapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponseError(Exception e) {

                    }
                };
                Presenter.footballLeaguePresenter.setOnResponseCallback(callback1);

                //积分榜和射手榜监听器
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //积分榜选中
                        if (v.getId() == R.id.rank_score) {
                            Presenter.footballLeaguePresenter.setOnResponseCallback(callback1);
                            v.setBackground(v.getContext().getDrawable(R.drawable.btn_shape));
                            ((TextView) v).setTextColor(Color.WHITE);
                            TextView shooter = (TextView) rootView.findViewById(R.id.rank_shooter);
                            shooter.setBackgroundColor(Color.WHITE);
                            shooter.setTextColor(Color.BLACK);
                            rankingList.removeHeaderView((View) rankingList.getTag());
                            View header = inflater.inflate(R.layout.item_team_ranking_header, rankingList, false);
                            rankingList.setTag(header);
                            rankingList.addHeaderView(header);
                            final ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.football_league_list);

                            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                                viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        viewGroup.findViewById((Integer) rankingList.getTag(R.id.football_league_list))
                                                .setBackgroundColor(v.getContext().getResources().getColor(R.color.lighter_gray));
                                        v.setBackgroundColor(v.getContext().getResources().getColor(R.color.white));
                                        rankingList.setTag(R.id.football_league_list, v.getId());
                                        String league = getLeagueNameFromId(v.getId());
                                        Log.i("FootballPagerAdapter", "onClick: " + league);
                                        Presenter.footballLeaguePresenter.setLeagueName(league);
                                        Presenter.footballLeaguePresenter.startPresent();
                                    }
                                });
                            }
                            if (rankingList.getTag(R.id.football_league_list) == null) {
                                View view = viewGroup.getChildAt(0);
                                view.setBackgroundColor(rootView.getContext().getResources().getColor(R.color.white));
                                rankingList.setTag(R.id.football_league_list, view.getId());
                                Presenter.footballLeaguePresenter.setLeagueName(getLeagueNameFromId(view.getId()));
                            } else {
                                Presenter.footballLeaguePresenter.setLeagueName(getLeagueNameFromId((Integer) rankingList.getTag(R.id.football_league_list)));
                            }
                            Presenter.footballLeaguePresenter.startPresent();
                        } else {    //射手榜选中
                            Presenter.footballLeaguePresenter.setOnResponseCallback(callback2);
                            v.setBackground(v.getContext().getDrawable(R.drawable.btn_shape));
                            ((TextView) v).setTextColor(Color.WHITE);
                            TextView score = (TextView) ((Activity) v.getContext()).findViewById(R.id.rank_score);
                            score.setBackgroundColor(Color.WHITE);
                            score.setTextColor(Color.BLACK);
                            rankingList.removeHeaderView((View) rankingList.getTag());
                            View header = inflater.inflate(R.layout.item_shooter_ranking_header, rankingList, false);
                            rankingList.setTag(header);
                            rankingList.addHeaderView(header);
                            final ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.football_league_list);
                            View.OnClickListener listener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewGroup.findViewById((Integer) rankingList.getTag(R.id.football_league_list))
                                            .setBackgroundColor(v.getContext().getResources().getColor(R.color.lighter_gray));
                                    v.setBackgroundColor(v.getContext().getResources().getColor(R.color.white));
                                    rankingList.setTag(R.id.football_league_list, v.getId());
                                    String league = getLeagueNameFromId(v.getId());
                                    Presenter.footballLeaguePresenter.setOnResponseCallback(callback2);
                                    Presenter.footballLeaguePresenter.setLeagueName(league);
                                    Log.i("FootballPagerAdapter", "onClick: " + league);
                                    Presenter.footballLeaguePresenter.startPresent();
                                }
                            };
                            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                                viewGroup.getChildAt(i).setOnClickListener(listener);
                            }
                            Presenter.footballLeaguePresenter.setOnResponseCallback(callback2);
                            Presenter.footballLeaguePresenter.setLeagueName(getLeagueNameFromId((Integer) rankingList.getTag(R.id.football_league_list)));
                            Presenter.footballLeaguePresenter.startPresent();
                        }
                    }
                };

                //积分榜射手榜设置监听器
                rootView.findViewById(R.id.rank_score).

                        setOnClickListener(listener);
                rootView.findViewById(R.id.rank_shooter).

                        setOnClickListener(listener);
                rootView.findViewById(R.id.rank_score).

                        callOnClick();
                return rootView;
            }
        }


        private void setLeagueAction(final View rootView) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Presenter.footballLeaguePresenter == null) {
                        Presenter.footballLeaguePresenter
                                = FootballLeaguePresenter
                                .getInstance(rootView.getContext());
                    }
                    Intent intent = new Intent(rootView.getContext(), FootballLeagueActivity.class);
                    intent.putExtra(
                            "league",
                            getLeagueNameFromId(v.getId()));
                    rootView.getContext().startActivity(intent);
                }
            };
            rootView.findViewById(R.id.yc).setOnClickListener(listener);
            rootView.findViewById(R.id.xj).setOnClickListener(listener);
            rootView.findViewById(R.id.yj).setOnClickListener(listener);
            rootView.findViewById(R.id.dj).setOnClickListener(listener);
            rootView.findViewById(R.id.fj).setOnClickListener(listener);
            rootView.findViewById(R.id.zc).setOnClickListener(listener);

        }

        /**
         * 根据联赛 view id 获取联赛名
         *
         * @param id 联赛 view id
         * @return 联赛名
         */
        private String getLeagueNameFromId(int id) {
            switch (id) {
                case R.id.yc:
                    return "英超";
                case R.id.xj:
                    return "西甲";
                case R.id.yj:
                    return "意甲";
                case R.id.dj:
                    return "德甲";
                case R.id.fj:
                    return "法甲";
                case R.id.zc:
                    return "中超";
                default:
                    return "";
            }
        }

    }
}
