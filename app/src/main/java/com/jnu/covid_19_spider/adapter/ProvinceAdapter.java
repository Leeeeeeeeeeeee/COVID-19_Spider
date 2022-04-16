package com.jnu.covid_19_spider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.model.Province;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceDailyViewHolder> {

    private List<Province> provinceList;

    public ProvinceAdapter(List<Province> provinceList) {
        this.provinceList = provinceList;
    }

    public static class ProvinceDailyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvArea, mTvCurrentConfirmedCount, mTvCurrentCount, mTvCuredCount, mTvDeadCount;

        public ProvinceDailyViewHolder(@NonNull View itemView) {
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
    public ProvinceDailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProvinceDailyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province_daily_list, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceDailyViewHolder holder, int position) {

        ProvinceDailyViewHolder provinceDailyViewHolder = (ProvinceDailyViewHolder) holder;
        Province province = provinceList.get(position);

        provinceDailyViewHolder.mTvArea.setText(province.getProvinceShortName());
        provinceDailyViewHolder.mTvCurrentConfirmedCount.setText(String.valueOf(province.getCurrentConfirmedCount()));
        provinceDailyViewHolder.mTvCurrentCount.setText(String.valueOf(province.getConfirmedCount()));
        provinceDailyViewHolder.mTvCuredCount.setText(String.valueOf(province.getCuredCount()));
        provinceDailyViewHolder.mTvDeadCount.setText(String.valueOf(province.getDeadCount()));
    }
    @Override
    public int getItemCount() {
        return provinceList == null ? 0 : provinceList.size();
    }

}
