package com.charmingwong.hoopsports.entity.nbagame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/2/28.
 */

public class Result implements Serializable {
    private String title;

    private Statuslist statuslist;

    private java.util.List<List> list ;

    private java.util.List<Teammatch> teammatch ;

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setStatuslist(Statuslist statuslist){
        this.statuslist = statuslist;
    }
    public Statuslist getStatuslist(){
        return this.statuslist;
    }
    public void setList(java.util.List<List> list){
        this.list = list;
    }
    public java.util.List<List> getList(){
        return this.list;
    }
    public void setTeammatch(java.util.List<Teammatch> teammatch){
        this.teammatch = teammatch;
    }
    public java.util.List<Teammatch> getTeammatch(){
        return this.teammatch;
    }
}
