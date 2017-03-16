package com.charmingwong.hoopsports.entity.footballgame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/3/6.
 */

public class Result implements Serializable {
    private String key;

    private Tabs tabs;

    private Views views;

    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
    public void setTabs(Tabs tabs){
        this.tabs = tabs;
    }
    public Tabs getTabs(){
        return this.tabs;
    }
    public void setViews(Views views){
        this.views = views;
    }
    public Views getViews(){
        return this.views;
    }
}
