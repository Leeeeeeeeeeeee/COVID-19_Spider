package com.jnu.covid_19_spider.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.event.SetImgEvent;
import com.jnu.covid_19_spider.event.SetProvinceEvent;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.News;
import com.jnu.covid_19_spider.network.HTTPHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> newsList;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private static final String titleFormat = "[%s]%s";

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvUpdate, mTvTitle;
        public ImageView mIvImg;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvUpdate = itemView.findViewById(R.id.tv_update_news_item);
            mTvTitle = itemView.findViewById(R.id.tv_news_title);
            mIvImg = itemView.findViewById(R.id.iv_news_img);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
        News news = newsList.get(position);

        long update = news.getDate();
        newsViewHolder.mTvUpdate.setText(sdf.format(new Date(update)));
        newsViewHolder.mTvTitle.setText(String.format(titleFormat, news.getFrom(), news.getTitle()));
        byte[] imgBytes = DataFactory.getInstance().getImgList().get(position);
        if(imgBytes!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            newsViewHolder.mIvImg.setImageBitmap(bitmap);//加载图片
        }
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }


}
