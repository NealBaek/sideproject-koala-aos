package com.ksdigtalnomad.koala.ui.views.dialogs.interview;

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


public class InterviewWhythisappListAdapter extends BaseRecyclerViewAdapter<InterviewWhythisappListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Item> itemList;

    InterviewWhythisappListAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.cell_interview_whythisapp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(itemList.get(position));
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.post(()->{
                itemClickListener.onItemClick(holder.getAdapterPosition()); // change save btn enable
            });
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Builder @ToString @Getter @Setter
    static class Item {
        String title;
        boolean isSelected;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public Item item;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        public void setItem(Item item){
            this.item = item;

            title.setText(item.getTitle());
            if(item.isSelected){
                title.setTextColor(BaseApplication.getInstance().getResources().getColor(R.color.colorPureWhite));
                title.setBackground(BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_cell_round_maincolor_on));
            }else{
                title.setTextColor(BaseApplication.getInstance().getResources().getColor(R.color.colorDarkGray));
                title.setBackground(BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_cell_round_gray_off));
            }

        }
    }

}
