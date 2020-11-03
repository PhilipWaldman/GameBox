package com.gamebox.ui.demos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;
import com.gamebox.R;

import org.jetbrains.annotations.NotNull;

public class DemosAdapter extends RecyclerView.Adapter<DemosAdapter.DemosViewHolder> {
    private final LayoutInflater inflater;
    private final int[] demos;
    OnItemClickListener onClickListener;

    public DemosAdapter(Context context, int[] demos, OnItemClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.demos = demos;
    }

    @NotNull
    @Override
    public DemosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_view_item_demos, parent, false);
        return new DemosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DemosViewHolder holder, int position) {
        holder.bind(demos[position], onClickListener);
    }

    @Override
    public int getItemCount() {
        return demos.length;
    }


    public static class DemosViewHolder extends RecyclerView.ViewHolder {
        public TextView demoName;
        public View itemView;

        public DemosViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            demoName = itemView.findViewById(R.id.demos_recycler_item_name);
        }

        public void bind(int demoName, OnItemClickListener clickListener) {
            this.demoName.setText(demoName);
            itemView.setOnClickListener(v -> clickListener.onItemClicked(demoName));
        }
    }
}