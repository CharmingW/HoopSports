/**
  * Copyright 2017 bejson.com 
  */
package com.charmingwong.hoopsports.entity.nbateamgame;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2017-03-04 15:28:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result  implements Serializable {

    private String title;
    private List<com.charmingwong.hoopsports.entity.nbateamgame.List> list;
    private More1 more1;
    private More2 more2;
    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setList(List<com.charmingwong.hoopsports.entity.nbateamgame.List> list) {
         this.list = list;
     }
     public List<com.charmingwong.hoopsports.entity.nbateamgame.List> getList() {
         return list;
     }

    public void setMore1(More1 more1) {
         this.more1 = more1;
     }
     public More1 getMore1() {
         return more1;
     }

    public void setMore2(More2 more2) {
         this.more2 = more2;
     }
     public More2 getMore2() {
         return more2;
     }

}