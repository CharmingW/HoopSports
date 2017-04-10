package com.charmingwong.hoopsports.presenter;

/**
 * Created by CharmingWong on 2017/3/20.
 */

public abstract class LeaguePresenter implements IPresenter {


    /**
     * 请求成功
     *
     * @param result 请求返回数据
     */
    public abstract void onRequestCompleted(Object result, String url);

    @Override
    public void startPresent() {

    }

    @Override
    public void onRequestCompleted(Object result) {

    }

    @Override
    public void returnData(Object ParsedData) throws Exception {

    }
}
