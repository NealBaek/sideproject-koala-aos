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
import java.util.Locale;

import static com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit.CalendarDetailListEditActivity.TYPE_DRINKS;
import static com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit.CalendarDetailListEditActivity.TYPE_FOODS;
import static com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit.CalendarDetailListEditActivity.TYPE_FRIENDS;


public class CalendarDetailListAdapter extends BaseRecyclerViewAdapter<CalendarDetailListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BaseData> originalList;
    private ArrayList<BaseData> searchList = new ArrayList<>();
    private String viewType;

    CalendarDetailListAdapter(Context context, ArrayList<BaseData> list, String viewType) {
        this.context = context;
        this.originalList = list;
        this.viewType = viewType;
        this.searchList.addAll(list);
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchList.clear();
        if (charText.length() == 0) {
            searchList.addAll(originalList);
        } else {
            for (BaseData item : originalList) {
                String name = "";
                if(viewType.equals(TYPE_FRIENDS)){
                    name = ((Friend)item).getName();
                }else if(viewType.equals(TYPE_FOODS)){
                    name = ((Food)item).getName();
                }else if(viewType.equals(TYPE_DRINKS)){
                    name = ((Drink)item).getName();
                }
                if (name.toLowerCase().contains(charText)) {
                    searchList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
    public ArrayList<BaseData> getSearchList(){
        return searchList;
    }
    public ArrayList<BaseData> getOriginalList(){
        return originalList;
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

        holder.setBaseData(searchList.get(position), viewType);

        holder.itemView.setOnClickListener(v -> {

            holder.onItemClick(); // Change cell selected


            int originalDataCnt = originalList.size();

            BaseData searchBaseData = holder.baseData;
            if(viewType.equals(TYPE_FRIENDS)){
                Friend searchItem = (Friend) searchBaseData;

                for(int i = 0; i < originalDataCnt; ++i){

                    Friend item = (Friend) originalList.get(i);
                    if(searchItem.getName().equals(item.getName())){
                        item.setSelected(searchItem.isSelected());
                        originalList.set(i, item);
                        break;
                    }
                }

            }

            if(viewType.equals(TYPE_FRIENDS)){
                Friend searchItem = (Friend) searchBaseData;

                for(int i = 0; i < originalDataCnt; ++i){

                    Friend item = (Friend) originalList.get(i);
                    if(searchItem.getName().equals(item.getName())){
                        item.setSelected(searchItem.isSelected());
                        break;
                    }
                }

            }else if(viewType.equals(TYPE_FOODS)){
                Food searchItem = (Food) searchBaseData;

                for(int i = 0; i < originalDataCnt; ++i){

                    Food item = (Food) originalList.get(i);
                    if(searchItem.getName().equals(item.getName())){
                        item.setSelected(searchItem.isSelected());
                        break;
                    }
                }

            }else if(viewType.equals(TYPE_DRINKS)){
                Drink searchItem = (Drink) searchBaseData;

                for(int i = 0; i < originalDataCnt; ++i){

                    Drink item = (Drink) originalList.get(i);
                    if(searchItem.getName().equals(item.getName())){
                        item.setSelected(searchItem.isSelected());
                        break;
                    }
                }

            }




            holder.itemView.post(()->{
                itemClickListener.onItemClick(holder.getAdapterPosition()); // change save btn enable
            });
        });

        holder.itemView.setOnLongClickListener(v -> {
            itemClickListener.onItemLongClick(holder.getAdapterPosition());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView checkBtn;
        public BaseData baseData;
        String viewType;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            checkBtn = itemView.findViewById(R.id.checkBtn);
        }

        public void onItemClick(){
            switch (viewType) {
                case TYPE_FRIENDS :
                    Friend friend = (Friend) baseData;
                    friend.setSelected(!friend.isSelected());
                    setSelected(friend.isSelected());

                    break;
                case CalendarDetailListEditActivity.TYPE_FOODS :
                    Food food = (Food) baseData;
                    food.setSelected(!food.isSelected());
                    setSelected(food.isSelected());

                    break;
                case CalendarDetailListEditActivity.TYPE_DRINKS :
                    Drink drink = (Drink) baseData;
                    drink.setSelected(!drink.isSelected());
                    setSelected(drink.isSelected());

                    break;
            }
        }
        private void setSelected(boolean isSelected){
            title.setSelected(isSelected);
            checkBtn.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }

        public void setBaseData(BaseData baseData, String viewType){

            this.baseData = baseData;
            this.viewType = viewType;

            switch (viewType) {
                case TYPE_FRIENDS :
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
