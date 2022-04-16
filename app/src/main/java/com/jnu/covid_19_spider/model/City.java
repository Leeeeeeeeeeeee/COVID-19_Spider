package com.jnu.covid_19_spider.model;

public class City implements Comparable<City> {
    private String cityName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private int highDangerCount;
    private int midDangerCount;
    private long locationId;

    public City() {

    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", suspectedCount=" + suspectedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", highDangerCount=" + highDangerCount +
                ", midDangerCount=" + midDangerCount +
                ", locationId=" + locationId +
                '}';
    }

    public City(String cityName, int currentConfirmedCount, int confirmedCount, int suspectedCount, int curedCount, int deadCount, int highDangerCount, int midDangerCount, long locationId) {
        this.cityName = cityName;
        this.currentConfirmedCount = currentConfirmedCount;
        this.confirmedCount = confirmedCount;
        this.suspectedCount = suspectedCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
        this.highDangerCount = highDangerCount;
        this.midDangerCount = midDangerCount;
        this.locationId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(int currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(int suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public int getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(int curedCount) {
        this.curedCount = curedCount;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public int getHighDangerCount() {
        return highDangerCount;
    }

    public void setHighDangerCount(int highDangerCount) {
        this.highDangerCount = highDangerCount;
    }

    public int getMidDangerCount() {
        return midDangerCount;
    }

    public void setMidDangerCount(int midDangerCount) {
        this.midDangerCount = midDangerCount;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    @Override
    public int compareTo(City city) {
        return ((this.getCurrentConfirmedCount() > city.getCurrentConfirmedCount()) ? (-1)
                :
                ((this.getCurrentConfirmedCount() == city.getCurrentConfirmedCount())
                        ? 0 : 1));
    }
}
