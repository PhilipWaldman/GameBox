package com.gamebox.ui.demos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gamebox.R;

public class FallDetectAlgoActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * The lower threshold used in the algorithm. In m/s^2.
     */
    private static final float LOWER_THRESHOLD = 0.5f;
    /**
     * The upper threshold used in the algorithm. In m/s^2.
     */
    private static final float UPPER_THRESHOLD = 50f;
    /**
     * The angle threshold used in the algorithm. In degrees.
     */
    private static final int ANGLE_THRESHOLD = 45;
    /**
     * A half a second in ms.
     */
    private static final int HALF_SECOND = 500;
    /**
     * Five seconds in ms.
     */
    private static final int FIVE_SECONDS = 5000;
    private final float[] preFallAcc = new float[3], postFallAcc = new float[3];
    private long freeFallStartTime;
    private long impactTime;
    private long layingOnGroundTime;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Vibrator vibrator;
    private ConstraintLayout bg;
    private TextView xAccView, yAccView, zAccView, totalAccView, minAccView, maxAccView, fallStateView, angleView;
    private float maxAcc = Float.MIN_VALUE, minAcc = Float.MAX_VALUE;
    private double angle = 0;
    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_detect_algo);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        state = State.IDLE;

        bg = findViewById(R.id.fall_detect_bg);
        xAccView = findViewById(R.id.xAcc);
        yAccView = findViewById(R.id.yAcc);
        zAccView = findViewById(R.id.zAcc);
        totalAccView = findViewById(R.id.totalAcc);
        minAccView = findViewById(R.id.minAcc);
        maxAccView = findViewById(R.id.maxAcc);
        fallStateView = findViewById(R.id.fallState);
        angleView = findViewById(R.id.angle);
    }

    /**
     * calculates the angle between two acceleration vectors.
     * if the angle is > 45 degrees the orientation has changed.
     */
    private boolean hasOrientationChanged(float xAcc0, float yAcc0, float zAcc0, float xAcc1, float yAcc1, float zAcc1) {
        float acc0DotAcc1 = xAcc0 * xAcc1 + yAcc0 * yAcc1 + zAcc0 * zAcc1;
        double acc0Length = Math.sqrt(xAcc0 * xAcc0 + yAcc0 * yAcc0 + zAcc0 * zAcc0);
        double acc1Length = Math.sqrt(xAcc1 * xAcc1 + yAcc1 * yAcc1 + zAcc1 * zAcc1);
        angle = Math.toDegrees(Math.acos(acc0DotAcc1 / (acc0Length * acc1Length)));
        return angle > ANGLE_THRESHOLD;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onSensorChanged(SensorEvent event) {
        // ------------------------- Algorithm start -------------------------
        float xAcc = event.values[0];
        float yAcc = event.values[1];
        float zAcc = event.values[2];
        float totalAcc = (float) Math.sqrt(xAcc * xAcc + yAcc * yAcc + zAcc * zAcc);

        long curTime = System.currentTimeMillis();
        switch (state) {
            case IDLE:
                if (totalAcc < LOWER_THRESHOLD) {
                    // Falling
                    freeFallStartTime = curTime;
                    state = State.FREE_FALL;
                    bg.setBackgroundColor(Color.CYAN);
                } else {
                    // Not falling
                    preFallAcc[0] = xAcc;
                    preFallAcc[1] = yAcc;
                    preFallAcc[2] = zAcc;
                    bg.setBackgroundColor(Color.WHITE);
                }
                break;
            case FREE_FALL:
                if (curTime - freeFallStartTime <= HALF_SECOND && totalAcc > UPPER_THRESHOLD) {
                    // Impact
                    state = State.IMPACT;
                    impactTime = curTime;
                    bg.setBackgroundColor(Color.YELLOW);
                } else if (curTime - freeFallStartTime > HALF_SECOND) {
                    // No impact within 0.5 s
                    state = State.IDLE;
                    bg.setBackgroundColor(Color.WHITE);
                }
                break;
            case IMPACT:
                if (curTime - impactTime > HALF_SECOND) {
                    if (hasOrientationChanged(preFallAcc[0], preFallAcc[1], preFallAcc[2], xAcc, yAcc, zAcc)) {
                        // Orientation has changed
                        state = State.AFTERMATH;
                        layingOnGroundTime = curTime;
                        postFallAcc[0] = xAcc;
                        postFallAcc[1] = yAcc;
                        postFallAcc[2] = zAcc;
                        bg.setBackgroundColor(0xFFFFA500);
                    } else {
                        // Orientation has stayed the same
                        state = State.IDLE;
                        bg.setBackgroundColor(Color.WHITE);
                    }
                }
                break;
            case AFTERMATH:
                if (curTime - layingOnGroundTime < FIVE_SECONDS) {
                    // Moved within 10 s of fall
                    if (hasOrientationChanged(postFallAcc[0], postFallAcc[1], postFallAcc[2], xAcc, yAcc, zAcc)) {
                        state = State.IDLE;
                    }
                } else {
                    // Send alarm here
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)); // Feedback for phone
                    } else {
                        for (int i = 0; i < 5; i++) {
                            try {
                                bg.setBackgroundColor(Color.RED);
                                Thread.sleep(100);
                                bg.setBackgroundColor(Color.WHITE);
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    bg.setBackgroundColor(Color.WHITE);
                    state = State.IDLE;
                }
                break;
        }
        // ------------------------- Algorithm end -------------------------

        minAcc = Math.min(totalAcc, minAcc);
        maxAcc = Math.max(totalAcc, maxAcc);

        // Text for app
        xAccView.setText(String.format("a_x = %.5f m/s^2", xAcc));
        yAccView.setText(String.format("a_y = %.5f m/s^2", yAcc));
        zAccView.setText(String.format("a_z = %.5f m/s^2", zAcc));
        totalAccView.setText(String.format("a_tot = %.5f m/s^2", totalAcc));
        minAccView.setText(String.format("a_min = %.5f m/s^2", minAcc));
        maxAccView.setText(String.format("a_max = %.5f m/s^2", maxAcc));
        switch (state) {
            case IDLE:
                fallStateView.setText(R.string.idle);
                break;
            case FREE_FALL:
                fallStateView.setText(R.string.free_fall);
                break;
            case IMPACT:
                fallStateView.setText(R.string.impact);
                break;
            case AFTERMATH:
                fallStateView.setText(R.string.laying_on_ground);
                break;
            default:
                fallStateView.setText("ERROR");
                break;
        }
        angleView.setText(String.format("Δθ = %.5f°", angle));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // SENSOR_DELAY_FASTEST updates every 0 ms
        // SENSOR_DELAY_GAME updates every 20 ms
        // SENSOR_DELAY_UI updates every 60 ms
        // SENSOR_DELAY_NORMAL updates every 200 ms
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void resetValues(View view) {
        maxAcc = Float.MIN_VALUE;
        minAcc = Float.MAX_VALUE;
    }

    public void aboutFallDetectionDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.how_it_works_title)
                .setMessage(R.string.how_it_works_description);
        AlertDialog about = builder.create();
        about.show();
    }

    enum State {IDLE, FREE_FALL, IMPACT, AFTERMATH}
}
