package com.charmingwong.hoopsports.comminterface;

/**
 * Created by 56223 on 2016/11/27.
 */

public interface OnResponseCallback {

    /**
     * 响应成功回调该方法
     *
     * @param response 请求的数据
     */
    public void onResponseSuccess(Object response);

    /**
     * 响应失败响应该方法
     *
     * @param e 异常信息
     */
    public void onResponseError(Exception e);
}
