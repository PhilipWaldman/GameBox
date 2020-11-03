package com.gamebox.ui.games;

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

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {
    private final LayoutInflater inflater;
    private final int[] games;
    OnItemClickListener onClickListener;

    public GamesAdapter(Context context, int[] games, OnItemClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.games = games;
    }

    @NotNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_view_item_games, parent, false);
        return new GamesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position) {
        holder.bind(games[position], onClickListener);
    }

    @Override
    public int getItemCount() {
        return games.length;
    }


    public static class GamesViewHolder extends RecyclerView.ViewHolder {
        public TextView gameName;
        public View itemView;

        public GamesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            gameName = itemView.findViewById(R.id.games_recycler_item_name);
        }

        public void bind(int gameName, OnItemClickListener clickListener) {
            this.gameName.setText(gameName);
            itemView.setOnClickListener(v -> clickListener.onItemClicked(gameName));
        }
    }
}