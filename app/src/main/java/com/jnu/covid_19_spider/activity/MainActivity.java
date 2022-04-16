package com.jnu.covid_19_spider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.fragment.MapFragment;
import com.jnu.covid_19_spider.fragment.StaticsFragment;
import com.jnu.covid_19_spider.fragment.SubscribeFragment;
import com.jnu.covid_19_spider.network.HTTPHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "#MainActivity";
    private TabLayoutMediator tabLayoutMediator;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabStr;
    private ArrayList<Integer> tabImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        tabStr = new ArrayList<>();
        tabStr.add(getString(R.string.tab_map));
        tabStr.add(getString(R.string.tab_statics));
        tabStr.add(getString(R.string.tab_subsribe));

        tabImg = new ArrayList<>();
        tabImg.add(R.drawable.ic_china_map);
        tabImg.add(R.drawable.ic_statistics);
        tabImg.add(R.drawable.ic_subscribe);

        fragments = new ArrayList<>();
        fragments.add(new MapFragment());
        fragments.add(new StaticsFragment());
        fragments.add(new SubscribeFragment());
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        for (int i = 0; i < tabStr.size(); i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, true, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabStr.get(position));
                tab.setIcon(tabImg.get(position));
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}