package com.charmingwong.hoopsports.entity.nbagame;

import java.io.Serializable;

/**
 * Created by 56223 on 2017/2/28.
 */

public class Tr implements Serializable {
    private String time;

    private String player1;

    private String player2;

    private String player1logo;

    private String player2logo;

    private String player1logobig;

    private String player2logobig;

    private String player1url;

    private String player2url;

    private String link1url;

    private String m_link1url;

    private String link2text;

    private String link2url;

    private String m_link2url;

    private int status;

    private String score;

    private String link1text;

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setPlayer1(String player1){
        this.player1 = player1;
    }
    public String getPlayer1(){
        return this.player1;
    }
    public void setPlayer2(String player2){
        this.player2 = player2;
    }
    public String getPlayer2(){
        return this.player2;
    }
    public void setPlayer1logo(String player1logo){
        this.player1logo = player1logo;
    }
    public String getPlayer1logo(){
        return this.player1logo;
    }
    public void setPlayer2logo(String player2logo){
        this.player2logo = player2logo;
    }
    public String getPlayer2logo(){
        return this.player2logo;
    }
    public void setPlayer1logobig(String player1logobig){
        this.player1logobig = player1logobig;
    }
    public String getPlayer1logobig(){
        return this.player1logobig;
    }
    public void setPlayer2logobig(String player2logobig){
        this.player2logobig = player2logobig;
    }
    public String getPlayer2logobig(){
        return this.player2logobig;
    }
    public void setPlayer1url(String player1url){
        this.player1url = player1url;
    }
    public String getPlayer1url(){
        return this.player1url;
    }
    public void setPlayer2url(String player2url){
        this.player2url = player2url;
    }
    public String getPlayer2url(){
        return this.player2url;
    }
    public void setLink1url(String link1url){
        this.link1url = link1url;
    }
    public String getLink1url(){
        return this.link1url;
    }
    public void setM_link1url(String m_link1url){
        this.m_link1url = m_link1url;
    }
    public String getM_link1url(){
        return this.m_link1url;
    }
    public void setLink2text(String link2text){
        this.link2text = link2text;
    }
    public String getLink2text(){
        return this.link2text;
    }
    public void setLink2url(String link2url){
        this.link2url = link2url;
    }
    public String getLink2url(){
        return this.link2url;
    }
    public void setM_link2url(String m_link2url){
        this.m_link2url = m_link2url;
    }
    public String getM_link2url(){
        return this.m_link2url;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setScore(String score){
        this.score = score;
    }
    public String getScore(){
        return this.score;
    }
    public void setLink1text(String link1text){
        this.link1text = link1text;
    }
    public String getLink1text(){
        return this.link1text;
    }
}
