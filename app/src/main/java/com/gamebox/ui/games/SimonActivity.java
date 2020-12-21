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

    private static final double TONE_DURATION = 0.25;
    private static final int RED = 0, GREEN = 1, BLUE = 2, YELLOW = 3;
    private static final ToneGenerator RED_TONE = new ToneGenerator(659.255, TONE_DURATION),
            GREEN_TONE = new ToneGenerator(783.991, TONE_DURATION),
            BLUE_TONE = new ToneGenerator(391.995, TONE_DURATION),
            YELLOW_TONE = new ToneGenerator(523.251, TONE_DURATION);
    private ImageButton redButton, greenButton, blueButton, yellowButton;
    private Button startButton;
    private ArrayList<Integer> order;
    private Random random;

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

        random = new Random();

        order = new ArrayList<>();

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
            sleep(TONE_DURATION);
        }
    }

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

    private void blink(ImageButton button, int onDrawable, int offDrawable) {
        final Thread thread = new Thread(() -> {
            button.setImageResource(onDrawable);
            sleep(TONE_DURATION);
            button.setImageResource(offDrawable);
        });
        thread.start();
    }
}