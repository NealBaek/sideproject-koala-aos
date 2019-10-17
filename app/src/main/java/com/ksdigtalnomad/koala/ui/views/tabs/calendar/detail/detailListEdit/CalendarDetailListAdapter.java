package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.BaseData;
import com.ksdigtalnomad.koala.data.models.Drink;
import com.ksdigtalnomad.koala.data.models.Food;
import com.ksdigtalnomad.koala.data.models.Friend;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;


public class CalendarDetailListAdapter extends BaseRecyclerViewAdapter<CalendarDetailListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BaseData> list;
    private String viewType;

    CalendarDetailListAdapter(Context context, ArrayList<BaseData> list, String viewType) {
        this.context = context;
        this.list = list;
        this.viewType = viewType;
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

        holder.setBaseData(list.get(position), viewType);

        holder.itemView.setOnClickListener(v -> {
            holder.onItemClick();
            itemClickListener.onItemClick(holder.getAdapterPosition());
        });

        holder.itemView.setOnLongClickListener(v -> {
            itemClickListener.onItemLongClick(holder.getAdapterPosition());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView checkBtn;
        BaseData baseData;
        String viewType;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            checkBtn = itemView.findViewById(R.id.checkBtn);
        }

        public void onItemClick(){
            switch (viewType) {
                case CalendarDetailListEditActivity.TYPE_FRIENDS :
                    Friend friend = (Friend) baseData;
                    friend.setSelected(!friend.isSelected());
                    title.setSelected(friend.isSelected());
                    checkBtn.setVisibility(friend.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
                case CalendarDetailListEditActivity.TYPE_FOODS :
                    Food food = (Food) baseData;
                    food.setSelected(!food.isSelected());
                    title.setSelected(food.isSelected());
                    checkBtn.setVisibility(food.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
                case CalendarDetailListEditActivity.TYPE_DRINKS :
                    Drink drink = (Drink) baseData;
                    drink.setSelected(!drink.isSelected());
                    title.setSelected(drink.isSelected());
                    checkBtn.setVisibility(drink.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
            }
        }

        public void setBaseData(BaseData baseData, String viewType){

            this.baseData = baseData;
            this.viewType = viewType;

            switch (viewType) {
                case CalendarDetailListEditActivity.TYPE_FRIENDS :
                    Friend friend = (Friend) baseData;
                    title.setText(friend.getName());
                    checkBtn.setVisibility(friend.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
                case CalendarDetailListEditActivity.TYPE_FOODS :
                    Food food = (Food) baseData;
                    title.setText(food.getName());
                    checkBtn.setVisibility(food.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
                case CalendarDetailListEditActivity.TYPE_DRINKS :
                    Drink drink = (Drink) baseData;
                    title.setText(drink.getName());
                    checkBtn.setVisibility(drink.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
            }

        }
    }

}
