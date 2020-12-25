package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.gamebox.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FaceClickerActivity extends AppCompatActivity {

    private final Random random = new Random();
    private final ImageButton[][] faceButtons = new ImageButton[3][3];
    MutableLiveData<Boolean>[][] faceStatesLD; // false = off, true = on
    MutableLiveData<Integer> startButtonText;
    MutableLiveData<String> scoreViewText;
    private boolean gameStarted = false;
    private int round = 0, score = 0;
    private int numFaces = 0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_clicker);

        faceStatesLD = new MutableLiveData[faceButtons.length][faceButtons[0].length];
        for (int i = 0; i < faceButtons.length; i++) {
            for (int j = 0; j < faceButtons[i].length; j++) {
                int c = j;
                int r = i;
                faceStatesLD[r][c] = new MutableLiveData<>();
                faceStatesLD[r][c].setValue(false);
                Observer<Boolean> faceStateObserver = state -> faceButtons[r][c].setImageResource(
                        state ? R.drawable.ic_face_clicker_face : R.drawable.ic_face_clicker_no_face);
                faceStatesLD[r][c].observe(this, faceStateObserver);
            }
        }

        faceButtons[0][0] = findViewById(R.id.face_0_0);
        faceButtons[0][1] = findViewById(R.id.face_0_1);
        faceButtons[0][2] = findViewById(R.id.face_0_2);
        faceButtons[1][0] = findViewById(R.id.face_1_0);
        faceButtons[1][1] = findViewById(R.id.face_1_1);
        faceButtons[1][2] = findViewById(R.id.face_1_2);
        faceButtons[2][0] = findViewById(R.id.face_2_0);
        faceButtons[2][1] = findViewById(R.id.face_2_1);
        faceButtons[2][2] = findViewById(R.id.face_2_2);

        for (int i = 0; i < faceButtons.length; i++) {
            for (int j = 0; j < faceButtons[i].length; j++) {
                int c = j;
                int r = i;
                faceButtons[r][c].setOnClickListener(v -> {
                    if (gameStarted) {
                        if (faceStatesLD[r][c].getValue()) {
                            turnFaceOff(r, c);
                        } else {
                            endGame();
                        }
                    }
                });
            }
        }

        Button startButton = findViewById(R.id.face_start_button);
        startButton.setOnClickListener(v -> {
            if (!gameStarted) {
                startGame();
            } else {
                endGame();
            }
        });
        startButtonText = new MutableLiveData<>();
        Observer<Integer> startButtonObserver = startButton::setText;
        startButtonText.observe(this, startButtonObserver);

        TextView scoreView = findViewById(R.id.face_score);
        scoreViewText = new MutableLiveData<>();
        Observer<String> scoreViewObserver = scoreView::setText;
        scoreViewText.observe(this, scoreViewObserver);
    }

    private void turnFaceOff(int row, int col) {
        faceStatesLD[row][col].postValue(false);
    }

    private void turnFaceOn(int row, int col) {
        faceStatesLD[row][col].postValue(true);
    }

    private void startGame() {
        score = 0;
        gameStarted = true;
        startButtonText.setValue(R.string.stop);

        int delay = 2000;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (this) {
                    tick();
                }
            }
        }, 1000, delay);
    }

    private void tick() {
        if (!areAllFacesOff()) {
            endGame();
            return;
        }
        score += numFaces;
        scoreViewText.postValue("Score: " + score);
        round++;
        numFaces = Math.min((round - 1) / 10 + 1, 9);
        turnOnFacesForNextRound(numFaces);
    }

    private void endGame() {
        gameStarted = false;
        startButtonText.postValue(R.string.start);
        for (int r = 0; r < faceButtons.length; r++) {
            for (int c = 0; c < faceButtons[r].length; c++) {
                turnFaceOff(r, c);
                timer.cancel();
            }
        }
    }

    private void turnOnFacesForNextRound(int numFaces) {
        ArrayList<Integer> faceNumbers = new ArrayList<>();
        while (faceNumbers.size() < numFaces) {
            int num = random.nextInt(9);
            if (!faceNumbers.contains(num)) {
                faceNumbers.add(num);
                turnFaceOn(num / faceButtons.length, num % faceButtons.length);
            }
        }
    }

    private boolean areAllFacesOff() {
        for (MutableLiveData<Boolean>[] row : faceStatesLD) {
            for (MutableLiveData<Boolean> state : row) {
                if (state.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}