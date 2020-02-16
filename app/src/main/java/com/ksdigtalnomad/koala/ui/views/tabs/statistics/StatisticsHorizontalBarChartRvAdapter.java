package com.ksdigtalnomad.koala.ui.views.tabs.statistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class StatisticsHorizontalBarChartRvAdapter extends BaseRecyclerViewAdapter<StatisticsHorizontalBarChartRvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HorizontalBarChartItem> itemList;

    StatisticsHorizontalBarChartRvAdapter(Context context, ArrayList<HorizontalBarChartItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.cell_statistics_chart_horizontalbar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Builder @ToString @Getter @Setter
    static class HorizontalBarChartItem {
        String title;
        BarData barData;
        ArrayList<String> xAxisLabelList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView totalBtn;
        HorizontalBarChart chart;
        public HorizontalBarChartItem item;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            chart = itemView.findViewById(R.id.chart);

            chart.setDrawBarShadow(false); //
            chart.setDrawValueAboveBar(true); //
            chart.getDescription().setEnabled(false); //
            chart.setMaxVisibleValueCount(60); //
            chart.setPinchZoom(false); //
            chart.setDoubleTapToZoomEnabled(false);
            chart.setScaleEnabled(false);
            chart.setDrawBarShadow(false);
            chart.setDrawGridBackground(false); //
            chart.setFitBars(true);
            chart.setExtraLeftOffset(40);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setXOffset(-10);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int pos = (int) value/10;
                    if(pos == 0) return "";
                    ArrayList<String> xAxisLabelList = item.xAxisLabelList;
                    if(xAxisLabelList == null || xAxisLabelList.isEmpty() || xAxisLabelList.size() < pos) return "";

                    return xAxisLabelList.get(pos -1);
                }
            });



            chart.getAxisLeft().setEnabled(false);
            chart.getAxisRight().setEnabled(false);
            chart.getLegend().setEnabled(false);


            itemView.findViewById(R.id.totalBtn).setOnClickListener((view)->{
                // @TODO:
                ToastHelper.writeBottomLongToast("메롱~");
            });
        }

        public void setItem(HorizontalBarChartItem item){
            this.item = item;
            title.setText(item.getTitle());
            chart.post(()->{
                chart.animateY(1500);
                chart.setData(item.barData);
            });
        }
    }

}
