package com.gamebox.ui.tools.encryption;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.gamebox.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class SubstitutionCipherActivity extends AppCompatActivity {

    private TextView outputTV, keyErrorTV;
    private Button copyKeyEncryption; // https://developer.android.com/guide/topics/text/copy-paste
    private ImageButton copyEncryption;
    private EditText keyInput;
    private boolean encrypt = true, isValidKey = true;
    private String key = "", message = "", outputMessage = "";
    private HashMap<String, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitution_cipher);

        // Find views
        outputTV = findViewById(R.id.encrypted_message_text_view);
        keyErrorTV = findViewById(R.id.key_error_message);
        SwitchCompat encryptSwitch = findViewById(R.id.encrypt_switch);
        Button clearAll = findViewById(R.id.clear_all_button);
        copyEncryption = findViewById(R.id.copy_output);
        copyKeyEncryption = findViewById(R.id.copy_encryption_button);
        keyInput = findViewById(R.id.key_input);
        EditText messageInput = findViewById(R.id.message_input);
        Button randomKey = findViewById(R.id.random_key);

        // Set change listener for switch
        encryptSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            encrypt = isChecked;
            buttonView.setText(isChecked ? R.string.encrypt : R.string.decrypt);
            updateViews();
        });

        // Add change listener to keyInput
        keyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                key = s.toString();
                generateDictionaryFromKey();
                updateViews();
            }
        });

        // Add change listener to messageInput
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                message = s.toString();
                updateViews();
            }
        });

        // Clear all views
        clearAll.setOnClickListener(v -> {
            keyInput.setText("");
            messageInput.setText("");
        });

        // Generate random key button
        randomKey.setOnClickListener(v -> generateRandomKey());
    }

    private void updateViews() {
        if (encrypt) {
            encrypt();
        } else {
            decrypt();
        }
        outputTV.setText(outputMessage);
    }

    /**
     * Encrypt the message.
     */
    private void encrypt() {
        if (isValidKey) {
            StringBuilder encrypted = new StringBuilder();
            for (int i = 0; i < message.length(); i++) {
                String letter = message.substring(i, i + 1);
                if (dictionary != null && dictionary.containsKey(letter)) {
                    letter = dictionary.get(letter);
                }
                encrypted.append(letter);
            }
            outputMessage = encrypted.toString();
        }
    }

    /**
     * Decrypt the message.
     */
    private void decrypt() {

    }

    /**
     * Generates a dictionary {@code Map} from the {@code String} key.
     */
    private void generateDictionaryFromKey() {
        isValidKey = isValidKey();
        if (isValidKey) {
            dictionary = new HashMap<>();
            for (int i = 0; i < key.length() - 1; i += 2) {
                String from = key.substring(i, i + 1);
                String to = key.substring(i + 1, i + 2);
                // Not case sensitive
                dictionary.put(from.toLowerCase(), to.toLowerCase());
                dictionary.put(from.toUpperCase(), to.toUpperCase());
            }
        }
    }

    /**
     * Checks whether the key is valid. If the key is not valid, the reason will be shown.
     *
     * @return Whether the key is valid.
     */
    private boolean isValidKey() {
        if (key.length() % 2 != 0) {
            String error = "Key length should be even";
            keyErrorTV.setText(error);
            return false;
        }
        HashSet<String> keys = new HashSet<>();
        HashSet<String> vals = new HashSet<>();
        for (int i = 0; i < key.length(); i += 2) {
            if (!keys.add(key.substring(i, i + 1))) {
                String error = "The even places cannot contain duplicate values";
                keyErrorTV.setText(error);
                return false;
            }
            if (i + 1 < key.length() && !vals.add(key.substring(i + 1, i + 2))) {
                String error = "The odd places cannot contain duplicate values";
                keyErrorTV.setText(error);
                return false;
            }
        }
        keyErrorTV.setText("");
        return true;
    }

    private void generateRandomKey() {
        Random rand = new Random();
        StringBuilder newKey = new StringBuilder();

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        ArrayList<Character> keys = new ArrayList<>();
        ArrayList<Character> vals = new ArrayList<>();
        for (char let : alphabet) {
            keys.add(let);
            vals.add(let);
        }
        Collections.shuffle(keys);
        Collections.shuffle(vals);
        for (int i = 0; i < keys.size(); i++) {
            newKey.append(keys.get(i));
            newKey.append(vals.get(i));
        }
        System.out.println(newKey.toString());
        keyInput.setText(newKey.toString());
    }
}