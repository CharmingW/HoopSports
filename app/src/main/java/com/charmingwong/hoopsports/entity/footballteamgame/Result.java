package com.charmingwong.hoopsports.entity.footballteamgame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/3/6.
 */

public class Result implements Serializable {
    private String key;

    private java.util.List<List> list ;

    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
    public void setList(java.util.List<List> list){
        this.list = list;
    }
    public java.util.List<List> getList(){
        return this.list;
    }
}
