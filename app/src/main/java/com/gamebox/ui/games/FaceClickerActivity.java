package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.gamebox.R;
import com.gamebox.util.ToneGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FaceClickerActivity extends AppCompatActivity {

    /**
     * The tone to play when a face is clicked.
     * Plays 555 Hz for 0.1 s.
     */
    private static final ToneGenerator FACE_CLICK = new ToneGenerator(555, 0.1);
    /**
     * The tone to play when the user loses (clicked an already off face, ran out of time, etc.).
     * Plays 333 Hz for 0.3 s.
     */
    private static final ToneGenerator FAIL = new ToneGenerator(333, 0.3);
    /**
     * The tone to play when the user levels up (every 10 rounds).
     * Plays 444 Hz for 0.2 s.
     */
    private static final ToneGenerator LEVEL_UP = new ToneGenerator(444, 0.2);

    private final Random random = new Random();
    private final ImageButton[][] faceButtons = new ImageButton[3][3]; // 2D array of the ImageButtons for the faces that can be pressed.
    MutableLiveData<Boolean>[][] faceStates; // 2D array of LiveData for the states of the faces that can be pressed. false = off, true = on.
    MutableLiveData<Integer> startButtonText; // LiveData that contains the reference id to the string that should be shown in the start/stop button.
    MutableLiveData<String> scoreViewText; // LiveData that contains the string to display the score.
    private boolean gameStarted = false; // Whether the game has started.
    private int round; // The current round.
    private int score; // The current score.
    private int numFaces; // The number of faces to turned on this round.
    private Timer gameLoop; // The timer for the game loop.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_clicker);

        faceStates = new MutableLiveData[faceButtons.length][faceButtons[0].length];
        for (int i = 0; i < faceButtons.length; i++) {
            for (int j = 0; j < faceButtons[i].length; j++) {
                int c = j;
                int r = i;
                // Initialize faceStates array
                faceStates[r][c] = new MutableLiveData<>();
                faceStates[r][c].setValue(false);
                // Add observer to each state to change the corresponding faceButton to the appropriate face
                Observer<Boolean> faceStateObserver = state -> faceButtons[r][c].setImageResource(
                        state ? R.drawable.ic_face_clicker_face : R.drawable.ic_face_clicker_no_face);
                faceStates[r][c].observe(this, faceStateObserver);
            }
        }

        // Initialize faceButtons
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
                // Add a click listener to every faceButton
                faceButtons[r][c].setOnClickListener(v -> onFaceButtonClicked(r, c));
            }
        }

        // Find start button, add click listener and update text through observer
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

        // find scoreView and assign values through observer
        TextView scoreView = findViewById(R.id.face_score);
        scoreViewText = new MutableLiveData<>();
        Observer<String> scoreViewObserver = scoreView::setText;
        scoreViewText.observe(this, scoreViewObserver);
    }

    /**
     * Code to be run when a face button is clicked.
     *
     * @param r The row of the button.
     * @param c The column of the button.
     */
    private void onFaceButtonClicked(int r, int c) {
        if (gameStarted) {
            // Only do stuff when the game has started.
            if (faceStates[r][c].getValue()) {
                // If the face was on, turn it off and play a sound.
                turnFaceOff(r, c);
                FACE_CLICK.play();
            } else {
                // If the face was off, end the game.
                endGame();
            }
        }
    }

    /**
     * Turns the face at (row, col) off.
     *
     * @param row The row of the face.
     * @param col The column of the face.
     */
    private void turnFaceOff(int row, int col) {
        faceStates[row][col].postValue(false);
    }

    /**
     * Turns the face at (row, col) on.
     *
     * @param row The row of the face.
     * @param col The column of the face.
     */
    private void turnFaceOn(int row, int col) {
        faceStates[row][col].postValue(true);
    }

    /**
     * Start a new game.
     */
    private void startGame() {
        // Values are initialized.
        numFaces = 0;
        round = 0;
        score = 0;
        gameStarted = true;
        startButtonText.setValue(R.string.stop);

        // Create the game loop.
        int delay = 2000;
        gameLoop = new Timer();
        gameLoop.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (this) {
                    tick();
                }
            }
        }, 1000, delay);
    }

    /**
     * This is run whenever the game loop ticks.
     */
    private void tick() {
        if (!areAllFacesOff()) {
            // If not all faces are turned off at the beginning of the next tick, end the game.
            endGame();
            return;
        }
        if (round % 10 == 0) {
            // Level up sound.
            LEVEL_UP.play();
        }
        score += numFaces;
        scoreViewText.postValue("Score: " + score);
        round++;
        numFaces = Math.min((round - 1) / 10 + 1, 9);
        turnOnFacesForNextRound(numFaces);
    }

    /**
     * End the game.
     */
    private void endGame() {
        gameStarted = false;
        startButtonText.postValue(R.string.start);
        // Reset all faces to off.
        for (int r = 0; r < faceButtons.length; r++) {
            for (int c = 0; c < faceButtons[r].length; c++) {
                turnFaceOff(r, c);
            }
        }
        gameLoop.cancel(); // Stop the game loop.
        FAIL.play(); // Play a sad tone :(
    }

    /**
     * Turns on the specified number of faces randomly.
     *
     * @param numFaces The number of faces to turn on.
     */
    private void turnOnFacesForNextRound(int numFaces) {
        ArrayList<Integer> faceNumbers = new ArrayList<>(); // The faces that have already been turned on.
        while (faceNumbers.size() < numFaces) {
            // While there are still faces to be turned on, turn on a random face if it hasn't already been turned on.
            int num = random.nextInt(9);
            if (!faceNumbers.contains(num)) {
                faceNumbers.add(num);
                turnFaceOn(num / faceButtons.length, num % faceButtons.length);
            }
        }
    }

    /**
     * Checks whether all faces are off.
     *
     * @return Whether all faces are off.
     */
    private boolean areAllFacesOff() {
        for (MutableLiveData<Boolean>[] row : faceStates) {
            for (MutableLiveData<Boolean> state : row) {
                if (state.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}