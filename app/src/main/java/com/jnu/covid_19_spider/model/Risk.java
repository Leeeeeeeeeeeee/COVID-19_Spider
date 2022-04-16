package com.jnu.covid_19_spider.model;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Risk {
    private String end_update_time;
    private int hcount;
    private int mcount;
    private List<JSONObject> highList = new ArrayList<>();
    private List<JSONObject> middleList = new ArrayList<>();

    public Risk() {

    }

    public Risk(String end_update_time, int hcount, int mcount, List<JSONObject> highList, List<JSONObject> middleList) {
        this.end_update_time = end_update_time;
        this.hcount = hcount;
        this.mcount = mcount;
        this.highList = highList;
        this.middleList = middleList;
    }

    @Override
    public String toString() {
        return "Risk{" +
                "end_update_time='" + end_update_time + '\'' +
                ", hcount=" + hcount +
                ", mcount=" + mcount +
                ", highList=" + highList +
                ", middleList=" + middleList +
                '}';
    }

    public String getEnd_update_time() {
        return end_update_time;
    }

    public void setEnd_update_time(String end_update_time) {
        this.end_update_time = end_update_time;
    }

    public int getHcount() {
        return hcount;
    }

    public void setHcount(int hcount) {
        this.hcount = hcount;
    }

    public int getMcount() {
        return mcount;
    }

    public void setMcount(int mcount) {
        this.mcount = mcount;
    }

    public List<JSONObject> getHighList() {
        return highList;
    }

    public void setHighList(List<JSONObject> highList) {
        this.highList = highList;
    }

    public List<JSONObject> getMiddleList() {
        return middleList;
    }

    public void setMiddleList(List<JSONObject> middleList) {
        this.middleList = middleList;
    }
}
