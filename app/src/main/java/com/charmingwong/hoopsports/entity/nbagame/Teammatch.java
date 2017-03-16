package com.charmingwong.hoopsports.entity.nbagame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/2/28.
 */

public class Teammatch implements Serializable {
    private String name;

    private String url;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
