package com.jnu.covid_19_spider.event;

public class SetImgEvent {
    private byte[] result;
    private int position;

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SetImgEvent(byte[] result, int position) {
        this.result = result;
        this.position = position;
    }
}
