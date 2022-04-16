package com.jnu.covid_19_spider.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jnu.covid_19_spider.activity.MainActivity;
import com.jnu.covid_19_spider.R;
import com.jnu.covid_19_spider.event.SetProvinceEvent;
import com.jnu.covid_19_spider.event.SetStatisticsEvent;
import com.jnu.covid_19_spider.model.DataFactory;
import com.jnu.covid_19_spider.model.Province;
import com.jnu.covid_19_spider.model.Statistic;
import com.jnu.covid_19_spider.model.Update;
import com.jnu.covid_19_spider.utils.ChartUtil;
import com.jnu.covid_19_spider.utils.FormatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaticsFragment extends Fragment {

    private static final String TAG = "#StaticsFragment";
    private static final int CODE_UPDATE_STATISTIC_VIEW = 101;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int BAR_TYPE_CURRENT = 1;
    public static final int BAR_TYPE_CONFIRMED = 2;
    private static final float BAR_CHART_X_MAX = 6;
    private static final int CODE_UPDATE_BAR_CHART = 102;
    public int barType = BAR_TYPE_CURRENT;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StatisticHandler handler;
    private TextView mTvGlobalConfirmedCount;
    private TextView mTvGlobalCurrentConfirmedCount;
    private TextView mTvGlobalCuredCount;
    private TextView mTvGlobalDeadCount;
    private TextView mTvDomesticConfirmedCount;
    private TextView mTvDomesticCurrentConfirmedCount;
    private TextView mTvDomesticCuredCount;
    private TextView mTvDomesticDeadCount;
    private BarChart barChart;
    private LineChart lineChart;
    private TextView mTvSource;
    private TextView mTvDate;
    private RadioGroup mRgBarChartSource;

    public StaticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StaticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StaticsFragment newInstance(String param1, String param2) {
        StaticsFragment fragment = new StaticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerEvent();
        handler = new StatisticHandler(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_statics, container, false);
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
        //init statistics label
        initStatisticsLabel();

        //init bar chart
        initBarChart();

        //init line chart
        initLineChart();
    }

    private void initLineChart() {
        lineChart = getView().findViewById(R.id.line_chart);
        //set style
        lineChart.getDescription().setEnabled(false);//取消描述
        lineChart.setTouchEnabled(false);//不允许触摸
        lineChart.setNoDataText(getString(R.string.tips_waiting));//设置无数据时的文字
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);//取消显示右侧的y轴
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);//取消显示左侧y轴边线
        yAxis.setGridColor(getResources().getColor(R.color.line));//设置横向网格线的颜色
        yAxis.setTextColor(getResources().getColor(R.color.hint));//设置y轴坐标的字体颜色
        yAxis.setXOffset(10);//设置数值偏移
        lineChart.getLegend().setEnabled(false);//隐藏标签
        lineChart.invalidate();
        //set data
        List<ILineDataSet> lineDataSetList = new ArrayList<>();
        {
            List<Entry> lineChartDatas = new ArrayList<Entry>();
            for (int i = 0; i < 10; i++) {
                // turn your data into Entry objects
                lineChartDatas.add(new Entry(i, (int) (i * Math.random() * 100)));
            }
            LineDataSet lineDataSet = new LineDataSet(lineChartDatas, "Cured"); // add entries to dataset
            lineDataSet.setColor(getResources().getColor(R.color.chart_orange));// 设置曲线颜色
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);// 设置平滑曲线
            lineDataSet.setDrawCircles(false);// 不显示坐标点的小圆点
            lineDataSet.setDrawValues(false);// 不显示坐标点的数据
            lineDataSet.setDrawFilled(true);//显示填充
            lineDataSet.setFillColor(getResources().getColor(R.color.chart_orange));
            lineDataSetList.add(lineDataSet);
        }
        {
            List<Entry> lineChartDatas = new ArrayList<Entry>();
            for (int i = 0; i < 10; i++) {
                // turn your data into Entry objects
                lineChartDatas.add(new Entry(i, (int) (i * Math.random() * 100)));
            }
            LineDataSet lineDataSet = new LineDataSet(lineChartDatas, "Dead"); // add entries to dataset
            lineDataSet.setColor(getResources().getColor(R.color.chart_green));// 设置曲线颜色
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);// 设置平滑曲线
            lineDataSet.setDrawCircles(false);// 不显示坐标点的小圆点
            lineDataSet.setDrawValues(false);// 不显示坐标点的数据
            lineDataSet.setDrawFilled(true);//显示填充
            lineDataSet.setFillColor(getResources().getColor(R.color.chart_green));
            lineDataSetList.add(lineDataSet);

        }
        LineData lineData = new LineData(lineDataSetList);
        lineChart.setData(lineData);
