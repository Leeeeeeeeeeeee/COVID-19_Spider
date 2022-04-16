package com.jnu.covid_19_spider.model;

import java.io.Serializable;

public class Update implements Serializable {
    private String source;
    private String date;

    public Update() {

    }

    public Update(String source, String date) {
        this.source = source;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Update{" +
                "source='" + source + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
