package com.jnu.covid_19_spider.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataFactory {
    private static final String TAG = "#DataFactory";
    private static DataFactory instance;
    private Statistic statistic;
    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityDailyList = new ArrayList<>();
    private List<String> provinceName = new ArrayList<>();
    private List<String> cityName = new ArrayList<>();
    private Risk risk;
    private List<RiskArea> highRiskAreas = new ArrayList<>();
    private List<RiskArea> middleRiskAreas = new ArrayList<>();
    private List<String> highRiskAreasChoose = new ArrayList<>();
    private List<String> middleRiskAreasChoose = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();
    private HashMap<Integer, byte[]> imgList = new HashMap<>();

    public synchronized static DataFactory getInstance() {
        if (instance == null) {
            instance = new DataFactory();
        }
        return instance;
    }

    public List<RiskArea> getHighRiskAreas() {
        return highRiskAreas;
    }

    public List<RiskArea> getMiddleRiskAreas() {
        return middleRiskAreas;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public List<Province> getProvinceList() {
        return provinceList;
    }

    public List<City> getCityDailyList() {
        return cityDailyList;
    }


    public List<String> getProvinceName() {
        return provinceName;
    }

    public List<String> getCityName() {
        return cityName;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public List<String> getHighRiskAreasChoose() {
        return highRiskAreasChoose;
    }

    public List<String> getMiddleRiskAreasChoose() {
        return middleRiskAreasChoose;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public HashMap<Integer, byte[]> getImgList() {
        return imgList;
    }
}
