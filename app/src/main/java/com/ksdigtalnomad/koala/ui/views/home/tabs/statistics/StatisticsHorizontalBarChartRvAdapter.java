package com.ksdigtalnomad.koala.ui.views.home.tabs.statistics;

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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class StatisticsHorizontalBarChartRvAdapter extends BaseRecyclerViewAdapter<StatisticsHorizontalBarChartRvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HorizontalBarChartItem> itemList;

    public static final int MAX_DATA_CNT = 5;
    public static final int LABEL_CNT = 6;
    public static final float BAR_WIDTH = 8f;
    public static final float BAR_SPACE = 10f;
    public static final int [] COLOR_ARRAY_MAIN =
            {CalendarConstUtils.getDrunkLvColorMainByStep(4),
                    CalendarConstUtils.getDrunkLvColorMainByStep(3),
                    CalendarConstUtils.getDrunkLvColorMainByStep(2),
                    CalendarConstUtils.getDrunkLvColorMainByStep(1),
                    CalendarConstUtils.getDrunkLvColorMainByStep(0)};
    public static final String INVISIBLE_LABEL_SPACE = " "; /** 절대 지우지 말것. */

    StatisticsHorizontalBarChartRvAdapter(Context context, ArrayList<HorizontalBarChartItem> itemList) {
        this.context = context;
        this.itemList = itemList;
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
        holder.setItem(itemList.get(position), itemList.size() == (position + 1));
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
        private TextView title;
        private HorizontalBarChart chart;
        private View divider;
        private HorizontalBarChartItem item;


        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            chart = itemView.findViewById(R.id.chart);
            divider = itemView.findViewById(R.id.divider);

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
            chart.setExtraLeftOffset(100);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setXOffset(-10);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int pos = LABEL_CNT /** 절대 들지말것.. */ - (int) value/10;
                    ArrayList<String> xAxisLabelList = item.xAxisLabelList;
                    if(xAxisLabelList == null || xAxisLabelList.isEmpty() || xAxisLabelList.size() <= pos) return "";
                    String label = xAxisLabelList.get(pos);
                    int length = label.length();
                    if(length >= 10) {
                        label = label.substring(0, 9);
                    }else{ /** 절대 건들지말것.. */
                        int cnt = 10 - length;
                        for(int i = 0; i < cnt; ++i){ label += INVISIBLE_LABEL_SPACE; }
                    }
                    return label;
                }
            });



            chart.getAxisLeft().setEnabled(false);
            chart.getAxisRight().setEnabled(false);
            chart.getLegend().setEnabled(false);


//            itemView.findViewById(R.id.totalBtn).setOnClickListener((view)->{
//                // @TODO:
//                ToastHelper.writeBottomLongToast("메롱~");
//            });
        }

        public void setItem(HorizontalBarChartItem item, boolean isLast){
            this.item = item;
            title.setText(item.getTitle());
            chart.post(()->{
                chart.clearAnimation();
                chart.animateY(1500);
                chart.setData(item.barData);
            });
            divider.setVisibility(isLast ? View.GONE : View.VISIBLE);
        }
    }

}
