package com.charmingwong.hoopsports.entity.nbagame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/2/28.
 */

public class List implements Serializable {
    private String title;

    private java.util.List<Tr> tr;

    private java.util.List<Bottomlink> bottomlink ;

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTr(java.util.List<Tr> tr){
        this.tr = tr;
    }
    public java.util.List<Tr> getTr(){
        return this.tr;
    }
    public void setBottomlink(java.util.List<Bottomlink> bottomlink){
        this.bottomlink = bottomlink;
    }
    public java.util.List<Bottomlink> getBottomlink(){
        return this.bottomlink;
    }
}
