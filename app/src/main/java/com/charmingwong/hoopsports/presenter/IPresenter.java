package com.charmingwong.hoopsports.presenter;

/**
 * Created by CharmingWong on 2016/11/28.
 */

public interface IPresenter {

    /**
     * 开始加载数据
     */
    public void startPresent();

    /**
     * 请求成功
     *
     * @param result 请求返回数据
     */
    public void onRequestCompleted(Object result);

    /**
     * 返回解析后的数据
     *
     * @param ParsedData 传入的原始数据
     * @throws Exception 数据有误抛出异常
     */
    public void returnData(Object ParsedData) throws Exception;

}