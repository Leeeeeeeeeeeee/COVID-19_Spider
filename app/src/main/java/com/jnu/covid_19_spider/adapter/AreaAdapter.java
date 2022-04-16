package com.jnu.covid_19_spider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.model.City;

import java.util.List;

import javax.xml.namespace.QName;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {

    private List<String> areaList;

    public AreaAdapter(List<String> cityList) {
        this.areaList = cityList;
    }

    public static class AreaViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvArea;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvArea = itemView.findViewById(R.id.tv_area);
        }
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AreaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {

        AreaViewHolder areaViewHolder = (AreaViewHolder) holder;

        areaViewHolder.mTvArea.setText(areaList.get(position));
    }

    @Override
    public int getItemCount() {
        return areaList == null ? 0 : areaList.size();
    }

}
