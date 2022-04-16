package com.jnu.covid_19_spider.model;

public class GlobalStatistics {

    private long confirmedCount;
    private long curedCount;
    private long deadCount;
    private long seriousCount;
    private long currentConfirmedCount;
    private long currentConfirmedIncr;
    private long curedIncr;
    private long deadIncr;
    private long yesterdayConfirmedCountIncr;
    private long suspectedCount;

    public GlobalStatistics() {

    }

    @Override
    public String toString() {
        return "GlobalStatistics{" +
                "confirmedCount=" + confirmedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", seriousCount=" + seriousCount +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", currentConfirmedIncr=" + currentConfirmedIncr +
                ", curedIncr=" + curedIncr +
                ", deadIncr=" + deadIncr +
                ", yesterdayConfirmedCountIncr=" + yesterdayConfirmedCountIncr +
                ", suspectedCount=" + suspectedCount +
                '}';
    }

    public GlobalStatistics(long confirmedCount, long curedCount, long deadCount, long seriousCount, long currentConfirmedCount, long currentConfirmedIncr, long curedIncr, long deadIncr, long yesterdayConfirmedCountIncr, long suspectedCount) {
        this.confirmedCount = confirmedCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
        this.seriousCount = seriousCount;
        this.currentConfirmedCount = currentConfirmedCount;
        this.currentConfirmedIncr = currentConfirmedIncr;
        this.curedIncr = curedIncr;
        this.deadIncr = deadIncr;
        this.yesterdayConfirmedCountIncr = yesterdayConfirmedCountIncr;
        this.suspectedCount = suspectedCount;
    }

    public long getCurrentConfirmedIncr() {
        return currentConfirmedIncr;
    }

    public void setCurrentConfirmedIncr(long currentConfirmedIncr) {
        this.currentConfirmedIncr = currentConfirmedIncr;
    }

    public long getCuredIncr() {
        return curedIncr;
    }

    public void setCuredIncr(long curedIncr) {
        this.curedIncr = curedIncr;
    }

    public long getDeadIncr() {
        return deadIncr;
    }

    public void setDeadIncr(long deadIncr) {
        this.deadIncr = deadIncr;
    }

    public long getYesterdayConfirmedCountIncr() {
        return yesterdayConfirmedCountIncr;
    }

    public void setYesterdayConfirmedCountIncr(long yesterdayConfirmedCountIncr) {
        this.yesterdayConfirmedCountIncr = yesterdayConfirmedCountIncr;
    }

    public long getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(long confirmedCount) {
        this.confirmedCount = confirmedCount;
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

    public long getSeriousCount() {
        return seriousCount;
    }

    public void setSeriousCount(long seriousCount) {
        this.seriousCount = seriousCount;
    }

    public long getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(long currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public long getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(long suspectedCount) {
        this.suspectedCount = suspectedCount;
    }
}
