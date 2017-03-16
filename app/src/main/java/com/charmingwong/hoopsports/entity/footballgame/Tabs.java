package com.charmingwong.hoopsports.entity.footballgame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/3/6.
 */

public class Tabs  implements Serializable {
    private String saicheng1;

    private String saicheng2;

    private String saicheng3;

    private String jifenbang;

    private String sheshoubang;

    public void setSaicheng1(String saicheng1){
        this.saicheng1 = saicheng1;
    }
    public String getSaicheng1(){
        return this.saicheng1;
    }
    public void setSaicheng2(String saicheng2){
        this.saicheng2 = saicheng2;
    }
    public String getSaicheng2(){
        return this.saicheng2;
    }
    public void setSaicheng3(String saicheng3){
        this.saicheng3 = saicheng3;
    }
    public String getSaicheng3(){
        return this.saicheng3;
    }
    public void setJifenbang(String jifenbang){
        this.jifenbang = jifenbang;
    }
    public String getJifenbang(){
        return this.jifenbang;
    }
    public void setSheshoubang(String sheshoubang){
        this.sheshoubang = sheshoubang;
    }
    public String getSheshoubang(){
        return this.sheshoubang;
    }
}
