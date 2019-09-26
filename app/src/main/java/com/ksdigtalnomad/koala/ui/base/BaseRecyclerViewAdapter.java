package com.ksdigtalnomad.koala.ui.base;

import android.support.v7.widget.RecyclerView;

import lombok.NonNull;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    protected void setItemClickListener(@NonNull VH holder) {
        holder.itemView.setOnClickListener(view -> {
            if (itemClickListener != null) itemClickListener.onItemClick(holder.getAdapterPosition());
        });
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
