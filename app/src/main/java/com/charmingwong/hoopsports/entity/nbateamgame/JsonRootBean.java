/**
 * Copyright 2017 bejson.com
 */
package com.charmingwong.hoopsports.entity.nbateamgame;

import java.io.Serializable;

/**
 * Auto-generated: 2017-03-04 15:28:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean implements Serializable {

    private String reason;
    private Result result;
    private int errorCode;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}