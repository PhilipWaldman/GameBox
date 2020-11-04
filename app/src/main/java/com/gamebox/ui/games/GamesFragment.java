package com.gamebox.ui.games;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;
import com.gamebox.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GamesFragment extends Fragment implements OnItemClickListener {

    private static final int[] GAME_NAMES = new int[]{
            R.string.tic_tac_toe_game,
            R.string.simon_game
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_games, container, false);

        // Recycler View
        RecyclerView recyclerView = root.findViewById(R.id.games_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        GamesAdapter gamesAdapter = new GamesAdapter(this.getContext(), GAME_NAMES, this);
        recyclerView.setAdapter(gamesAdapter);

        // Rick roll fab
        MediaPlayer rickRoll = MediaPlayer.create(this.getContext(), R.raw.rick_roll);
        FloatingActionButton fab = root.findViewById(R.id.rick_roll_fab);
        fab.setOnClickListener(view -> rickRoll.start());

        return root;
    }

    @Override
    public void onItemClicked(int gameName) {
        if (R.string.tic_tac_toe_game == gameName) { // Tic-tac-toe
            Intent intent = new Intent(this.getContext(), TicTacToeActivity.class);
            startActivity(intent);
        } else if (R.string.simon_game == gameName) {
            Intent intent = new Intent(this.getContext(), SimonActivity.class);
            startActivity(intent);
        }
    }
}