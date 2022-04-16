package com.jnu.covid_19_spider.utils;

import static com.jnu.covid_19_spider.fragment.StaticsFragment.BAR_TYPE_CONFIRMED;
import static com.jnu.covid_19_spider.fragment.StaticsFragment.BAR_TYPE_CURRENT;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.Province;


public class ChartUtil {
    private static final String TAG = "#ChartUtil";

    public static IndexAxisValueFormatter getIndexAxisValueFormatter(int barType) {
        switch (barType) {
            case BAR_TYPE_CURRENT: {
                String[] strs = new String[DataFactory.getInstance().getProvinceList().size()];
                int i = 0;
                for (Province province : DataFactory.getInstance().getProvinceList()) {
                    if (province.getCurrentConfirmedCount() != 0) {
                        strs[i] = province.getProvinceShortName();
                        i++;
                    }
                }
//                Log.d(TAG, "getIndexAxisValueFormatter: " + Arrays.toString(strs));
                return new IndexAxisValueFormatter(strs);
            }
            case BAR_TYPE_CONFIRMED: {
                String[] strs = new String[DataFactory.getInstance().getProvinceList().size()];
                int i = 0;
                for (Province province : DataFactory.getInstance().getProvinceList()) {
                    if (province.getConfirmedCount() != 0) {
                        strs[i] = province.getProvinceShortName();
                        i++;
                    }
                }
//                Log.d(TAG, "getIndexAxisValueFormatter: " + Arrays.toString(strs));
                return new IndexAxisValueFormatter(strs);
            }
        }
        return null;
    }
}