//        lineChart.setData(lineData);
        lineChart.notifyDataSetChanged(); // refresh
    }

    private void initBarChart() {
        barChart = getView().findViewById(R.id.bar_chart);
        //set style
        barChart.getDescription().setEnabled(false);//取消描述
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);
        barChart.setNoDataText(getString(R.string.tips_waiting));//设置无数据时的文字
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴在下方
        xAxis.setDrawGridLines(false);//取消网格线
        xAxis.setDrawAxisLine(false);//取消x轴
        barChart.getAxisRight().setEnabled(false);//取消显示右侧的y轴
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);//取消显示左侧y轴边线
        yAxis.setGridColor(getResources().getColor(R.color.line));//设置横向网格线的颜色
        yAxis.setTextColor(getResources().getColor(R.color.hint));//设置y轴坐标的字体颜色
        yAxis.setXOffset(10);//设置数值偏移
        barChart.getLegend().setEnabled(false);//隐藏标签
        barChart.invalidate();

        mRgBarChartSource = getView().findViewById(R.id.rg_bar_chart_source);
        mRgBarChartSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_current_confirmed) {
                    barType = BAR_TYPE_CURRENT;
                } else {
                    barType = BAR_TYPE_CONFIRMED;
                }
                Message message = handler.obtainMessage();
                message.what = CODE_UPDATE_BAR_CHART;
                handler.sendMessage(message);
            }
        });
    }

    private void initStatisticsLabel() {
        mTvSource = getView().findViewById(R.id.tv_source);
        mTvDate = getView().findViewById(R.id.tv_date);
        mTvGlobalConfirmedCount = getView().findViewById(R.id.tv_global_current_confirmed_count);
        mTvGlobalCurrentConfirmedCount = getView().findViewById(R.id.tv_global_confirmed_count);
        mTvGlobalCuredCount = getView().findViewById(R.id.tv_global_cured_count);
        mTvGlobalDeadCount = getView().findViewById(R.id.tv_global_dead_count);
        mTvDomesticConfirmedCount = getView().findViewById(R.id.tv_domestic_current_confirmed_count);
        mTvDomesticCurrentConfirmedCount = getView().findViewById(R.id.tv_domestic_confirmed_count);
        mTvDomesticCuredCount = getView().findViewById(R.id.tv_domestic_cured_count);
        mTvDomesticDeadCount = getView().findViewById(R.id.tv_domestic_dead_count);
    }

    private void updateView() {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.tab_statics));
    }

    private static class StatisticHandler extends Handler {

        final WeakReference<StaticsFragment> mWeakReference;

        public StatisticHandler(StaticsFragment fragment) {
            this.mWeakReference = new WeakReference<>(fragment);
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(@NonNull Message msg) {

            super.handleMessage(msg);
            StaticsFragment fragment = mWeakReference.get();
            switch (msg.what) {
                case CODE_UPDATE_STATISTIC_VIEW: {
                    Update update = (Update) msg.obj;
                    fragment.mTvSource.setText(update.getSource());
                    fragment.mTvDate.setText(update.getDate());
                    Statistic statistic = DataFactory.getInstance().getStatistic();
                    fragment.mTvGlobalConfirmedCount.setText(FormatUtil.formatTT(statistic.getGlobalStatistics().getConfirmedCount()));
                    fragment.mTvGlobalCurrentConfirmedCount.setText(FormatUtil.formatTT(statistic.getGlobalStatistics().getCurrentConfirmedCount()));
                    fragment.mTvGlobalCuredCount.setText(FormatUtil.formatTT(statistic.getGlobalStatistics().getCuredCount()));
                    fragment.mTvGlobalDeadCount.setText(FormatUtil.formatTT(statistic.getGlobalStatistics().getDeadCount()));
                    fragment.mTvDomesticConfirmedCount.setText(FormatUtil.formatTT(statistic.getConfirmedCount()));
                    fragment.mTvDomesticCurrentConfirmedCount.setText(FormatUtil.formatTT(statistic.getCurrentConfirmedCount()));
                    fragment.mTvDomesticCuredCount.setText(FormatUtil.formatTT(statistic.getCuredCount()));
                    fragment.mTvDomesticDeadCount.setText(FormatUtil.formatTT(statistic.getDeadCount()));
                }
                break;
                case CODE_UPDATE_BAR_CHART: {
                    //set data
                    List<BarEntry> barChartDatas = new ArrayList<BarEntry>();
                    int i = 0;
                    for (Province province : DataFactory.getInstance().getProvinceList()) {
                        switch (fragment.barType) {
                            case BAR_TYPE_CURRENT:
                                if (province.getCurrentConfirmedCount() != 0)
                                    barChartDatas.add(new BarEntry(i, province.getCurrentConfirmedCount()));
                                break;
                            case BAR_TYPE_CONFIRMED:
                                if (province.getConfirmedCount() != 0)
                                    barChartDatas.add(new BarEntry(i, province.getConfirmedCount()));
                                break;
                        }
                        i++;
                    }
                    BarDataSet barDataSet = new BarDataSet(barChartDatas, ""); // add entries to dataset
                    barDataSet.setColor(fragment.getResources().getColor(R.color.chart_orange));//设置数据条颜色
                    barDataSet.setValueTextColor(fragment.getResources().getColor(R.color.text_default));//设置数值标字体颜色
                    BarData barData = new BarData(barDataSet);
                    barData.setBarWidth(0.3f);//设置数据条宽度
                    fragment.barChart.setData(barData);
                    fragment.barChart.setFitBars(true);//让所有的X轴和数据条适配
                    fragment.barChart.setVisibleXRangeMaximum(BAR_CHART_X_MAX);
                    XAxis xAxis = fragment.barChart.getXAxis();
                    xAxis.setValueFormatter(ChartUtil.getIndexAxisValueFormatter(fragment.barType));//设置柱条标签
                    xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
                    fragment.barChart.notifyDataSetChanged();
                    fragment.barChart.invalidate();
//                    Log.d(TAG, "handleMessage: ");
                }
                break;
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void onSetStatisticsEvent(SetStatisticsEvent setStatisticsEvent) {
//        Log.d(TAG, "onSetStatisticsResultEvent: ");
        Message message = handler.obtainMessage();
        message.what = CODE_UPDATE_STATISTIC_VIEW;
        message.obj = setStatisticsEvent.getUpdate();
        handler.sendMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void onSetProvinceResultEvent(SetProvinceEvent setProvinceEvent) {
        Message message = handler.obtainMessage();
        message.what = CODE_UPDATE_BAR_CHART;
        message.obj = setProvinceEvent.getUpdate();
        handler.sendMessage(message);
    }
}