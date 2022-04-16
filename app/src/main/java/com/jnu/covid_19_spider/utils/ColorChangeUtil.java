package com.jnu.covid_19_spider.utils;

import android.graphics.Color;

import com.jnu.covid_19_spider.model.Bound;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.Province;
import com.wxy.chinamapview.model.ChinaMapModel;
import com.wxy.chinamapview.model.ProvinceModel;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Vmmet on 2016/11/1.
 */
public class ColorChangeUtil {
    private static final String TAG = "#ColorChangeUtil";

    public static String colorStrings[] = {"#fef1ec", "#eedfd8", "#ffbca2", "#fc956a", "#ff6623", "#c13e08", "#aa3707", "#731f00"};

    public static Bound bounds[] = new Bound[colorStrings.length];

    public static String textStrings[] = new String[colorStrings.length];

    public static void generateBounds(int max, int min) {
        double n = Math.sqrt(Math.sqrt(Math.sqrt(max)));
        int nu = (int) Math.ceil(n);
        long t = 1;
        bounds[0] = new Bound(nu, 1);
        for (int i = 1; i < colorStrings.length; i++) {
            t = t * nu;
            bounds[i] = new Bound(t * nu, (t + 1));
        }
        generateTextString();
    }

    public static void generateTextString() {
        for (int i = 0; i < colorStrings.length; i++) {
            textStrings[i] = FormatUtil.formatK(bounds[i].getUpperBound()) + " - " + FormatUtil.formatK(bounds[i].getLowerBound());
        }
    }

    public static String getProvinceColor(long number) {
//        Log.d(TAG, "getProvinceColor() called with: number = [" + number + "]");
        int i = 0;
        for (Bound bound : bounds) {
//            Log.d(TAG, "getProvinceColor: " + bound);
            if (number < bound.getUpperBound()) {
                break;
            }
            i++;
        }
        return colorStrings[i];
    }

    public static void changeMapColors(ChinaMapModel chinaMapModel, int type) {
        List<Province> provinceList = DataFactory.getInstance().getProvinceList();
        for (ProvinceModel provinceModel : chinaMapModel.getProvincesList()) {
            for (Iterator iterator = provinceList.iterator(); iterator.hasNext(); ) {
                Province province = (Province) iterator.next();
                if (province.getProvinceShortName().equals(provinceModel.getName())) {
                    long number;
                    if (type == 1) {
                        number = province.getCurrentConfirmedCount();
                    } else {
                        number = province.getConfirmedCount();
                    }
                    String colorStr = getProvinceColor(number);
                    provinceModel.setColor(Color.parseColor(colorStr));
                    provinceModel.setSelectBorderColor(Color.parseColor(colorStrings[colorStrings.length-1]));
                    break;
                }
            }
        }
    }
}
