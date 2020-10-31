package com.gamebox.ui.tools;

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

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ToolsViewHolder> {
    private final LayoutInflater inflater;
    private final String[] tools;
    OnItemClickListener onClickListener;

    public ToolsAdapter(Context context, String[] tools, OnItemClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.tools = tools;
    }

    @NotNull
    @Override
    public ToolsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_view_item_tools, parent, false);
        return new ToolsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToolsViewHolder holder, int position) {
        holder.bind(tools[position], onClickListener);
    }

    @Override
    public int getItemCount() {
        return tools.length;
    }


    public static class ToolsViewHolder extends RecyclerView.ViewHolder {
        public TextView toolName;
        public View itemView;

        public ToolsViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            toolName = itemView.findViewById(R.id.tools_recycler_item_name);
        }

        public void bind(String toolName, OnItemClickListener clickListener) {
            this.toolName.setText(toolName);
            itemView.setOnClickListener(v -> clickListener.onItemClicked(toolName));
        }
    }
}