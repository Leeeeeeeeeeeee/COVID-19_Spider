package com.jnu.covid_19_spider.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jnu.covid_19_spider.activity.MainActivity;
import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.adapter.CityAdapter;
import com.jnu.covid_19_spider.adapter.ProvinceAdapter;
import com.jnu.covid_19_spider.event.SetProvinceEvent;
import com.jnu.covid_19_spider.model.City;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.MycolorArea;
import com.jnu.covid_19_spider.model.Province;
import com.jnu.covid_19_spider.model.Update;
import com.jnu.covid_19_spider.ui.ColorView;
import com.jnu.covid_19_spider.utils.ColorChangeUtil;
import com.jnu.covid_19_spider.utils.MathUtil;
import com.wxy.chinamapview.model.ChinaMapModel;
import com.wxy.chinamapview.view.ChinaMapView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static final String TAG = "#MapFragment";
    private static final int CODE_UPDATE_VIEW = 101;
    private static final int CODE_UPDATE_CHINA_MAP = 102;
    private static final int CODE_UPDATE_LIST = 103;
    public static int TYPE_CURRENT = 1;
    public static int TYPE_CONFIRMED = 2;
    private ProvinceAdapter provinceAdapter;
    private RecyclerView mRvProvinceDailyList;
    private List<MycolorArea> colorAreaList;
    private ColorView colorView;
    private MapHandler handler;
    private ChinaMapModel chinaMapModel;
    private ChinaMapView chinaMapView;
    private TextView mTvSource;
    private TextView mTvDate;
    private RadioGroup mRgSource;
    private int type = TYPE_CURRENT;
    private RecyclerView mRvCityList;
    private CityAdapter cityAdapter;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerEvent();
        handler = new MapHandler(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
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

    private void initView() {

        mTvSource = getView().findViewById(R.id.tv_source);
        mTvDate = getView().findViewById(R.id.tv_date);

        mRgSource = getView().findViewById(R.id.rg_data_source);
        mRgSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_confirmed) {
                    type = 2;
                } else {
                    type = 1;
                }
                updateChinaMapView();
                Message message = handler.obtainMessage();
                message.what = CODE_UPDATE_CHINA_MAP;
                handler.sendMessage(message);
            }
        });

        colorView = getView().findViewById(R.id.color_view);
        chinaMapView = getView().findViewById(R.id.china_map_view);
        chinaMapModel = chinaMapView.getChinaMapModel();
        chinaMapModel.setShowName(true);
        chinaMapView.setOnProvinceClickLisener(new ChinaMapView.onProvinceClickLisener() {
            @Override
            public void onSelectProvince(String provinceName) {
                mRvProvinceDailyList.setVisibility(View.GONE);
                mRvCityList.setVisibility(View.VISIBLE);
                for (Province province : DataFactory.getInstance().getProvinceList()) {
                    if (provinceName.equals(province.getProvinceShortName())) {
                        List<City> cityList = new ArrayList<City>();
                        for (JSONObject jsonObject : province.getCities()) {
                            City city = JSON.toJavaObject(jsonObject,City.class);
                            cityList.add(city);
                        }
                        DataFactory.getInstance().getCityDailyList().clear();
                        DataFactory.getInstance().getCityDailyList().addAll(cityList);
                        Message message = handler.obtainMessage();
                        message.what = CODE_UPDATE_LIST;
                        handler.sendMessage(message);
                        return;
                    }
                }
            }
        });
        {
            mRvProvinceDailyList = getView().findViewById(R.id.rv_province_daily_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvProvinceDailyList.setLayoutManager(layoutManager);
            provinceAdapter = new ProvinceAdapter(DataFactory.getInstance().getProvinceList());
            mRvProvinceDailyList.setAdapter(provinceAdapter);
        }

        {
            mRvCityList = getView().findViewById(R.id.rv_city_daily_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvCityList.setLayoutManager(layoutManager);
            cityAdapter = new CityAdapter(DataFactory.getInstance().getCityDailyList());
            mRvCityList.setAdapter(cityAdapter);
        }
    }

    private void updateChinaMapView() {
        int max = MathUtil.getMaxFromProvinceList(type);
        int min = MathUtil.getMinFromProvinceList(type);
        ColorChangeUtil.generateBounds(max, min);
        colorAreaList = new ArrayList<>();
        for (int i = 0; i < ColorChangeUtil.colorStrings.length; i++) {
            MycolorArea c = new MycolorArea();
            c.setColor(Color.parseColor(ColorChangeUtil.colorStrings[i]));
            c.setText(ColorChangeUtil.textStrings[i]);
            colorAreaList.add(c);
        }
        ColorChangeUtil.changeMapColors(chinaMapModel, type);
    }

    private void updateView() {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.tab_map));
    }

    private static class MapHandler extends Handler {

        final WeakReference<MapFragment> mWeakReference;

        public MapHandler(MapFragment fragment) {
            this.mWeakReference = new WeakReference<>(fragment);
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(@NonNull Message msg) {

            super.handleMessage(msg);
            MapFragment fragment = mWeakReference.get();
            switch (msg.what) {
                case CODE_UPDATE_VIEW: {
                    Update update = (Update) msg.obj;
                    fragment.mTvSource.setText(update.getSource());
                    fragment.mTvDate.setText(update.getDate());
                    fragment.colorView.setList(fragment.colorAreaList);
                    fragment.mRvProvinceDailyList.setVisibility(View.VISIBLE);
                    fragment.mRvCityList.setVisibility(View.GONE);
                    fragment.provinceAdapter.notifyDataSetChanged();
                    fragment.chinaMapView.notifyDataChanged();
                }
                break;
                case CODE_UPDATE_CHINA_MAP: {
                    fragment.colorView.setList(fragment.colorAreaList);
                    fragment.chinaMapView.notifyDataChanged();
                }
                break;
                case CODE_UPDATE_LIST: {
                    Log.d(TAG, "handleMessage: " + DataFactory.getInstance().getCityDailyList());
                    fragment.cityAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void onSetProvinceDailyResultList(SetProvinceEvent setProvinceEvent) {
        updateChinaMapView();
        Message message = handler.obtainMessage();
        message.what = CODE_UPDATE_VIEW;
        message.obj = setProvinceEvent.getUpdate();
        handler.sendMessage(message);
    }
}