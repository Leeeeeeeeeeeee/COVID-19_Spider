package com.jnu.covid_19_spider.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.event.SetNewsEvent;
import com.jnu.covid_19_spider.event.SetProvinceEvent;
import com.jnu.covid_19_spider.event.SetRiskEvent;
import com.jnu.covid_19_spider.event.SetStatisticsEvent;
import com.jnu.covid_19_spider.model.City;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.News;
import com.jnu.covid_19_spider.model.Province;
import com.jnu.covid_19_spider.model.Risk;
import com.jnu.covid_19_spider.model.RiskArea;
import com.jnu.covid_19_spider.model.Statistic;
import com.jnu.covid_19_spider.model.Update;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataUtil {
    private static final String TAG = "#DataUtil";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void parseAreaResult(String content) {
        JSONObject container = JSONObject.parseObject(content);
//        Log.d(TAG, "parseAreaResult: jsonObject:" + container);
        JSONArray results = container.getJSONArray("results");
//        Log.d(TAG, "parseAreaResult: results:" + results);

        List<Province> provinceList = new ArrayList<Province>(JSONArray.parseArray(results.toJSONString(), Province.class));
//        Log.d(TAG, "parseAreaResult: list" + provinceList);
        for (Province province : provinceList) {
            if (province.getCities().size() != 0
                    || province.getProvinceShortName().equals("北京")
                    || province.getProvinceShortName().equals("天津")
                    || province.getProvinceShortName().equals("上海")
                    || province.getProvinceShortName().equals("重庆")) {
                DataFactory.getInstance().getProvinceList().add(province);
            }
        }
        Collections.sort(DataFactory.getInstance().getProvinceList());
        long timeStamp = System.currentTimeMillis();
        String date = sdf.format(new Date(timeStamp));
        EventBus.getDefault().postSticky(new SetProvinceEvent(new Update("丁香园", date)));
    }

    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();

    }

    public static void parseStatisticsResult(String content) {
        JSONObject container = JSONObject.parseObject(content);
//        Log.d(TAG, "parseStatisticsResult: jsonObject:" + container);
        JSONArray results = container.getJSONArray("results");
//        Log.d(TAG, "parseStatisticsResult: results:" + results);
        JSONObject result = results.getJSONObject(0);
//        Log.d(TAG, "parseStatisticsResult: result:" + result);
        Statistic statistic = JSON.toJavaObject(result, Statistic.class);
//        Log.d(TAG, "parseStatisticsResult: statistic:" + statistic);
        DataFactory.getInstance().setStatistic(statistic);
        String date = sdf.format(new Date(statistic.getUpdateTime()));
        EventBus.getDefault().postSticky(new SetStatisticsEvent(new Update("丁香园", date)));
    }

    public static void parseRiskResult(String content) {
        JSONObject container = JSONObject.parseObject(content);
        JSONObject data = container.getJSONObject("data");
//        Log.d(TAG, "parseRiskResult: data:" + data);
        Risk risk = JSON.toJavaObject(data, Risk.class);
//        Log.d(TAG, "parseRiskResult: risk:" + risk);
        DataFactory.getInstance().setRisk(risk);
        List<RiskArea> highRiskAreas = new ArrayList<RiskArea>();
        for (JSONObject jsonObject : risk.getHighList()) {
            RiskArea riskArea = JSON.toJavaObject(jsonObject, RiskArea.class);
            highRiskAreas.add(riskArea);
        }
        DataFactory.getInstance().getHighRiskAreas().clear();
        DataFactory.getInstance().getHighRiskAreas().addAll(highRiskAreas);
        List<RiskArea> middleRiskAreas = new ArrayList<RiskArea>();
        for (JSONObject jsonObject : risk.getMiddleList()) {
            RiskArea riskArea = JSON.toJavaObject(jsonObject, RiskArea.class);
            middleRiskAreas.add(riskArea);
        }
        DataFactory.getInstance().getMiddleRiskAreas().clear();
        DataFactory.getInstance().getMiddleRiskAreas().addAll(middleRiskAreas);

        String date = risk.getEnd_update_time().split(" ")[0];
        EventBus.getDefault().postSticky(new SetRiskEvent(new Update("diqu.gezhong.vip", date)));
    }

    public static void parseNewsResult(String content) {
        JSONObject container = JSONObject.parseObject(content);
//        Log.d(TAG, "parseNewsResult: " + container);
        JSONArray result = container.getJSONArray("data");
//        Log.d(TAG, "parseNewsResult: " + result);
        List<News> newsList = new ArrayList<News>();
        for (int i = 0; i < result.size(); i++) {
            News news = JSON.toJavaObject(result.getJSONObject(i), News.class);
            newsList.add(news);
        }
        DataFactory.getInstance().getNewsList().clear();
        DataFactory.getInstance().getNewsList().addAll(newsList);
        Collections.sort(DataFactory.getInstance().getNewsList());
        long timeStamp = container.getLong("update");
        String date = sdf.format(new Date(timeStamp));
        EventBus.getDefault().postSticky(new SetNewsEvent(new Update("爬虫程序", date)));
    }
}
