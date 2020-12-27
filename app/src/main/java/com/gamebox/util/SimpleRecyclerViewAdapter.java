package com.gamebox.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ItemsViewHolder> {
    private final LayoutInflater inflater;
    private final int[] items;
    private final OnItemClickListener onClickListener;
    private final int recyclerViewItem;
    private final int recyclerViewItemName;

    public SimpleRecyclerViewAdapter(Context context, int[] items, OnItemClickListener onClickListener,
                                     int recyclerViewItem, int recyclerViewItemName) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.items = items;
        this.recyclerViewItem = recyclerViewItem;
        this.recyclerViewItemName = recyclerViewItemName;
    }

    @NotNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(recyclerViewItem, parent, false);
        return new ItemsViewHolder(itemView, recyclerViewItemName);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        holder.bind(items[position], onClickListener);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public View itemView;

        public ItemsViewHolder(View itemView, int recyclerViewItemName) {
            super(itemView);
            this.itemView = itemView;
            itemName = itemView.findViewById(recyclerViewItemName);
        }

        public void bind(int itemName, OnItemClickListener clickListener) {
            this.itemName.setText(itemName);
            itemView.setOnClickListener(v -> clickListener.onItemClicked(itemName));
        }
    }
}