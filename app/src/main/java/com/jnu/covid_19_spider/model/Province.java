package com.jnu.covid_19_spider.model;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Province implements Comparable<Province> {
    private long locationId;
    private String provinceShortName;
    private long currentConfirmedCount;
    private long confirmedCount;
    private long suspectedCount;
    private long curedCount;
    private long deadCount;
    private List<JSONObject> cities = new ArrayList<>();

    public Province() {

    }

    public Province(long locationId, String provinceShortName, long currentConfirmedCount, long confirmedCount, long suspectedCount, long curedCount, long deadCount) {
        this.locationId = locationId;
        this.provinceShortName = provinceShortName;
        this.currentConfirmedCount = currentConfirmedCount;
        this.confirmedCount = confirmedCount;
        this.suspectedCount = suspectedCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
    }

    @Override
    public String toString() {
        return "Province{" +
                "locationId=" + locationId +
                ", provinceShortName='" + provinceShortName + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", suspectedCount=" + suspectedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", cities=" + cities +
                '}';
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getProvinceShortName() {
        return provinceShortName;
    }

    public void setProvinceShortName(String provinceShortName) {
        this.provinceShortName = provinceShortName;
    }

    public long getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(long currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public long getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(long confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public long getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(long suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public long getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(long curedCount) {
        this.curedCount = curedCount;
    }

    public long getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(long deadCount) {
        this.deadCount = deadCount;
    }

    public List<JSONObject> getCities() {
        return cities;
    }

    public void setCities(List<JSONObject> cities) {
        this.cities = cities;
    }

    @Override
    public int compareTo(Province province) {
        return ((this.getCurrentConfirmedCount() > province.getCurrentConfirmedCount()) ? (-1)
                :
                ((this.getCurrentConfirmedCount() == province.getCurrentConfirmedCount())
                        ? 0 : 1));
    }

}
