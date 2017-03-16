package com.charmingwong.hoopsports.entity.nbagame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/2/28.
 */

public class Bottomlink implements Serializable {
    private String text;

    private String url;

    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
