package com.jnu.covid_19_spider.event;

import com.jnu.covid_19_spider.model.Update;

public class SetRiskEvent {
    private Update update;

    public SetRiskEvent(Update update) {
        this.update = update;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "SetProvinceEvent{" +
                "update=" + update +
                '}';
    }
}
