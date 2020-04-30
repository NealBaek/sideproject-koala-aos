package com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.detailListEditNew;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.calendar.BaseData;
import com.ksdigtalnomad.koala.data.models.calendar.Drink;
import com.ksdigtalnomad.koala.data.models.calendar.Food;
import com.ksdigtalnomad.koala.data.models.calendar.Friend;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;
import com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.EditType;

import java.util.ArrayList;
import java.util.Locale;

public class CalendarDetailListNewAdapter extends BaseRecyclerViewAdapter<CalendarDetailListNewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BaseData> originalList;
    private ArrayList<BaseData> searchList = new ArrayList<>();
    private EditType editType;

    CalendarDetailListNewAdapter(Context context, ArrayList<BaseData> list, EditType editType) {
        this.context = context;
        this.originalList = list;
        this.editType = editType;
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
                switch (editType){
                    case friends: name = ((Friend)item).getName(); break;
                    case foods: name = ((Food)item).getName(); break;
                    case drinks: name = ((Drink)item).getName(); break;
                }
                if (name.toLowerCase().contains(charText)) searchList.add(item);
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
                R.layout.cell_calendar_detail_edit_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setBaseData(searchList.get(position), editType);

        holder.itemView.setOnClickListener(v -> {

            holder.onItemClick(); // Change cell selected


            int originalDataCnt = originalList.size();

            BaseData searchBaseData = holder.baseData;

            if(editType == EditType.friends){
                Friend searchItem = (Friend) searchBaseData;

                for(int i = 0; i < originalDataCnt; ++i){

                    Friend item = (Friend) originalList.get(i);
                    if(searchItem.getName().equals(item.getName())){
                        item.setSelected(searchItem.isSelected());
                        break;
                    }
                }

            }else if(editType == EditType.foods){
                Food searchItem = (Food) searchBaseData;

                for(int i = 0; i < originalDataCnt; ++i){

                    Food item = (Food) originalList.get(i);
                    if(searchItem.getName().equals(item.getName())){
                        item.setSelected(searchItem.isSelected());
                        break;
                    }
                }

            }else if(editType == EditType.drinks){
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
                notifyItemChanged(holder.getAdapterPosition());
            });
        });

        holder.amountLayout.setOnClickListener((v)->{
            ToastHelper.writeBottomShortToast("헤헤");
            // TODO: 주량 팝업 추가
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
        RelativeLayout amountLayout;
        public BaseData baseData;
        EditType editType;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            checkBtn = itemView.findViewById(R.id.checkBtn);
            amountLayout = itemView.findViewById(R.id.layout_amount);
        }

        public void onItemClick(){
            switch (editType) {
                case friends:
                    Friend friend = (Friend) baseData;
                    friend.setSelected(!friend.isSelected());
                    setSelected(friend.isSelected());

                    break;
                case foods:
                    Food food = (Food) baseData;
                    food.setSelected(!food.isSelected());
                    setSelected(food.isSelected());

                    break;
                case drinks:
                    Drink drink = (Drink) baseData;
                    drink.setSelected(!drink.isSelected());
                    setSelected(drink.isSelected());
                    changeVisibility(drink.isSelected());

                    break;
            }
        }
        private void setSelected(boolean isSelected){
            title.setSelected(isSelected);
            checkBtn.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }

        public void setBaseData(BaseData baseData, EditType editType){

            this.baseData = baseData;
            this.editType = editType;

            switch (editType) {
                case friends:
                    Friend friend = (Friend) baseData;
                    title.setText(friend.getName());
                    checkBtn.setVisibility(friend.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
                case foods:
                    Food food = (Food) baseData;
                    title.setText(food.getName());
                    checkBtn.setVisibility(food.isSelected() ? View.VISIBLE : View.INVISIBLE);

                    break;
                case drinks:
                    Drink drink = (Drink) baseData;
                    title.setText(drink.getName());
                    checkBtn.setVisibility(drink.isSelected() ? View.VISIBLE : View.INVISIBLE);
                    changeVisibility(drink.isSelected());

                    break;
            }

        }

        private void changeVisibility(final boolean isSelected) {
            ValueAnimator va = isSelected ? ValueAnimator.ofInt(0, amountLayout.getHeight()) : ValueAnimator.ofInt(amountLayout.getHeight(), 0);

            va.setDuration(300);
            va.addUpdateListener(
                (ValueAnimator animation)->{
                    amountLayout.requestLayout();
                    amountLayout.setVisibility(isSelected ? View.VISIBLE : View.GONE);
                }

            );

            va.start();
        }
    }

}
