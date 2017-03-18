package com.charmingwong.hoopsports.config;

import com.charmingwong.hoopsports.presenter.FootballLeaguePresenter;
import com.charmingwong.hoopsports.presenter.FootballTeamPresenter;
import com.charmingwong.hoopsports.presenter.NBARegularPresenter;
import com.charmingwong.hoopsports.presenter.NBATeamPresenter;

/**
 * Created by 56223 on 2017/3/10.
 */

public class Presenter {

    /**
     * 各个频道的数据主导器
     */
    public static NBARegularPresenter nbaRegularPresenter;
    public static NBATeamPresenter nbaTeamPresenter;
    public static FootballLeaguePresenter footballLeaguePresenter;
    public static FootballTeamPresenter footballTeamPresenter;
}
