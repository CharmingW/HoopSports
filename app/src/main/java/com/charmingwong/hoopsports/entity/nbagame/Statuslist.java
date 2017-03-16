package com.charmingwong.hoopsports.entity.nbagame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/2/28.
 */

public class Statuslist implements Serializable {
    private String st0;

    private String st1;

    private String st2;

    public void setSt0(String st0){
        this.st0 = st0;
    }
    public String getSt0(){
        return this.st0;
    }
    public void setSt1(String st1){
        this.st1 = st1;
    }
    public String getSt1(){
        return this.st1;
    }
    public void setSt2(String st2){
        this.st2 = st2;
    }
    public String getSt2(){
        return this.st2;
    }
}
