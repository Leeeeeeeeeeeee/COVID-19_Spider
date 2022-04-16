package com.jnu.covid_19_spider.model;

public class Statistic {

    private long currentConfirmedCount;
    private long currentConfirmedIncr;
    private long confirmedCount;
    private long confirmedIncr;
    private long suspectedCount;
    private long suspectedIncr;
    private long curedCount;
    private long curedIncr;
    private long deadCount;
    private long deadIncr;
    private long seriousCount;
    private long seriousIncr;
    private GlobalStatistics globalStatistics;
    private long updateTime;

    public Statistic() {
    }

    public long getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(long currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public long getCurrentConfirmedIncr() {
        return currentConfirmedIncr;
    }

    public void setCurrentConfirmedIncr(long currentConfirmedIncr) {
        this.currentConfirmedIncr = currentConfirmedIncr;
    }

    public long getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(long confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public long getConfirmedIncr() {
        return confirmedIncr;
    }

    public void setConfirmedIncr(long confirmedIncr) {
        this.confirmedIncr = confirmedIncr;
    }

    public long getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(long suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public long getSuspectedIncr() {
        return suspectedIncr;
    }

    public void setSuspectedIncr(long suspectedIncr) {
        this.suspectedIncr = suspectedIncr;
    }

    public long getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(long curedCount) {
        this.curedCount = curedCount;
    }

    public long getCuredIncr() {
        return curedIncr;
    }

    public void setCuredIncr(long curedIncr) {
        this.curedIncr = curedIncr;
    }

    public long getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(long deadCount) {
        this.deadCount = deadCount;
    }

    public long getDeadIncr() {
        return deadIncr;
    }

    public void setDeadIncr(long deadIncr) {
        this.deadIncr = deadIncr;
    }

    public long getSeriousCount() {
        return seriousCount;
    }

    public void setSeriousCount(long seriousCount) {
        this.seriousCount = seriousCount;
    }

    public long getSeriousIncr() {
        return seriousIncr;
    }

    public void setSeriousIncr(long seriousIncr) {
        this.seriousIncr = seriousIncr;
    }

    public GlobalStatistics getGlobalStatistics() {
        return globalStatistics;
    }

    public void setGlobalStatistics(GlobalStatistics globalStatistics) {
        this.globalStatistics = globalStatistics;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "currentConfirmedCount=" + currentConfirmedCount +
                ", currentConfirmedIncr=" + currentConfirmedIncr +
                ", confirmedCount=" + confirmedCount +
                ", confirmedIncr=" + confirmedIncr +
                ", suspectedCount=" + suspectedCount +
                ", suspectedIncr=" + suspectedIncr +
                ", curedCount=" + curedCount +
                ", curedIncr=" + curedIncr +
                ", deadCount=" + deadCount +
                ", deadIncr=" + deadIncr +
                ", seriousCount=" + seriousCount +
                ", seriousIncr=" + seriousIncr +
                ", globalStatistics=" + globalStatistics +
                ", updateTime=" + updateTime +
                '}';
    }

    public Statistic(long currentConfirmedCount, long currentConfirmedIncr, long confirmedCount, long confirmedIncr, long suspectedCount, long suspectedIncr, long curedCount, long curedIncr, long deadCount, long deadIncr, long seriousCount, long seriousIncr, GlobalStatistics globalStatistics, long updateTime) {
        this.currentConfirmedCount = currentConfirmedCount;
        this.currentConfirmedIncr = currentConfirmedIncr;
        this.confirmedCount = confirmedCount;
        this.confirmedIncr = confirmedIncr;
        this.suspectedCount = suspectedCount;
        this.suspectedIncr = suspectedIncr;
        this.curedCount = curedCount;
        this.curedIncr = curedIncr;
        this.deadCount = deadCount;
        this.deadIncr = deadIncr;
        this.seriousCount = seriousCount;
        this.seriousIncr = seriousIncr;
        this.globalStatistics = globalStatistics;
        this.updateTime = updateTime;
    }
}
