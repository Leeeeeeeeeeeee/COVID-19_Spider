package com.jnu.covid_19_spider.application;

import android.app.Application;

import com.jnu.covid_19_spider.network.HTTPHelper;

public class MyApplication extends Application {

    private static final String TAG = "#MyApplication";

    public static MyApplication getApplicationByReflection() {
        try {
            return (MyApplication) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HTTPHelper.getInstance().loadData();
    }
}
