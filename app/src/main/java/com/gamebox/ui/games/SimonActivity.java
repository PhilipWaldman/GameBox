package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.gamebox.R;
import com.gamebox.util.ToneGenerator;

import java.util.ArrayList;
import java.util.Random;

public class SimonActivity extends AppCompatActivity {

    /**
     * The duration of a beep.
     */
    private static final double TONE_DURATION = 0.25;
    // Constants representing the colors.
    private static final int RED = 0, GREEN = 1, BLUE = 2, YELLOW = 3;
    /**
     * The tone for the red button. 659.255 Hz for {@code TONE_DURATION} seconds.
     */
    private static final ToneGenerator RED_TONE = new ToneGenerator(659.255, TONE_DURATION);
    /**
     * The tone for the green button. 783.991 Hz for {@code TONE_DURATION} seconds.
     */
    private static final ToneGenerator GREEN_TONE = new ToneGenerator(783.991, TONE_DURATION);
    /**
     * The tone for the blue button. 391.995 Hz for {@code TONE_DURATION} seconds.
     */
    private static final ToneGenerator BLUE_TONE = new ToneGenerator(391.995, TONE_DURATION);
    /**
     * The tone for the yellow button. 523.251 Hz for {@code TONE_DURATION} seconds.
     */
    private static final ToneGenerator YELLOW_TONE = new ToneGenerator(523.251, TONE_DURATION);
    /**
     * The order in which to play the buttons.
     */
    private final ArrayList<Integer> order = new ArrayList<>();
    private final Random random = new Random();
    // The ImageButtons for the game.
    private ImageButton redButton, greenButton, blueButton, yellowButton;
    /**
     * The start button.
     */
    private Button startButton;

    /**
     * @param duration in seconds
     */
    public static void sleep(double duration) {
        try {
            Thread.sleep((long) (duration * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        // Find the buttons
        redButton = findViewById(R.id.simon_red_button);
        greenButton = findViewById(R.id.simon_green_button);
        blueButton = findViewById(R.id.simon_blue_button);
        yellowButton = findViewById(R.id.simon_yellow_button);

        redButton.setOnClickListener(v -> {
            blinkBeepButton(RED);
        });

        greenButton.setOnClickListener(v -> {
            blinkBeepButton(GREEN);
        });

        blueButton.setOnClickListener(v -> {
            blinkBeepButton(BLUE);
        });

        yellowButton.setOnClickListener(v -> {
            blinkBeepButton(YELLOW);
        });

        // Find the start button
        startButton = findViewById(R.id.simon_start_button);
        startButton.setOnClickListener(v -> {
//            for (int i = 0; i < 3; i++) {
//                playNextRound();
//            }
            for (int i = 0; i < 5; i++) {
                int color = random.nextInt(4);
                blinkBeepButton(color);
            }
        });
    }

    public void playNextRound() {
        order.add(random.nextInt(4));
        sleep(1);
        for (int color : order) {
            blinkBeepButton(color);
            sleep(TONE_DURATION);//TODO: replace with Time thing as in Face Clicker, maybe.
        }
    }

    /**
     * Blinks and beeps the specified button.
     *
     * @param color The color to blink and beep.
     */
    private void blinkBeepButton(int color) {
        switch (color) {
            case RED:
                RED_TONE.play();
                blink(redButton, R.drawable.ic_simon_red_on, R.drawable.ic_simon_red_off);
                break;
            case GREEN:
                GREEN_TONE.play();
                blink(greenButton, R.drawable.ic_simon_green_on, R.drawable.ic_simon_green_off);
                break;
            case BLUE:
                BLUE_TONE.play();
                blink(blueButton, R.drawable.ic_simon_blue_on, R.drawable.ic_simon_blue_off);
                break;
            case YELLOW:
                YELLOW_TONE.play();
                blink(yellowButton, R.drawable.ic_simon_yellow_on, R.drawable.ic_simon_yellow_off);
                break;
            default:
                break;
        }
    }

    /**
     * Turn the button "on" and "off."
     *
     * @param button      The {@code ImageButton} to blink.
     * @param onDrawable  The reference to the drawable when the button is on.
     * @param offDrawable The reference to the drawable when the button is off.
     */
    private void blink(ImageButton button, int onDrawable, int offDrawable) {
        final Thread thread = new Thread(() -> {
            button.setImageResource(onDrawable);
            sleep(TONE_DURATION);
            button.setImageResource(offDrawable);
        });
        thread.start();
    }
}