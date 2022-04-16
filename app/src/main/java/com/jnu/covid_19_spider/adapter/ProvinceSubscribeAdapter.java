package com.jnu.covid_19_spider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.model.Province;

import java.util.List;

public class ProvinceSubscribeAdapter extends RecyclerView.Adapter<ProvinceSubscribeAdapter.MyViewHolder> {

    private List<String> provinceNameList;
    private int checkedPosition = 0;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public ProvinceSubscribeAdapter(List<String> provinceNameList) {
        this.provinceNameList = provinceNameList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTvSubscribe;
        public ImageView mIvTriangle;
        private OnItemCheckedListener onItemCheckedListener;

        public MyViewHolder(@NonNull View itemView, OnItemCheckedListener onItemCheckedListener) {
            super(itemView);
            this.onItemCheckedListener = onItemCheckedListener;
            mTvSubscribe = itemView.findViewById(R.id.tv_subscribe);
            mIvTriangle = itemView.findViewById(R.id.iv_triangle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemCheckedListener != null) {
                onItemCheckedListener.onChecked(getLayoutPosition());
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province_subscribe, parent, false), onItemCheckedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        String provinceName = provinceNameList.get(position);

        myViewHolder.mTvSubscribe.setText(provinceName);
        if (position == checkedPosition) {
            myViewHolder.mIvTriangle.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.mIvTriangle.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return provinceNameList == null ? 0 : provinceNameList.size();
    }

    private OnItemCheckedListener onItemCheckedListener;

    public interface OnItemCheckedListener {
        void onChecked(int position);
    }

    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }


}
