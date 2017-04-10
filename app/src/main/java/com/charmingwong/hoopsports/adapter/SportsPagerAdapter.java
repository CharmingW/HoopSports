package com.charmingwong.hoopsports.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charmingwong.hoopsports.R;

/**
 * Created by 56223 on 2017/2/28.
 */

public class SportsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] SPORTS_TYPE = {"NBA", "足球"};

    public SportsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        SportsFragment fragment = SportsFragment.newInstance(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return SPORTS_TYPE.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return SPORTS_TYPE[position];
    }

    public static class SportsFragment extends Fragment {

        /**
         * @param page ViewPager的页码
         * @return 填充ViewPager页的Fragment
         */
        public static SportsFragment newInstance(int page) {
            SportsFragment fragment = new SportsFragment();
            Bundle args = new Bundle();
            args.putInt("page", page);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView;
            if (getArguments().getInt("page") == 0) {
                rootView = inflater.inflate(R.layout.fragment_nba, container, false);
                ViewPager nbaPager = (ViewPager) rootView.findViewById(R.id.nba_pager);
                nbaPager.setAdapter(
                        new NBAPagerAdapter(((AppCompatActivity) rootView.getContext()).getSupportFragmentManager()));
                nbaPager.setOffscreenPageLimit(1);
                TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.nba_type_tl);
                tabLayout.setupWithViewPager(nbaPager);
            } else {
                rootView = inflater.inflate(R.layout.fragment_football, container, false);
                ViewPager footballPager = (ViewPager) rootView.findViewById(R.id.football_pager);
                footballPager.setOffscreenPageLimit(2);
                footballPager.setAdapter(
                        new FootballPagerAdapter(((AppCompatActivity) rootView.getContext()).getSupportFragmentManager()));
                TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.football_type_tl);
                tabLayout.setupWithViewPager(footballPager);
            }
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

