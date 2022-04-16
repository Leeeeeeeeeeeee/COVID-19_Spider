package com.jnu.covid_19_spider.network;

import static com.jnu.covid_19_spider.network.HTTPConst.AREA_URL;
import static com.jnu.covid_19_spider.network.HTTPConst.CODE_UPDATE_IMG;
import static com.jnu.covid_19_spider.network.HTTPConst.RISK_URL;
import static com.jnu.covid_19_spider.network.HTTPConst.STATISTIC_URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jnu.covid_19_spider.application.MyApplication;
import com.jnu.covid_19_spider.event.SetImgEvent;
import com.jnu.covid_19_spider.utils.DataUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPHelper {

    private static final String TAG = "#HTTPHelper";
    private static final int POOL_SIZE = 3;
    private OkHttpClient mClient;
    private ExecutorService httpExecutor;
    private Runnable statisticsRunnable;
    private Runnable areaRunnable;
    private Runnable riskRunnable;

    private static HTTPHelper instance;

    public static HTTPHelper getInstance() {
        if (instance == null) {
            instance = new HTTPHelper();
        }
        return instance;
    }

    public HTTPHelper() {
        mClient = new OkHttpClient();
        httpExecutor = Executors.newFixedThreadPool(POOL_SIZE);
        statisticsRunnable = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(STATISTIC_URL);
                Request request = builder.build();
                Call call = mClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "STATISTICS > onFailure() called with: call = [" + call + "], e = [" + e + "]");
                        getStatisticsJsonFromLocal();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String content = response.body().string();
                            DataUtil.parseStatisticsResult(content);
                        } else {
                            getStatisticsJsonFromLocal();
                        }
                    }
                });
            }
        };
        areaRunnable = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(AREA_URL);
                Request request = builder.build();
                Call call = mClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "AREA > onFailure() called with: call = [" + call + "], e = [" + e + "]");
                        getAreaJsonFromLocal();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String content = response.body().string();
                            DataUtil.parseAreaResult(content);
                        } else {
                            getAreaJsonFromLocal();
                        }
                    }
                });
            }
        };
        riskRunnable = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(RISK_URL);
                Request request = builder.build();
                Call call = mClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "RISK > onFailure() called with: call = [" + call + "], e = [" + e + "]");
                        getRiskJsonFromLocal();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String content = response.body().string();
                            DataUtil.parseRiskResult(content);
                        } else {
                            getRiskJsonFromLocal();
                        }
                    }
                });
            }
        };
    }

    private void getRiskJsonFromLocal() {
        try {
            InputStream is = MyApplication.getApplicationByReflection().getAssets().open("risk.json");
            String content = DataUtil.convertStreamToString(is);
            DataUtil.parseRiskResult(content);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void getAreaJsonFromLocal() {
        try {
            InputStream is = MyApplication.getApplicationByReflection().getAssets().open("area.json");
            String content = DataUtil.convertStreamToString(is);
            DataUtil.parseAreaResult(content);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void getStatisticsJsonFromLocal() {
        try {
            InputStream is = MyApplication.getApplicationByReflection().getAssets().open("statistics.json");
            String content = DataUtil.convertStreamToString(is);
            DataUtil.parseStatisticsResult(content);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void getNewsJsonFromLocal() {
        try {
            InputStream is = MyApplication.getApplicationByReflection().getAssets().open("news.json");
            String content = DataUtil.convertStreamToString(is);
            DataUtil.parseNewsResult(content);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    private class ImgRunnable implements Runnable {

        private String url;
        private int position;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void run() {
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d(TAG, "IMG > onFailure() called with: call = [" + call + "], e = [" + e + "]");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        EventBus.getDefault().postSticky(new SetImgEvent(response.body().bytes(), position));
                    }
                }
            });
        }
    }


    public void loadData() {
        httpExecutor.submit(statisticsRunnable);
        httpExecutor.submit(areaRunnable);
        httpExecutor.submit(riskRunnable);
        getNewsJsonFromLocal();
    }


    public void loadImg(String url, int position) {
        ImgRunnable imgRunnable = new ImgRunnable();
        imgRunnable.setPosition(position);
        imgRunnable.setUrl(url);
        httpExecutor.submit(imgRunnable);
    }
}
