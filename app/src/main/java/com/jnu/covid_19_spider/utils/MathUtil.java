package com.jnu.covid_19_spider.utils;

import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.Province;

import java.util.Iterator;

public class MathUtil {

    public static final int getMaxFromProvinceList(int type) {
        int max = 0;
        for (Iterator iterator = DataFactory.getInstance().getProvinceList().iterator(); iterator.hasNext(); ) {
            Province province = (Province) iterator.next();
            if(type==1) {
                if (province.getCurrentConfirmedCount() > max) {
                    max = (int) province.getCurrentConfirmedCount();
                }
            }else{
                if (province.getConfirmedCount() > max) {
                    max = (int) province.getConfirmedCount();
                }
            }
        }
        return max;
    }

    public static final int getMinFromProvinceList(int type) {
        int min = (int) DataFactory.getInstance().getProvinceList().get(0).getConfirmedCount();
        for (Iterator iterator = DataFactory.getInstance().getProvinceList().iterator(); iterator.hasNext(); ) {
            Province province = (Province) iterator.next();
            if(type==1) {
                if (province.getCurrentConfirmedCount() < min) {
                    min = (int) province.getCurrentConfirmedCount();
                }
            }else{
                if (province.getConfirmedCount() < min) {
                    min = (int) province.getConfirmedCount();
                }
            }
        }
        return min;
    }

}
