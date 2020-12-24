package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gamebox.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FaceClickerActivity extends AppCompatActivity {

    private final Random random = new Random();
    private final ImageButton[][] faceButtons = new ImageButton[3][3];
    private final boolean[][] faceStates = new boolean[3][3]; // false = off, true = on
    private Button startButton;
    private boolean gameStarted = false;
    private int round = 0, score = 0;
    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_clicker);

        scoreView = findViewById(R.id.face_score);

        for (boolean[] row : faceStates) {
            Arrays.fill(row, false);
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
                        if (faceStates[r][c]) {
                            turnFaceOff(r, c);
                        } else {
                            endGame();
                        }
                    }
                });
            }
        }

        startButton = findViewById(R.id.face_start_button);
        startButton.setOnClickListener(v -> {
            if (!gameStarted) {
                startGame();
            } else {
                endGame();
            }
        });
    }

    private void turnFaceOff(int row, int col) {
        faceStates[row][col] = false;
        faceButtons[row][col].setImageResource(R.drawable.ic_face_clicker_no_face);
    }

    private void turnFaceOn(int row, int col) {
        faceStates[row][col] = true;
        faceButtons[row][col].setImageResource(R.drawable.ic_face_clicker_face);
    }

    private void startGame() {
        gameStarted = true;
        startButton.setText(R.string.stop);

//        long prevTime = System.currentTimeMillis();
        int numFaces = 0;
        while (gameStarted) {
//            long curTime = System.currentTimeMillis();
            try {
                Thread.sleep(1000); // TODO: need better game loop design pattern
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if (curTime / 1000 != prevTime / 1000) {
//                prevTime = curTime;
            if (!areAllFacesOff()) {
                endGame();
                return;
            }
            score += numFaces;
            String scoreText = "Score: " + score;
            scoreView.setText(scoreText);
            round++;
            numFaces = Math.min((round - 1) / 10 + 1, 9);
            turnOnFacesForNextRound(numFaces);
//            }
        }
    }

    private void endGame() {
        gameStarted = false;
        startButton.setText(R.string.start);
        for (boolean[] row : faceStates) {
            Arrays.fill(row, false);
        }
    }

    private void turnOnFacesForNextRound(int numFaces) {
        ArrayList<Integer> faceNumbers = new ArrayList<>();
        while (faceNumbers.size() < numFaces) {
            int num = random.nextInt(9);
            if (!faceNumbers.contains(num)) {
                faceNumbers.add(num);
                turnFaceOn(num / faceStates.length, num % faceStates.length);
            }
        }
    }

    private boolean areAllFacesOff() {
        for (boolean[] row : faceStates) {
            for (boolean state : row) {
                if (state) {
                    return false;
                }
            }
        }
        return true;
    }
}