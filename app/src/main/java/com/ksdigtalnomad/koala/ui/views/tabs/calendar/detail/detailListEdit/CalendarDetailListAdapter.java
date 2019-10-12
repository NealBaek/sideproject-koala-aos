package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;


public class CalendarDetailListAdapter extends BaseRecyclerViewAdapter<CalendarDetailListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    CalendarDetailListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.cell_calendar_detail_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Terms item = list.get(position);

//        holder.title.setText(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            holder.title.setSelected(true);
            holder.detailBtn.setSelected(true);

            itemClickListener.onItemClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView detailBtn;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
