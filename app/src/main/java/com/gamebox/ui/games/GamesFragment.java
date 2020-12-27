package com.gamebox.ui.games;

import android.content.Intent;
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
import com.gamebox.util.SimpleRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GamesFragment extends Fragment implements OnItemClickListener {

    /**
     * Array of reference id's of the names of all the games to show in the list.
     */
    private static final int[] GAME_NAMES = new int[]{
            R.string.tic_tac_toe_game,
            R.string.simon_game,
            R.string.face_clicker_game
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_games, container, false);

        // Create Recycler View
        RecyclerView recyclerView = root.findViewById(R.id.games_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        SimpleRecyclerViewAdapter gamesAdapter = new SimpleRecyclerViewAdapter(this.getContext(),
                GAME_NAMES, this,
                R.layout.recycler_view_item_games, R.id.games_recycler_item_name);
        recyclerView.setAdapter(gamesAdapter);

        // Rick roll fab
        MediaPlayer rickRoll = MediaPlayer.create(this.getContext(), R.raw.rick_roll);
        FloatingActionButton fab = root.findViewById(R.id.rick_roll_fab);
        fab.setOnClickListener(view -> rickRoll.start());

        return root;
    }

    /**
     * Called when one of the items in the recycler view is clicked. This starts a new activity depending on which item was clicked.
     *
     * @param gameName The reference id of the name of the game.
     */
    @Override
    public void onItemClicked(int gameName) {
        Class<?> c = null;
        if (R.string.tic_tac_toe_game == gameName) { // Tic-tac-toe
            c = TicTacToeActivity.class;
        } else if (R.string.simon_game == gameName) { // Simon
            c = SimonActivity.class;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this.getContext(), "This game is currently under development...", Toast.LENGTH_LONG).show();
        } else if (R.string.face_clicker_game == gameName) { // Face Clicker
            c = FaceClickerActivity.class;
        }
        if (c != null) {
            Intent intent = new Intent(this.getContext(), c);
            startActivity(intent);
        }
    }
}