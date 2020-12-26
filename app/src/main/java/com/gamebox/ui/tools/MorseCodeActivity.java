package com.gamebox.ui.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.gamebox.R;
import com.gamebox.util.ToneGenerator;

public class MorseCodeActivity extends AppCompatActivity {

    private static final String DOT = "·";
    private static final boolean ON = true, OFF = false;
    private ImageButton morseButton;
    private EditText messageInput;
    private String cameraId;
    private CameraManager cameraManager;
    private TextView speedView;
    private int morseSpeed = 200;
    private ToneGenerator dotBeep, dashBeep;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_code_light);

        if (!getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            showError(ErrorTypes.NO_FLASH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                cameraId = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            dotBeep = new ToneGenerator(1000, (double) morseSpeed / 1000.0);
            dashBeep = new ToneGenerator(1000, (double) morseSpeed / 1000.0 * 3);

            speedView = findViewById(R.id.morse_speed_text);
            speedView.setText("Morse speed: " + morseSpeed + " ms");
            SeekBar speedBar = findViewById(R.id.morse_speed);
            speedBar.setMin(10);
            speedBar.setMax(30);
            speedBar.setProgress(morseSpeed / 10);
            speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    morseSpeed = progress * 10;
                    speedView.setText("Morse speed: " + morseSpeed + " ms");

                    dotBeep = new ToneGenerator(1000, (double) morseSpeed / 1000.0);
                    dashBeep = new ToneGenerator(1000, (double) morseSpeed / 1000.0 * 3);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            morseButton = findViewById(R.id.show_morse_code_message);
            messageInput = findViewById(R.id.morse_code_message);

            morseButton.setOnClickListener(v -> {
                String message = messageInput.getText().toString();
                String[] inputSymbols = new String[message.length()];
                for (int i = 0; i < message.length(); i++) {
                    inputSymbols[i] = message.substring(i, i + 1);
                }

                String[] morseSymbols = new String[message.length()];
                for (int i = 0; i < inputSymbols.length; i++) {
                    morseSymbols[i] = symbolToMorse(inputSymbols[i]);
                }

                // TODO: make views disappear and show the current symbol
//                morseButton.setVisibility(View.GONE);
//                messageInput.setVisibility(View.GONE);
//                speedBar.setVisibility(View.GONE);
//                speedView.setVisibility(View.GONE);

                TextView symbolView = findViewById(R.id.morse_input_symbol);
                TextView morseView = findViewById(R.id.morse_code);

                for (int i = 0; i < morseSymbols.length; i++) {
                    String morse = morseSymbols[i];
                    morseView.setText(morse);
                    symbolView.setText(inputSymbols[i].toUpperCase());
                    delay(morseSpeed * 2);
                    flashMorseSymbol(morse);
                }
//                morseView.setText("");
//                symbolView.setText("");
//
//                morseButton.setVisibility(View.VISIBLE);
//                messageInput.setVisibility(View.VISIBLE);
//                speedBar.setVisibility(View.VISIBLE);
//                speedView.setVisibility(View.VISIBLE);
            });
        } else {
            showError(ErrorTypes.WRONG_VERSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashMorseSymbol(String morse) {
        for (int i = 0; i < morse.length(); i++) {
            String symbol = morse.substring(i, i + 1);
            delay(morseSpeed);
            if (DOT.equals(symbol)) {
                dotBeep.play();
                blinkFlashLight(morseSpeed);
            } else {
                dashBeep.play();
                blinkFlashLight(morseSpeed * 3);
            }
        }
    }

    private void delay(int millis) {
        long endTime = System.currentTimeMillis() + millis;
        while (true) {
            if (System.currentTimeMillis() >= endTime) break;
        }
    }

    private String symbolToMorse(String symbol) {
        symbol = symbol.toUpperCase();
        for (MorseCode morseCode : MorseCode.values()) {
            if (morseCode.getSymbol().equals(symbol)) {
                return morseCode.getCode();
            }
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashLight(boolean status) {
        try {
            cameraManager.setTorchMode(cameraId, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void blinkFlashLight(int millis) {
        switchFlashLight(ON);
        delay(millis);
        switchFlashLight(OFF);
    }

    private void showError(ErrorTypes errorType) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Oops!");
        switch (errorType) {
            case NO_FLASH:
                alert.setMessage("Flashlight not available on your device.");
                break;
            case WRONG_VERSION:
                alert.setMessage("You need at least android 8.0 Oreo.");
                break;
        }
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> finish());
        alert.show();
    }

    private enum ErrorTypes {NO_FLASH, WRONG_VERSION}

    public enum MorseCode {
        A("A", "·—"),
        B("B", "—···"),
        C("C", "—·—·"),
        D("D", "—··"),
        E("E", "·"),
        F("F", "··—·"),
        G("G", "——·"),
        H("H", "····"),
        I("I", "··"),
        J("J", "·———"),
        K("K", "—·—"),
        L("L", "·—··"),
        M("M", "——"),
        N("N", "—·"),
        O("O", "———"),
        P("P", "·——·"),
        Q("Q", "——·—"),
        R("R", "·—·"),
        S("S", "···"),
        T("T", "—"),
        U("U", "··—"),
        V("V", "···—"),
        W("W", "·——"),
        X("X", "—··—"),
        Y("Y", "—·——"),
        Z("Z", "——··"),
        ZERO("0", "—————"),
        ONE("1", "·————"),
        TWO("2", "··———"),
        THREE("3", "···——"),
        FOUR("4", "····—"),
        FIVE("5", "·····"),
        SIX("6", "—····"),
        SEVEN("7", "——···"),
        EIGHT("8", "———··"),
        NINE("9", "————·"),
        DOT("·", "·—·—·—"),
        COMMA(",", "——··——"),
        QUESTION("?", "··——··"),
        EXCLAMATION("!", "—·—·——"),
        APOSTROPHE("'", "·————·"),
        QUOTE("\"", "·—··—·"),
        SLASH("/", "—··—·"),
        LEFT_BRACKET("(", "—·——·"),
        RIGHT_BRACKET(")", "—·——·—"),
        COLON(":", "———···"),
        SEMI_COLON(";", "—·—·—·"),
        PLUS("+", "·—·—·"),
        MINUS("—", "—····—"),
        EQUALS("=", "—···—"),
        AND("&", "·—···"),
        AT("@", "·——·—·");


        private final String symbol;
        private final String code;

        MorseCode(String symbol, String code) {
            this.symbol = symbol;
            this.code = code;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getCode() {
            return code;
        }
    }
}