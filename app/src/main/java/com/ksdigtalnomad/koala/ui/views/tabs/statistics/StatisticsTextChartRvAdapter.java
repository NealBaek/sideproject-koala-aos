package com.ksdigtalnomad.koala.ui.views.tabs.statistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class StatisticsTextChartRvAdapter extends BaseRecyclerViewAdapter<StatisticsTextChartRvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TextChartItem> itemList;

    StatisticsTextChartRvAdapter(Context context, ArrayList<TextChartItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.cell_statistics_chart_text, parent, false);
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
    static class TextChartItem {
        String title;
        String data;
        String info;
        boolean enoughData;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView data;
        TextView info;
        public TextChartItem item;
        private final int COLOR_MAIN = BaseApplication.getInstance().getResources().getColor(R.color.colorMain);
        private final int COLOR_GRAY = BaseApplication.getInstance().getResources().getColor(R.color.colorGray);
        private final int COLOR_DKGRAY = BaseApplication.getInstance().getResources().getColor(R.color.colorDarkGray);
        private final String NOTENOUGTH_DATA = BaseApplication.getInstance().getResources().getString(R.string.fragment_tab_statistics_notenough_data);
        private final String NOTENOUGTH_INFO = BaseApplication.getInstance().getResources().getString(R.string.fragment_tab_statistics_notenough_info);

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            data = itemView.findViewById(R.id.data);
            info = itemView.findViewById(R.id.info);
        }

        public void setItem(TextChartItem item){
            this.item = item;
            title.setText(item.getTitle());

            if(item.enoughData){
                data.setText(item.getData());
                info.setText(item.getInfo());
                data.setTextColor(COLOR_MAIN);
                info.setTextColor(COLOR_DKGRAY);
            }else{
                data.setText(NOTENOUGTH_DATA);
                info.setText(NOTENOUGTH_INFO);
                data.setTextColor(COLOR_GRAY);
                info.setTextColor(COLOR_GRAY);
            }

        }
    }

}
