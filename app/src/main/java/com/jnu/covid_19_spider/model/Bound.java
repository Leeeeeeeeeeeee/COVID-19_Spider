package com.jnu.covid_19_spider.model;

public class Bound {

    private long upperBound;
    private long lowerBound;

    public Bound() {

    }

    public Bound(long upperBound, long lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    @Override
    public String toString() {
        return "Bound{" +
                "upperBound=" + upperBound +
                ", lowerBound=" + lowerBound +
                '}';
    }

    public long getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(long upperBound) {
        this.upperBound = upperBound;
    }

    public long getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(long lowerBound) {
        this.lowerBound = lowerBound;
    }
}
