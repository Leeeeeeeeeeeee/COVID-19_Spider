package com.jnu.covid_19_spider.model;

import java.util.ArrayList;
import java.util.List;

public class RiskArea {
    private int type;
    private String province;
    private String city;
    private String county;
    private String area_name;
    private List<String> communitys = new ArrayList<>();

    public RiskArea() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public List<String> getCommunitys() {
        return communitys;
    }

    public void setCommunitys(List<String> communitys) {
        this.communitys = communitys;
    }

    @Override
    public String toString() {
        return "RiskArea{" +
                "type=" + type +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", area_name='" + area_name + '\'' +
                ", communitys=" + communitys +
                '}';
    }

    public RiskArea(int type, String province, String city, String county, String area_name, List<String> communitys) {
        this.type = type;
        this.province = province;
        this.city = city;
        this.county = county;
        this.area_name = area_name;
        this.communitys = communitys;
    }
}
