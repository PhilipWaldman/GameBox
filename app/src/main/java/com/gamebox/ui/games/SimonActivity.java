package com.gamebox.ui.games;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.gamebox.R;
import com.gamebox.util.ToneGenerator;

public class SimonActivity extends AppCompatActivity {

    private static final double TONE_DURATION = 0.25;
    private ImageButton red, green, blue, yellow;

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

        red = findViewById(R.id.simon_red_button);
        green = findViewById(R.id.simon_green_button);
        blue = findViewById(R.id.simon_blue_button);
        yellow = findViewById(R.id.simon_yellow_button);

        red.setOnClickListener(v -> {
            ToneGenerator toneGenerator = new ToneGenerator(659.255, TONE_DURATION);
            toneGenerator.play();
            blinkButton(red, "red");
        });

        green.setOnClickListener(v -> {
            ToneGenerator toneGenerator = new ToneGenerator(783.991, TONE_DURATION);
            toneGenerator.play();
            blinkButton(green, "green");
        });

        blue.setOnClickListener(v -> {
            ToneGenerator toneGenerator = new ToneGenerator(391.995, TONE_DURATION);
            toneGenerator.play();
            blinkButton(blue, "blue");
        });

        yellow.setOnClickListener(v -> {
            ToneGenerator toneGenerator = new ToneGenerator(523.251, TONE_DURATION);
            toneGenerator.play();
            blinkButton(yellow, "yellow");
        });
    }

    private void blinkButton(ImageButton button, String color) {
        Thread thread = new Thread(() -> {
            switch (color) {
                case "red":
                    button.setImageResource(R.drawable.ic_simon_red_on);
                    sleep(TONE_DURATION);
                    button.setImageResource(R.drawable.ic_simon_red_off);
                    break;
                case "green":
                    button.setImageResource(R.drawable.ic_simon_green_on);
                    sleep(TONE_DURATION);
                    button.setImageResource(R.drawable.ic_simon_green_off);
                    break;
                case "blue":
                    button.setImageResource(R.drawable.ic_simon_blue_on);
                    sleep(TONE_DURATION);
                    button.setImageResource(R.drawable.ic_simon_blue_off);
                    break;
                case "yellow":
                    button.setImageResource(R.drawable.ic_simon_yellow_on);
                    sleep(TONE_DURATION);
                    button.setImageResource(R.drawable.ic_simon_yellow_off);
                    break;
                default:
                    break;
            }
        });
        thread.start();
    }
}