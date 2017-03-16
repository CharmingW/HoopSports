package com.charmingwong.hoopsports.entity.footballgame;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 56223 on 2017/3/6.
 */

public class Views implements Serializable {
    private List<Saicheng1> saicheng1;

    private List<Saicheng2> saicheng2;

    private String saicheng3;

    private List<Jifenbang> jifenbang;

    private List<Sheshoubang> sheshoubang;

    public void setSaicheng1(List<Saicheng1> saicheng1) {
        this.saicheng1 = saicheng1;
    }

    public List<Saicheng1> getSaicheng1() {
        return this.saicheng1;
    }

    public void setSaicheng2(List<Saicheng2> saicheng2) {
        this.saicheng2 = saicheng2;
    }

    public List<Saicheng2> getSaicheng2() {
        return this.saicheng2;
    }

    public void setSaicheng3(String saicheng3) {
        this.saicheng3 = saicheng3;
    }

    public String getSaicheng3() {
        return this.saicheng3;
    }

    public void setJifenbang(List<Jifenbang> jifenbang) {
        this.jifenbang = jifenbang;
    }

    public List<Jifenbang> getJifenbang() {
        return this.jifenbang;
    }

    public void setSheshoubang(List<Sheshoubang> sheshoubang) {
        this.sheshoubang = sheshoubang;
    }

    public List<Sheshoubang> getSheshoubang() {
        return this.sheshoubang;
    }
}
