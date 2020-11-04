package com.gamebox.ui.games;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gamebox.R;
import com.gamebox.util.ToneGenerator;

public class SimonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        ToneGenerator toneGenerator = new ToneGenerator(440, 3);
        toneGenerator.play();
    }
}