package com.jnu.covid_19_spider.utils;

public class FormatUtil {

    public static final String formatTT(long number) {
        String form = "%.1f万";
        double ttResult = number / 10000d;
        return String.format(form, ttResult);
    }

    public static final String formatK(long number) {
        String form = "%dk";
        if (number >= 1000) {
            int kResult = (int) (number / 1000);
            return String.format(form, kResult);
        } else {
            return String.valueOf(number);
        }
    }

    public static final String formatProvince(String province) {
        if (province.contains("省") || province.contains("市") || province.contains("自治区")) {
            province = province.replace("省", "");
            province = province.replace("市", "");
            province = province.replace("自治区", "");
            province = province.replace("维吾尔", "");
            province = province.replace("壮族", "");
            province = province.replace("回族", "");
        }
        return province;
    }

    public static final String formatCity(String city) {
        if (city.contains("市")) {
            return city.replace("市", "");
        } else {
            return city;
        }
    }
}
