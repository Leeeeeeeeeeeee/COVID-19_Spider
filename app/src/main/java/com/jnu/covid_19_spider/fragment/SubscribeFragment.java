package com.jnu.covid_19_spider.fragment;

import static com.jnu.covid_19_spider.constant.MyConst.SETTINGS;
import static com.jnu.covid_19_spider.constant.MyConst.SETTINGS_CITY;
import static com.jnu.covid_19_spider.constant.MyConst.SETTINGS_PROVINCE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.donkingliang.labels.LabelsView;
import com.jnu.covid_19_spider.activity.MainActivity;
import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.adapter.AreaAdapter;
import com.jnu.covid_19_spider.adapter.NewsAdapter;
import com.jnu.covid_19_spider.adapter.ProvinceSubscribeAdapter;
import com.jnu.covid_19_spider.event.SetImgEvent;
import com.jnu.covid_19_spider.event.SetNewsEvent;
import com.jnu.covid_19_spider.event.SetRiskEvent;
import com.jnu.covid_19_spider.model.City;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.News;
import com.jnu.covid_19_spider.model.Province;
import com.jnu.covid_19_spider.model.RiskArea;
import com.jnu.covid_19_spider.model.Update;
import com.jnu.covid_19_spider.network.HTTPHelper;
import com.jnu.covid_19_spider.utils.FormatUtil;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubscribeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscribeFragment extends Fragment {

    private static final String TAG = "#SubscribeFragment";

    private static final int CODE_UPDATE_SUBSCRIBE_AREA = 101;
    private static final int CODE_UPDATE_STATISTIC = 102;
    private static final int CODE_UPDATE_SOURCE_RISK = 103;
    private static final int CODE_UPDATE_RISK = 104;
    private static final int CODE_UPDATE_SOURCE_NEWS = 105;


    private SubScribeHandler handler;
    private SharedPreferences settings;
    private RecyclerView mRvProvinceSubscribe;
    private ProvinceSubscribeAdapter provinceSubscribeAdapter;
    private LabelsView labelsView;
    private int provinceCheckedPosition = 0;
    private int cityCheckedPosition = 0;
    private TextView mTvCurrentConfirmedCount;
    private TextView mTvConfirmedCount;
    private TextView mTvHighRiskAreaCount;
    private TextView mTvSource;
    private TextView mTvDate;
    private TextView mTvMiddleRiskAreaCount;
    private LinearLayout mLlHighRiskArea;
    private LinearLayout mLlMiddleRiskArea;
    private AreaAdapter highAreaAdapter;
    private AreaAdapter middleAreaAdapter;
    private TextView mTvSourceNews;
    private TextView mTvDateNews;
    private NewsAdapter newsAdapter;
    private RecyclerView mRvNews;

    public SubscribeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscribeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscribeFragment newInstance(String param1, String param2) {
        SubscribeFragment fragment = new SubscribeFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerEvent();
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribe, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onDetach() {
        unRegisterEvent();
        super.onDetach();
    }

    private void unRegisterEvent() {
        EventBus.getDefault().unregister(this);
    }

    private void registerEvent() {
        EventBus.getDefault().register(this);
    }

    private void initData() {
        handler = new SubScribeHandler(this);
        settings = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
    }

    private void initView() {

        mTvSource = getView().findViewById(R.id.tv_source);
        mTvDate = getView().findViewById(R.id.tv_date);

        TextView mTvSubscribe = getView().findViewById(R.id.tv_subscribe);
        mTvSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JDCityPicker cityPicker = new JDCityPicker();
                JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();
                jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY);
                cityPicker.init(getContext());
                cityPicker.setConfig(jdCityConfig);
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        Log.d(TAG, "onSelected() called with: province = [" + province + "], city = [" + city + "], district = [" + district + "]");
                        SharedPreferences.Editor editor = settings.edit();
                        String provinces = settings.getString(SETTINGS_PROVINCE, "");
                        if (!provinces.contains(String.valueOf(province))) {
                            provinces = provinces.concat(":" + province);
                            editor.putString(SETTINGS_PROVINCE, provinces);
                        }
                        String cities = settings.getString(SETTINGS_CITY, "");
                        if (!(cities.contains(String.valueOf(city)) && cities.contains(String.valueOf(province)))) {
                            cities = cities.concat(":" + province + "_" + city);
                            editor.putString(SETTINGS_CITY, cities);
                        }
                        editor.apply();
                        Message message = handler.obtainMessage();
                        message.what = CODE_UPDATE_SUBSCRIBE_AREA;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                cityPicker.showCityPicker();
            }
        });

        {
            mRvProvinceSubscribe = getView().findViewById(R.id.rv_province_subscribe_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvProvinceSubscribe.setLayoutManager(layoutManager);
            provinceSubscribeAdapter = new ProvinceSubscribeAdapter(DataFactory.getInstance().getProvinceName());
            provinceSubscribeAdapter.setOnItemCheckedListener(new ProvinceSubscribeAdapter.OnItemCheckedListener() {
                @Override
                public void onChecked(int position) {
                    provinceSubscribeAdapter.setCheckedPosition(position);
                    provinceCheckedPosition = position;
                    Message message = handler.obtainMessage();
                    message.what = CODE_UPDATE_SUBSCRIBE_AREA;
                    handler.sendMessage(message);
                }
            });
            mRvProvinceSubscribe.setAdapter(provinceSubscribeAdapter);
        }

        labelsView = getView().findViewById(R.id.labels_view);
        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                cityCheckedPosition = position;
                Message message = handler.obtainMessage();
                message.what = CODE_UPDATE_STATISTIC;
                handler.sendMessage(message);
            }
        });

        mTvCurrentConfirmedCount = getView().findViewById(R.id.tv_current_confirmed_count);
        mTvConfirmedCount = getView().findViewById(R.id.tv_confirmed_count);

        mTvHighRiskAreaCount = getView().findViewById(R.id.tv_high_risk_area_count);
        mTvMiddleRiskAreaCount = getView().findViewById(R.id.tv_middle_risk_area_count);

        {
            mLlHighRiskArea = getView().findViewById(R.id.ll_high_risk_area);
            RecyclerView mRvHighRiskArea = getView().findViewById(R.id.rv_high_risk_area);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvHighRiskArea.setLayoutManager(layoutManager);
            highAreaAdapter = new AreaAdapter(DataFactory.getInstance().getHighRiskAreasChoose());
            mRvHighRiskArea.setAdapter(highAreaAdapter);
        }
        {
            mLlMiddleRiskArea = getView().findViewById(R.id.ll_middle_risk_area);
            RecyclerView mRvMiddleRiskArea = getView().findViewById(R.id.rv_middle_risk_area);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvMiddleRiskArea.setLayoutManager(layoutManager);
            middleAreaAdapter = new AreaAdapter(DataFactory.getInstance().getMiddleRiskAreasChoose());
            mRvMiddleRiskArea.setAdapter(middleAreaAdapter);
        }
        Message message = handler.obtainMessage();
        message.what = CODE_UPDATE_SUBSCRIBE_AREA;
        handler.sendMessage(message);

        mTvSourceNews = getView().findViewById(R.id.tv_source_news);
        mTvDateNews = getView().findViewById(R.id.tv_date_news);

        {
            mRvNews = getView().findViewById(R.id.rv_news);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;

                }
            };
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvNews.setLayoutManager(layoutManager);
            mRvNews.setHasFixedSize(true);
            mRvNews.setNestedScrollingEnabled(false);
            newsAdapter = new NewsAdapter(DataFactory.getInstance().getNewsList());
            mRvNews.setAdapter(newsAdapter);
        }

    }


    private static class SubScribeHandler extends Handler {

        final WeakReference<SubscribeFragment> mWeakReference;

        public SubScribeHandler(SubscribeFragment fragment) {
            this.mWeakReference = new WeakReference<>(fragment);
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(@NonNull Message msg) {

            super.handleMessage(msg);
            SubscribeFragment fragment = mWeakReference.get();
            switch (msg.what) {
                case CODE_UPDATE_SUBSCRIBE_AREA: {
                    try {
                        String provinces = fragment.settings.getString(SETTINGS_PROVINCE, "");
                        String[] provinceArray = provinces.split(":");
                        DataFactory.getInstance().getProvinceName().clear();
                        for (String province : provinceArray) {
                            if (!province.equals(""))
                                DataFactory.getInstance().getProvinceName().add(province);
                        }
                        fragment.provinceSubscribeAdapter.notifyDataSetChanged();

                        String cities = fragment.settings.getString(SETTINGS_CITY, "");
                        String[] cityArray = cities.split(":");

                        String choose = DataFactory.getInstance().getProvinceName().get(fragment.provinceCheckedPosition);
                        DataFactory.getInstance().getCityName().clear();
                        for (String city : cityArray) {
                            if (!city.equals("")) {
                                String[] cityInfo = city.split("_");
                                if (cityInfo[0].equals(choose)) {
                                    DataFactory.getInstance().getCityName().add(cityInfo[1]);
                                }
                            }
                        }
                        fragment.labelsView.setLabels(DataFactory.getInstance().getCityName()); //直接设置一个字符串数组就可以了。
                        fragment.cityCheckedPosition = 0;
                        fragment.labelsView.setSelects(0);
                        fragment.labelsView.invalidate();

                        Message message = fragment.handler.obtainMessage();
                        message.what = CODE_UPDATE_STATISTIC;
                        fragment.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CODE_UPDATE_STATISTIC: {
                    try {
                        String choose = DataFactory.getInstance().getProvinceName().get(fragment.provinceCheckedPosition);
                        choose = FormatUtil.formatProvince(choose);
                        Province provinceChoose = null;
                        for (Province province : DataFactory.getInstance().getProvinceList()) {
                            if (choose.equals(province.getProvinceShortName())) {
                                provinceChoose = province;
                                break;
                            }
                        }
                        City cityChoose = null;
                        if (provinceChoose != null && DataFactory.getInstance().getCityName().size() != 0) {
                            for (JSONObject jsonObject : provinceChoose.getCities()) {
                                City city = JSON.toJavaObject(jsonObject, City.class);
                                if (city.getCityName().equals(FormatUtil.formatCity(DataFactory.getInstance().getCityName().get(fragment.cityCheckedPosition)))) {
                                    cityChoose = city;
                                    break;
                                }
                            }
                        }
                        if (cityChoose != null) {
                            fragment.mTvCurrentConfirmedCount.setText(String.valueOf(cityChoose.getCurrentConfirmedCount()));
                            fragment.mTvConfirmedCount.setText(String.valueOf(cityChoose.getConfirmedCount()));
                        } else if (provinceChoose != null) {
                            fragment.mTvCurrentConfirmedCount.setText(String.valueOf(provinceChoose.getCurrentConfirmedCount()));
                            fragment.mTvConfirmedCount.setText(String.valueOf(provinceChoose.getConfirmedCount()));
                        } else {
                            fragment.mTvCurrentConfirmedCount.setText(R.string.not_found);
                            fragment.mTvConfirmedCount.setText(R.string.not_found);
                        }

                        Message message = fragment.handler.obtainMessage();
                        message.what = CODE_UPDATE_RISK;
                        fragment.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CODE_UPDATE_SOURCE_RISK: {
                    try {
                        Update update = (Update) msg.obj;
                        fragment.mTvSource.setText(update.getSource());
                        fragment.mTvDate.setText(update.getDate());
                        Message message = fragment.handler.obtainMessage();
                        message.what = CODE_UPDATE_RISK;
                        fragment.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CODE_UPDATE_RISK: {
                    try {
                        String provinceChoose = DataFactory.getInstance().getProvinceName().get(fragment.provinceCheckedPosition);
                        String cityChoose = DataFactory.getInstance().getCityName().get(fragment.cityCheckedPosition);
                        if (cityChoose.equals("市辖区")) {
                            cityChoose = provinceChoose;
                        }
                        provinceChoose = FormatUtil.formatProvince(provinceChoose);
                        cityChoose = FormatUtil.formatCity(cityChoose);
                        int highRiskCount = 0;
                        List<RiskArea> highRiskAreaChoose = new ArrayList<>();
                        for (RiskArea area : DataFactory.getInstance().getHighRiskAreas()) {
                            String provinceArea = FormatUtil.formatProvince(area.getProvince());
                            String cityArea = FormatUtil.formatCity(area.getCity());
                            if ((provinceChoose.equals(provinceArea) && cityChoose.equals(cityArea))) {
                                highRiskCount++;
                                highRiskAreaChoose.add(area);
                            }
                        }
                        int middleRiskCount = 0;
                        List<RiskArea> middleRiskAreaChoose = new ArrayList<>();
                        for (RiskArea area : DataFactory.getInstance().getMiddleRiskAreas()) {
                            String provinceArea = FormatUtil.formatProvince(area.getProvince());
                            String cityArea = FormatUtil.formatCity(area.getCity());
                            if ((provinceChoose.equals(provinceArea) && cityChoose.equals(cityArea))) {
                                middleRiskCount++;
                                middleRiskAreaChoose.add(area);
                            }
                        }
                        fragment.mTvHighRiskAreaCount.setText(String.valueOf(highRiskCount));
                        fragment.mTvMiddleRiskAreaCount.setText(String.valueOf(middleRiskCount));
                        if (highRiskCount == 0) {
                            fragment.mLlHighRiskArea.setVisibility(View.GONE);
                        } else {
                            fragment.mLlHighRiskArea.setVisibility(View.VISIBLE);
                            DataFactory.getInstance().getHighRiskAreasChoose().clear();
                            for (RiskArea area : highRiskAreaChoose) {
                                for (String communities : area.getCommunitys()) {
                                    DataFactory.getInstance().getHighRiskAreasChoose().add(area.getArea_name() + " " + communities);
                                }
                            }
                            fragment.highAreaAdapter.notifyDataSetChanged();
                        }
                        if (middleRiskCount == 0) {
                            fragment.mLlMiddleRiskArea.setVisibility(View.GONE);
                        } else {
                            fragment.mLlMiddleRiskArea.setVisibility(View.VISIBLE);
                            DataFactory.getInstance().getMiddleRiskAreasChoose().clear();
                            for (RiskArea area : middleRiskAreaChoose) {
                                for (String communities : area.getCommunitys()) {
                                    DataFactory.getInstance().getMiddleRiskAreasChoose().add(area.getArea_name() + " " + communities);
                                }
                            }
                            fragment.middleAreaAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CODE_UPDATE_SOURCE_NEWS: {
                    try {
                        Update update = (Update) msg.obj;
                        fragment.mTvSourceNews.setText(update.getSource());
                        fragment.mTvDateNews.setText(update.getDate());
                        fragment.newsAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }

    }

    private void updateView() {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.tab_subsribe));
    }


    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void onSetRiskEvent(SetRiskEvent setRiskEvent) {
        Message message = handler.obtainMessage();
        message.what = CODE_UPDATE_SOURCE_RISK;
        message.obj = setRiskEvent.getUpdate();
        handler.sendMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void onSetNewsEvent(SetNewsEvent setNewsEvent) {
        Message message = handler.obtainMessage();
        message.what = CODE_UPDATE_SOURCE_NEWS;
        message.obj = setNewsEvent.getUpdate();
        handler.sendMessage(message);
        int i = 0;
        for (News news : DataFactory.getInstance().getNewsList()) {
            String img = news.getImg();
            Log.d(TAG, "onSetNewsEvent: " + i);
            HTTPHelper.getInstance().loadImg(img, i);
            i++;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void onSetImgEvent(SetImgEvent setImgEvent) {
        try {
            int position = setImgEvent.getPosition();
            View itemView = mRvNews.getChildAt(position);
            ImageView mIvImg = itemView.findViewById(R.id.iv_news_img);
            DataFactory.getInstance().getImgList().put(position, setImgEvent.getResult());
            Bitmap bitmap = BitmapFactory.decodeByteArray(setImgEvent.getResult(), 0, setImgEvent.getResult().length);
            mIvImg.setImageBitmap(bitmap);//加载图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}