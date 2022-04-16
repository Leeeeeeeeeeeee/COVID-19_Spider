package com.jnu.covid_19_spider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.model.City;
import com.jnu.covid_19_spider.model.Province;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cityList;

    public CityAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvArea, mTvCurrentConfirmedCount, mTvCurrentCount, mTvCuredCount, mTvDeadCount;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvArea = itemView.findViewById(R.id.tv_area);
            mTvCurrentConfirmedCount = itemView.findViewById(R.id.tv_current_confirmed_count);
            mTvCurrentCount = itemView.findViewById(R.id.tv_current_count);
            mTvCuredCount = itemView.findViewById(R.id.tv_cured_count);
            mTvDeadCount = itemView.findViewById(R.id.tv_dead_count);
        }
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province_daily_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {

        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        City city = cityList.get(position);

        cityViewHolder.mTvArea.setText(city.getCityName());
        cityViewHolder.mTvCurrentConfirmedCount.setText(String.valueOf(city.getCurrentConfirmedCount()));
        cityViewHolder.mTvCurrentCount.setText(String.valueOf(city.getConfirmedCount()));
        cityViewHolder.mTvCuredCount.setText(String.valueOf(city.getCuredCount()));
        cityViewHolder.mTvDeadCount.setText(String.valueOf(city.getDeadCount()));
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

}
