package com.gamebox.ui.games;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gamebox.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GamesFragment extends Fragment {

    private GamesViewModel gamesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_games, container, false);

        final TextView textView = root.findViewById(R.id.text_games);
        gamesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Rick roll fab
        MediaPlayer rickRoll = MediaPlayer.create(this.getContext(), R.raw.rick_roll);
        FloatingActionButton fab = root.findViewById(R.id.rick_roll_fab);
        fab.setOnClickListener(view -> rickRoll.start());

        return root;
    }
}