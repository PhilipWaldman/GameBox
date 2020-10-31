package com.gamebox.ui.games;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;
import com.gamebox.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GamesFragment extends Fragment implements OnItemClickListener {

    private static final String[] GAME_NAMES = new String[]{
            "Game 1",
            "Game 2",
            "Game 3"
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
    public void onItemClicked(String gameName) {
        if (GAME_NAMES[0].equals(gameName)) {
            Toast.makeText(this.getContext(), gameName, Toast.LENGTH_LONG).show();
            // Open game 1
        } else if (GAME_NAMES[1].equals(gameName)) {
            Toast.makeText(this.getContext(), gameName, Toast.LENGTH_LONG).show();
            // Open game 2
        } else if (GAME_NAMES[2].equals(gameName)) {
            Toast.makeText(this.getContext(), gameName, Toast.LENGTH_LONG).show();
            // Open game 3
        }
    }
}